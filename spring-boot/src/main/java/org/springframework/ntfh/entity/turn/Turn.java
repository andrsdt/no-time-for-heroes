package org.springframework.ntfh.entity.turn;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.ntfh.entity.game.Game;
import org.springframework.ntfh.entity.model.BaseEntity;
import org.springframework.ntfh.entity.player.Player;
import org.springframework.ntfh.entity.scene.Scene;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Turn extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "game_id")
    @JsonIgnoreProperties({ "startTime" })
    private Game game;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnoreProperties({ "glory", "kills", "gold", "wounds", "user", "characterType" })
    private Player player;

    @ManyToOne(optional = true)
    @JoinColumn(name = "scene_id")
    private Scene currentScene;
}