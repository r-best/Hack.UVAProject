package states;

import HackUVAProject.Game;
import entities.EntityManager;
import entities.Player;
import entities.Spawner;
import graphics.Camera;
import java.awt.Shape;

import javafx.scene.shape.Circle;
import sun.plugin2.util.ColorUtil;
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
		currentRoomNum = 1;
		currentManager = new EntityManager(currentRoomNum);

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
		//Colors
		Color darkGreen = new Color(30,128,30);
		Color darkRed = new Color(128,30,30);
		Color darkBlue = new Color(30,30,128);

		currentManager.draw(g);
		player.draw(g);

		Point mouse = MouseInfo.getPointerInfo().getLocation();

		g.setColor(Color.black);
		g.drawLine(Game.getGameWidth()/2, Game.getGameHeight()/2, (int)(mouse.getX()-Game.getWindowX()), (int)(mouse.getY()-Game.getWindowY()-50));

		g.setColor(Color.white);
		g.draw(energyMeter);
		g.draw(healthMeter);
		//Energy Gradient
		GradientPaint energy = new GradientPaint(0,0, darkBlue,60, 0,Color.blue, true);
		g.setPaint(energy);
		g.fill(energyMeterFill);
		GradientPaint energyPreviewFill = new GradientPaint(0,0, darkRed,60, 0,Color.red, true);
		g.setPaint(energyPreviewFill);
		g.fill(energyPreview);
		//Health Gradient
		GradientPaint health = new GradientPaint(0,0, darkGreen,60, 0,Color.green, true);
		g.setPaint(health);
		g.fill(healthMeterFill);
		//HEALTH label
		g.setColor(Color.white);
		g.setFont(g.getFont().deriveFont(80f));
		g.drawString("H", (int)healthMeter.getX()+10, (int)healthMeter.getY()+300);
		g.drawString("E", (int)healthMeter.getX()+10, (int)healthMeter.getY()+390);
		g.drawString("A", (int)healthMeter.getX()+10, (int)healthMeter.getY()+480);
		g.drawString("L", (int)healthMeter.getX()+10, (int)healthMeter.getY()+570);
		g.drawString("T", (int)healthMeter.getX()+10, (int)healthMeter.getY()+660);
		g.drawString("H", (int)healthMeter.getX()+10, (int)healthMeter.getY()+750);
		//ENERGY label
		g.drawString("E", (int)energyMeter.getX()+10, (int)energyMeter.getY()+300);
		g.drawString("N", (int)energyMeter.getX()+10, (int)energyMeter.getY()+390);
		g.drawString("E", (int)energyMeter.getX()+10, (int)energyMeter.getY()+480);
		g.drawString("R", (int)energyMeter.getX()+10, (int)energyMeter.getY()+570);
		g.drawString("G", (int)energyMeter.getX()+10, (int)energyMeter.getY()+660);
		g.drawString("Y", (int)energyMeter.getX()+10, (int)energyMeter.getY()+750);

	}

	public static boolean isFrozen(){ return frozen; }

	@Override
	public void onEnter() {

	}

	@Override
	public void onExit() {

	}
}
