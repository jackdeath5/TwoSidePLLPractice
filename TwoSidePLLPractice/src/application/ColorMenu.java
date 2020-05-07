package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ColorMenu extends Menu {

		private Slider redSlider;
		private TextField redtf;
	    private Slider greenSlider;
	    private TextField greentf;
	    private Slider blueSlider;
	    private TextField bluetf;
	    private Canvas colorDisp;
	    private GraphicsContext disPen;
	    private HBox redbox;
	    private HBox greenbox;
	    private HBox bluebox;

		//Will Create the color choosing menu items in Debug mode
		public ColorMenu(String name, int r, int g, int b) {

			super(name);

			redSlider = new Slider(0,255,r);
	        redtf = new TextField("" + (int) redSlider.getValue());
	        redtf.setPrefWidth(40);
	        greenSlider = new Slider(0,255,g);
	        greentf = new TextField("" + (int) greenSlider.getValue());
	        greentf.setPrefWidth(40);
	        blueSlider = new Slider(0,255,b);
	        bluetf = new TextField("" + (int) blueSlider.getValue());
	        bluetf.setPrefWidth(40);
	        colorDisp = new Canvas(232,25);
	        disPen = colorDisp.getGraphicsContext2D();
	        redbox = new HBox();
	        greenbox = new HBox();
	        bluebox = new HBox();

	        ////////////////////////////////////////////

			ColorLabel redLabel = new ColorLabel("Red:   ",(int) redSlider.getValue(),0,0);

			redSlider.valueProperty().addListener((ov, old_val, new_val) -> {
				redtf.setText("" + ov.getValue().intValue());
				redLabel.changeColor(ov.getValue().intValue(),0,0);
				//redtf.setText("" + (int) ov.getValue());
				//disPen.setFill(Color.rgb(Integer.parseInt(redtf.getText()),Integer.parseInt(greentf.getText()),Integer.parseInt(bluetf.getText())));
				disPen.setFill(Color.WHITE);
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
				disPen.setFill(Color.rgb((int) redSlider.getValue(),(int) greenSlider.getValue(),(int) blueSlider.getValue()));
				//COLOR = (Color.rgb((int) redSlider.getValue(),(int) greenSlider.getValue(),(int) blueSlider.getValue()));
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
			});

			redtf.textProperty().addListener((ov, old_val, new_val) -> {
				if(!new_val.matches("\\d*")) {redtf.setText(new_val.replaceAll("[^\\d]", ""));} //Gets Rid of Non-Numerical Characters
				if(new_val.length() > 3) {redtf.setText(redtf.getText().substring(0, 3));}
				if(ov.getValue().isEmpty()) {redSlider.setValue(0);}
				else {redSlider.setValue(Double.parseDouble(ov.getValue()));}
			});

			redbox.getChildren().addAll(redLabel,redSlider,redtf);
			////////////////////

			ColorLabel greenLabel = new ColorLabel("Green: ",0,(int) greenSlider.getValue(),0);
			greenSlider.valueProperty().addListener((ov, old_val, new_val) -> {
				greentf.setText("" + ov.getValue().intValue());
				greenLabel.changeColor(0,ov.getValue().intValue(),0);
				disPen.setFill(Color.WHITE);
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
				disPen.setFill(Color.rgb((int) redSlider.getValue(),(int) greenSlider.getValue(),(int) blueSlider.getValue()));
				//COLOR = (Color.rgb((int) redSlider.getValue(),(int) greenSlider.getValue(),(int) blueSlider.getValue()));
				//outColor = (Color.rgb((int) redSlider.getValue(),(int) greenSlider.getValue(),(int) blueSlider.getValue()));
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
			});

			greentf.textProperty().addListener((ov, old_val, new_val) -> {
				if(!new_val.matches("\\d*")) {greentf.setText(new_val.replaceAll("[^\\d]", ""));} //Gets Rid of Non-Numerical Characters
				if(new_val.length() > 3) {greentf.setText(greentf.getText().substring(0, 3));}
				if(ov.getValue().isEmpty()) {greenSlider.setValue(0);}
				else {greenSlider.setValue(Double.parseDouble(ov.getValue()));}
			});

			greenbox.getChildren().addAll(greenLabel,greenSlider,greentf);
			////////////////////

			ColorLabel blueLabel = new ColorLabel("Blue:  ",0,0,(int) blueSlider.getValue());

			blueSlider.valueProperty().addListener((ov, old_val, new_val) -> {
				bluetf.setText("" + ov.getValue().intValue());
				blueLabel.changeColor(0,0,ov.getValue().intValue());
				disPen.setFill(Color.WHITE);
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
				disPen.setFill(Color.rgb((int) redSlider.getValue(),(int) greenSlider.getValue(),(int) blueSlider.getValue()));
				//COLOR = (Color.rgb((int) redSlider.getValue(),(int) greenSlider.getValue(),(int) blueSlider.getValue()));
				disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());
			});

			bluetf.textProperty().addListener((ov, old_val, new_val) -> {
				if(!new_val.matches("\\d*")) {bluetf.setText(new_val.replaceAll("[^\\d]", ""));} //Gets Rid of Non-Numerical Characters
				if(new_val.length() > 3) {bluetf.setText(bluetf.getText().substring(0, 3));}
				if(ov.getValue().isEmpty()) {blueSlider.setValue(0);}
				else {blueSlider.setValue(Double.parseDouble(ov.getValue()));}
				});

			bluebox.getChildren().addAll(blueLabel,blueSlider,bluetf);
			////////////////////

			//Sets the color of the canvas initially
			disPen.setFill(Color.rgb((int) redSlider.getValue(),(int) greenSlider.getValue(),(int) blueSlider.getValue()));
			disPen.fillRect(0, 0, colorDisp.getWidth(), colorDisp.getHeight());

			//Sets the out color when menu is closed
			//WILL HAVE TO BE SET INDIVIDUALLY TO EACH COLOR OUTSIDE OF DEFINITION
//			setOnHidden( e -> {
//				//outColor = (Color.rgb((int) redSlider.getValue(),(int) greenSlider.getValue(),(int) blueSlider.getValue()));
//			});

			//Add everything as customMenuItems to menu as items
			getItems().addAll(new CustomMenuItem(redbox),new CustomMenuItem(greenbox),new CustomMenuItem(bluebox),new CustomMenuItem(colorDisp));

		} //END OF CONSTRUCTOR

		//Gets the Color of the sliders
		public Color getColor() {
			return Color.rgb((int) redSlider.getValue(),(int) greenSlider.getValue(),(int) blueSlider.getValue());
		}

		public void setColor(int r, int g, int b) {
			redSlider.setValue(r);
			greenSlider.setValue(g);
			blueSlider.setValue(b);
		}

	} //END OF COLORMENU