package states;

import java.awt.*;

/**
 * Created by Bobby on 3/25/2017.
 */
public class StateManager{
	private static State currentState;

	public static void setState(State state){
		currentState = state;
	}

	public static void update(){
		currentState.update();
	}

	public static void draw(Graphics2D g){
		currentState.draw(g);
	}
}
