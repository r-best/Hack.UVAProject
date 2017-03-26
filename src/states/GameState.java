package states;

import entities.EntityManager;
import entities.Player;
import graphics.Camera;

import java.awt.*;

/**
 * Created by Bobby on 3/25/2017.
 */
public class GameState implements State{

	public static int currentRoomNum;
	public static EntityManager currentManager;
	public static Player player;

	public GameState(){
		player = new Player(1, 1);
		currentRoomNum = 0;
		currentManager = new EntityManager(1);
	}

	@Override
	public void update() {
		currentManager.update();
		player.update();
		Camera.centerOnEntity(player);
	}

	@Override
	public void draw(Graphics2D g) {
		currentManager.draw(g);
		player.draw(g);
	}

	@Override
	public void onEnter() {

	}

	@Override
	public void onExit() {

	}
}
