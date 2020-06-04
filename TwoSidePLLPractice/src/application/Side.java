package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//These will be the two 2x3 blocks at the bottom of the cube -- the solved first 2 layers.
/**
 * This class is used to isometrically draw the side 2x3 blocks on the cube drawings. It implements Drawable 
 * so it can be added to an ArrayList with the other parts of the cube so that all of the 
 * different parts of a cube can be drawn through a simple execution.
 * @author Jack Lanois
 */
public class Side implements Drawable {

	/** The X position of the bottommost middle corner of the cube.**/
	private final double x;
	/** The Y position of the bottommost middle corner of the cube.**/
	private final double y;
	/** The total lenght of the side of side, as measured along the side of the cube. (Diagonally to 
	 * match the angle of the side) Can be negative, which would indicate the side is being drawn to 
	 * the left instead of to the right.
	 */
	private final double l;
	/** The Color of all of the squares on the side.**/
	Color color;
	
	/** This is the X value of the bottom right part of where top side square over the 
	 * first column of side squares would be. It is public so that a different class can 
	 * use this to draw the square.*/
	public double topX1;
	/** This is the Y value of the bottom right part of where top side square over the 
	 * first column of side squares would be. It is public so that a different class can 
	 * use this to draw the square.*/
	public double topY1;
	/** This is the X value of the bottom right part of where top side square over the 
	 * second column of side squares would be. It is public so that a different class can 
	 * use this to draw the square.*/
	public double topX2;
	/** This is the Y value of the bottom right part of where top side square over the 
	 * second column of side squares would be. It is public so that a different class can 
	 * use this to draw the square.*/
	public double topY2;
	/** This is the X value of the bottom right part of where top side square over the 
	 * third column of side squares would be. It is public so that a different class can 
	 * use this to draw the square.*/
	public double topX3;
	/** This is the Y value of the bottom right part of where top side square over the 
	 * third column of side squares would be. It is public so that a different class can 
	 * use this to draw the square.*/
	public double topY3;


	//private final double squareSize = 10;
	/**
	 * Constructor for the side, based on the bottommost middle point of the cube. It will be sized 
	 * (diagonally) as per the length given to it, and color the squares in it as specified.
	 * @param x Double representing the X position of the bottommost middle corner of the cube.
	 * @param y Double representing the Y position of the bottommost middle corner of the cube.
	 * @param length Length of the diagonally, as the side is drawn isometrically. A negative value 
	 * will cause the side to be drawn to the left instead of to the right.
	 * @param color The Color of all of the squares on the side.
	 */
	public Side(double x, double y,double length,Color color) {
		this.x = x;
		this.y = y;
		this.l = length;
		this.color = color;
		double flip = l/Math.abs(l); //determines if the length is positive (1) or negative (-1). Made for easier determination and usage later on.
		this.topX1 = this.x;
		this.topY1 = this.y-2*Math.abs(l);
		if(flip>0) {this.topX2 = this.x+Math.abs(l)*Math.cos(Math.PI/6);}
		else {this.topX2 = this.x-Math.abs(l)*Math.cos(Math.PI/6);}
		this.topY2 = this.y-2*Math.abs(l)-Math.abs(l)*Math.sin(Math.PI/6);
		if(flip>0) {this.topX3 = this.x+2*Math.abs(l)*Math.cos(Math.PI/6);}
		else {this.topX3 = this.x-2*Math.abs(l)*Math.cos(Math.PI/6);}
		this.topY3 = this.y-2*Math.abs(l)-2*Math.abs(l)*Math.sin(Math.PI/6);
	}

	//When NO FILL COLOR is needed - PRIMARILY FOR TESTING
	/**
	 * Constructor for the side, based on the bottommost middle point of the cube. It will be sized 
	 * (diagonally) as per the length given to it. It is transparent for color. This method with this 
	 * signature was primarily used for testing.
	 * @param x Double representing the X position of the bottommost middle corner of the cube.
	 * @param y Double representing the Y position of the bottommost middle corner of the cube.
	 * @param length Length of the diagonally, as the side is drawn isometrically. A negative value 
	 * will cause the side to be drawn to the left instead of to the right.
	 */
	public Side(double x, double y,double length) {
		this.x = x;
		this.y = y;
		this.l = length;
		double flip = l/Math.abs(l);
		this.color = Color.rgb(0, 0, 0, 0.0); //Transparent Color
		this.topX1 = this.x;
		this.topY1 = this.y-2*Math.abs(l);
		if(flip>0) {this.topX2 = this.x+Math.abs(l)*Math.cos(Math.PI/6);}
		else {this.topX2 = this.x-Math.abs(l)*Math.cos(Math.PI/6);}
		this.topY2 = this.y-2*Math.abs(l)-Math.abs(l)*Math.sin(Math.PI/6);
		if(flip>0) {this.topX3 = this.x+2*Math.abs(l)*Math.cos(Math.PI/6);}
		else {this.topX3 = this.x-2*Math.abs(l)*Math.cos(Math.PI/6);}
		this.topY3 = this.y-2*Math.abs(l)-2*Math.abs(l)*Math.sin(Math.PI/6);
	}

	/**
	 * This method is implemented from Drawable, and is used to set the Color of the 
	 * squares on the side. Side will need to be redrawn in order for the colors to show.
	 */
	public void setColor(Color newColor) {
		this.color = newColor;
	}

	/**
	 * This is a method implemented by the Drawable, and is used to draw the Side 
	 * using the specified GraphicsContext. The Canvas it will be drawn on is the 
	 * one that the GraphicsContext is used for.
	 */
	public void draw(GraphicsContext g) {
		g.setFill(color);
		g.setStroke(Color.rgb(0, 0, 0)); //Change color to black for outline

		int flip = 1; //MADE IN ORDER TO FLIP THE ISOMETRY IF NEGATIVE LENGTH IS GIVEN
		if (l < 0) {flip = -1;} //Makes L positive if it's negative

		double layerY = y;

		for(int layer=0;layer<2;layer++) {
			double startX = x;
			double startY = layerY;

			for(int k=0;k<3;k++) {
				//{y,y+l,y+l-l*Math.sin(Math.PI/6),y-l*Math.sin(Math.PI/6)}
				//(l*flip)
				double[] xs = new double[]{startX,startX,startX+l*Math.cos(Math.PI/6),startX+l*Math.cos(Math.PI/6)};
				double[] ys = new double[]{startY,startY+(l*flip),startY+(l*flip)-(l*flip)*Math.sin(Math.PI/6),startY-(l*flip)*Math.sin(Math.PI/6)};
				g.fillPolygon(xs,ys,4);
				g.strokePolygon(xs,ys,4);

				if(l < 0) {startX += -Math.abs(l)*Math.cos(Math.PI/6);} //If the length is negative
				else {startX += Math.abs(l)*Math.cos(Math.PI/6);} //If the length is positive
				startY += -Math.abs(l)*Math.sin(Math.PI/6);
			} //END OF DRAWING SET OF SQUARES

			layerY -= Math.abs(l); //Increments Y so next layer is up one
		} //END OF DRAWING ROWS


	} //END OF DRAW
}
