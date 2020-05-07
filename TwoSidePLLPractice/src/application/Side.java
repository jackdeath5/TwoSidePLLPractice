package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//These will be the 2x3 blocks at the bottom of the cube
public class Side implements Drawable {

	final double x;
	final double y;
	final double l;
	Color color;
	double topX1;
	double topY1;
	double topX2;
	double topY2;
	double topX3;
	double topY3;


	//private final double squareSize = 10;
	public Side(double x, double y,double length,Color color) {
		this.x = x;
		this.y = y;
		this.l = length;
		this.color = color;
		double flip = l/Math.abs(l);
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

	public void setColor(Color newColor) {
		this.color = newColor;
	}

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
