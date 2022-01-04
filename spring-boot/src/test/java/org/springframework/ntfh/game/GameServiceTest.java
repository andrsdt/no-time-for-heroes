package org.springframework.ntfh.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.ntfh.command.DealDamageCommand;
import org.springframework.ntfh.command.GoldOnKillCommand;
import org.springframework.ntfh.command.ReturnedToAbilityPileCommand;
import org.springframework.ntfh.entity.character.CharacterService;
import org.springframework.ntfh.entity.enemy.EnemyService;
import org.springframework.ntfh.entity.enemy.ingame.EnemyIngame;
import org.springframework.ntfh.entity.enemy.ingame.EnemyIngameService;
import org.springframework.ntfh.entity.game.Game;
import org.springframework.ntfh.entity.game.GameRepository;
import org.springframework.ntfh.entity.game.GameService;
import org.springframework.ntfh.entity.lobby.Lobby;
import org.springframework.ntfh.entity.lobby.LobbyService;
import org.springframework.ntfh.entity.playablecard.abilitycard.ingame.AbilityCardIngameService;
import org.springframework.ntfh.entity.playablecard.marketcard.MarketCardService;
import org.springframework.ntfh.entity.playablecard.marketcard.ingame.MarketCardIngame;
import org.springframework.ntfh.entity.playablecard.marketcard.ingame.MarketCardIngameService;
import org.springframework.ntfh.entity.player.Player;
import org.springframework.ntfh.entity.turn.TurnService;
import org.springframework.ntfh.entity.turn.concretestates.MarketState;
import org.springframework.ntfh.entity.turn.concretestates.PlayerState;
import org.springframework.ntfh.entity.user.User;
import org.springframework.ntfh.entity.user.UserService;
import org.springframework.ntfh.util.TokenUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Import({ BCryptPasswordEncoder.class, PlayerState.class, MarketState.class })
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private LobbyService lobbyService;

    @Autowired
    private UserService userService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private MarketCardService marketCardService;

    @Autowired
    private MarketCardIngameService marketCardIngameService;

    @Autowired
    private TurnService turnService;

    @Autowired
    private AbilityCardIngameService abilityCardIngameService;

    @Autowired
    private EnemyService enemyService;
    
    @Autowired
    private EnemyIngameService enemyIngameService;

    private Game gameTester;

    protected Lobby lobbyTester;

    private Player playerTester;

    @BeforeEach
    public void init() {
        User user1 = userService.findUser("user1");
        User user2 = userService.findUser("user2");
        Set<User> users = Sets.newSet(user1, user2);

        lobbyTester = new Lobby();
        lobbyTester.setName("Init lobby");
        lobbyTester.setHasScenes(true);
        lobbyTester.setSpectatorsAllowed(false);
        lobbyTester.setMaxPlayers(4);
        lobbyTester.setUsers(users);
        lobbyTester.setHost(user1);
        lobbyTester.setLeader(user1);
        lobbyService.save(lobbyTester);

        user1.setCharacter(characterService.findCharacterById(2).get());
        user2.setCharacter(characterService.findCharacterById(4).get());
        
        gameTester = gameService.createFromLobby(lobbyTester);
        user1.setLobby(lobbyTester);
        playerTester = gameTester.getPlayers().get(0);
    }

    @AfterEach
    public void teardown() {
        gameService.delete(gameTester);
    }

    @Test
    public void testCountWithInitialData() {
        Integer count = gameService.gameCount();
        assertEquals(4, count);
    }

    @Test
    public void testfindAll() {
        Integer count = Lists.newArrayList(gameService.findAll()).size();
        assertEquals(4, count);
    }

    // H1+
    @Test
    public void testfindAllListVersion_Success() {
        List<Game> gamesServiceList = new ArrayList<>();
        gameService.findAll().forEach(g -> gamesServiceList.add(g));

        assertEquals(false, gamesServiceList.isEmpty());
    }

    @Test
    public void testFindById() {
        Game tester = this.gameService.findGameById(1);
        assertEquals(true, tester.getHasScenes());
        assertEquals(1, tester.getLeader().getId());
    }

    @Test
    public void testCreateFromLobby() {
        Game tester = gameService.createFromLobby(lobbyTester);
        assertEquals(gameService.findGameById(tester.getId()).getId(), tester.getId());
        gameService.delete(tester);
    }

    // H7 - E1
    @Test
    public void testCreateFromLobbyNotEnoughPlayers() {
        User user2 = userService.findUser("user2");
        lobbyTester.removeUser(user2);
        assertThrows(IllegalArgumentException.class, () -> gameService.createFromLobby(lobbyTester));
    }

    @Test
    public void testSaveGame_success() {
        // Test made in the init
        assertEquals(gameRepository.findById(gameTester.getId()).get().getId(), gameTester.getId());
    }

    @Test
    public void testDeleteGame() {
        Game tester = gameService.createFromLobby(lobbyTester);
        gameService.delete(tester);
        assertThrows(DataAccessException.class, () -> {
            gameService.findGameById(tester.getId());
        });
    }

    // H24 + E1
    @Test
    void testRestorePlayerHand() {
        turnService.initializeFromGame(gameTester);
        abilityCardIngameService.refillHandWithCards(playerTester);
        new ReturnedToAbilityPileCommand(playerTester, playerTester.getHand().get(0)).execute();;
        assertEquals(3, playerTester.getHand().size());
        abilityCardIngameService.refillHandWithCards(playerTester);
        assertEquals(4, playerTester.getHand().size());
    }

    // H25 + E1
    @Test
    void testRegularBountyCollection() {
        EnemyIngame enemyIngame = enemyIngameService.createFromEnemy(enemyService.findEnemyById(3).get(), gameTester);
        new DealDamageCommand(8000, enemyIngame).execute();
        new GoldOnKillCommand(enemyIngame.getEnemy().getGold(), enemyIngame, playerTester).execute();
        assertEquals(1, playerTester.getGold());
    }

    // H26 + E1
    @Test
    void testBuyMarketCard_Success() {
        MarketCardIngame marketCardIngame = marketCardIngameService.createFromMarketCard(marketCardService.findMarketCardById(3).get(), gameTester);
        Integer marketCardIngameId = marketCardIngame.getId();
        playerTester.setGold(10);
        String playerToken = TokenUtils.generateJWTToken(playerTester.getUser());
        turnService.initializeFromGame(gameTester);
        gameService.setNextTurnState(gameService.getCurrentTurnByGameId(gameTester.getId()));
        gameService.buyMarketCard(marketCardIngameId, playerToken);

        assertEquals(2, playerTester.getGold());
    }

    // H26 - E1
    @Test
    void testBuyMarketCard_Failure() {
        MarketCardIngame marketCardIngame = marketCardIngameService.createFromMarketCard(marketCardService.findMarketCardById(3).get(), gameTester);
        Integer marketCardIngameId = marketCardIngame.getId();
        playerTester.setGold(4);
        String playerToken = TokenUtils.generateJWTToken(playerTester.getUser());
        turnService.initializeFromGame(gameTester);
        gameService.setNextTurnState(gameService.getCurrentTurnByGameId(gameTester.getId()));
        try {
            gameService.buyMarketCard(marketCardIngameId, playerToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(Exception.class, () -> {gameService.buyMarketCard(marketCardIngameId, playerToken);});
    }

}
