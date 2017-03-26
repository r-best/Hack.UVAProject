package entities;

import graphics.Assets;
import states.GameState;

import java.awt.*;

/**
 * Created by Bobby on 3/26/2017.
 */
public class LevelChanger extends Entity{
	private int destRoom;

	public LevelChanger(float x, float y, int destRoom) {
		super(x, y, Assets.getEntityAnimation("teleporter"));
		isSolid = false;
		this.destRoom = destRoom;
	}

	@Override
	public void update() {
		super.update();

		if(collidesWith(GameState.player)) {
			GameState.currentManager = new EntityManager(destRoom);
			GameState.player.moveTo(2, 2);
		}
	}

	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
	}
}
