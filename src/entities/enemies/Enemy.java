package entities.enemies;

import entities.Entity;
import graphics.Animation;
import states.GameState;

/**
 * Created by Bobby on 3/26/2017.
 */
public class Enemy extends Entity{
	public Enemy(int x, int y, Animation anim) {
		super(x, y, anim);
	}

	public void update(){
		super.update();
		if(this.bounds.intersects(GameState.player.getBounds())){
			GameState.player.damage(10);
		}
	}
}