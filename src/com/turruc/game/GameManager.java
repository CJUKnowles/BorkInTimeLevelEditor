package com.turruc.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.turruc.LevelPicker;
import com.turruc.engine.AbstractGame;
import com.turruc.engine.GameContainer;
import com.turruc.engine.Renderer;
import com.turruc.engine.gfx.Image;
import com.turruc.engine.gfx.ImageTile;
import com.turruc.game.entities.BuildEnt;
import com.turruc.game.entities.CameraFollow;
import com.turruc.game.entities.EntityType;
import com.turruc.game.entities.GameObject;
import com.turruc.game.entities.MeleeEnemy;
import com.turruc.game.entities.ResourceBall;
import com.turruc.game.entities.Turret;


public class GameManager extends AbstractGame {
	private static GameContainer gc;
	public static GameManager gm;
	
	public static final int TS = 32; // tilesize for hitboxes

	
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Camera camera;

	private int[] collision;
	private int levelW, levelH;
	
	private ImageTile dirt;
	private Image background;
	private Image midground;
	private Image level;
	private Image platform;
	private Image ladder;
	private ImageTile lava;

	private Process proc;
	
	private int normalAnimationSpeed = 7;
	private int animationSpeed = normalAnimationSpeed;

	private float anim = 0;

	public GameManager() {
		if(gm == null) {
			gm = this;
		}else {
			throw new IllegalStateException("Tried to create new instance of GameManager");
		}
		level = new Image("/levels/levelExample/levelExample.png");
		loadLevel("/levels/levelExample/levelExample.png");
		camera = new Camera(EntityType.CameraFollow);
		dirt = new ImageTile("/dirtTileset.png", 32, 32);
		background = new Image("/levels/levelExample/backgroundExample.png");
		midground = new Image("/levels/levelExample/midgroundExample.png");
		platform = new Image("/platform.png");
		lava = new ImageTile("/lava.png", 32, 32);
		ladder = new Image("/ladder.png");
	}

	public static void main(String[] args) {
		
		gc = new GameContainer(new GameManager());
		gc.start();
		
	}

	
	@Override
	public void init(GameContainer gc) {
		gc.getRenderer().setAmbientColor(-1);
	}

