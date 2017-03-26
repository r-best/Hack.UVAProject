package states;

import HackUVAProject.Game;
import entities.EntityManager;
import entities.Player;
import entities.Spawner;
import graphics.Camera;
import java.awt.Shape;

import javafx.scene.shape.Circle;
import utilities.KeyManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

/**
 * Created by Bobby on 3/25/2017.
 */
public class GameState implements State{

	public static int currentRoomNum;
	public static EntityManager currentManager;
	public static Player player;
	public static boolean frozen;

	private Rectangle healthMeter, healthMeterFill, energyMeter, energyMeterFill, energyPreview;

	public GameState(){
		player = new Player(1, 1);
		currentRoomNum = 0;
		currentManager = new EntityManager(1);

		healthMeter = new Rectangle(
				(int)(Game.getGameWidth()*.01),
				(int)(Game.getGameHeight()*.01),
				(int)(Game.getGameWidth()*.05),
				(int)(Game.getGameHeight()*.98)
		);
		healthMeterFill = new Rectangle(healthMeter);

		energyMeter = new Rectangle(
				(int)(Game.getGameWidth()*.07),
				(int)(Game.getGameHeight()*.01),
				(int)(Game.getGameWidth()*.05),
				(int)(Game.getGameHeight()*.98)
		);
		energyMeterFill = new Rectangle(energyMeter);
		energyPreview = new Rectangle(energyMeter);

		new Spawner();
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

		healthMeterFill.setBounds(healthMeterFill.x, healthMeter.y+(int)(healthMeter.height*((100-player.getHealth()))/100), healthMeterFill.width, (int)(healthMeter.height*(player.getHealth()/100)));

		energyMeterFill.setBounds(energyMeterFill.x, energyMeter.y+(int)(energyMeter.height*((100-player.getEnergy())/100)), energyMeterFill.width, (int)(energyMeter.height*(player.getEnergy()/100)));
		energyPreview.setBounds(energyPreview.x, energyMeterFill.y, energyPreview.width, (int)(energyMeter.height*(player.estimateJumpCost()/100)));

		Camera.centerOnEntity(player);
	}

	@Override
	public void draw(Graphics2D g) {
		currentManager.draw(g);
		player.draw(g);

		Point mouse = MouseInfo.getPointerInfo().getLocation();

		g.setColor(Color.black);
		g.drawLine(Game.getGameWidth()/2, Game.getGameHeight()/2, (int)(mouse.getX()-Game.getWindowX()), (int)(mouse.getY()-Game.getWindowY()-50));

		g.setColor(Color.white);
		g.draw(energyMeter);
		g.draw(healthMeter);
		g.setColor(Color.blue);
		g.fill(energyMeterFill);
		g.setColor(Color.green);
		g.fill(healthMeterFill);
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
