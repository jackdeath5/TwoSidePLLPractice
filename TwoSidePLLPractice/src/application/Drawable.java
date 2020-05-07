package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Drawable {

	public void draw(GraphicsContext g); //All objects can be drawn

	public void setColor(Color newColor); //All object can have their color changed

}
