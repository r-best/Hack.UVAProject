package entities;

import graphics.Assets;
import states.GameState;

import java.awt.*;

/**
 * Created by Bobby on 3/26/2017.
 */
public class LevelChanger extends Entity{
	public LevelChanger(float x, float y) {
		super(x, y, Assets.getEntityAnimation("teleporter"));
		isSolid = false;
	}

	@Override
	public void update() {
		super.update();

		if(collidesWith(GameState.player)) {
			GameState.currentManager = new EntityManager(++GameState.currentRoomNum);
			GameState.player.moveTo(2, 2);
		}
	}

	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
	}
}
