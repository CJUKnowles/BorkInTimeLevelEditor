package com.turruc.game.entities;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.engine.audio.SoundClip;
import com.turruc.engine.gfx.ImageTile;
import com.turruc.game.GameManager;

public class MeleeEnemy extends GameObject {
	private ImageTile meleeEnemy = new ImageTile("/player.png", 32, 32);

	private int direction = 1;
	private float anim = 0;

	private int maxHealth = 100;
	private int health = maxHealth;

	private boolean attacking = false;
	private float attackAnim = 4;

	private SoundClip ugh;

	public MeleeEnemy(GameManager gm, int posX, int posY) {
		this.tag = EntityType.meleeEnemy;
		super.tileX = posX;
		super.tileY = posY;
		this.posX = posX * GameManager.TS;
		this.posY = posY * GameManager.TS;
		this.width = 32;
		this.height = 32;

		ugh = new SoundClip("/audio/ugh.wav");
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {

	}

	public void hit(int damage) {
		health -= damage;
		if (health < 0) {
			health = 0;
		}
		ugh.play();
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		if (attacking) {
			r.drawImageTile(meleeEnemy, (int) posX, (int) posY, (int) attackAnim, direction);
		} else {
			r.drawImageTile(meleeEnemy, (int) posX, (int) posY, (int) anim, direction);
		}

		// health
		r.drawFillRect((int) posX, (int) posY - 9, this.width, 5, 0xbb000000);
		r.drawFillRect((int) posX + 1, (int) posY + 1 - 9, (int) ((float) (this.width - 2) * ((float) health / (float) maxHealth)), 3, 0xbbff0000);
		// end of health
	}

	public void moveTo(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
		this.tileX = (int) (posX / GameManager.TS);
		this.tileY = (int) (posY / GameManager.TS);
	}

	public void setDead(boolean dead) {
		this.dead = dead;
		ugh.play();
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getTileX() {
		return tileX;
	}

	public float getTileY() {
		return tileY;
	}

}
