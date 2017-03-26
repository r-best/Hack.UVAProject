package entities.enemies;

import graphics.Assets;
import states.GameState;

import java.awt.*;

/**
 * Created by Bobby on 3/26/2017.
 */
public class Chaser extends Enemy{
	public Chaser(int x, int y) {
		super(x, y, Assets.getEntityAnimation("chaser"));
	}

	public void update(){
		double targetSpeed = 5;

		double playerX = GameState.player.x;
		double playerY = GameState.player.y;

		double c = Math.sqrt(Math.pow(playerX, 2) + Math.pow(playerY, 2));

		double ratio = c / targetSpeed;

		XSpd = playerX / ratio;
		YSpd = playerY / ratio;
	}

	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
	}
}
