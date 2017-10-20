package com.turruc.game.entities;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.game.GameManager;

public class CameraFollow extends GameObject {

	private float anim = 0;
	private int normalAnimationSpeed = 10;
	private int animationSpeed = normalAnimationSpeed;

	private int tileX;
	private int tileY;

	public CameraFollow(int tileX, int tileY) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.posX = tileX * GameManager.TS;
		this.posY = tileY * GameManager.TS;

		this.tag = EntityType.CameraFollow;
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		tileX += 10;
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {}

	public void setDead(boolean dead) {}
		
}
