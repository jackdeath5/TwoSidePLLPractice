package application;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class InitColor extends HBox {

		private Label nameLabel;
		private TextField redtf;
		private ColorLabel redLabel;
		private TextField greentf;
		private ColorLabel greenLabel;
		private TextField bluetf;
		private ColorLabel blueLabel;
		private Canvas colorDisp;
		private GraphicsContext disPen;

		public InitColor(String name, int r, int g, int b) {
			nameLabel = new Label(name + " Face:  "); //Set name of label, with a space between it and the next part
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
				if(!new_val.matches("\\d*")) {redtf.setText(new_val.replaceAll("[^\\d]", ""));} //Gets Rid of Non-Numerical Characters
				if(new_val.isEmpty()) {redtf.setText("0"); new_val = "0";} //WILL SET TO 0  IF NOTHING IS IN THE BOX
				if(new_val.length() > 3) {redtf.setText(redtf.getText().substring(0, 3)); new_val = redtf.getText();} //to keep number at 3 digits MAX
				if(Integer.parseInt(new_val) > 255) {redtf.setText("255"); new_val ="255";}//{new_val = "100";} //Sets the text field to 100 if number input is larger than that
				if(new_val.charAt(0) == '0' && new_val.length() >= 2) {redtf.setText(redtf.getText().substring(1, new_val.length()));}
				//Change the  color of the textfield label
				redLabel.changeColor(Integer.parseInt(new_val),0,0);
				//Change the color of the canvas
				disPen.setFill(Color.WHITE);
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
				disPen.setFill(Color.rgb(Integer.parseInt(new_val),Integer.parseInt(greentf.getText()),Integer.parseInt(bluetf.getText())));
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
			});

			greentf.textProperty().addListener((ov, old_val, new_val) -> {
				if(!new_val.matches("\\d*")) {greentf.setText(new_val.replaceAll("[^\\d]", ""));} //Gets Rid of Non-Numerical Characters
				if(new_val.isEmpty()) {greentf.setText("0"); new_val = "0";} //WILL SET TO 0  IF NOTHING IS IN THE BOX
				if(new_val.length() > 3) {greentf.setText(greentf.getText().substring(0, 3));} //to keep number at 3 digits MAX
				if(Integer.parseInt(new_val) > 255) {greentf.setText("255");}//{new_val = "100";} //Sets the text field to 100 if number input is larger than that
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
				if(!new_val.matches("\\d*")) {bluetf.setText(new_val.replaceAll("[^\\d]", ""));} //Gets Rid of Non-Numerical Characters
				if(new_val.isEmpty()) {bluetf.setText("0"); new_val = "0";} //WILL SET TO 0  IF NOTHING IS IN THE BOX
				if(new_val.length() > 3) {bluetf.setText(bluetf.getText().substring(0, 3));} //to keep number at 3 digits MAX
				if(Integer.parseInt(new_val) > 255) {bluetf.setText("255");}//{new_val = "100";} //Sets the text field to 100 if number input is larger than that
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

		public Color getColor() {
			return Color.rgb(Integer.parseInt(redtf.getText()),Integer.parseInt(greentf.getText()),Integer.parseInt(bluetf.getText()));
		}

		public int getRedInt() {return Integer.parseInt(redtf.getText());}
		public int getGreenInt() {return Integer.parseInt(greentf.getText());}
		public int getBlueInt() {return Integer.parseInt(bluetf.getText());}

	} //END OF INITCOLOR CLASS