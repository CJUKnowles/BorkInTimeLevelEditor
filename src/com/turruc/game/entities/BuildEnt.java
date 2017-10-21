package com.turruc.game.entities;

public enum BuildEnt {

	COLLISION_BLOCK(1, 0xff000000, "Collision Block"),
	TURRET(2, 0xff00ff00, "Turret Gay"),
	PLATFORM(4, 0xff963200, "Platform"),
	LADDER(5, 0xff6400ff, "Ladder"),
	HEALTHBALL(-1, 0xffff0000, "Health Ball"),
	MANABALL(-2, 0xff0000ff, "Mana Ball"),
	LAVA(3, 0xffffff00, "Lava"),
	MELEE_ENEMY(-100, 0xff00ffff, "Melee Enemy");
	
	
	int collision;
	int color;
	String name;
	
	BuildEnt(int collision, int color, String name) {
		this.collision = collision;
		this.color = color;
		this.name = name;
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
	
}
