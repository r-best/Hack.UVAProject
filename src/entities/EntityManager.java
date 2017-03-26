package entities;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import entities.effects.Effect;
import entities.enemies.Chaser;
import entities.enemies.DisappearingWall;
import entities.enemies.Turret;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EntityManager{
	private ArrayList<Entity> entities;
	private ArrayList<Effect> effects;

	public EntityManager(int roomNum){
		entities = new ArrayList<>();
		effects = new ArrayList<>();
		initializeEntities(roomNum);
	}

	public void update(){
		for(Entity e : entities)
			e.update();
		for(Effect e : effects)
			e.update();
	}

	public void draw(Graphics2D graphics){
		for (Entity e : entities)
			e.draw(graphics);
		for(Effect e : effects)
			e.draw(graphics);
	}

	public void addEntity(Entity ent){
		entities.add(ent);
	}
	public void addEffect(Effect eff){ effects.add(eff); }

	public void removeEntity(Entity ent){
		entities.remove(ent);
	}
	public void removeEffect(Effect eff){ effects.remove(eff); }

	public ArrayList<Entity> getEntities(){
		return entities;
	}
	public ArrayList<Effect> getEffects(){ return effects; }

	public void generateRoom(int difficulty){
		Random r = new Random();
		int width = 50, height = 50;

		/*for(int i = -10; i < width+10; i++){
			for(int j =-10; j < height+10; j++){
				if(i <= 0 || i >= width || j <= 0 || j >= height)
					addEntity(new Wall(i, j));
				else {
					int chance = r.nextInt() % 100;
					if(chance > 70)
						addEntity(new Wall(i, j));
				}
			}
		}*/


		int[][] hallways = new int[width*2][height*2];
		for(int h = 0; h < 20; h++){
			boolean horizontal = r.nextBoolean();
			int position;
			if(horizontal) {
				position = Math.abs(r.nextInt() % height - 4);
				for(int i = 0; i < width; i++){
					hallways[i][position] = 1;
					hallways[i][position+1] = 1;
					hallways[i][position+2] = 1;
					hallways[i][position+3] = 1;
				}
			}
			else {
				position = Math.abs(r.nextInt() % width - 4);
				for (int j = 0; j < height; j++) {
					hallways[position][j] = 1;
					hallways[position + 1][j] = 1;
					hallways[position + 2][j] = 1;
					hallways[position + 3][j] = 1;
				}
			}
		}
		for(int i = -1; i < width+10; i++)
			for(int j = -1; j < height+10; j++)
				if(i < 0 || j < 0 || i > width-1 || j > height-1 || hallways[i][j] != 1)
					addEntity(new Wall(i, j));
	}

	/**
	 * Adds the room's entities to the EntityManager from the data in rooms.xml
	 * Called by the EntityManager contructor when a new EntityManager is made, which happens every time a player enters a room they have not entered before
	 * @param roomNum
	 */
	private void initializeEntities(int roomNum){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("src/resources/data/rooms.xml");
			document.normalize();

			//Get correct room
			Element room = (Element)document.getElementsByTagName("room").item(roomNum-1);

			int rowCount = 0;
			//Get all child elements of the room tag & iterate through them
			NodeList entities = room.getElementsByTagName("*");
			for(int i = 0; i < entities.getLength(); i++){
				Element entity = (Element) entities.item(i);

				//Create entity
				switch(entity.getTagName()){
					case "spawnRange":
						Spawner.minX = Integer.parseInt(entity.getAttribute("minx"));
						Spawner.maxX = Integer.parseInt(entity.getAttribute("maxx"));
						Spawner.minY = Integer.parseInt(entity.getAttribute("miny"));
						Spawner.maxY = Integer.parseInt(entity.getAttribute("maxy"));
						break;
					case "wall":
						addEntity(new Wall(
								Integer.parseInt(entity.getAttribute("x")),
								Integer.parseInt(entity.getAttribute("y"))
						));
						break;
					case "chaser":
						addEntity(new Chaser(
								Integer.parseInt(entity.getAttribute("x")),
								Integer.parseInt(entity.getAttribute("y"))
						));
						break;
					case "turret":
						addEntity(new Turret(
								Integer.parseInt(entity.getAttribute("x")),
								Integer.parseInt(entity.getAttribute("y"))
						));
						break;
					case "row":
						String row = entity.getTextContent();
						for(int j = 0; j < row.length(); j++){
							if(row.charAt(j) == '1')
								addEntity(new Wall(j, rowCount));
						}
						rowCount++;
						break;
					case "switch":
						Switch controller = new Switch(
								Integer.parseInt(entity.getAttribute("x")),
								Integer.parseInt(entity.getAttribute("y"))
						);
						addEntity(controller);
						NodeList nodes = entity.getElementsByTagName("vanishwall");
						System.out.println(nodes.getLength());
						for(int j = 0; j < nodes.getLength(); j++){
							DisappearingWall temp = new DisappearingWall(
									Integer.parseInt(((Element)nodes.item(j)).getAttribute("x")),
									Integer.parseInt(((Element)nodes.item(j)).getAttribute("y")));
							controller.addWall(temp);
							addEntity(temp);
						}
						break;
					default:
						break;
				}
			}
		}
		catch (ParserConfigurationException e){
			e.printStackTrace();
		}catch (SAXException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		//generateRoom(0);
	}
}