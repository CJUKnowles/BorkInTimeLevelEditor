package com.turruc.game.entities;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.game.GameManager;

public abstract class GameObject {
	protected EntityType tag;
	protected float posX, posY;
	protected int width, height;
	protected boolean dead = false;
	public static int slowMotion = 8;
	
	public abstract void update(GameContainer gc, GameManager gm, float dt);

	public abstract void render(GameContainer gc, Renderer r);

	public EntityType getTag() {
		return tag;
	}

	public void setTag(EntityType tag) {
		this.tag = tag;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
