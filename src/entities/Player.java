package entities;

import HackUVAProject.Game;
import graphics.Assets;
import graphics.Camera;
import utilities.KeyManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity{

	public Player(int x, int y) {
		super(x, y, Assets.getEntityAnimation("player"));


	}

	@Override
	public void update(){
		super.update();
		setPlayerMovement();
		currentFrame = anim.getCurrentFrame();

		if(KeyManager.checkKeyAndReset(KeyEvent.VK_E)){
			jump();
		}

		move();
	}

	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
	}

	public void jump(){
		Point mouse = MouseInfo.getPointerInfo().getLocation();

		setXInPixels((float)(mouse.x - Game.getWindowX() - Camera.getXOffset()));
		setYInPixels((float)(mouse.y - Game.getWindowY() - Camera.getYOffset()));
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
}