package graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Animation {
	private int index;
	private BufferedImage[] frames;

	public Animation(BufferedImage[] frames){
		this.frames = frames;
		index = 0;

		new Timer(150, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				index++;
				if(index >= frames.length)
					index = 0;
			}
		}).start();
	}

	public BufferedImage getCurrentFrame(){
		return frames[index];
	}

	public BufferedImage getFrame(int x){
		return frames[x];
	}
}