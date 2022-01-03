package org.springframework.ntfh.cardlogic.abilitycard.rogue;

import org.springframework.ntfh.command.DealDamageCommand;
import org.springframework.ntfh.command.DiscardCommand;
import org.springframework.ntfh.command.GoldOnKillCommand;
import org.springframework.ntfh.entity.enemy.ingame.EnemyIngame;
import org.springframework.ntfh.entity.player.Player;
import org.springframework.stereotype.Component;


@Component
public class AlCorazon {
    public void execute(Player playerFrom, EnemyIngame targetedEnemy) {
        new DealDamageCommand(4, targetedEnemy).execute();
        new GoldOnKillCommand(1, targetedEnemy, playerFrom).execute();
//TODO        falta la implementación de que la condición del oro se ejecuta solo una vez por ronda
        new DiscardCommand(1, playerFrom).execute();
    }
}