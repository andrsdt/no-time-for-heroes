package org.springframework.samples.ntfh.enemy.ingame;

/**
 * Applicable for both HordeEnemyIngame and WardlordIngame entities in principle
 */
public enum EnemyLocation {
    // TODO this could be a good chance to apply the state design pattern, since the
    // functionality of the card is different depending on its location (state)

    /** The enemy is still waiting to fight */
    PILE,

    /** The enemy is currently fighting the player(s) */
    FIGHTING,

    /** The enemy has been slayed and can no longer appear */
    EXILED
}
