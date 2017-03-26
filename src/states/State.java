package states;

import java.awt.*;

/**
 * Created by Bobby on 3/25/2017.
 */
public interface State{

	void update();

	void draw(Graphics2D g);

	void onEnter();

	void onExit();
}