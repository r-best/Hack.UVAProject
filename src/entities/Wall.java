package entities;

import graphics.Animation;
import graphics.Assets;

/**
 * Created by Bobby on 3/25/2017.
 */
public class Wall extends Entity{
	public Wall(int x, int y) {
		super(x, y, Assets.getEntityAnimation("grass"));
	}
}
