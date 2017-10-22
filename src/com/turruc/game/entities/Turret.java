package com.turruc.game.entities;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.engine.audio.SoundClip;
import com.turruc.engine.gfx.Image;
import com.turruc.game.GameManager;

public class Turret extends GameObject {
	private Image turret = new Image("/turret.png");
	private Image turretHead = new Image("/turretHead.png");

	private GameManager gm;
	private double angle = 0;
	private double angle2;

	private static SoundClip boof;

	public Turret(GameManager gm, int posX, int posY) {
		this.tag = EntityType.turret;
		tileX = posX;
		tileY = posY;
		this.posX = posX * GameManager.TS;
		this.posY = posY * GameManager.TS;
		this.width = 32;
		this.height = 32;
		this.gm = gm;

		if(boof == null)
			boof = new SoundClip("/audio/boof.wav");
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		if (gm.getCollisionNum(tileX, tileY + 1) == 1) { // below
			angle2 = 0;
		} else if (gm.getCollisionNum(tileX + 1, tileY) == 1) { // right
			angle2 = 270;
		} else if (gm.getCollisionNum(tileX - 1, tileY) == 1) { // left
			angle2 = 90;
		} else if (gm.getCollisionNum(tileX, tileY - 1) == 1) { // above
			angle2 = 180;
		}

	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawImage(r.transformImage(turret.getBufferedImage(), (int) angle2), (int) posX, (int) posY);
		r.drawImage(r.transformImage(turretHead.getBufferedImage(), (int) Math.toDegrees(angle)), (int) posX, (int) posY);
	}

	public void setDead(boolean dead) {
		gm.getCollision()[ (this.tileY * gm.getLevelW()) + (int) this.tileX] = 0;
		this.dead = dead;
		boof.play();
	}

	public float getTileX() {
		return tileX;
	}

	public float getTileY() {
		return tileY;
	}


}
