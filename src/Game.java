import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

/**
 * Created by Bobby on 3/25/2017.
 */
public class Game extends Canvas{
	private static int Width, Height;
	private JFrame frame;

	public Game(Dimension dimension){
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

	public void Init(){
		new Timer(33, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Update();
				Draw();
			}
		}).start();
	}

	public void Update(){

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
		graphics.setColor(Color.cyan);
		graphics.fillRect(0, 0, Width, Height);

		//Begin Draw

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

	public static void main(String[] args){
		new Game(new Dimension(1600, 1200));
	}
}
