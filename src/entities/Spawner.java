package entities;

import entities.enemies.Chaser;
import states.GameState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Bobby on 3/26/2017.
 */
public class Spawner{

	private Timer timer;

	public Spawner(){
		timer = new Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spawn();
			}
		});
		timer.start();
	}

	public void update(){
		if(Math.random() > .95)
			timer.setDelay((int)(timer.getDelay()/1.5));
	}

	/**
	 * Spawns a random enemy
	 */
	public void spawn(){
		GameState.currentManager.addEntity(new Chaser(10, 10));
	}
}
