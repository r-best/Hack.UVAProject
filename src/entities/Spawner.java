package entities;

import entities.enemies.Chaser;
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

	private Timer timer;

	public Spawner(){
		timer = new Timer(5000, new ActionListener() {
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
		if(r.nextInt() < 80)
			GameState.currentManager.addEntity(new Chaser(r.nextInt(50 + 1 + 50) - 50, r.nextInt(50 + 1 + 50) - 50));
		else
			GameState.currentManager.addEntity(new Turret(r.nextInt(50 + 1 + 50) - 50, r.nextInt(50 + 1 + 50) - 50));
	}
}
