package com.turruc.game.entities;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.engine.gfx.Image;
import com.turruc.game.GameManager;

public class Bullet extends GameObject {
	private Image bullet = new Image("/bullet.png");
	
	
	private int tileX, tileY;
	private float offX, offY;
	private float normalSpeed = 400; // 400
	private float slowSpeed = normalSpeed / (float) slowMotion;
	private float speed = 400;
	private int size = 8; // width/height of bullet
	double xVelocity;
	double yVelocity;
	
	private double angle = 0;
	private double angle2 = 0;

	public Bullet(int mouseX, int mouseY, int tileX, int tileY, float offX, float offY) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.offX = offX + GameManager.TS / 2 - size / 2;
		this.offY = offY + GameManager.TS / 2 - size / 2;
		this.tag = EntityType.bullet;
		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;
		
		angle = Math.atan2(mouseX - posX, mouseY - posY);
		angle2 = Math.atan2(mouseY - posY, mouseX - posX);
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		
		
		if (gm.getPlayer().isSlow()) {
			speed = slowSpeed;
		} else {
			speed = normalSpeed;
		}
		
		this.xVelocity = speed * Math.cos(angle);
		this.yVelocity = speed * Math.sin(angle);
		
		offY += xVelocity * dt;
		offX += yVelocity * dt;

		// Final Position
		if (offY > GameManager.TS / (GameManager.TS / size)) {
			tileY++;
			offY -= GameManager.TS;
		}

		if (offY < -GameManager.TS / (GameManager.TS / size)) {
			tileY--;
			offY += GameManager.TS;
		}

		if (offX >  GameManager.TS / (GameManager.TS / size)) {
			tileX++;
			offX -= GameManager.TS;
		}

		if (offX < -GameManager.TS / (GameManager.TS / size)) {
			tileX--;
			offX += GameManager.TS;
		}

		if (gm.getCollision(tileX, tileY)) {
			this.dead = true;

			if (gm.getCollisionNum(tileX, tileY) == 2) {
				for (int i = 0; i < GameManager.getObjects().size(); i++) {
					if (GameManager.getObjects().get(i).getTag().equals(EntityType.turret)) {
						if (Math.abs(posX - GameManager.getObjects().get(i).getPosX()) <= 32 && Math.abs(posY - GameManager.getObjects().get(i).getPosY()) <= 32) {
							GameManager.getObjects().get(i).setDead(true);
							break;
							// i = gm.getObjects().size();
						}
					}
				}
			}
		}

		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawImage(r.transformImage(bullet.getBufferedImage(), (int) Math.toDegrees(angle2)), (int) posX, (int) posY);
	}

}
