package com.turruc.game.entities;

import java.awt.event.KeyEvent;

import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.game.GameManager;

public class CameraFollow extends GameObject {

	public static int selection = 0; 

	public CameraFollow(int tileX, int tileY) {
		this.posX = tileX * GameManager.TS;
		this.posY = tileY * GameManager.TS;
		this.tag = EntityType.CameraFollow;
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {

		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();

		int tileX = (int) (   (x + gm.getCamera().getOffX())/GameManager.TS   );
		int tileY = (int) (   (y + gm.getCamera().getOffY())/GameManager.TS   );
		//if out of bounds, round down
		if(tileY * gm.getLevelW() + tileX > gm.getCollision().length) {
			tileX = (int) ((x + gm.getCamera().getOffX() - GameManager.TS/2)/GameManager.TS   );
			tileY = (int) ((y + gm.getCamera().getOffY() - GameManager.TS/2)/GameManager.TS   );
		}
		//if still out of bounds return
		if(tileY * gm.getLevelW() + tileX > gm.getCollision().length) {
			return;
		}
		
		//scrolling
		int scroll = -gc.getInput().getScroll();
		if(scroll != 0) {

			if(scroll == 1) {
				if(selection+1 > BuildEnt.values().length-1) selection = 0;
				else selection += 1;
			}else if(scroll == -1) {
				if(selection-1 < 0) selection = BuildEnt.values().length-1;
				else selection -= 1;
			}
		}
		

		int div = 4;
		if(gc.getInput().isKey(KeyEvent.VK_SHIFT)) {
			div = 2;
		}

		if (gc.getInput().isKey(KeyEvent.VK_D)){
			posX += GameManager.TS/div;
		}else if(gc.getInput().isKey(KeyEvent.VK_A)){
			posX -= GameManager.TS/div;
		}
		
		if (gc.getInput().isKey(KeyEvent.VK_W)){
			posY -= GameManager.TS/div;
		}else if(gc.getInput().isKey(KeyEvent.VK_S)){
			posY += GameManager.TS/div;
		}
		
		int col = gm.getCollision()[ (tileY * gm.getLevelW()) + tileX];
		
		if(tileY == 0 || tileY == gm.getLevelH()-1 || tileX == 0 || tileX == gm.getLevelW()-1) {
			return;
		}
		//if r clicking
		if(gc.getInput().isButton(3)) {
			
			//Turret, resource ball, or player
			if(col == 2 || col == -1 || col == -2 || col == -100) {

				//TODO: remove from obj array
				for(GameObject obj : GameManager.getObjects()) {
					if(obj.getTileX() == tileX && obj.getTileY() == tileY) {
						obj.setDead(true);
						return;
					}
				}
				gm.getCollision()[ (tileY * gm.getLevelW()) + tileX] = 0;

			}else if(col == 1 || col == 3 || col == 4 || col == 5) {
				gm.getCollision()[ (tileY * gm.getLevelW()) + tileX] = 0;
			}
		}else if(gc.getInput().isButton(1)) {
			
			if(col == BuildEnt.values()[selection].getCollision()) {
				return;
			}
			
			if(col == 2 || col == -1 || col == -2 || col == -100) {
				for(GameObject obj : GameManager.getObjects()) {
					if(obj.getTileX() == tileX && obj.getTileY() == tileY) {
						obj.setDead(true);
						return;
					}
				}
				gm.getCollision()[ (tileY * gm.getLevelW()) + tileX] = 0;
			}
			

			for(int i = 0; i < BuildEnt.values().length; i++) {
				if(selection == i) {
					switch(BuildEnt.values()[i]) {
					case HEALTHBALL:
						GameManager.getObjects().add(new ResourceBall(gm, tileX, tileY, 0, 100));
						break;
					case MANABALL:
						GameManager.getObjects().add(new ResourceBall(gm, tileX, tileY, 1, 100));
						break;
					case MELEE_ENEMY:
						GameManager.getObjects().add(new MeleeEnemy(gm, tileX, tileY));
						break;
					case TURRET:
						GameManager.getObjects().add(new Turret(gm, tileX, tileY));
						break;
					default:
						break;
						
					}
					break;
				}
			}
			
			gm.getCollision()[ (tileY * gm.getLevelW()) + tileX] = BuildEnt.values()[selection].getCollision();
		}

		



	}

	@Override
	public void render(GameContainer gc, Renderer r) {}
	public void setDead(boolean dead) {}

}
