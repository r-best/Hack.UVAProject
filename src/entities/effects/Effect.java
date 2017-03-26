package entities.effects;

import entities.Entity;
import graphics.Animation;
import states.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Bobby on 3/26/2017.
 */
public class Effect extends Entity{
	private int duration;

	public Effect(float x, float y, Animation anim, int duration){
		super(x, y, anim);
		isSolid = false;

		Effect temp = this;

		Timer timer = new Timer(duration, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				GameState.currentManager.removeEffect(temp);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
	}
}