package entities;

import HackUVAProject.Game;
import graphics.Assets;
import graphics.Camera;
import states.GameState;
import utilities.KeyManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity{

	private double energyPercentage = 100;

	public Player(int x, int y) {
		super(x, y, Assets.getEntityAnimation("player"));
	}

	@Override
	public void update(){
		super.update();
		setPlayerMovement();
		currentFrame = anim.getCurrentFrame();

		if(energyPercentage < 100)
			energyPercentage += .2;

		move();
	}

	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
	}

	public void jump(){
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		double cost = estimateJumpCost();

		if(cost < energyPercentage) {
			setXInPixels((float) (mouse.x - Game.getWindowX() - Camera.getXOffset()));
			setYInPixels((float) (mouse.y - Game.getWindowY() - Camera.getYOffset()));
			energyPercentage -= cost;
		}
	}

	public double estimateJumpCost(){
		Point mouse = MouseInfo.getPointerInfo().getLocation();

		double xDistance = mouse.x - Game.getWindowX() - Camera.getXOffset() - this.x;
		double yDistance = mouse.y - Game.getWindowY() - Camera.getYOffset() - this.y;
		double jumpDistance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

		double unitJumpDistance = Math.sqrt(Math.pow(Game.getGameWidth(), 2) + Math.pow(Game.getGameHeight(), 2)) / 4; //This far of a jump costs 30% of energy, used to determine other costs

		return Math.min(energyPercentage, (jumpDistance / unitJumpDistance) * 30);
	}

	public void freeze(){
		if(!GameState.isFrozen())
			GameState.frozen = true;

		energyPercentage -= 1;
	}

	/**
	 * Sets the XSpd and YSpd of the controlled character depending on
	 what keys are being pressed
	 */
	public void setPlayerMovement(){
		Point mouse = MouseInfo.getPointerInfo().getLocation();

		double targetSpeed = 7;

		double mouseX = mouse.x - Game.getWindowX() - Game.getGameWidth()/2;
		double mouseY = mouse.y - Game.getWindowY() - Game.getGameHeight()/2;

		double c = Math.sqrt(Math.pow(mouseX, 2) + Math.pow(mouseY, 2));

		double ratio = c / targetSpeed;

		XSpd = mouseX / ratio;
		YSpd = mouseY / ratio;
	}

	public double getEnergy(){ return energyPercentage; }
}