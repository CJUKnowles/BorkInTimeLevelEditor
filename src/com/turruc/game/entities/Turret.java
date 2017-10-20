package com.turruc.game.entities;

import java.util.Random;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.engine.audio.SoundClip;
import com.turruc.engine.gfx.Image;
import com.turruc.game.GameManager;

public class Turret extends GameObject {
	private Image turret = new Image("/turret.png");
	private Image turretHead = new Image("/turretHead.png");

	private float targetX;
	private float targetY;

	private int tileX, tileY;
	private float offX, offY;
	private int normalFireRate = 70;
	private int slowFireRate = normalFireRate * slowMotion;
	private int fireRate = 70; // Smaller = faster firerate //70
	private int timeUntilNextShot = fireRate;
	private GameManager gm;
	private double angle = 0;
	private double angle2;
	private double targetAngle;
	private int range = 60; // 5 per tile roughly
	private double normalTurnSpeed = 5;
	private double slowTurnSpeed = normalTurnSpeed / slowMotion;
	private double turnSpeed = normalTurnSpeed;

	private boolean canShoot = false;
	private int accuracy = 10; //the closer to zero, the more on target the turret has to be before it fires
	
	private int manaReward = 20;
	
	private SoundClip pew;
	private SoundClip boof;
	
	public Turret(GameManager gm, int posX, int posY) {
		Random random = new Random();
		normalFireRate += random.nextInt(20) - 10;
		this.tag = EntityType.turret;
		this.tileX = posX;
		this.tileY = posY;
		this.offX = 0;
		this.offY = 0;
		this.posX = posX * GameManager.TS;
		this.posY = posY * GameManager.TS;
		this.width = 32;
		this.height = 32;
		this.gm = gm;
		
		pew = new SoundClip("/audio/pew.wav");
		boof = new SoundClip("/audio/boof.wav");
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {

		
	}

	public boolean checkLOS(float x0, float y0, float x1, float y1) {
		LOSBullet check = new LOSBullet((int) targetX, (int) targetY, tileX, tileY, offX, offY, GameManager.getGc(), gm, GameManager.getGc().getDt(), range, true, false);
		return check.LOS;
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawImage(r.transformImage(turret.getBufferedImage(), (int) angle2), (int) posX, (int) posY);
		r.drawImage(r.transformImage(turretHead.getBufferedImage(), (int) Math.toDegrees(angle)), (int) posX, (int) posY);
	}

	public void setDead(boolean dead) {
		gm.getCollision()[(((int) this.tileY) * gm.getLevelW()) + (int) this.tileX] = 0;
		this.dead = dead;
		boof.play();
	}

}
