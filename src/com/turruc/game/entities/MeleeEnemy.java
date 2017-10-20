package com.turruc.game.entities;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.engine.audio.SoundClip;
import com.turruc.engine.gfx.ImageTile;
import com.turruc.game.GameManager;

public class MeleeEnemy extends GameObject {
	private ImageTile meleeEnemy = new ImageTile("/player.png", 32, 32);
	private int padding, paddingTop;

	private int direction = 1;
	private float anim = 0;
	private int tileX, tileY;
	private float offX, offY;

	private float normalSpeed = 100;
	private float slowSpeed = normalSpeed / slowMotion;
	private float speed = normalSpeed;

	private float fallDistance = 0;
	private float normalFallSpeed = 20;
	private float slowFallSpeed = normalFallSpeed / slowMotion;
	private float fallSpeed = normalFallSpeed;
	private float jump = -9; // must be negative
	private boolean ground = false;
	private boolean groundLast = false;

	private int maxHealth = 100;
	private int health = maxHealth;

	private int normalAnimationSpeed = 10;
	private int slowAnimationSpeed = normalAnimationSpeed / slowMotion;
	private int animationSpeed = normalAnimationSpeed;

	private double lastTimeLavaDamage;
	private int lavaDamageCooldown = 1;
	private int lavaDamage = 10;

	private boolean againstWall = false;

	private double lastTimeDamage;
	private int damageCooldown = 1;
	private int damage = 25;

	private boolean attacking = false;
	private float attackAnim = 4;

	private SoundClip ugh;
	private SoundClip boof;


	private int manaReward = 20;
	private int range = 60;

	public MeleeEnemy(GameManager gm, int posX, int posY) {
		this.tag = EntityType.meleeEnemy;
		this.tileX = posX;
		this.tileY = posY;
		this.offX = 0;
		this.offY = 0;
		this.posX = posX * GameManager.TS;
		this.posY = posY * GameManager.TS;
		this.width = 32;
		this.height = 32;

		this.padding = 4;
		this.paddingTop = 0;


		ugh = new SoundClip("/audio/ugh.wav");
		boof = new SoundClip("/audio/boof.wav");
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
		this.offX = (int) (posX % GameManager.TS);
		this.offY = (int) (posY % GameManager.TS);
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

}
