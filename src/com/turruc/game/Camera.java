package com.turruc.game;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.game.entities.EntityType;
import com.turruc.game.entities.GameObject;

public class Camera {
	private float offX, offY;
	private EntityType targetTag;
	private GameObject target = null;
	private int camSpeed = 7;
	private float normalScrollSpeed = (float) 2;
	private float slowScrollSpeed = normalScrollSpeed / GameObject.slowMotion;
	private float scrollSpeed = normalScrollSpeed;

	public Camera(EntityType tag) {
		this.targetTag = tag;
	}

	public void update(GameContainer gc, GameManager gm, float dt) {
		if (target == null) {
			target = gm.getObject(targetTag);
		}

		if (target == null) {
			return;
		}

		if (gm.getPlayer().isSlow()) {
			scrollSpeed = slowScrollSpeed;
		} else {
			scrollSpeed = normalScrollSpeed;
		}

		// float targetX = (target.getPosX() + target.getWidth() / 2) - gc.getWidth() / 2;
		float targetY = (target.getPosY() + target.getHeight() / 2) - gc.getHeight() / 2;

		// offX -= dt * (int) (offX - targetX) * camSpeed;
		offX += scrollSpeed;
		offY -= dt * (int) (offY - targetY) * camSpeed;

		if (offX < 0) {
			offX = 0;
		}

		if (offY < 0) {
			offY = 0;
		}

		if (offX + gc.getWidth() > gm.getLevelW() * GameManager.TS) {
			offX = gm.getLevelW() * GameManager.TS - gc.getWidth();
		}

		if (offY + gc.getHeight() > gm.getLevelH() * GameManager.TS) {
			offY = gm.getLevelH() * GameManager.TS - gc.getHeight();
		}

	}

	public void render(Renderer r) {
		r.setCamX((int) offX);
		r.setCamY((int) offY);
	}

	public float getOffX() {
		return offX;
	}

	public void setOffX(float offX) {
		this.offX = offX;
	}

	public float getOffY() {
		return offY;
	}

	public void setOffY(float offY) {
		this.offY = offY;
	}

	public EntityType getTargetTag() {
		return targetTag;
	}

	public void setTargetTag(EntityType targetTag) {
		this.targetTag = targetTag;
	}

	public GameObject getTarget() {
		return target;
	}

	public void setTarget(GameObject target) {
		this.target = target;
	}
}
