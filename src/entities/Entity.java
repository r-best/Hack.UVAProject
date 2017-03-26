package entities;

import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameState;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Entity {
	protected Rectangle bounds;
	protected int width, height;
	public float x, y; //stored in PIXELS, not tiles
	public boolean isSolid = true; //to determine if the player can collide with this entity (true by default, subclass must define as false)

	public static final int DEFAULT_WIDTH = 50, DEFAULT_HEIGHT = 50;
	protected double XSpd, YSpd;
	protected Animation anim;
	protected BufferedImage tempAnim, currentFrame;
	public int boundsXOffset, boundsYOffset; //used to shrink the bounds Rectangle into the entity, to reduce the size of the hitbox

	/**
	 * @param x starting X position in tiles
	 * @param y starting Y position in tiles
	 */
	public Entity(int x, int y, int width, int height, Animation anim){
		this.x = x * Assets.tilesize;
		this.y = y * Assets.tilesize;
		this.width = width;
		this.height = height;
		boundsXOffset = (int)(this.width*.3);
		boundsYOffset = (int)(this.height*.3);
		bounds = new Rectangle((int)(this.x + boundsXOffset), (int)(this.y + boundsYOffset), (int)(this.width*.4), (int)(this.height*.6));
		this.anim = anim;
		tempAnim = this.anim.getFrame(0);
	}
	public Entity(int x, int y, Animation anim){
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, anim);
	}

	public void update(){
		currentFrame = anim.getCurrentFrame();
		move();
	}

	public void draw(Graphics2D graphics){
		graphics.drawImage(currentFrame, (int)(x + Camera.getXOffset()), (int)(y + Camera.getYOffset()), null);
		//draw hitbox (for debugging)
		//graphics.fillRect(bounds.x + (int)Camera.getXOffset(), bounds.y + (int)Camera.getYOffset(), bounds.width, bounds.height);
	}

	public void move(){
		if(XSpd != 0 || YSpd != 0) {
			moveX();
			moveY();
			XSpd = 0;
			YSpd = 0;
			bounds.x = (int) x + boundsXOffset;
			bounds.y = (int) y + boundsYOffset;
		}
	}

	public void moveX(){
		if(XSpd > 0){
			bounds.x += XSpd;
			x += XSpd;
			if(collidesWithAnEntity()){
				x -= XSpd;
				bounds.x -= XSpd;
			}
		}
		else if(XSpd < 0){
			bounds.x += XSpd;
			x += XSpd;
			if(collidesWithAnEntity()){
				x -= XSpd;
				bounds.x -= XSpd;
			}
		}
	}

	public void moveY(){
		if(YSpd < 0){
			bounds.y += YSpd;
			y += YSpd;
			if(collidesWithAnEntity()){
				y -= YSpd;
				bounds.y -= YSpd;
			}
		}
		else if(YSpd > 0){
			bounds.y += YSpd;
			y += YSpd;
			if(collidesWithAnEntity()){
				y -= YSpd;
				bounds.y -= YSpd;
			}
		}
	}

	/**
	 * Moves an entity to the given (x, y) tile
	 * @param x x tile coord to move to
	 * @param y y tile coord to move to
	 */
	public void moveTo(int x, int y){
		setXInTiles(x);
		setYInTiles(y);
		bounds.x = (int) this.x + boundsXOffset;
		bounds.y = (int) this.y + boundsYOffset;
	}

	/**
	 * Tests to see if the entity intersects any of the other entities in the room,
	 then if it intersects the player
	 *@return true if any collision, false if none
	 */
	public boolean collidesWithAnEntity(){
		for(Entity ent : GameState.currentManager.getEntities()){
			if(this.bounds.intersects(ent.getBounds()) && ent != this){
				return ent.isSolid;
			}
		}
		if(this.collidesWith(GameState.player) && !this.equals(GameState.player))
			return true;
		return false;
	}

	/**
	 * @param ent any existing entity
	 * @return true if this intersects the passed entity
	 */
	public boolean collidesWith(Entity ent){
		if(this.bounds.intersects(ent.getBounds())){
			return true;
		}
		return false;
	}

	public float getXInPixels(){
		return x;
	}
	public int getXInTiles(){
		return (int)(x /  Assets.tilesize);
	}
	public void setXInPixels(float x){
		this.x = x;
	}
	public void setXInTiles(int x){
		this.x = x * Assets.tilesize;
	}

	public float getYInPixels(){
		return y;
	}
	public int getYInTiles(){
		return (int)(y / Assets.tilesize);
	}
	public void setYInPixels(float y){
		this.y = y;
	}
	public void setYInTiles(int y){
		this.y = y * Assets.tilesize;
	}

	public Rectangle getBounds(){
		return bounds;
	}

	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
}