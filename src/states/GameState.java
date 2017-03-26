package states;

import HackUVAProject.Game;
import entities.EntityManager;
import entities.Player;
import graphics.Camera;
import utilities.KeyManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Bobby on 3/25/2017.
 */
public class GameState implements State{

	public static int currentRoomNum;
	public static EntityManager currentManager;
	public static Player player;
	public static boolean frozen;

	private Rectangle energyMeter, energyMeterFill, energyPreview;

	public GameState(){
		player = new Player(1, 1);
		currentRoomNum = 0;
		currentManager = new EntityManager(1);

		energyMeter = new Rectangle(
				(int)(Game.getGameWidth()*.01),
				(int)(Game.getGameHeight()*.01),
				(int)(Game.getGameWidth()*.05),
				(int)(Game.getGameHeight()*.98)
		);
		energyMeterFill = new Rectangle(energyMeter);
		energyPreview = new Rectangle(energyMeter);
	}

	@Override
	public void update() {
		if(KeyManager.checkKeyWithoutReset(KeyEvent.VK_SPACE))
			player.freeze();

		if(!frozen)
			currentManager.update();
		else
			frozen = false;
		player.update();

		energyMeterFill.setBounds(energyMeterFill.x, energyMeter.y+(int)(energyMeter.height*((100-player.getEnergy())/100)), energyMeterFill.width, (int)(energyMeter.height*(player.getEnergy()/100)));
		energyPreview.setBounds(energyPreview.x, energyMeterFill.y, energyPreview.width, (int)(energyMeter.height*(player.estimateJumpCost()/100)));

		Camera.centerOnEntity(player);
	}

	@Override
	public void draw(Graphics2D g) {
		currentManager.draw(g);
		player.draw(g);

		g.setColor(Color.white);
		g.draw(energyMeter);
		g.setColor(Color.cyan);
		g.fill(energyMeterFill);
		g.setColor(Color.red);
		g.fill(energyPreview);
	}

	public static boolean isFrozen(){ return frozen; }

	@Override
	public void onEnter() {

	}

	@Override
	public void onExit() {

	}
}
