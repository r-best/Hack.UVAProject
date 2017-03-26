package utilities;

import states.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Bobby on 3/25/2017.
 */
public class MouseManager implements MouseListener{
	private static MouseManager ourInstance = new MouseManager();

	public static MouseManager getInstance() {
		return ourInstance;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		GameState.player.jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
