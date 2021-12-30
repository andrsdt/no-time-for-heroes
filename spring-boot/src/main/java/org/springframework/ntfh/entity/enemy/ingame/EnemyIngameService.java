package org.springframework.ntfh.entity.enemy.ingame;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.ntfh.entity.enemy.Enemy;
import org.springframework.ntfh.entity.enemy.EnemyCategoryType;
import org.springframework.ntfh.entity.enemy.EnemyService;
import org.springframework.ntfh.entity.game.Game;
import org.springframework.ntfh.entity.game.GameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andrsdt
 * @author alegestor
 */
@Service
public class EnemyIngameService {

    @Autowired
    private EnemyIngameRepository enemyIngameRepository;

    @Autowired
    private EnemyService enemyService;

    @Transactional
    public Integer enemyIngameCount() {
        return (int) enemyIngameRepository.count();
    }

    @Transactional
    public Iterable<EnemyIngame> findAll() {
        return enemyIngameRepository.findAll();
    }

    @Transactional
    public EnemyIngame findById(Integer id) throws DataAccessException {
        Optional<EnemyIngame> enemyIngame = enemyIngameRepository.findById(id);
        if (!enemyIngame.isPresent())
            throw new DataAccessException("EnemyIngame with id " + id + " was not found") {
            };
        return enemyIngame.get();
    }

    @Transactional
    public EnemyIngame save(EnemyIngame enemyIngame) throws DataAccessException {
        return enemyIngameRepository.save(enemyIngame);
    }

    /**
     * Keep taking enemies from the pile and adding them to the fighting area while
     * there are less than 3
     * 
     * @author @andrsdt
     */
    private void refillTableWithEnemies(Game game) {
        List<EnemyIngame> enemiesInPile = game.getEnemiesInPile();
        List<EnemyIngame> enemiesFighting = game.getEnemiesFighting();

        while (!enemiesInPile.isEmpty() && enemiesFighting.size() < 3) {
            // The game rules tell us that the horde enemy cards have to be taken from the
            // bottom of the pile
            EnemyIngame lastEnemyInPile = enemiesInPile.get(0);
            enemiesInPile.remove(lastEnemyInPile);
            enemiesFighting.add(lastEnemyInPile);
        }
        // TODO do we have to .save() something as we were doing before?
    }

    @Transactional
    public void initializeFromGame(Game game) {
        initializeHordeEnemies(game);
        initializeWarlord(game);
        refillTableWithEnemies(game);
    }

    /**
     * Given a game, create the initial horde enemies. These will be random and the
     * number of enemies will depend on the number of players
     * 
     * @author andrsdt
     * @param game that the horde enemies will be created for
     * @return
     */
    private void initializeHordeEnemies(Game game) {
        Integer numPlayers = game.getPlayers().size();
        Map<Integer, Integer> numEnemies = Map.of(
                2, 19, // 19 horde enemies for 2 players
                3, 23, // 23 horde enemies for 3 players
                4, 27);// 27 horde enemies for 4 players

        List<Enemy> allHordeEnemies = enemyService.findByEnemyCategoryType(EnemyCategoryType.HORDE);

        Collections.shuffle(allHordeEnemies);

        List<EnemyIngame> hordeEnemiesIngame = allHordeEnemies.stream()
                .limit(numEnemies.get(numPlayers))
                .map(hordeEnemy -> createFromEnemy(hordeEnemy, game)) // And create the DB row of each one
                .collect(Collectors.toList());

        game.getEnemiesInPile().addAll(hordeEnemiesIngame);

    }

    private void initializeWarlord(Game game) {
        List<Enemy> allWarlords = enemyService.findByEnemyCategoryType(EnemyCategoryType.WARLORD);
        Collections.shuffle(allWarlords);
        Enemy randomWarlord = allWarlords.get(0);
        EnemyIngame warlordEntity = createFromEnemy(randomWarlord, game);
        game.getEnemiesInPile().add(warlordEntity);
    }

    @Transactional
    public EnemyIngame createFromEnemy(Enemy enemy, Game game) {
        EnemyIngame enemyIngame = new EnemyIngame();
        enemyIngame.setEnemy(enemy);
        enemyIngame.setCurrentEndurance(enemy.getEndurance());
        return this.save(enemyIngame);
    }
}