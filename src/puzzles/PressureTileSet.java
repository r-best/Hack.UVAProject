package puzzles;

import entities.Entity;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameState;

import java.awt.*;
import java.util.Random;

/**
 * Created by Bobby on 3/26/2017.
 */
public class PressureTileSet extends Entity{
	private Tile[][] tiles;
	private float x, y;
	private boolean active;

	public PressureTileSet(float x, float y){
		super(x, y, Assets.getEntityAnimation("player"));
		isSolid = false;
		this.x = x/32;
		this.y = y/32;
		this.width = 500;
		this.height = 500;
		this.bounds = new Rectangle((int)getXInPixels(), (int)getYInPixels(), getWidth(), getHeight());
		start();
		stop();
	}

	public void start(){
		System.out.println("Activating!");
		active = true;
		if(tiles == null){
			tiles = new Tile[10][10];
			for(int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					int random = new Random().nextInt() % 100;
					if(random < 50)
						tiles[i][j] = new PressureTile(getXInPixels()+ i*50, getYInPixels() + j * 50);
					else
						tiles[i][j] = new BrokenTile(getXInPixels()+i*50, getYInPixels()+j*50);
					GameState.currentManager.addPuzzleTile(tiles[i][j]);
				}
			}
		}
	}

	public void stop(){
		System.out.println("Deactivating!");
		active = false;
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++)
				tiles[i][j].deactivate();
	}

	@Override
	public void update() {
		super.update();
		if(!active && collidesWith(GameState.player))
			start();
		if(active && !collidesWith(GameState.player))
			stop();
		if(active)
			for(int i = 0; i < 10; i++)
				for(int j = 0; j < 10; j++)
					tiles[i][j].update();
	}

	@Override
	public void draw(Graphics2D graphics) {
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++)
				tiles[i][j].draw(graphics);
	}

	public abstract class Tile extends Entity{
		protected boolean activated = false;
		public Tile(float x, float y) {
			super(x, y, Assets.getEntityAnimation("player"));
			isSolid = false;
		}

		public void activate(){
			System.out.println("Tile collision!");
			activated = true;
		}
		public void deactivate(){
			activated = false;
		}

		@Override
		public void update() {
			super.update();
			if(this.collidesWith(GameState.player))
				activate();
		}

		@Override
		public void draw(Graphics2D graphics) {
			super.draw(graphics);
		}
	}

	public class BrokenTile extends Tile{
		public BrokenTile(float x, float y) {
			super(x, y);
		}

		@Override
		public void draw(Graphics2D graphics) {

		}
	}

	public class PressureTile extends Tile{
		public PressureTile(float x, float y) {
			super(x, y);
		}

		@Override
		public void update() {
			super.update();
		}

		@Override
		public void draw(Graphics2D graphics) {
			super.draw(graphics);
			if(activated)
				graphics.setColor(Color.green);
			else
				graphics.setColor(Color.black);
			graphics.fill(new Rectangle((int) (x/32 + Camera.getXOffset()), (int) (y/32 + Camera.getYOffset()), 50, 50));
		}
	}
}
