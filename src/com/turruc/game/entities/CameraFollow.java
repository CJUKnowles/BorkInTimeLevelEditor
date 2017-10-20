package com.turruc.game.entities;

import java.awt.event.KeyEvent;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.game.GameManager;

public class CameraFollow extends GameObject {

	public CameraFollow(int tileX, int tileY) {
		this.posX = tileX * GameManager.TS;
		this.posY = tileY * GameManager.TS;
		this.tag = EntityType.CameraFollow;
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		
		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();
		
		int tileX = (int) ((x + gm.getCamera().getOffX())/gm.TS);
		int tileY = (int) ((y + gm.getCamera().getOffY())/gm.TS);
		
		System.out.println(tileX + " " + tileY);

		if(gc.getInput().isButton(1)) {
			int currentCollision = gm.getCollision()[ (tileY * gm.getLevelW()) + tileX];
			
			//Turret
			if(currentCollision == 2) {
				
				for(GameObject obj : GameManager.getObjects()) {
					if(obj instanceof Turret) {
						if(((Turret)obj).getTileX() == tileX && ((Turret)obj).getTileY() == tileY) {
							((Turret)obj).setDead(true);
							return;
						}
					}
				}
				
			}else if(currentCollision == -1 || currentCollision == -2) {//resource ball
				for(GameObject obj : GameManager.getObjects()) {
					if(obj instanceof ResourceBall) {
						if(((ResourceBall)obj).getTileX() == tileX && ((ResourceBall)obj).getTileY() == tileY) {
							((ResourceBall)obj).setDead(true);
							return;
						}
					}
				}
			}
		}
		
		
		if (gc.getInput().isKey(KeyEvent.VK_D)){
			posX += 10;
		}else if(gc.getInput().isKey(KeyEvent.VK_A)){
			posX -= 10;
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r) {}
	public void setDead(boolean dead) {}
		
}
