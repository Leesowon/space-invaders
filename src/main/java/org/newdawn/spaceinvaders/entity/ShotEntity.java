package org.newdawn.spaceinvaders.entity;

import org.newdawn.spaceinvaders.Game;
import org.newdawn.spaceinvaders.configuration.GameConfig;

/**
 * An entity representing a shot fired by the player's ship
 *
 * @author Kevin Glass
 */
public class ShotEntity extends Entity {
	/** The vertical speed at which the players shot moves */
	private double moveSpeed = -300;
	/** The game in which this entity exists */
	private Game game;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;

	private int power;
	public boolean isShip;
	public boolean isSkill;

	/**
	 * Create a new shot from the player
	 *
	 * @param game   The game in which the shot has been created
	 * @param sprite The sprite representing this shot
	 * @param x      The initial x location of the shot
	 * @param y      The initial y location of the shot
	 */
//	public ShotEntity(Game game, String sprite, int x, int y, int power, double direction) {
//
//		super(sprite, x, y);
//
//		this.game = game;
//
//		dy = moveSpeed * direction;
//
//		this.power = power;
//
//
//	}
	public ShotEntity(Game game, GameConfig gameConfig, String shotRef, boolean isShip, int x, int y, boolean isSkill) {
		super(shotRef, x, y);
		if (isShip) {
			if (isSkill) {
				this.moveSpeed = gameConfig.getShipShotMoveSpeed() * 1.2;
			} else {
				this.moveSpeed = gameConfig.getShipShotMoveSpeed();
			}
			this.power = gameConfig.getShipPower();
		} else {
			this.moveSpeed = gameConfig.getAlienShotMoveSpeed();
			this.power = gameConfig.getAlienPower();
		}
		this.isShip = isShip;
		this.isSkill = isSkill;
		this.game = game;

		dy = moveSpeed;



	}

	/**
	 * Request that this shot moved based on time elapsed
	 *
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// proceed with normal move
		super.move(delta);

		// if we shot off the screen, remove ourselfs
		if (y < -100) {
			game.removeEntity(this);
		}
	}

	/**
	 * Notification that this shot has collided with another
	 * entity
	 *
	 * @parma other The other entity with which we've collided
	 */
	public void collidedWith(Entity other) {
		// prevents double kills, if we've already hit something,
		// don't collide
		if(!(other instanceof AlienEntity)){ return; }
		if (used) {
			return;
		}
		if (!(other instanceof ShotEntity)) {
			if (!isSkill) {
				game.removeEntity(this);
			}
			used = true;
		}
	}

	public int attack() {
		return this.power;
	}
}