package HackUVAProject;

import graphics.Assets;
import states.GameState;
import states.StateManager;
import utilities.KeyManager;
import utilities.MouseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/**
 * Created by Bobby on 3/25/2017.
 */
public class Game extends Canvas{
	private static int Width, Height;
	private static JFrame frame;

	public Game(Dimension dimension) throws IOException, FontFormatException {
		this.setPreferredSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setFocusable(false);

		Width = dimension.width;
		Height = dimension.height;

		frame = new JFrame("Hack.UVA Project");
		frame.setSize(Width, Height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(this);
		frame.pack();

		Init();
	}

	public void Init() throws IOException, FontFormatException {
		frame.addKeyListener(KeyManager.getInstance());
		this.addMouseListener(MouseManager.getInstance());

		Assets.init();

		StateManager.gameState = new GameState();
		StateManager.setState(StateManager.gameState);

		new Timer(33, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Update();
				Draw();
			}
		}).start();
	}

	public void Update(){
		StateManager.update();
	}

	public void Draw(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics2D graphics = (Graphics2D)bs.getDrawGraphics();
		//Clear Screen
		graphics.clearRect(0, 0, Width, Height);

		//Color Background
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, Width, Height);

		//Begin Draw
		StateManager.draw(graphics);

		//Stop draw
		bs.show();
		graphics.dispose();
	}

	public static int getGameWidth(){
		return Width;
	}
	public static int getGameHeight(){
		return Height;
	}

	public static double getWindowX(){
		return frame.getX();
	}

	public static double getWindowY(){
		return frame.getY();
	}

	public static void main(String[] args){
		try {
			new Game(new Dimension(1600, 1200));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
