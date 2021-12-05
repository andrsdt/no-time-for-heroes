package org.springframework.samples.ntfh.enemy;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.samples.ntfh.model.BaseEntity;

import lombok.Getter;

/**
 * Entity that contains base information about an enemy. Not concrete to any
 * game. This will be used to instantiate the initial enemies ingame.
 * 
 * @author andrsdt
 */
@Getter
@Entity
@Table(name = "horde_enemies")
public class HordeEnemy extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "horde_enemy_type_enum")
    private HordeEnemyType hordeEnemyType;

    @NotNull
    private Integer gold;

    @NotNull
    private Integer extraGlory;

    /**
     * Derived. Return the route to the image of an enemy. Can be obtained by
     * knowing the HordeEnemyType and, in case of the warrior, also the loot behind
     * the card
     * 
     * @author andrsdt
     * @return route to the card image
     */
    @Transient
    public String getFrontImage() {
        // WarlordType.GURDRUG -> gurdrug
        HordeEnemyTypeEnum type = hordeEnemyType.getHordeEnemyTypeEnum();
        String hordeEnemyName = type.toString().toLowerCase();

        // The warrior with extraGlory and gold has a different image
        if ((type.equals(HordeEnemyTypeEnum.WARRIOR)) && (extraGlory != null || gold != null))
            hordeEnemyName = "warrior_with_loot";

        return "/cards/enemies/horde_enemies/fronts/" + hordeEnemyName + ".png";
    }

    /**
     * Derived. Return the route to the standard back image of a HordeEnemy.
     * 
     * @author andrsdt
     * @return String route to the card's back image
     */
    @Transient
    public String getBackImage() {
        return String.format("/cards/enemies/horde_enemies/backs/%dgold_%dglory.png", gold, extraGlory);
    }

    @Transient
    public Integer getEndurance() {
        return hordeEnemyType.getEndurance();
    }
}
