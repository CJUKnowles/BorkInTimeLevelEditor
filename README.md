# A Bork In Time

Java game made for fun!

## Ideas and To-Do

* colors may not be a perfect (0, 255, 0) forever, may need to have specific colors if I want to add extra features

* Win condition
	* Win if the player reaches a certain color of the level, for now winning will just print something on the screen
* Enemy health
	* Add a system for health in turrets, even if they only have one
	* To be reused in code for other enemies
* Melee Enemies
	* Will follow same gravity/jumping rules as the player
	* Will follow the player
	* Will deal ticking damage within a certain range
	* Impossible to kill with melee without taking damage
* Cannons/Static Turrets
	* Will not fire towards player, but in a set direction and pace
	* Will fire different projectiles from normal turrets
		* Larger
		* slower
		* deals more damage
		* does not despawn on contact with player
	    * Cannot be destroyed
* Bubble ability
	* Costs ~40 mana
	* Creates a bubble shield around the player
	* shield will absorb one hit, regardless of the amount of damage
	* Health bar will have a semi transparent golden cover on it while the bubble is active
	* cannot stack more than one bubble
* EMP ability
	 * costs ~100 mana
	* disables all enemies on screen/within a certain range
  
## Level Design Guide

* Each pixel represents one tile
* the color and alpha of a pixel determine what will be spawned there
	* Pink: Player Spawn 0xffff00ff
	* White: Air 0xff000000
	* Black: Collision 0xff000000
	* Green: Turret 0xff00ff00
	* Red: Health Ball 0xffff0000
	* Alpha 255 (0xffff0000) Gives 100 Health, Alpha 155 (0x9bff0000) Gives 0 Health
	 * Anything below 155 will subtract health
	* Blue: Mana Ball 0xff0000ff
		* Alpha 255 (0xffff0000) Gives 100 mana, Alpha 155 (0x9bff0000) Gives 0 mana
		* Anything below 155 will subtract mana
	* Yellow: Lava 0xffffff00
	* Other: Air 
		
* Turrets will automatically attach to the nearest surface
	* if there are multiple surfaces, the turret will prioritize below, right, left, then above
* Anything out of the map by default has normal collision, but outlining the level in black is still a good idea
