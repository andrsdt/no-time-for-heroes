package org.springframework.ntfh.cardlogic.abilitycard.wizard;

import org.springframework.ntfh.command.DealDamageCommand;
import org.springframework.ntfh.entity.enemy.ingame.EnemyIngame;
import org.springframework.ntfh.entity.game.Game;
import org.springframework.ntfh.entity.playablecard.abilitycard.AbilityCardTypeEnum;
import org.springframework.ntfh.entity.player.Player;
import org.springframework.stereotype.Component;

/**
 * Daño: 1
 * Si ya utilizaste "Golpe de bastón" contra el mismo enemigo, el daño de esta
 * carta es 2.
 * 
 * @author Pablosancval
 */
@Component
public class GolpeDeBaston {
    public void execute(Player playerFrom, EnemyIngame targetedEnemy){
        

        if(targetedEnemy.getPlayedCardsOnMeInTurn().contains(AbilityCardTypeEnum.GOLPE_DE_BASTON)){
            new DealDamageCommand(2, targetedEnemy).execute();
        }else{
            new DealDamageCommand(1, targetedEnemy).execute();
        }

        Game game = playerFrom.getGame();
        
        targetedEnemy.getPlayedCardsOnMeInTurn().add(AbilityCardTypeEnum.GOLPE_DE_BASTON);
    }
}
