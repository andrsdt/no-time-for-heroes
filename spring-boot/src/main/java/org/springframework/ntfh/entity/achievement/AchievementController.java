package org.springframework.ntfh.entity.achievement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ntfh.entity.game.Game;
import org.springframework.ntfh.entity.statistics.StatisticsService;
import org.springframework.ntfh.entity.user.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Achievement> getAll() {
        return this.achievementService.findAll();
    }

    @GetMapping("types")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<AchievementType> getTypes() {
        // ! PROVISIONAL PLACE FOR TESTING STATISTICS
        Game longest = statisticsService.longestGame();
        Game shortest = statisticsService.shortestGame();
        List<User> fiveMoreWon = statisticsService.rankingByWonGames();
        List<Object> fiveMoreKills = statisticsService.rankingByTotalKills();
        List<Object> fiveMoreGlory = statisticsService.rankingByTotalGlory();
        Double averageNumPlayers = statisticsService.averagePlayersPerGame();

        return this.achievementService.findAllTypes();
    }

    @GetMapping("{achievementId}")
    @ResponseStatus(HttpStatus.OK)
    public Achievement getAchivementById(@PathVariable("achievementId") Integer id) {
        return this.achievementService.findById(id);
    }

    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAchievement(@RequestBody Achievement achievement) {
        achievementService.createAchievement(achievement);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void updateAchievement(@RequestBody Achievement achievement, @RequestHeader("Authorization") String token) {
        achievementService.updateAchievement(achievement, token);
    }

    @DeleteMapping("{achievementId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAchievement(@PathVariable("achievementId") Achievement achievement) {
        achievementService.delete(achievement);
    }
}
