package com.turruc.game.entities;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.engine.gfx.ImageTile;
import com.turruc.game.GameManager;

public class Lava extends GameObject {

	private int type = 0; // 0 surface, 1 surrounded
	private Player player;
	private float anim = 0;
	private int normalAnimationSpeed = 10;
	private int slowAnimationSpeed = normalAnimationSpeed / slowMotion;
	private int animationSpeed = normalAnimationSpeed;
	private ImageTile image = new ImageTile("/lava.png", 32, 32);

	private int tileX;
	private int tileY;

	public Lava(GameManager gm, int tileX, int tileY) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.posX = tileX * GameManager.TS;
		this.posY = tileY * GameManager.TS;
		this.player = gm.getPlayer();
		this.tag = EntityType.lava;

		if (gm.getCollisionNum((int) tileX, (int) tileY - 1) == 3) {
			type = 1;
		} else {
			type = 0;
		}
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		//IF FALSE? IFFFFF FALSE?!?!?!?!?
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		// r.drawImageTile(image, (int) tileX * GameManager.TS, (int) tileY *
		// GameManager.TS, 0, type);
	}
}
