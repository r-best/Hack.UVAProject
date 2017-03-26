package entities.enemies;

import graphics.Animation;
import graphics.Assets;

import java.awt.*;
import java.util.Random;

/**
 * Created by Bobby on 3/26/2017.
 */
public class FastEnemy extends Enemy{
	private double tempXSpd, tempYSpd;

	public FastEnemy(int x, int y) {
		super(x, y, Assets.getEntityAnimation("fast"));
		recalculateVelocity();
	}

	private void recalculateVelocity(){
		Random r = new Random();
		Point point = new Point(
				(int)(this.getXInPixels() + r.nextInt(100 + 1 + 100) - 100),
				(int)(this.getYInPixels() + r.nextInt(100 + 1 + 100) - 100)
		);

		double targetSpeed = 50;

		double destX = point.getX() - this.x;
		double destY = point.getY() - this.y;
		double c = Math.sqrt(Math.pow(destX, 2) + Math.pow(destY, 2));

		double ratio = c / targetSpeed;

		tempXSpd = destX / ratio;
		tempYSpd = destY / ratio;
	}

	@Override
	public void update() {
		super.update();
		Random r = new Random();
		if(r.nextInt() % 100 >= 99)
			recalculateVelocity();

		XSpd = tempXSpd;
		YSpd = tempYSpd;
	}
}
