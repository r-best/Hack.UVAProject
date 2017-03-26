package entities.enemies;

import entities.Player;
import graphics.Assets;
import graphics.Camera;
import states.GameState;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Bobby on 3/26/2017.
 */
public class Chaser extends Enemy{
	private Player.HistoricPosition chasePoint;

	public Chaser(int x, int y) {
		super(x, y, Assets.getEntityAnimation("chaser"));
		isSolid = false;
	}

	public void update(){
		super.update();
		if(Player.points.size() > 0)
			chasePoint = Player.points.get(0);

		double targetSpeed = 5;

		double playerX = chasePoint.getX() - this.x;
		double playerY = chasePoint.getY() - this.y;
		double c = Math.sqrt(Math.pow(playerX, 2) + Math.pow(playerY, 2));

		double ratio = c / targetSpeed;

		XSpd = playerX / ratio;
		YSpd = playerY / ratio;
	}

	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
		//graphics.setColor(Color.blue);
		//graphics.fill(new Rectangle((int)(bounds.x + Camera.getXOffset()), (int)(bounds.y + Camera.getYOffset()), bounds.width, bounds.height));
	}
}
