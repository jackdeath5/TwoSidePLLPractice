package application;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class creates an HBox with everything needed to setup a color. These are the color selecting sections on the 
 * main menu. In the HBox exists the "____ Face: " label, the color square, followed by a set of 3 ColorLables each 
 * paired with a TextField for the RGB of the color that is displayed in the ColorSquare. In the main file, TwoSidePLLPractice, 
 * InitColors are used to make set the individual colors of the cube. Changing one of them will change that change that color 
 * for that face whenever a cube is drawn.
 * @author Jack Lanois
 */
public class InitColor extends HBox {

		/** The general name of the color of the face. "Face:" is added onto it.**/
		private Label nameLabel;
		/** The TextField representing the Red (R) value for the object.**/
		private TextField redtf;
		/** The ColorLabel paired with redtf that changes hues to reflect the Red (R) value in redtf.**/
		private ColorLabel redLabel;
		/** The TextField representing the Green (G) value for the object.**/
		private TextField greentf;
		/** The ColorLabel paired with greentf that changes hues to reflect the Green (G) value in greentf.**/
		private ColorLabel greenLabel;
		/** The TextField representing the Blue (B) value for the object.**/
		private TextField bluetf;
		/** The ColorLabel paired with bluetf that changes hues to reflect the Blue (B) value in bluetf.**/
		private ColorLabel blueLabel;
		/** The Canvas used to display the color of the combined RGB values from the RGB TextFields.**/
		private Canvas colorDisp;
		/** The GraphicsContext used to draw on the Canvas to display the color of the combined RGB values.**/
		private GraphicsContext disPen;

		/**
		 * Constructor for InitColor. It creates a object which is an HBox containing a "<SPECIFIED> Face: " label, the color square, 
		 * followed by a set of 3 ColorLables each paired with a TextField for the RGB of the color that is displayed in the ColorSquare.
		 * @param name String input for the name of the face color. " Face:" will be added after it automatically.
		 * @param r Int that's between 0 and 255 representing the red (R) value of the default color.
		 * @param g Int that's between 0 and 255 representing the green (G) value of the default color.
		 * @param b Int that's between 0 and 255 representing the blue (B) value of the default color.
		 */
		public InitColor(String name, int r, int g, int b) {
			nameLabel = new Label(name + " Face:"); //Set name of label, with a space between it and the next part
			nameLabel.setFont(new Font("Segoe UI", 12));
			nameLabel.setMinWidth(75);
			colorDisp = new Canvas(25,25); //Used to display the colors
			disPen = colorDisp.getGraphicsContext2D();
			redtf = new TextField("" + r);
			redtf.setPrefWidth(40.0);
			redLabel = new ColorLabel("  R: ",r,0,0);
			greentf = new TextField("" + g);
			greentf.setPrefWidth(40.0);
			greenLabel = new ColorLabel("  G: ",0,g,0);
			bluetf = new TextField("" + b);
			bluetf.setPrefWidth(40.0);
			blueLabel = new ColorLabel("  B: ",0,0,b);

			//Initial drawing of the canvas
			disPen.setFill(Color.rgb(Integer.parseInt(redtf.getText()),Integer.parseInt(greentf.getText()),Integer.parseInt(bluetf.getText())));
			disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());

			redtf.textProperty().addListener((ov, old_val, new_val) -> {
				if(!new_val.matches("\\d*")) {redtf.setText(new_val.replaceAll("[^\\d]", "")); new_val = new_val.replaceAll("[^\\d]", "");} //Gets Rid of Non-Numerical Characters
				if(new_val.isEmpty()) {redtf.setText("0"); new_val = "0";} //WILL SET TO 0  IF NOTHING IS IN THE BOX
				if(new_val.length() > 3) {redtf.setText(redtf.getText().substring(0, 3)); new_val = redtf.getText();} //to keep number at 3 digits MAX
				if(Integer.parseInt(new_val) > 255) {redtf.setText("255"); new_val ="255";}//{new_val = "100";} //Sets the text field to 100 if number input is larger than that
				if(new_val.charAt(0) == '0' && new_val.length() >= 2) {redtf.setText(redtf.getText().substring(1, new_val.length()));}
				//Change the  color of the textfield label
//				System.out.println(new_val);
				redLabel.changeColor(Integer.parseInt(new_val),0,0);
				//Change the color of the canvas
				disPen.setFill(Color.WHITE);
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
				disPen.setFill(Color.rgb(Integer.parseInt(new_val),Integer.parseInt(greentf.getText()),Integer.parseInt(bluetf.getText())));
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
			});

