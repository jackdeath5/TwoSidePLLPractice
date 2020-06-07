package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class is used to draw the top 3x3 squares of the cube in a diamond-like shape to top the isometrically drawn 
 * sides. It implements Drawable so it can be added to an ArrayList with the other parts of the cube so that all of 
 * the  different parts of a cube can be drawn through a simple execution. It is one of the 3 classes used to create 
 * a cube drawing.
 * @author Jack Lanois
 */
public class Top implements Drawable {

	/** The X position of the bottommost middle corner of the top 3x3 squares.*/
	private final double x;
	/** The Y position of the bottommost middle corner of the top 3x3 blocks.*/
	private final double y;
	/** The total length of the side, as measured along the side of the cube. (Diagonally to 
	 * match the angle of the side). Since it will be a cube in shape, it does not matter 
	 * whether it is positive or negative.*/
	private final double l;
	/** The Color of all of the squares on the top.*/
	Color color;

	/**
	 * Constructor for the top, based on the topmost middle point of the cube. It will be sized 
	 * (diagonally) as per the length given to it, and color the squares in it as specified.
	 * @param x Double representing the X position of the bottommost middle corner of the top 3x3 squares.
	 * @param y Double representing the Y position of the bottommost middle corner of the top 3x3 squares.
	 * @param length Length of the diagonally, as the side is drawn isometrically. Since it will be a cube 
	 * in shape, it does not matter whether it is positive or negative
	 * @param color The Color of all of the squares on the top.
	 */
	public Top(double x, double y,double length,Color color) {
		this.x = x;
		this.y = y;
		this.l = length;
		this.color = color;
	}

	//When NO FILL COLOR is needed - PRIMARILY FOR TESTING
	/**
	 * Constructor for the top, based on the topmost middle point of the cube. It will be sized 
	 * (diagonally) as per the length given to it, and color the squares in it as specified. It 
	 * is transparent for color. This method with this signature was primarily used for testing.
	 * @param x Double representing the X position of the bottommost middle corner of the top 3x3 squares.
	 * @param y Double representing the Y position of the bottommost middle corner of the top 3x3 squares.
	 * @param length Length of the diagonally, as the side is drawn isometrically. Since it will be a cube 
	 * in shape, it does not matter whether it is positive or negative
	 */
	public Top(double x, double y,double length) {
		this.x = x;
		this.y = y;
		this.l = length;
		this.color = Color.rgb(0, 0, 0, 0.0); //Transparent Color
	}

	/**
	 * This method is implemented from Drawable, and is used to set the Color of the 
	 * top squares. Top will need to be redrawn in order for the new colors to show.
	 */
	public void setColor(Color newColor) {
		this.color = newColor;
	}

	/**
	 * This is a method implemented by the Drawable, and is used to draw the Top 
	 * using the specified GraphicsContext. The Canvas it will be drawn on is the 
	 * one that the GraphicsContext is used for.
	 * @param g The GraphicsContext used to draw the Top.
	 */
	public void draw(GraphicsContext g) {
		g.setFill(color);
		g.setStroke(Color.rgb(0, 0, 0)); //Change color to black for outline

		double rowX = x;
		double rowY = y;

		for(int row=0;row<3;row++) {
			double startX = rowX;
			double startY = rowY;

			for(int k=0;k<3;k++) {
				double[] xs = new double[]{startX,startX+l*Math.cos(Math.PI/6),startX,startX-l*Math.cos(Math.PI/6)};
				double[] ys = new double[] {startY,startY-l*Math.sin(Math.PI/6),startY-2*l*Math.sin(Math.PI/6),startY-l*Math.sin(Math.PI/6)};
				g.fillPolygon(xs,ys,4);
				g.strokePolygon(xs,ys,4);
				startX += l*Math.cos(Math.PI/6); //Increment X for right side
				startY += -l*Math.sin(Math.PI/6); //Increment Y
			} //END OF DRAWING SET OF SQUARES

			rowX += -l*Math.cos(Math.PI/6);
			rowY += -l*Math.sin(Math.PI/6);

		} //END OF DRAWING ROWS


	} //END OF DRAW

}
