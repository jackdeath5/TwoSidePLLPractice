package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Top implements Drawable {

	final double x;
	final double y;
	final double l;
	Color color;
	double topY;

	//private final double squareSize = 10;
	public Top(double x, double y,double length,Color color) {
		this.x = x;
		this.y = y;
		this.l = length;
		this.color = color;
	}

	//When NO FILL COLOR is needed - PRIMARILY FOR TESTING
	public Top(double x, double y,double length) {
		this.x = x;
		this.y = y;
		this.l = length;
		this.color = Color.rgb(0, 0, 0, 0.0); //Transparent Color
	}

	public void setColor(Color newColor) {
		this.color = newColor;
	}

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
