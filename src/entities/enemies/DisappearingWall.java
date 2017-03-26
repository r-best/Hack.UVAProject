package entities.enemies;

import entities.Entity;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameState;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Bobby on 3/26/2017.
 */
public class DisappearingWall extends Entity{

	private Rectangle2D rect;

	public DisappearingWall(float x, float y) {
		super(x, y, Assets.getEntityAnimation("dirt"));
		rect = new Rectangle2D.Double(getXInPixels(), getYInPixels(), 32, 32);
		isSolid = true;
	}

	public void activate(){
		isSolid = true;
	}

	public void deactivate(){
		isSolid = false;
	}

	@Override
	public void update() {
		super.update();
		System.out.println(collidesWith(GameState.player));
	}

	@Override
	public void draw(Graphics2D graphics) {
		if(isSolid)
			graphics.drawImage(anim.getFrame(0), (int)(getXInPixels() + Camera.getXOffset()), (int)(getYInPixels() + Camera.getYOffset()), null);
		//graphics.setColor(Color.black);
		//graphics.fill(new Rectangle((int)(this.getBounds().getX() + Camera.getXOffset()), (int)(this.getBounds().getY() + Camera.getYOffset()), (int)this.getBounds().getWidth(), (int)this.getBounds().getHeight()));
	}
}
