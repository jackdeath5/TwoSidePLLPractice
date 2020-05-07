package application;

//import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
//import java.math.*;

//These will be the squared at the top, each assigned by a number from left to right: 1 | 2 | 3|| 4 | 5 | 6
public class SideSquare implements Drawable {

	final double x;
	final double y;
	final double l;
	Color color;


	//private final double squareSize = 10;
	public SideSquare(double x, double y,double length,Color color) {
		this.x = x;
		this.y = y;
		this.l = length;
		this.color = color;
	}

	//When NO FILL COLOR is needed - PRIMARILY FOR TESTING
	public SideSquare(double x, double y,double length) {
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
