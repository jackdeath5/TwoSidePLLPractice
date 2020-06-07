package application;

//import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
//import java.math.*;

//These will be the squared at the top, each assigned by a number from left to right: 0 | 1 | 2 || 3 | 4 | 5
/**
 * This class is used to isometrically draw a 1x1 square on the side of the cube drawings. It implements Drawable 
 * so it can be added to an ArrayList with the other parts of the cube so that all of the  different parts of a 
 * cube can be drawn through a simple execution. It is one of the 3 classes used to create a cube drawing.
 * @author Jack Lanois
 */
public class SideSquare implements Drawable {

	/** The X value of the bottom left/right corner (depending on 
	 * whether l is greater than 0 or less than 0 respectively) of the SideSquare.*/
	private final double x;
	/** The X value of the bottom left/right corner (depending on 
	 * whether l is greater than 0 or less than 0 respectively) of the SideSquare.*/
	private final double y;
	/** The total length of the side of side, as measured along the side of the cube. (Diagonally to 
	 * match the angle of the side) Can be negative, which would indicate the side is being drawn to 
	 * the left instead of to the right.*/
	private final double l;
	/** The Color of the square.*/
	Color color;
	
	/**
	 * Constructor for the SideSquare, based on the bottom left/right corner (depending on 
	 * whether l is greater than 0 or less than 0 respectively) of the square. It will be sized 
	 * (diagonally) as per the length given to it, and color the squares in it as specified.
	 * @param x Double representing the X position of the bottom left/right corner (depending on 
	 * whether l is greater than 0 or less than 0 respectively) of the SideSquare.
	 * @param y Double representing the Y position of the bottom left/right corner (depending on 
	 * whether l is greater than 0 or less than 0 respectively) of the SideSquare.
	 * @param length Length of the diagonally, as the side is drawn isometrically. A negative value 
	 * will cause the side to be drawn to the left instead of to the right.
	 * @param color The Color of the square.
	 */
	public SideSquare(double x, double y,double length,Color color) {
		this.x = x;
		this.y = y;
		this.l = length;
		this.color = color;
	}

	//When NO FILL COLOR is needed - PRIMARILY FOR TESTING
	/**
	 * Constructor for the SideSquare, based on the bottom left/right corner (depending on 
	 * whether l is greater than 0 or less than 0 respectively) of the square. It will be sized 
	 * (diagonally) as per the length given to it, and color the squares in it as specified. It 
	 * is transparent for color. This method with this signature was primarily used for testing.
	 * @param x Double representing the X position of the bottom left/right corner (depending on 
	 * whether l is greater than 0 or less than 0 respectively) of the SideSquare.
	 * @param y Double representing the Y position of the bottom left/right corner (depending on 
	 * whether l is greater than 0 or less than 0 respectively) of the SideSquare.
	 * @param length Length of the diagonally, as the side is drawn isometrically. A negative value 
	 * will cause the side to be drawn to the left instead of to the right.
	 */
	public SideSquare(double x, double y,double length) {
		this.x = x;
		this.y = y;
		this.l = length;
		this.color = Color.rgb(0, 0, 0, 0.0); //Transparent Color
	}

	/**
	 * This method is implemented from Drawable, and is used to set the Color of the 
	 * square. SideSquare will need to be redrawn in order for the new colors to show.
	 */
	public void setColor(Color newColor) {
		this.color = newColor;
	}

	/**
	 * This is a method implemented by the Drawable, and is used to draw the SideSquare 
	 * using the specified GraphicsContext. The Canvas it will be drawn on is the 
	 * one that the GraphicsContext is used for.
	 * @param g The GraphicsContext used to draw the SideSquare.
	 */
	public void draw(GraphicsContext g) {
		g.setFill(color);
		g.setStroke(Color.rgb(0, 0, 0)); //Change color to black for outline
		int flip = 1; //MADE IN ORDER TO FLIP THE ISOMETRY IF NEGATIVE LENGTH IS GIVEN
		double[] xs = new double[]{x,x,x+l*Math.cos(Math.PI/6),x+l*Math.cos(Math.PI/6)};
		if (l < 0) {flip = -1;} //Makes L positive if it's negative
		double[] ys = new double[] {y,y+l*flip,y+l*flip-flip*l*Math.sin(Math.PI/6),y-flip*l*Math.sin(Math.PI/6)};
		g.fillPolygon(xs,ys,4);
//		double[] xs = new double[]{x,x,x+l*Math.cos(Math.PI/6),x+l*Math.cos(Math.PI/6)};
//		//Already flipped if L's negative
//		double[] ys = new double[] {y,y+l*flip,y+l*flip-flip*l*Math.sin(Math.PI/6),y-flip*l*Math.sin(Math.PI/6)};
		g.strokePolygon(xs,ys,4);
	}

}
