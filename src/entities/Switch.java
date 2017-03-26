package entities;

import entities.enemies.DisappearingWall;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by Bobby on 3/26/2017.
 */
public class Switch extends Entity{
	private Rectangle2D rect;
	private boolean active = false;
	private int countdown;

	ArrayList<DisappearingWall> walls;

	public Switch(float x, float y) {
		super(x, y, Assets.getEntityAnimation("player"));
		isSolid = false;
		rect = new Rectangle2D.Double(getXInPixels(), getYInPixels(), 100, 100);
		walls = new ArrayList<>();
	}

	public void activate(){
		active = true;
		countdown = 100;
		for(DisappearingWall e : walls)
			e.deactivate();
	}

	public void deactivate(){
		active = false;
		for(DisappearingWall e : walls)
			e.activate();
	}

	public void addWall(DisappearingWall wall){
		walls.add(wall);
	}

	@Override
	public void update() {
		super.update();
		if(collidesWith(GameState.player))
			activate();

		if(countdown > 0)
			countdown -= 2;

		if(countdown <= 0)
			deactivate();

		for(DisappearingWall e : walls)
			e.update();
	}

	@Override
	public void draw(Graphics2D graphics) {
		for(DisappearingWall e : walls)
			e.draw(graphics);

		if(!active)
			graphics.setColor(Color.red);
		else
			graphics.setColor(Color.green);
		graphics.fill(new Rectangle((int)(rect.getX() + Camera.getXOffset()), (int)(rect.getY() + Camera.getYOffset()), (int)rect.getWidth(), (int)rect.getHeight()));
		//graphics.setColor(Color.black);
		//graphics.fill(new Rectangle((int)(this.getBounds().getX() + Camera.getXOffset()), (int)(this.getBounds().getY() + Camera.getYOffset()), (int)this.getBounds().getWidth(), (int)this.getBounds().getHeight()));
	}
}
