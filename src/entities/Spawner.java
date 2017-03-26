package entities;

import entities.enemies.Chaser;
import entities.enemies.FastEnemy;
import entities.enemies.Turret;
import states.GameState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by Bobby on 3/26/2017.
 */
public class Spawner{

	public static int minX, maxX, minY, maxY;

	private Timer timer;

	public Spawner(){
		minX = -50;
		maxX = 50;
		minY = -50;
		maxY = 50;

		timer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spawn();
			}
		});
		timer.start();

		new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Math.random() > .95)
					timer.setDelay((int)(timer.getDelay()/1.5));
			}
		}).start();
	}

	/**
	 * Spawns a random enemy
	 */
	public void spawn(){
		Random r = new Random();
		int chance = r.nextInt() % 100;
		if(chance < 40)
			GameState.currentManager.addEntity(new Chaser(r.nextInt(maxX + 1 - minX) + minX, r.nextInt(maxY + 1 - minY) + minY));
		else if(chance >= 40 && chance < 85)
			GameState.currentManager.addEntity(new FastEnemy(r.nextInt(maxX + 1 - minX) + minX, r.nextInt(maxY + 1 - minY) + minY));
		else
			GameState.currentManager.addEntity(new Turret(r.nextInt(maxX + 1 - minX) + minX, r.nextInt(maxY + 1 - minY) + minY));

	}
}
