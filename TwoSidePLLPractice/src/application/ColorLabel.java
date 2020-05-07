package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ColorLabel extends Label {

    	public ColorLabel(String label, int red, int green, int blue) {
    		super(label);
    		//setStyle("-fx-font-family:monospace;-fx-text-fill: rgb(@red,@green,@blue);");
    		setStyle("-fx-font-family:monospace;");
    		super.setTextFill(Color.rgb(red,green,blue));
    	}
    	public ColorLabel(String label) {
    		super(label);
    		//setStyle("-fx-font-family:monospace;-fx-text-fill: rgb(0,0,0);");
    		setStyle("-fx-font-family:monospace;");
    		super.setTextFill(Color.rgb(0,0,0));
    	}

    	public void changeColor(int red, int green, int blue) {
    		//setStyle("-fx-text-fill: rgb(red,green,blue)");
    		super.setTextFill(Color.rgb(red,green,blue));
    	}
    } //END OF COLORLABEL
