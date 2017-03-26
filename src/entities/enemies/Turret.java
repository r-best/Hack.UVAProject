package entities.enemies;

import graphics.Assets;
import states.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Bobby on 3/26/2017.
 */
public class Turret extends Enemy{
	public Turret(int x, int y) {
		super(x, y, Assets.getEntityAnimation("turret"));
		isSolid = false;

		new Timer(7000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameState.currentManager.addEntity(new Projectile(x, y));
			}
		}).start();
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