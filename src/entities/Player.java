package entities;

import HackUVAProject.Game;
import entities.enemies.Chaser;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameOverState;
import states.GameState;
import entities.effects.Effect;
import states.StateManager;
import utilities.KeyManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Entity{
	public static ArrayList<HistoricPosition> points;

	private double health = 100;
	private double energyPercentage = 100;

	private Timer damageCooldown;
	private boolean invincible = false;

	public Player(int x, int y) {
		super(x, y, Assets.getEntityAnimation("player"));
		points = new ArrayList<>();
		points.add(new HistoricPosition(
				this.x + Camera.getXOffset(),
				this.y + Camera.getYOffset()
		));

		new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Animation anim = Assets.getEntityAnimation("trail");
				GameState.currentManager.addEffect(
						new Effect(
								((GameState.player.x+GameState.player.getWidth()/2-anim.getFrame(0).getWidth()/2)/32),
								((GameState.player.y+GameState.player.getHeight()/2-anim.getFrame(0).getHeight()/2)/32),
								anim,
								2000)
				);
			}
		}).start();

		new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				points.add(new HistoricPosition(
						GameState.player.x,
						GameState.player.y
				));
			}
		}).start();

		damageCooldown = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				invincible = false;
			}
		});
		damageCooldown.setRepeats(false);
	}

	@Override
	public void update(){
		super.update();
		setPlayerMovement();
		currentFrame = anim.getCurrentFrame();

		if(energyPercentage < 100)
			energyPercentage += .2;

		if(KeyManager.checkKeyAndReset(KeyEvent.VK_E))
			jump();

		move();
	}

	@Override
	public void draw(Graphics2D graphics){
		super.draw(graphics);
	}

	public void damage(int amnt){
		if(!invincible) {
			GameState.currentManager.addEffect(new Effect(this.x/32, this.y/32, Assets.getEntityAnimation("damage"), 1000));
			health -= amnt;
			invincible = true;
			damageCooldown.start();
		}

		if(health <= 0){
			StateManager.setState(new GameOverState());
		}
	}

	public boolean spendEnergy(double amnt){
		energyPercentage -= amnt;

		if(energyPercentage < 0) {
			energyPercentage = 0;
			return false;
		}
		return true;
	}

	public void jump(){
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		double cost = estimateJumpCost();

		if(cost < energyPercentage) {
			GameState.currentManager.addEffect(new Effect(this.x/32, this.y/32, Assets.getEntityAnimation("jump"), 750));
			this.setXInPixels((float) (mouse.x - Game.getWindowX() - Camera.getXOffset()));
			this.setYInPixels((float) (mouse.y - Game.getWindowY() - Camera.getYOffset()));
			//spendEnergy(cost);
		}
	}

	public double estimateJumpCost(){
		Point mouse = MouseInfo.getPointerInfo().getLocation();

		double xDistance = mouse.x - Game.getWindowX() - Camera.getXOffset() - this.x;
		double yDistance = mouse.y - Game.getWindowY() - Camera.getYOffset() - this.y;
		double jumpDistance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

		double unitJumpDistance = Math.sqrt(Math.pow(Game.getGameWidth(), 2) + Math.pow(Game.getGameHeight(), 2)) / 4; //This far of a jump costs 55% of energy, used to determine other costs

		return Math.min(energyPercentage, (jumpDistance / unitJumpDistance) * 55);
	}

	public void freeze(){
		if(spendEnergy(1))
			if(!GameState.isFrozen())
				GameState.frozen = true;
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

	public double getHealth(){ return health; }
	public double getEnergy(){ return energyPercentage; }

	public class HistoricPosition{
		private float x, y;

		public HistoricPosition(float x, float y){
			this.x = x;
			this.y = y;

			HistoricPosition temp = this;

			new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Player.points.remove(temp);
				}
			}).start();
		}

		public float getX(){ return x; }
		public float getY(){ return y; }
	}
}