			greentf.textProperty().addListener((ov, old_val, new_val) -> {
				if(!new_val.matches("\\d*")) {greentf.setText(new_val.replaceAll("[^\\d]", "")); new_val = new_val.replaceAll("[^\\d]", "");} //Gets Rid of Non-Numerical Characters
				if(new_val.isEmpty()) {greentf.setText("0"); new_val = "0";} //WILL SET TO 0  IF NOTHING IS IN THE BOX
				if(new_val.length() > 3) {greentf.setText(greentf.getText().substring(0, 3)); new_val = greentf.getText();} //to keep number at 3 digits MAX
				if(Integer.parseInt(new_val) > 255) {greentf.setText("255"); new_val ="255";}//{new_val = "100";} //Sets the text field to 100 if number input is larger than that
				if(new_val.charAt(0) == '0' && new_val.length() >= 2) {greentf.setText(greentf.getText().substring(1, new_val.length()));}
				//Change the  color of the textfield label
				greenLabel.changeColor(0,Integer.parseInt(new_val),0);
				//Change the color of the canvas
				disPen.setFill(Color.WHITE);
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
				disPen.setFill(Color.rgb(Integer.parseInt(redtf.getText()),Integer.parseInt(new_val),Integer.parseInt(bluetf.getText())));
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
			});

			bluetf.textProperty().addListener((ov, old_val, new_val) -> {
				if(!new_val.matches("\\d*")) {bluetf.setText(new_val.replaceAll("[^\\d]", "")); new_val = new_val.replaceAll("[^\\d]", "");} //Gets Rid of Non-Numerical Characters
				if(new_val.isEmpty()) {bluetf.setText("0"); new_val = "0";} //WILL SET TO 0  IF NOTHING IS IN THE BOX
				if(new_val.length() > 3) {bluetf.setText(bluetf.getText().substring(0, 3)); new_val = bluetf.getText();} //to keep number at 3 digits MAX
				if(Integer.parseInt(new_val) > 255) {bluetf.setText("255"); new_val ="255";}//{new_val = "100";} //Sets the text field to 100 if number input is larger than that
				if(new_val.charAt(0) == '0' && new_val.length() >= 2) {bluetf.setText(bluetf.getText().substring(1, new_val.length()));}
				//Change the  color of the textfield label
				blueLabel.changeColor(0,Integer.parseInt(new_val),0);
				//Change the color of the canvas
				disPen.setFill(Color.WHITE);
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
				disPen.setFill(Color.rgb(Integer.parseInt(redtf.getText()),Integer.parseInt(greentf.getText()),Integer.parseInt(new_val)));
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
			});
			super.setAlignment(Pos.CENTER);
			super.getChildren().addAll(nameLabel,colorDisp,new Label("  "),redLabel,redtf,greenLabel,greentf,blueLabel,bluetf);
		} //End of constructor

		/**
		 * Gets the color of the combined RGB values from values within the TextFields, which is also the same color 
		 * shown in the color box.
		 * @return Color from the RGB values from the TextFields.
		 */
		public Color getColor() {
			return Color.rgb(Integer.parseInt(redtf.getText()),Integer.parseInt(greentf.getText()),Integer.parseInt(bluetf.getText()));
		}

		/**
		 * Returns the value in the Red (R) TextField (redtf).
		 * @return Int of the value in the Red (R) TextField.
		 */
		public int getRedInt() {return Integer.parseInt(redtf.getText());}
		/**
		 * Returns the value in the Green (G) TextField (greentf).
		 * @return Int of the value in the Green (G) TextField.
		 */
		public int getGreenInt() {return Integer.parseInt(greentf.getText());}
		/**
		 * Returns the value in the Blue (B) TextField (bluetf).
		 * @return Int of the value in the Blue (B) TextField.
		 */
		public int getBlueInt() {return Integer.parseInt(bluetf.getText());}
		
		/**
		 * Method used to set the color of the InitColor to an entirely different one, as specified by the 
		 * input RGB values.
		 * @param red Int that's between 0 and 255 representing the red (R) value of the default color.
		 * @param green Int that's between 0 and 255 representing the green (G) value of the default color.
		 * @param blue Int that's between 0 and 255 representing the blue (B) value of the default color.
		 */
		public void setColor(int red, int green, int blue) {
			if(red > 255 || green > 255 || blue > 255) {
				throw new IllegalArgumentException("Colors cannot be greater than 255");
				//Maybe make it so colors greater than 255 will be set to 255?
			}
			redtf.setText(red + "");
			greentf.setText(green + "");
			bluetf.setText(blue + "");
		}

	} //END OF INITCOLOR CLASS