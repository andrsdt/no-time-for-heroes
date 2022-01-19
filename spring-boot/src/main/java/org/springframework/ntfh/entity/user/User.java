package org.springframework.ntfh.entity.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.hibernate.validator.constraints.Length;
import org.springframework.ntfh.entity.user.authorities.Authorities;
import org.springframework.ntfh.entity.player.Player;
import org.springframework.ntfh.entity.game.Game;

import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.Setter;

/**
 * @author andrsdt
 */
@Getter
@Setter
@Entity
@Audited
@Table(name = "users")
public class User {
	@Id
	@NotBlank(message = "Username is required")
	@Length(min = 4, max = 20, message = "Your username must be 4-20 characters long")
	private String username;

	@Length(min = 4, message = "Your password must be at least 4 characters long")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Column(unique = true)
	@NotBlank(message = "The email must not be empty")
	@Email(message = "Please provide a valid email")
	private String email;

	@NotNull
	@Column(columnDefinition = "boolean default true")
	private Boolean enabled; // If a user gets banned, he/she will get disabled

	// TODO check if this is making some sense
	// @ManyToOne
	// @JoinColumn(name = "game")
	// @JsonIgnoreProperties({"players", "winner", "leader"})
	// private Game game;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "player")
	private Player player;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnoreProperties({"user"})
	private Set<Authorities> authorities;
}

