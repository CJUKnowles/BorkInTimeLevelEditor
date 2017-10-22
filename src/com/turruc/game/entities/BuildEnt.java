package com.turruc.game.entities;

public enum BuildEnt {

	COLLISION_BLOCK(1, 0xff000000, "Collision Block", "dirtTileset.png"),
	TURRET(2, 0xff00ff00, "Turret Gay", "turret.png"),
	PLATFORM(4, 0xff963200, "Platform", "platform.png"),
	LADDER(5, 0xff6400ff, "Ladder", "ladder.png"),
	HEALTHBALL(-1, 0xffff0000, "Health Ball", "resourceBall.png"),
	MANABALL(-2, 0xff0000ff, "Mana Ball", "resourceBall.png"),
	LAVA(3, 0xffffff00, "Lava", "lava.png"),
	MELEE_ENEMY(-100, 0xff00ffff, "Melee Enemy", "player.png");
	
	
	int collision;
	int color;
	String name;
	String path;
	
	BuildEnt(int collision, int color, String name, String path) {
		this.collision = collision;
		this.color = color;
		this.name = name;
		this.path = path;
	}
	
	public int getCollision() {
		return collision;
	}
	
	public int getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
}
