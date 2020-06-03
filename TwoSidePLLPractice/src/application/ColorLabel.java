package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * ColorLabel extends the Label class and is essentially  just a label that can have the color of its font changed easily. It 
 * is used by ColorMenu and InitColor for their labels, as they change the color of those often.
 * @author Jack Lanois
 */
public class ColorLabel extends Label {
	
		/**
		 * Constructor for the ColorLabel for creating the label with a specific initial color specified in RGB in values 0-255.
		 * @param label The String for the text of the label.
		 * @param red Int that's between 0 and 255 representing the red (R) value of the color.
		 * @param green Int that's between 0 and 255 representing the green (G) value of the color.
		 * @param blue Int that's between 0 and 255 representing the blue (B) value of the color.
		 */
    	public ColorLabel(String label, int red, int green, int blue) {
    		super(label);
    		//setStyle("-fx-font-family:monospace;-fx-text-fill: rgb(@red,@green,@blue);");
    		setStyle("-fx-font-family:monospace;");
    		super.setTextFill(Color.rgb(red,green,blue));
    	}
    	
    	/**
    	 * Default constructor for the label that creates it with an initially black color.
    	 * @param label The String for the text of the label.
    	 */
    	public ColorLabel(String label) {
    		super(label);
    		//setStyle("-fx-font-family:monospace;-fx-text-fill: rgb(0,0,0);");
    		setStyle("-fx-font-family:monospace;");
    		super.setTextFill(Color.rgb(0,0,0));
    	}
    	
    	/**
    	 * Method used to more easily change the color of the label.
    	 * @param red Int that's between 0 and 255 representing the red (R) value of the color.
		 * @param green Int that's between 0 and 255 representing the green (G) value of the color.
		 * @param blue Int that's between 0 and 255 representing the blue (B) value of the color.
    	 */
    	public void changeColor(int red, int green, int blue) {
    		//setStyle("-fx-text-fill: rgb(red,green,blue)");
    		super.setTextFill(Color.rgb(red,green,blue));
    	}
    } //END OF COLORLABEL
