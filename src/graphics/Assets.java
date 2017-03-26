package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Assets {

	public static Font timesNewRoman;

	public static int tilesize = 32;
	private static HashMap<String, Animation> entityAnims;

	public static Animation getEntityAnimation(String key){ return entityAnims.get(key); }

	public static void init() throws FontFormatException, IOException{
		timesNewRoman = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/times new roman.ttf"));

		entityAnims = new HashMap<>();
		loadAnimations("src/resources/animations");
		loadSprites("src/resources/sprites");
	}

	private static void loadAnimations(String path) {
		File dir = new File(path);
		if(dir.isDirectory()){
			for(File f : dir.listFiles()){
				if(f.isDirectory())
					loadAnimations(f.getPath());
				if(f.getName().endsWith(".png") || f.getName().endsWith(".jpg")){
					BufferedImage spriteSheet = loadImage(f.getPath().substring(3).replace('\\', '/'));
					if(spriteSheet.getWidth() >= tilesize*4) {
						BufferedImage[] sprites = new BufferedImage[4];
						for (int i = 0; i < 4; i++) {
							sprites[i] = spriteSheet.getSubimage(tilesize, tilesize * i, tilesize, tilesize);
						}
						entityAnims.put(f.getName().substring(0, f.getName().length() - 4), new Animation(sprites));
					}
				}
			}
		}
	}

	private static void loadSprites(String path) {
		File dir = new File(path);
		if(dir.isDirectory()){
			for(File f : dir.listFiles()){
				if(f.isDirectory())
					loadAnimations(f.getPath());
				if(f.getName().endsWith(".png") || f.getName().endsWith(".jpg")){
					BufferedImage spriteSheet = loadImage(f.getPath().substring(3).replace('\\', '/'));
					if(spriteSheet.getWidth() >= tilesize) {
						BufferedImage[] sprites = new BufferedImage[1];
						sprites[0] = spriteSheet.getSubimage(0, 0, tilesize, tilesize);
						entityAnims.put(f.getName().substring(0, f.getName().length() - 4), new Animation(sprites));
					}
				}
			}
		}
	}

	public static BufferedImage loadImage(String path){
		try{
			return ImageIO.read(Assets.class.getResource(path));
		} catch(IOException ex){
			Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}