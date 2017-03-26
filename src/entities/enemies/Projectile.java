package entities.enemies;

import entities.Player;
import graphics.Animation;
import graphics.Assets;

import java.awt.*;

/**
 * Created by Bobby on 3/26/2017.
 */
public class Projectile extends Enemy{
	private double tempXSpd, tempYSpd;

	public Projectile(int x, int y) {
		super(x, y, Assets.getEntityAnimation("projectile"));
		isSolid = false;

		double targetSpeed = 7;

		Player.HistoricPosition chasePoint = Player.points.get(0);

		double playerX = chasePoint.getX() - this.x;
		double playerY = chasePoint.getY() - this.y;
		double c = Math.sqrt(Math.pow(playerX, 2) + Math.pow(playerY, 2));

		double ratio = c / targetSpeed;

		tempXSpd = playerX / ratio;
		tempYSpd = playerY / ratio;
	}

	@Override
	public void update() {
		super.update();
		XSpd = tempXSpd;
		YSpd = tempYSpd;
	}

	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
	}
}
