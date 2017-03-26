package states;

import HackUVAProject.Game;

import java.awt.*;

/**
 * Created by Bobby on 3/26/2017.
 */
public class GameOverState implements State {
	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		StateManager.gameState.draw(g);
		g.setFont(g.getFont().deriveFont(100f));
		g.drawString("GAME OVER", Game.getGameWidth()/2-g.getFontMetrics().stringWidth("GAME OVER")/2, Game.getGameHeight()/2-g.getFontMetrics().getHeight()/2);
	}

	@Override
	public void onEnter() {

	}

	@Override
	public void onExit() {

	}
}
