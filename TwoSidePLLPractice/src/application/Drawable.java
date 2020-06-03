package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This is the interface implemented by the different classes created to draw different parts of 
 * the cube when needed. Those classes were split up to make drawing the different sides of the cube 
 * with different colors easier by going through an ArrayList to draw them.
 * @author Jack Lanois
 */
public interface Drawable {

	/**
	 * The method used to draw the piece of the cube.
	 * @param g The GraphicsContext used for the Canvas to draw on.
	 */
	public void draw(GraphicsContext g); //All objects can be drawn

	/**
	 * Sets the Color of the GraphicsContext to essentially change the color of something on the cube.
	 * @param newColor The Color to change the color to.
	 */
	public void setColor(Color newColor); //All object can have their color changed

}