	public void runMainGame(){
		if(proc != null) {
			proc.destroy();
			proc = null;
		}
		try {
			proc = Runtime.getRuntime().exec("java -Dsun.java2d.d3d=false -Dsun.java2d.noddraw=true -cp \"ABorkInTime.jar;resources;.\" com.turruc.game.GameManager true");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		
		for (int i = 0; i < getObjects().size(); i++) {
			getObjects().get(i).update(gc, this, dt);
			if (getObjects().get(i).isDead()) {
				getObjects().remove(i);
				i--;
			}
		}
		camera.update(gc, this, dt);
	}



	@Override
	public void render(GameContainer gc, Renderer r) {
		camera.render(r);

		animationSpeed = normalAnimationSpeed;

		anim += gc.getDt() * animationSpeed;
		anim %= 4;

		// Start of drawing map
		for(int i = 0; i < (levelW * 32)/background.getW() * 2; i++) { //add 1 to i < x if drawing one too few backgrounds
			r.drawImage(background, (int) (i * background.getW() + camera.getOffX() * camera.getBackgroundSpeed()), 0);
		}

		for(int i = 0; i < (levelW * 32)/background.getW() * 2; i++) { //add 1 to i < x if drawing one too few backgrounds
			r.drawImage(midground, (int) (i * midground.getW() + camera.getOffX() * camera.getMidgroundSpeed()), 0);
		}

		for (int y = 0; y < levelH; y++)  {
			for (int x = 0; x < levelW; x++) {
				// drawing normal tileset (dirt, cave, etc)
				if (collision[x + y * levelW] == 1) {
					if (y != 0 && y != levelH - 1 && x != 0 && x != levelW) {
						if (!getContact(x, y - 1) && getContact(x - 1, y) && getCollision(x, y + 1) && getContact(x + 1, y)) {
							//dirt.getTileImage(0, 0).setLightBlock(Light.FULL);
							r.drawImageTile(dirt, x * TS, y * TS, 0, 0); // up
						} else if (getContact(x, y- 1) && !getContact(x - 1, y) && getCollision(x, y + 1) && getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 1, 0); // left
						} else if (getContact(x, y- 1) && getContact(x - 1, y) && !getCollision(x, y + 1) && getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 2, 0); // down
						} else if (getContact(x, y- 1) && getContact(x - 1, y) && getCollision(x, y + 1) && !getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 3, 0); // right
						} else if (!getContact(x, y - 1) && !getContact(x - 1, y) && getCollision(x, y + 1) && getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 0, 1); // up, left
						} else if (!getContact(x, y - 1) && getContact(x - 1, y) && getCollision(x, y + 1) && !getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 1, 1); // up, right
						} else if (getContact(x, y- 1) && getContact(x - 1, y) && !getCollision(x, y + 1) && !getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 2, 1); // down, right
						} else if (getContact(x, y- 1) && !getContact(x - 1, y) && !getCollision(x, y + 1) && getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 3, 1); // down, left
						} else if (!getContact(x, y - 1) && getContact(x - 1, y) && !getCollision(x, y + 1) && getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 0, 2); // up, down
						} else if (getContact(x, y- 1) && !getContact(x - 1, y) && getCollision(x, y + 1) && !getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 1, 2); // left, right
						} else if (!getContact(x, y - 1) && !getContact(x - 1, y) && getCollision(x, y + 1) && !getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 2, 2); // up, left, right
						} else if (!getContact(x, y - 1) && getContact(x - 1, y) && !getCollision(x, y + 1) && !getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 3, 2); // up, down, right
						} else if (getContact(x, y- 1) && !getContact(x - 1, y) && !getCollision(x, y + 1) && !getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 0, 3); // left, right, down
						} else if (!getContact(x, y - 1) && !getContact(x - 1, y) && !getCollision(x, y + 1) && getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 1, 3); // up, left, down
						} else if (!getContact(x, y - 1) && !getContact(x - 1, y) && !getCollision(x, y + 1) && !getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 2, 3); // up, left, down, right
						} else if (getContact(x, y- 1) && getContact(x - 1, y) && getCollision(x, y + 1) && getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 3, 3); // none
						}

						// top row
					} else if (y == 0) {
						if (!getCollision(x, y + 1)) {
							r.drawImageTile(dirt, x * TS, y * TS, 2, 0); // down
						} else {
							r.drawImageTile(dirt, x * TS, y * TS, 3, 3); // none
						}

						// left column
					} else if (x == 0) {
						if (!getContact(x + 1, y)) {
							r.drawImageTile(dirt, x * TS, y * TS, 3, 0); // right
						} else {
							r.drawImageTile(dirt, x * TS, y * TS, 3, 3); // none
						}
						// bottom row
					} else if (y == levelH - 1) {
						if (!getContact(x, y - 1)) {
							r.drawImageTile(dirt, x * TS, y * TS, 0, 0); // up
						} else {
							r.drawImageTile(dirt, x * TS, y * TS, 3, 3); // none
						}
					}
				}
				// end of drawing normal tileset

				// Drawing lava
				if (collision[x + y * levelW] == 3) {
					if (collision[x + (y - 1) * levelW] == 3) {
						r.drawImageTile(lava, x * TS, y * TS, (int) anim, 1); // none
					} else {
						r.drawImageTile(lava, x * TS, y * TS, (int) anim, 0); // up

					}

				}
				// end of drawing lava

				// Drawing platforms
				if (collision[x + y * levelW] == 4) {
					r.drawImage(platform, x * TS, y * TS);

				}
				// end of drawing platforms
				
				// Drawing ladders
				if (collision[x + y * levelW] == 5) {
					r.drawImage(ladder, x * TS, y * TS);

				}
				// end of drawing ladders

				// end of drawing map
			}
		}

		for (GameObject obj : getObjects()) {
			obj.render(gc, r);

		}

		r.drawText("Current Block: " + BuildEnt.values()[CameraFollow.selection].getName(), (int) camera.getOffX(), 32, Color.WHITE.getRGB());
		LevelPicker.update();

	}

	public void updateLevel() {

		level = new Image("/level.png");
		
		GameManager.gm.levelW = level.getW();
		GameManager.gm.levelH = level.getH();
		GameManager.gm.collision = new int[GameManager.gm.levelW * GameManager.gm.levelH];

		for (int y = 0; y < level.getH(); y++) {
			for (int x = 0; x < level.getW(); x++) {
				if(y == 0 || y == level.getH()-1 || x == 0 || x == level.getW()-1) {
					GameManager.gm.collision[x + y * level.getW()] = 1;
				}else if (level.getP()[x + y * level.getW()] == 0xffff00ff) {
					GameManager.gm.collision[x + y * level.getW()] = -100;// player
				} else if (level.getP()[x + y * level.getW()] == Color.BLACK.getRGB()) {// black
					GameManager.gm.collision[x + y * level.getW()] = 1; // collision block
				} else if (level.getP()[x + y * level.getW()] == Color.WHITE.getRGB()) {// white
					GameManager.gm.collision[x + y * level.getW()] = 0;// air
				} else if (level.getP()[x + y * level.getW()] == Color.GREEN.getRGB()) {// green
					GameManager.gm.collision[x + y * level.getW()] = 2;// turret
				} else if ((level.getP()[x + y * level.getW()] | 0xff000000) == Color.RED.getRGB()) {// red // | 0xff000000 removes alpha
					GameManager.gm.collision[x + y * level.getW()] = -1;// health ball 
				} else if (level.getP()[x + y * level.getW()] == Color.BLUE.getRGB()) {// blue
					GameManager.gm.collision[x + y * level.getW()] = -2;// mana ball
				} else if (level.getP()[x + y * level.getW()] == Color.YELLOW.getRGB()) {// yellow
					GameManager.gm.collision[x + y * level.getW()] = 3;// lava
				} else if (level.getP()[x + y * level.getW()] == 0xff963200) {// Brown
					GameManager.gm.collision[x + y * level.getW()] = 4;// platform
				} else if (level.getP()[x + y * level.getW()] == 0xff6400ff) {// Purple
					GameManager.gm.collision[x + y * level.getW()] = 5;// ladder
				} else if (level.getP()[x + y * level.getW()] == 0xff00ffff) {// teal
					//getObjects().add(new MeleeEnemy(this, x, y)); //meleeEnemy
				}
			}
		}
		
		
	}
	public void loadLevel(String path) {
		Image levelImage = new Image(path);

		levelW = levelImage.getW();
		levelH = levelImage.getH();
		collision = new int[levelW * levelH];
		
		getObjects().add(new CameraFollow(10, 10));

		for (int y = 0; y < levelImage.getH(); y++) {
			for (int x = 0; x < levelImage.getW(); x++) {

				if(y == 0 || y == level.getH()-1 || x == 0 || x == level.getW()-1) {
					GameManager.gm.collision[x + y * level.getW()] = 1;
				}else if (levelImage.getP()[x + y * levelImage.getW()] == 0xffff00ff) {
					//collision[x + y * levelImage.getW()] = -100;// player
				} else if (levelImage.getP()[x + y * levelImage.getW()] == 0xff000000) {// black
					collision[x + y * levelImage.getW()] = 1; // collision block
				} else if (levelImage.getP()[x + y * levelImage.getW()] == 0xffffffff) {// white
					collision[x + y * levelImage.getW()] = 0;// air
				} else if (levelImage.getP()[x + y * levelImage.getW()] == 0xff00ff00) {// green
					collision[x + y * levelImage.getW()] = 2;// turret
				} else if ((levelImage.getP()[x + y * levelImage.getW()] | 0xff000000) == Color.RED.getRGB()) {// red // | 0xff000000 removes alpha
					collision[x + y * levelImage.getW()] = -1;// health ball 
				} else if (levelImage.getP()[x + y * levelImage.getW()] == 0xff0000ff) {// blue
					collision[x + y * levelImage.getW()] = -2;// mana ball
				} else if (levelImage.getP()[x + y * levelImage.getW()] == 0xffffff00) {// yellow
					collision[x + y * levelImage.getW()] = 3;// lava
				} else if (levelImage.getP()[x + y * levelImage.getW()] == 0xff963200) {// Brown
					collision[x + y * levelImage.getW()] = 4;// platform
				} else if (levelImage.getP()[x + y * levelImage.getW()] == 0xff6400ff) {// Purple
					collision[x + y * levelImage.getW()] = 5;// ladder
				} else if (levelImage.getP()[x + y * levelImage.getW()] == 0xff00ffff) {// teal
					collision[x + y * levelImage.getW()] = -100;
					getObjects().add(new MeleeEnemy(this, x, y)); //meleeEnemy
				}
			}
		}

		for (int y = 0; y < levelH; y++) {
			for (int x = 0; x < levelW; x++) {
				if (collision[x + y * levelW] == -100) { // player
					//player.moveTo(x * TS, y * TS);
				}

				if (collision[x + y * levelW] == 2) { // turret
					getObjects().add(new Turret(this, x, y));
				}

				if (collision[x + y * levelW] == -1) { // healthBall
					getObjects().add(new ResourceBall(this, x, y, 0, 101 + (level.getP()[x + y * levelW] >> 24)));
				}

				if (collision[x + y * levelW] == -2) { // manaBall
					getObjects().add(new ResourceBall(this, x, y, 1, 101 + (level.getP()[x + y * levelW] >> 24)));
				}

			}
		}
	}

	public void addObject(GameObject object) {
		getObjects().add(object);
	}

	public GameObject getObject(EntityType tag) {
		for (int i = 0; i < getObjects().size(); i++) {
			if (getObjects().get(i).getTag().equals(tag)) {
				return getObjects().get(i);
			}
		}
		return null;
	}

	public boolean getContact(int x, int y) {
		return x < 0 || x >= levelW || y < 0 || y >= levelH || collision[x + y * levelW] == 1 || collision[x + y * levelW] == 2 || collision[x + y * levelW] == 3;
	}
	
	public boolean getCollision(int x, int y) {
		return x < 0 || x >= levelW || y < 0 || y >= levelH || collision[x + y * levelW] == 1 || collision[x + y * levelW] == 2;
	}

	public int getCollisionNum(int x, int y) {
		return collision[x + y * levelW];
	}

	public Camera getCamera() {
		return camera;
	}

	public int getLevelW() {
		return levelW;
	}

	public int getLevelH() {
		return levelH;
	}

	public static ArrayList<GameObject> getObjects() {
		return objects;
	}

	public int[] getCollision() {
		return collision;
	}

	public static GameContainer getGc() {
		return gc;
	}
	
	public static void updateTileSet(String s) {
		GameManager.gm.dirt = new ImageTile(s, 32, 32);
	}
	
	public void exportToImage(String path, String name) {
		int width = getLevelW();
		int height = getLevelH();
		int[] rgbs = new int[width * height];
		
		for(int j = 0; j < rgbs.length; j++) {
			int col = getCollision()[j];
			boolean found = false;
			for(BuildEnt ent : BuildEnt.values()) {
				if(ent.getCollision() == col) {
					rgbs[j] = ent.getColor();
					found = true;
					break;
				}
			}
			
			if(!found) {
				rgbs[j] = 0xffffffff;
			}
		}

		DataBuffer rgbData = new DataBufferInt(rgbs, rgbs.length);

		int[] masks = new int[]{0xff0000, 0xff00, 0xff, 0xff000000};
		WritableRaster raster = Raster.createPackedRaster(rgbData, width, height, width, masks, null);
		ColorModel colorModel = new DirectColorModel(32, 0xff0000, 0xff00, 0xff, 0xff000000);
		BufferedImage img = new BufferedImage(colorModel, raster, false, null);

		try {
			if(!name.endsWith(".png")) name += ".png";
			
			ImageIO.write(img, "png", new File("resources/" + path + name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}