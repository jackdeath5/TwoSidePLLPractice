package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
//import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

//Commented out imports were done so because they weren't used

public class TwoSidePLLPractice extends Application{

	//Define Screen Size
	public double scale = 1; //Best scale is 1.5

	public double screenLength = 575*scale;//475*scale;
	public double screenWidth = 350*scale;
	public double cubeCanvasLength = 350*scale;
	public double cubeCanvasWidth = 350*scale;
	public double cubeSize = 50*scale;
	//public int ss = 500; //Kept public incase I wanted to make it change based on screen size?

	//Necessary
	private BorderPane bp;
	private VBox master; //OverArching VBox
	private Canvas cubeCanvas;
	private GraphicsContext cubePen;
	private Random rand;

	//Rubik's Cube Arrays
	private ArrayList<Drawable> rubiksCube;
	private Color[] faceColors;

	//PLL Things
	private int[][] PLL; //PLL Cases
	private int PLLnum; //Index of the active PLL
	private String[] PLLname; //PLL Case Names
	private int[] activePLL; //Current PLL setup
	private String activePLLFullName; //current full name of the PLL
	private String activePLLname; //current PLL name
	private int[] PLLCaseColors; //The U rotation of the PLL
	private String PLLGuess; //PLL guess, as given by the user with the button
	private int guessNum; //The number of the guess. Will be stored in the bottom left corner
	private int caseCount; //The total number of problems the user will be give, as defined in countBox
	private Color crossColor; //The integer of the crossColor
	private int modeValue; //1 if in debug mode, 0 if not
	private boolean canGuess; //WILL THE BUTTONS WORK OR NAW? - primarily to make countdown timer work

	private ArrayList<int[]> enabledPLLs; //PLL Cases that have been toggled
	private ArrayList<String> enabledPLLNames; //PLL names that have been toggled

	//Define Default Colors
	private Color white;
	private Color red;
	private Color blue;
	private Color green;
	private Color orange;
	private Color yellow;
	private Color[] colors;

  //History Arrays
	private ArrayList<Integer> GuessNumHist; //Record the Number of the guess -> prevents need to auto generate these later
	private ArrayList<int[]> PLLHist;//Record the PLL setup
	private ArrayList<String> PLLFullNameHist;//Record the Full PLL name
	private ArrayList<String> PLLNameHist;//Record the PLL name
	private ArrayList<Color[]> FaceColorHist;//Record the PLLFaceColors - (rotation of the top)
	private ArrayList<int[]> URotHist; //Record the Rotation of U (PLLCaseColors)
	private ArrayList<String> GuessHist;//Record the Guess Name
	private ArrayList<Double> TimestampHist;//Record the TIMESTAMP - Time at which the PLL was identified
	private ArrayList<Double> TimeHist;//Record the TIME - time it took to analyze the PLL
	private double prevTime; //The previous time - used to calculate the time it took to do each PLL
	private int totalCorrect; //The number of PLLs guessed correctly - Used to calculate accuracy

	//Menu Stuffs
	private MenuBar mb;

	private Menu debug;
	private MenuItem crossRefreshMenu;
	private MenuItem PLLRefreshMenu;
	private MenuItem caseRefreshMenu;

	private MenuItem startTimerMenu;
	private MenuItem stopTimerMenu;
	private MenuItem getTimeMenu;
	private MenuItem incGuessNumMenu;
	private MenuItem returnToInitial;

	private Label timeLabel;
	private DoubleProperty time;
	private AnimationTimer timer; //Timer


	private Menu colorMenu; //To change colors of cube
	//Label - Canvas with Color - RGB values
	private ColorMenu whiteMenu;
	private ColorMenu redMenu;
	private ColorMenu blueMenu;
	private ColorMenu greenMenu;
	private ColorMenu orangeMenu;
	private ColorMenu yellowMenu;

	private Menu difficulty;
	//Checks the one that's in use, and will uncheck the other one when switched
	private CheckMenuItem simple;
	private CheckMenuItem full;

	private int difficultyValue; //Modes: 0=Simple, 1=Full

	private GridPane simpleGP; //5 Columns in SIMPLE
	private ColumnConstraints c00;
	private ColumnConstraints c01;
	private ColumnConstraints c02;
	private ColumnConstraints c03;
	private ColumnConstraints c04;

	private RowConstraints r00;
	private RowConstraints r01;
	private RowConstraints r02;
	private RowConstraints r03;

	private GuessButton SKIPs;
	private GuessButton Es;
	private GuessButton Us;
	private GuessButton Hs;
	private GuessButton Zs;
	private GuessButton As;
	private GuessButton Ts;
	private GuessButton Rs;
	private GuessButton Gs;
	private GuessButton Ys;
	private GuessButton Fs;
	private GuessButton Vs;
	private GuessButton Js;
	private GuessButton Ns;

	//BUTTONS GO HERE

	private GridPane fullGP; //5 Columns in FULL, with an EXTRA ROW
	private ColumnConstraints c10;
	private ColumnConstraints c11;
	private ColumnConstraints c12;
	private ColumnConstraints c13;

	private RowConstraints r10;
	private RowConstraints r11;
	private RowConstraints r12;
	private RowConstraints r13;
	private RowConstraints r14;
	private RowConstraints r15;
	private RowConstraints r16;
	private RowConstraints r17;

	private GuessButton SKIPf;
	private GuessButton Ef;
	private GuessButton J1f;
	private GuessButton J2f;
	private GuessButton N1f;
	private GuessButton N2f;
	private GuessButton U1f;
	private GuessButton U2f;
	private GuessButton Hf;
	private GuessButton Zf;
	private GuessButton Ff;
	private GuessButton Vf;
	private GuessButton A1f;
	private GuessButton A2f;
	private GuessButton R1f;
	private GuessButton R2f;
	private GuessButton Tf;
	private GuessButton Yf;
	private GuessButton G1f;
	private GuessButton G2f;
	private GuessButton G3f;
	private GuessButton G4f;

//Keybinds - modifiers buttons 1-4 for Full
	private boolean mod1;
	private boolean mod2;
	private boolean mod3;
	private boolean mod4;

//	HBox testbox; //TEMP HBOX FOR TESTING

  //Initial Menu
	//VBOX WITH HBOXES
	private VBox initial; //Initial menu over-arching VBox
	private HBox modes; // HBox for the mode selection
	private String[] modesList; //Modes List
	private ComboBox<String> modeBox; //Selection for mode

	private HBox cross; //HBox for cross selection
	private String[] crossList; //List of Cross Colors
	private ComboBox<String> crossBox; //Selection for Cross

	private HBox difficulties; //HBox for difficulties
	private String[] difficultyList; //List of difficulties
	private ComboBox<String> difficultyBox; //Selection for difficulties

	private HBox count; //HBox for selecting the number of times to do PLL
	private TextField countBox; //Set count of times for PLL Guessing

	private InitColor initWhite;
	private InitColor initRed;
	private InitColor initBlue;
	private InitColor initGreen;
	private InitColor initOrange;
	private InitColor initYellow;

	private HBox startBox;
	private Button startButton; //Button for starting the program
	private Button caseSelectButton; //Button that takes you to the menu to set the cases
	private Button sessionStatsButton;

	//PLL Case Selection menu
	private VBox caseSelection; //Over-Arching VBox used for the caseSelection menu

		//booleans for cases to include
	private SimpleBooleanProperty A11,A12,A13,A14,
					A21,A22,A23,A24,
					E1,E2,E3,E4,
					F1,F2,F3,F4,
					G11,G12,G13,G14,
					G21,G22,G23,G24,
					G31,G32,G33,G34,
					G41,G42,G43,G44,
					H1,H2,H3,H4,
					J11,J12,J13,J14,
					J21,J22,J23,J24,
					N11,N12,N13,N14,
					N21,N22,N23,N24,
					R11,R12,R13,R14,
					R21,R22,R23,R24,
					SKIP1,SKIP2,SKIP3,SKIP4,
					T1,T2,T3,T4,
					U11,U12,U13,U14,
					U21,U22,U23,U24,
					V1,V2,V3,V4,
					Y1,Y2,Y3,Y4,
					Z1,Z2,Z3,Z4;

	private SetCaseBox[] caseSelectArray; //Array of all case selections

	//Results Table Stuff
	private TableView<StatEntry> statTable;
//	ObservableList<StatEntry> data; //Makes the observable list to put into the table
    private TableColumn<StatEntry,Integer> numCol;
    private TableColumn<StatEntry,Double> timestampCol;
    private TableColumn<StatEntry,Canvas> drawCol;
    private TableColumn<StatEntry,String> nameCol;
    private TableColumn<StatEntry,String> guessCol;
    private TableColumn<StatEntry,Double> timeCol;
    private TableColumn<StatEntry,String> checkCol;
    private ScrollPane sp; //A scroll pane for the table to go into

    //Session Stats Table Stuff - Need different tables for simple and full
    private TableView<SessionEntry> simpleSessionTable;
    private TableView<SessionEntry> fullSessionTable;
    //UNLIKE THE RESULTS TABLE, where the observable list shown is created anew each time,
    //these tables keep the same data, which is just changed by parsing into the right things
//    private ObservableList<SessionEntry> simpleData; //Makes the observable list to the table
//    private ObservableList<SessionEntry> fullData; //Makes the observable list to the table

    private TableColumn<SessionEntry,String> sPllCol,sQCol,sCCol,sIcCol,sAccCol,
    										 sFCol,sSCol,sAvgCol,sStdCol,sMcgCol,
    										 fPllCol,fQCol,fCCol,fIcCol,fAccCol,
    										 fFCol,fSCol,fAvgCol,fStdCol,fMcgCol;

    private SessionEntry sA11SE,sA12SE,sA13SE,sA14SE,sA21SE,sA22SE,sA23SE,sA24SE,sE1SE,sE2SE,sE3SE,
    					 sE4SE,sF1SE,sF2SE,sF3SE,sF4SE,sG11SE,sG12SE,sG13SE,sG14SE,sG21SE,sG22SE,
    					 sG23SE,sG24SE,sG31SE,sG32SE,sG33SE,sG34SE,sG41SE,sG42SE,sG43SE,sG44SE,
    					 sH1SE,sH2SE,sH3SE,sH4SE,sJ11SE,sJ12SE,sJ13SE,sJ14SE,sJ21SE,sJ22SE,sJ23SE,
    					 sJ24SE,sN11SE,sN12SE,sN13SE,sN14SE,sN21SE,sN22SE,sN23SE,sN24SE,sR11SE,sR12SE,
    					 sR13SE,sR14SE,sR21SE,sR22SE,sR23SE,sR24SE,sSKIP1SE,sSKIP2SE,sSKIP3SE,sSKIP4SE,sT1SE,
    					 sT2SE,sT3SE,sT4SE,sU11SE,sU12SE,sU13SE,sU14SE,sU21SE,sU22SE,sU23SE,sU24SE,
    					 sV1SE,sV2SE,sV3SE,sV4SE,sY1SE,sY2SE,sY3SE,sY4SE,sZ1SE,sZ2SE,sZ3SE,sZ4SE,

    					 fA11SE,fA12SE,fA13SE,fA14SE,fA21SE,fA22SE,fA23SE,fA24SE,fE1SE,fE2SE,fE3SE,
    					 fE4SE,fF1SE,fF2SE,fF3SE,fF4SE,fG11SE,fG12SE,fG13SE,fG14SE,fG21SE,fG22SE,
    					 fG23SE,fG24SE,fG31SE,fG32SE,fG33SE,fG34SE,fG41SE,fG42SE,fG43SE,fG44SE,
    					 fH1SE,fH2SE,fH3SE,fH4SE,fJ11SE,fJ12SE,fJ13SE,fJ14SE,fJ21SE,fJ22SE,fJ23SE,
    					 fJ24SE,fN11SE,fN12SE,fN13SE,fN14SE,fN21SE,fN22SE,fN23SE,fN24SE,fR11SE,fR12SE,
    					 fR13SE,fR14SE,fR21SE,fR22SE,fR23SE,fR24SE,fSKIP1SE,fSKIP2SE,fSKIP3SE,fSKIP4SE,fT1SE,
    					 fT2SE,fT3SE,fT4SE,fU11SE,fU12SE,fU13SE,fU14SE,fU21SE,fU22SE,fU23SE,fU24SE,
    					 fV1SE,fV2SE,fV3SE,fV4SE,fY1SE,fY2SE,fY3SE,fY4SE,fZ1SE,fZ2SE,fZ3SE,fZ4SE;

    private SessionEntry[] simpCols;
    private SessionEntry[] fullCols;

    private ScrollPane sp2; //A scroll pane for the simpleSession table to go into
    private ScrollPane sp3; //A scroll pane for the fullSession table to go into

    private SimpleBooleanProperty sessionShowSwitch; //Boolean property used to change the button
    private SimpleBooleanProperty sessionCICSwitch; //Boolean property used to switch between showing correct vs incorrect column

    private VBox sessions;
    
    private ErrorMessagePopup popupMessage;
    
    private Stage primary;

	//Constructor
	public TwoSidePLLPractice() {
		bp = new BorderPane();
		master = new VBox();
		cubeCanvas = new Canvas(cubeCanvasLength,cubeCanvasWidth);//(ss,ss);
		cubePen = cubeCanvas.getGraphicsContext2D();
		rand = new Random();

		white = Color.rgb(250, 246, 228);
		red = Color.rgb(173, 8, 4);
		blue = Color.rgb(8, 131, 219);
		green = Color.rgb(90, 224, 49);
		orange = Color.rgb(255, 172, 24);
		yellow = Color.rgb(212, 197, 0);
		colors = new Color[] {white,red,blue,green,orange,yellow}; //Used to store colors before changing to new ones

		rubiksCube = new ArrayList<>();
		faceColors = new Color[] {white,red,blue,green,orange,yellow}; //Placeholder Values
		PLLnum = 0; //Initial index of the PLL
		activePLL = new int[] {0,0,0,1,1,1}; //Nothing active at start -> defaults to skip
		activePLLFullName = "SKIP-1"; //Nothing active at start -> defaults to skip-1
		activePLLname = "SKIP"; //Nothing active at start -> defaults to skip
		PLLCaseColors = new int[] {0,1,2,3}; //Indicies of the colors in the cube -> RADOMIZE INCREASEING BASED ON
		PLLGuess = "";
		guessNum = 0; //Starts at 0
		modeValue = 0; //Starts in normal mode (0). Will be set to 1 if in debug mode
		canGuess = false; //Start off as false

//		white = Color.rgb(250, 246, 228);
//		red = Color.rgb(173, 8, 4);
//		blue = Color.rgb(8, 131, 219);
//		green = Color.rgb(90, 224, 49);
//		orange = Color.rgb(255, 172, 24);
//		yellow = Color.rgb(212, 197, 0);
//		colors = new Color[] {white,red,blue,green,orange,yellow}; //Used to store colors before changing to new ones
//
		crossColor = white; //Initial cross color - will be reset

		PLL = new int[][] {
			new int[] {3,0,0,1,1,3},new int[] {1,1,3,0,2,1},new int[] {0,2,1,2,3,2},new int[] {2,3,2,3,0,0}, //A1
			new int[] {2,0,0,1,1,2},new int[] {1,1,2,3,2,3},new int[] {3,2,3,0,3,1},new int[] {0,3,1,2,0,0}, //A2
			new int[] {1,0,3,0,1,2},new int[] {0,1,2,3,2,1},new int[] {3,2,1,2,3,0},new int[] {2,3,0,1,0,3}, //E
			new int[] {1,0,3,0,3,1},new int[] {0,3,1,2,2,2},new int[] {2,2,2,3,1,0},new int[] {3,1,0,1,0,3}, //F
			new int[] {0,1,1,2,3,0},new int[] {2,3,0,1,0,2},new int[] {1,0,2,3,2,3},new int[] {3,2,3,0,1,1}, //G1
			new int[] {0,2,1,2,0,0},new int[] {2,0,0,1,3,2},new int[] {1,3,2,3,1,3},new int[] {3,1,3,0,2,1}, //G2
			new int[] {0,2,1,2,3,0},new int[] {2,3,0,1,1,2},new int[] {1,1,2,3,0,3},new int[] {3,0,3,0,2,1}, //G3
			new int[] {0,3,1,2,2,0},new int[] {2,2,0,1,0,2},new int[] {1,0,2,3,1,3},new int[] {3,1,3,0,3,1}, //G4
			new int[] {0,2,0,1,3,1},new int[] {1,3,1,2,0,2},new int[] {2,0,2,3,1,3},new int[] {3,1,3,0,2,0}, //H
			new int[] {0,0,0,1,1,2},new int[] {1,1,2,3,3,1},new int[] {3,3,1,2,2,3},new int[] {2,2,3,0,0,0}, //J1
			new int[] {0,1,1,2,0,0},new int[] {2,0,0,1,2,2},new int[] {1,2,2,3,3,3},new int[] {3,3,3,0,1,1}, //J2
			new int[] {2,0,0,1,3,3},new int[] {1,3,3,0,2,2},new int[] {0,2,2,3,1,1},new int[] {3,1,1,2,0,0}, //N1
			new int[] {0,0,2,3,3,1},new int[] {3,3,1,2,2,0},new int[] {2,2,0,1,1,3},new int[] {1,1,3,0,0,2}, //N2
			new int[] {0,3,0,1,1,2},new int[] {1,1,2,3,2,1},new int[] {3,2,1,2,0,3},new int[] {2,0,3,0,3,0}, //R1
			new int[] {0,1,0,1,0,2},new int[] {1,0,2,3,2,1},new int[] {3,2,1,2,3,3},new int[] {2,3,3,0,1,0}, //R2
			new int[] {0,0,0,1,1,1},new int[] {1,1,1,2,2,2},new int[] {2,2,2,3,3,3},new int[] {3,3,3,0,0,0}, //SKIP
			new int[] {0,0,1,2,3,0},new int[] {2,3,0,1,2,2},new int[] {1,2,2,3,1,3},new int[] {3,1,3,0,0,1}, //T
			new int[] {0,3,0,1,0,1},new int[] {1,0,1,2,2,2},new int[] {2,2,2,3,1,3},new int[] {3,1,3,0,3,0}, //U1
			new int[] {0,1,0,1,3,1},new int[] {1,3,1,2,2,2},new int[] {2,2,2,3,0,3},new int[] {3,0,3,0,1,0}, //U2
			new int[] {0,0,2,3,2,1},new int[] {3,2,1,2,1,0},new int[] {2,1,0,1,3,3},new int[] {1,3,3,0,0,2}, //V
			new int[] {0,0,2,3,1,1},new int[] {3,1,1,2,3,0},new int[] {2,3,0,1,2,3},new int[] {1,2,3,0,0,2}, //Y
			new int[] {0,1,0,1,0,1},new int[] {1,0,1,2,3,2},new int[] {2,3,2,3,2,3},new int[] {3,2,3,0,1,0}  //Z
			}; //End of PLL array
		PLLname = new String[] {"A1","A2","E","F","G1","G2","G3","G4","H","J1","J2","N1","N2","R1","R2","SKIP","T","U1","U2","V","Y","Z"};

		enabledPLLs = new ArrayList<>(); //PLL Cases that have been toggled
		enabledPLLNames = new ArrayList<>(); //PLL names that have been toggled

		/*

			A1
		new int[] {3,0,0,1,1,3} //GOOD
		new int[] {1,1,3,0,2,1} //GOOD
		new int[] {0,2,1,2,3,2} //GOOD
		new int[] {2,3,2,3,0,0} //GOOD

			A2
		new int[] {2,0,0,1,1,2} //GOOD
		new int[] {1,1,2,3,2,3} //GOOD
		new int[] {3,2,3,0,3,1} //GOOD
		new int[] {0,3,1,2,0,0} //GOOD

			E
		new int[] {1,0,3,0,1,2} //GOOD
		new int[] {0,1,2,3,2,1} //GOOD
		new int[] {3,2,1,2,3,0} //GOOD
		new int[] {2,3,0,1,0,3} //GOOD

			F
		new int[] {1,0,3,0,3,1} //GOOD
		new int[] {0,3,1,2,2,2} //GOOD
		new int[] {2,2,2,3,1,0} //GOOD
		new int[] {3,1,0,1,0,3} //GOOD

			G1
		new int[] {0,1,1,2,3,0} //GOOD
		new int[] {2,3,0,1,0,2} //GOOD
		new int[] {1,0,2,3,2,3} //GOOD
		new int[] {3,2,3,0,1,1} //GOOD

			G2
		new int[] {0,2,1,2,0,0} //GOOD
		new int[] {2,0,0,1,3,2} //GOOD
		new int[] {1,3,2,3,1,3} //GOOD
		new int[] {3,1,3,0,2,1} //GOOD

			G3
		new int[] {0,2,1,2,3,0} //GOOD
		new int[] {2,3,0,1,1,2} //GOOD
		new int[] {1,1,2,3,0,3} //GOOD
		new int[] {3,0,3,0,2,1} //GOOD

			G4
		new int[] {0,3,1,2,2,0} //GOOD
		new int[] {2,2,0,1,0,2} //GOOD
		new int[] {1,0,2,3,1,3} //GOOD
		new int[] {3,1,3,0,3,1} //GOOD

			H
		new int[] {0,2,0,1,3,1} //GOOD
		new int[] {1,3,1,2,0,2} //GOOD
		new int[] {2,0,2,3,1,3} //GOOD
		new int[] {3,1,3,0,2,0} //GOOD

			J1
		new int[] {0,0,0,1,1,2} //GOOD
		new int[] {1,1,2,3,3,1} //GOOD
		new int[] {3,3,1,2,2,3} //GOOD
		new int[] {2,2,3,0,0,0} //GOOD

			J2
		new int[] {0,1,1,2,0,0} //GOOD
		new int[] {2,0,0,1,2,2} //GOOD
		new int[] {1,2,2,3,3,3} //GOOD
		new int[] {3,3,3,0,1,1} //GOOD

			N1 (Left)
		new int[] {2,0,0,1,3,3} //GOOD
		new int[] {1,3,3,0,2,2} //GOOD
		new int[] {0,2,2,3,1,1} //GOOD
		new int[] {3,1,1,2,0,0} //GOOD

			N2
		new int[] {0,0,2,3,3,1} //GOOD
		new int[] {3,3,1,2,2,0} //GOOD
		new int[] {2,2,0,1,1,3} //GOOD
		new int[] {1,1,3,0,0,2} //GOOD

			R1 (Left)
		new int[] {0,3,0,1,1,2} //GOOD
		new int[] {1,1,2,3,2,1} //GOOD
		new int[] {3,2,1,2,0,3} //GOOD
		new int[] {2,0,3,0,3,0} //GOOD

			R2
		new int[] {0,1,0,1,0,2} //GOOD
		new int[] {1,0,2,3,2,1} //GOOD
		new int[] {3,2,1,2,3,3} //GOOD
		new int[] {2,3,3,0,1,0} //GOOD

		 	Skip
		 new int[] {0,0,0,1,1,1} //GOOD
		 new int[] {1,1,1,2,2,2} //GOOD
		 new int[] {2,2,2,3,3,3} //GOOD
		 new int[] {3,3,3,0,0,0} //GOOD

			T
		new int[] {0,0,1,2,3,0} //GOOD
		new int[] {2,3,0,1,2,2} //GOOD
		new int[] {1,2,2,3,1,3} //GOOD
		new int[] {3,1,3,0,0,1} //GOOD

			U1
		new int[] {0,3,0,1,0,1} //GOOD
		new int[] {1,0,1,2,2,2} //GOOD
		new int[] {2,2,2,3,1,3} //GOOD
		new int[] {3,1,3,0,3,0} //GOOD

			U2
		new int[] {0,1,0,1,3,1} //GOOD
		new int[] {1,3,1,2,2,2} //GOOD
		new int[] {2,2,2,3,0,3} //GOOD
		new int[] {3,0,3,0,1,0} //GOOD

			V
		new int[] {0,0,2,3,2,1} //GOOD
		new int[] {3,2,1,2,1,0} //GOOD
		new int[] {2,1,0,1,3,3} //GOOD
		new int[] {1,3,3,0,0,2} //GOOD

			Y
		new int[] {0,0,2,3,1,1} //GOOD
		new int[] {3,1,1,2,3,0} //GOOD
		new int[] {2,3,0,1,2,3} //GOOD
		new int[] {1,2,3,0,0,2} //GOOD

			Z
		new int[] {0,1,0,1,0,1} //GOOD
		new int[] {1,0,1,2,3,2} //GOOD
		new int[] {2,3,2,3,2,3} //GOOD
		new int[] {3,2,3,0,1,0} //GOOD

		*/

		//History Arrays
		GuessNumHist = new ArrayList<Integer>();//Record the guess number
		PLLHist = new ArrayList<int[]>();//Record the PLL setup
		PLLFullNameHist = new ArrayList<String>();//Record the Full PLL name
		PLLNameHist = new ArrayList<String>();//Record the PLL name
		FaceColorHist = new ArrayList<Color[]>();//Record the PLLFaceColors - (rotation of the top)
		URotHist = new ArrayList<int[]>(); //Record the Rotation of U (PLLCaseColors)
		GuessHist = new ArrayList<String>();//Record the Guess Name
		TimestampHist = new ArrayList<Double>();//Record the TIMESTAMP - Time at which the PLL was identified
		TimeHist = new ArrayList<Double>();//Record the TIME - time it took to analyze the PLL
		prevTime = 0.0; //The previous time - used to calculate the time it took to do each PLL
		totalCorrect = 0; //The number of PLLs guessed correctly - Used to calculate accuracy

	//Menu Stuffs
		mb = new MenuBar();

		//debug menu
		debug = new Menu("Debug");
		crossRefreshMenu = new MenuItem("Refresh Sides");
		PLLRefreshMenu = new MenuItem("New PLL");
		caseRefreshMenu = new MenuItem("New Case");

		startTimerMenu = new MenuItem("Start Timer");
		stopTimerMenu = new MenuItem("Stop Timer");
		getTimeMenu = new MenuItem("Get Time");
		returnToInitial = new MenuItem("Main Menu");

		incGuessNumMenu = new MenuItem("Inc Guess Num");

		time = new SimpleDoubleProperty();
		timeLabel = new Label();
		timeLabel.textProperty().bind(time.asString("%.3f")); //Binds the label to the timer
		timer = new AnimationTimer() {

			private long startTime;

			@Override
            public void start() { //What it does when the timer starts
                startTime = System.currentTimeMillis();
                super.start();
            }

            @Override
            public void stop() { //What happens when the timer stops
                super.stop();
            }
		 	@Override
            public void handle(long timestamp) { //While the timer is running
                long now = System.currentTimeMillis();
                time.set((now - startTime) / 1000.0);
              //MARK ON CANVAS
                drawTimer();
            }
		};

		//Color Menu
		colorMenu = new Menu("Colors");

		whiteMenu = new ColorMenu("White",250,246,228); //White
		redMenu = new ColorMenu("Red",173,8,4); //Red
		blueMenu = new ColorMenu("Blue",8,131,219); //Blue
		greenMenu = new ColorMenu("Green",90,224,49); //Green
		orangeMenu = new ColorMenu("Orange",255,172,24); //Orange
		yellowMenu = new ColorMenu("Yellow",212,197,0); //Yellow
		
//		whiteMenu = new ColorMenu("White",(int) white.getRed(),(int) white.getBlue(),(int) white.getGreen()); //White
//		redMenu = new ColorMenu("Red",173,8,4); //Red
//		blueMenu = new ColorMenu("Blue",8,131,219); //Blue
//		greenMenu = new ColorMenu("Green",90,224,49); //Green
//		orangeMenu = new ColorMenu("Orange",255,172,24); //Orange
//		yellowMenu = new ColorMenu("Yellow",212,197,0); //Yellow


		//Difficulty Menu
		difficulty = new Menu("Difficulty");

		simple = new CheckMenuItem("Simple");
		full = new CheckMenuItem("Full");

		difficultyValue = 0; //Starts off in simple -> 1 is Full

		simpleGP = new GridPane();
		c00 = new ColumnConstraints();
		c01 = new ColumnConstraints();
		c02 = new ColumnConstraints();
		c03 = new ColumnConstraints();
		c04 = new ColumnConstraints();

		r00 = new RowConstraints();
		r01 = new RowConstraints();
		r02 = new RowConstraints();
		r03 = new RowConstraints();

		fullGP = new GridPane();
		c10 = new ColumnConstraints();
		c11 = new ColumnConstraints();
		c12 = new ColumnConstraints();
		c13 = new ColumnConstraints();

		r10 = new RowConstraints();
		r11 = new RowConstraints();
		r12 = new RowConstraints();
		r13 = new RowConstraints();
		r14 = new RowConstraints();
		r15 = new RowConstraints();
		r16 = new RowConstraints();
		r17 = new RowConstraints();

	//Keybinds - modifiers buttons 1-4 for Full
		mod1 = false;
		mod2 = false;
		mod3 = false;
		mod4 = false;

	//Initial Menu
		//VBOX WITH HBOXES
		modesList = new String[] {"Normal","Debug"}; //Modes List
		modeBox = new ComboBox<String>(FXCollections.observableArrayList(modesList)); //Selection for mode
		modeBox.getSelectionModel().selectFirst(); //Set Normal as default
		modes = new HBox(new Label("Select Mode: "),modeBox); // HBox for the mode selection
		modes.setAlignment(Pos.CENTER);

		crossList = new String[] {"Color Neutral","White","Red","Blue","Green","Orange","Yellow"}; //List of Cross Colors
		crossBox = new ComboBox<String>(FXCollections.observableArrayList(crossList)); //Selection for Cross
		crossBox.getSelectionModel().selectFirst(); //Set Color Neutral as default
		cross = new HBox(new Label("Select Cross Color: "),crossBox); //HBox for cross selection
		cross.setAlignment(Pos.CENTER);

		difficultyList = new String[] {"Simple","Full"}; //Difficulty selection list
		difficultyBox = new ComboBox<String>(FXCollections.observableArrayList(difficultyList)); //Selection for difficulty
		difficultyBox.getSelectionModel().selectFirst(); //Set Simple as default
		difficulties = new HBox(new Label("    Difficulty:"),difficultyBox);
		difficulties.setAlignment(Pos.CENTER);
		difficulties.setSpacing(5);

		countBox = new TextField("10"); //Set count of times for PLL Guessing -> 10 is default
		countBox.textProperty().addListener((ov, old_val, new_val) -> {
			if(!new_val.matches("\\d*")) {countBox.setText(new_val.replaceAll("[^\\d]", "")); new_val = countBox.getText();} //Gets Rid of Non-Numerical Characters
			if(new_val.isEmpty()) {countBox.setText("0"); new_val = "0";} //WILL SET TO 0  IF NOTHING IS IN THE BOX
			if(new_val.length() > 3) {countBox.setText(countBox.getText().substring(0, 3));} //to keep number at 3 digits MAX
			if(Integer.parseInt(new_val) > 100) {countBox.setText("100");}//{new_val = "100";} //Sets the text field to 100 if number input is larger than that
			if(new_val.charAt(0) == '0' && new_val.length() >= 2) {countBox.setText(countBox.getText().substring(1, new_val.length()));}
		});
		countBox.setPrefWidth(40);

		caseCount = Integer.parseInt(countBox.getText()); //Defaults to 10, as defined for default text in TextField

		count = new HBox(new Label("Number of Cases: "),countBox); //HBox for selecting the number of times to do PLL
		count.setAlignment(Pos.CENTER);
//		count.setSpacing(0);

		initWhite = new InitColor("White",250,246,228);
		initRed = new InitColor("Red",173,8,4);
		initBlue = new InitColor("Blue",8,131,219);
		initGreen = new InitColor("Green",90,224,49);
		initOrange = new InitColor("Orange",255,172,24);
		initYellow = new InitColor("Yellow",212,197,0);

		startBox = new HBox(); //Used to center the start button
		startBox.setAlignment(Pos.CENTER); //Centers this HBox
		startBox.setSpacing(50);
		startButton = new Button("Start"); //This will be clicked to start the program
		caseSelectButton = new Button("Select Cases");
		sessionStatsButton = new Button("Session Stats");

		startBox.getChildren().addAll(caseSelectButton,startButton,sessionStatsButton); //Adds the startButton to the VBox

		//Title
		Label title = new Label("2-Side PLL Recognition Practice");
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 18)); //Makes the font bigger and bolded
		Label author = new Label("Created by JackDeath5");
		author.setFont(Font.font("Verdana", FontPosture.ITALIC, 11)); //make the text small and italicized
		VBox titleBox = new VBox(title, author, new Label()); //Empty label used as a simple spacer
		titleBox.setAlignment(Pos.CENTER); //Center things in the titlebox

		//Infobox
		Label keybindLabel = new Label("Keybind Notes:");
		keybindLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
		keybindLabel.setTextFill(Color.rgb(100, 100, 100));
		keybindLabel.setUnderline(true);
		Label keybindLabel2 = new Label("- Cases correspond to their repsective letter");
		keybindLabel2.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
		keybindLabel2.setTextFill(Color.rgb(100, 100, 100));
		Label keybindLabel3 = new Label("- Press LETTER AND NUMBER for similar cases in Full");
		keybindLabel3.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
		keybindLabel3.setTextFill(Color.rgb(100, 100, 100));
		Label keybindLabel4 = new Label("- s = SKIP");
		keybindLabel4.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
		keybindLabel4.setTextFill(Color.rgb(100, 100, 100));

		Label info = new Label("A source (the one I learned from at least) of 2-Side PLL:");
		info.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
		info.setTextFill(Color.rgb(150, 150, 150));
		Hyperlink link = new Hyperlink("https://sarah.cubing.net/3x3x3/pll-recognition-guide");
		link.setOnAction( e -> getHostServices().showDocument(link.getText())); //Makes the hyperlink clickable
		link.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
		link.setTextFill(Color.rgb(150, 150, 150));
		//link.setTextFill(Color.rgb(6,69,173));
		link.setUnderline(true); //Underlines the text

		VBox infoBox = new VBox(new Label(),keybindLabel,keybindLabel2,keybindLabel3,keybindLabel4,new Label(), info, link); //Empty label used as a simple spacer
		infoBox.setAlignment(Pos.CENTER); //center things in the info box

		//Used to load JUST THE COLORS of the cube without loading session data.
		HBox colorsIOBox = new HBox();
		colorsIOBox.setAlignment(Pos.CENTER);
		Button loadColorsFromFile = new Button("Load Colors...");
		loadColorsFromFile.setOnAction(e -> {
			try {
				loadColorsFromFile();
			} catch (Exception e1) {
				popupMessage.show(e1.getMessage());
				System.out.println("Unable to load cube colors.");
			}
		});
		Button saveColorsToFile = new Button("Save Colors...");
		saveColorsToFile.setOnAction(e -> {
			try {
				writeColorsToFile();
			} catch (Exception e1) {
				popupMessage.show(e1.getMessage());
				System.out.println("Unable to save cube colors.");
			}
		});
		colorsIOBox.getChildren().addAll(loadColorsFromFile, saveColorsToFile);
//		colorsIOBox.setSpacing(165); //SETUP 5
		colorsIOBox.setSpacing(50);
		
		initial = new VBox();
		initial.setAlignment(Pos.CENTER);
		initial.getChildren().addAll(titleBox,modes,cross,difficulties,count,initWhite,initRed,initBlue,initGreen,
									 initOrange,initYellow,new Label(),colorsIOBox,new Label(),startBox,infoBox); //Initial menu over-arching VBox
//		initial.getChildren().addAll(titleBox,modes,cross,difficulties,count,colorsIOBox,initWhite,initRed,initBlue,initGreen,
//				 initOrange,initYellow,new Label(),startBox,infoBox); //Initial menu over-arching VBox //SETUP 5

		//Added the empty label new Label() as a spacer for simplicity

		//PLL Case Selection menu - generated using a Ruby script cuz I'm lazy
		caseSelection = new VBox(); //Over-Arching VBox used for the caseSelection menu
		caseSelection.setAlignment(Pos.CENTER); //Set center alignment of things in caseSelection

			//boolean properties for all cases to include - default to true
			//these will be bound to a CheckBox.selectedProperty()
		A11=new SimpleBooleanProperty(true);
		A12=new SimpleBooleanProperty(true);
		A13=new SimpleBooleanProperty(true);
		A14=new SimpleBooleanProperty(true);

		A21=new SimpleBooleanProperty(true);
		A22=new SimpleBooleanProperty(true);
		A23=new SimpleBooleanProperty(true);
		A24=new SimpleBooleanProperty(true);

		E1=new SimpleBooleanProperty(true);
		E2=new SimpleBooleanProperty(true);
		E3=new SimpleBooleanProperty(true);
		E4=new SimpleBooleanProperty(true);

		F1=new SimpleBooleanProperty(true);
		F2=new SimpleBooleanProperty(true);
		F3=new SimpleBooleanProperty(true);
		F4=new SimpleBooleanProperty(true);

		G11=new SimpleBooleanProperty(true);
		G12=new SimpleBooleanProperty(true);
		G13=new SimpleBooleanProperty(true);
		G14=new SimpleBooleanProperty(true);

		G21=new SimpleBooleanProperty(true);
		G22=new SimpleBooleanProperty(true);
		G23=new SimpleBooleanProperty(true);
		G24=new SimpleBooleanProperty(true);

		G31=new SimpleBooleanProperty(true);
		G32=new SimpleBooleanProperty(true);
		G33=new SimpleBooleanProperty(true);
		G34=new SimpleBooleanProperty(true);

		G41=new SimpleBooleanProperty(true);
		G42=new SimpleBooleanProperty(true);
		G43=new SimpleBooleanProperty(true);
		G44=new SimpleBooleanProperty(true);

		H1=new SimpleBooleanProperty(true);
		H2=new SimpleBooleanProperty(true);
		H3=new SimpleBooleanProperty(true);
		H4=new SimpleBooleanProperty(true);

		J11=new SimpleBooleanProperty(true);
		J12=new SimpleBooleanProperty(true);
		J13=new SimpleBooleanProperty(true);
		J14=new SimpleBooleanProperty(true);

		J21=new SimpleBooleanProperty(true);
		J22=new SimpleBooleanProperty(true);
		J23=new SimpleBooleanProperty(true);
		J24=new SimpleBooleanProperty(true);

		N11=new SimpleBooleanProperty(true);
		N12=new SimpleBooleanProperty(true);
		N13=new SimpleBooleanProperty(true);
		N14=new SimpleBooleanProperty(true);

		N21=new SimpleBooleanProperty(true);
		N22=new SimpleBooleanProperty(true);
		N23=new SimpleBooleanProperty(true);
		N24=new SimpleBooleanProperty(true);

		R11=new SimpleBooleanProperty(true);
		R12=new SimpleBooleanProperty(true);
		R13=new SimpleBooleanProperty(true);
		R14=new SimpleBooleanProperty(true);

		R21=new SimpleBooleanProperty(true);
		R22=new SimpleBooleanProperty(true);
		R23=new SimpleBooleanProperty(true);
		R24=new SimpleBooleanProperty(true);

		SKIP1=new SimpleBooleanProperty(true);
		SKIP2=new SimpleBooleanProperty(true);
		SKIP3=new SimpleBooleanProperty(true);
		SKIP4=new SimpleBooleanProperty(true);

		T1=new SimpleBooleanProperty(true);
		T2=new SimpleBooleanProperty(true);
		T3=new SimpleBooleanProperty(true);
		T4=new SimpleBooleanProperty(true);

		U11=new SimpleBooleanProperty(true);
		U12=new SimpleBooleanProperty(true);
		U13=new SimpleBooleanProperty(true);
		U14=new SimpleBooleanProperty(true);

		U21=new SimpleBooleanProperty(true);
		U22=new SimpleBooleanProperty(true);
		U23=new SimpleBooleanProperty(true);
		U24=new SimpleBooleanProperty(true);

		V1=new SimpleBooleanProperty(true);
		V2=new SimpleBooleanProperty(true);
		V3=new SimpleBooleanProperty(true);
		V4=new SimpleBooleanProperty(true);

		Y1=new SimpleBooleanProperty(true);
		Y2=new SimpleBooleanProperty(true);
		Y3=new SimpleBooleanProperty(true);
		Y4=new SimpleBooleanProperty(true);

		Z1=new SimpleBooleanProperty(true);
		Z2=new SimpleBooleanProperty(true);
		Z3=new SimpleBooleanProperty(true);
		Z4=new SimpleBooleanProperty(true);

		caseSelectArray = new SetCaseBox[] {
			new SetCaseBox("A1-1",PLL[0],faceColors),
			new SetCaseBox("A1-2",PLL[1],faceColors),
			new SetCaseBox("A1-3",PLL[2],faceColors),
			new SetCaseBox("A1-4",PLL[3],faceColors),
			new SetCaseBox("A2-1",PLL[4],faceColors),
			new SetCaseBox("A2-2",PLL[5],faceColors),
			new SetCaseBox("A2-3",PLL[6],faceColors),
			new SetCaseBox("A2-4",PLL[7],faceColors),
			new SetCaseBox("E-1",PLL[8],faceColors),
			new SetCaseBox("E-2",PLL[9],faceColors),
			new SetCaseBox("E-3",PLL[10],faceColors),
			new SetCaseBox("E-4",PLL[11],faceColors),
			new SetCaseBox("F-1",PLL[12],faceColors),
			new SetCaseBox("F-2",PLL[13],faceColors),
			new SetCaseBox("F-3",PLL[14],faceColors),
			new SetCaseBox("F-4",PLL[15],faceColors),
			new SetCaseBox("G1-1",PLL[16],faceColors),
			new SetCaseBox("G1-2",PLL[17],faceColors),
			new SetCaseBox("G1-3",PLL[18],faceColors),
			new SetCaseBox("G1-4",PLL[19],faceColors),
			new SetCaseBox("G2-1",PLL[20],faceColors),
			new SetCaseBox("G2-2",PLL[21],faceColors),
			new SetCaseBox("G2-3",PLL[22],faceColors),
			new SetCaseBox("G2-4",PLL[23],faceColors),
			new SetCaseBox("G3-1",PLL[24],faceColors),
			new SetCaseBox("G3-2",PLL[25],faceColors),
			new SetCaseBox("G3-3",PLL[26],faceColors),
			new SetCaseBox("G3-4",PLL[27],faceColors),
			new SetCaseBox("G4-1",PLL[28],faceColors),
			new SetCaseBox("G4-2",PLL[29],faceColors),
			new SetCaseBox("G4-3",PLL[30],faceColors),
			new SetCaseBox("G4-4",PLL[31],faceColors),
			new SetCaseBox("H-1",PLL[32],faceColors),
			new SetCaseBox("H-2",PLL[33],faceColors),
			new SetCaseBox("H-3",PLL[34],faceColors),
			new SetCaseBox("H-4",PLL[35],faceColors),
			new SetCaseBox("J1-1",PLL[36],faceColors),
			new SetCaseBox("J1-2",PLL[37],faceColors),
			new SetCaseBox("J1-3",PLL[38],faceColors),
			new SetCaseBox("J1-4",PLL[39],faceColors),
			new SetCaseBox("J2-1",PLL[40],faceColors),
			new SetCaseBox("J2-2",PLL[41],faceColors),
			new SetCaseBox("J2-3",PLL[42],faceColors),
			new SetCaseBox("J2-4",PLL[43],faceColors),
			new SetCaseBox("N1-1",PLL[44],faceColors),
			new SetCaseBox("N1-2",PLL[45],faceColors),
			new SetCaseBox("N1-3",PLL[46],faceColors),
			new SetCaseBox("N1-4",PLL[47],faceColors),
			new SetCaseBox("N2-1",PLL[48],faceColors),
			new SetCaseBox("N2-2",PLL[49],faceColors),
			new SetCaseBox("N2-3",PLL[50],faceColors),
			new SetCaseBox("N2-4",PLL[51],faceColors),
			new SetCaseBox("R1-1",PLL[52],faceColors),
			new SetCaseBox("R1-2",PLL[53],faceColors),
			new SetCaseBox("R1-3",PLL[54],faceColors),
			new SetCaseBox("R1-4",PLL[55],faceColors),
			new SetCaseBox("R2-1",PLL[56],faceColors),
			new SetCaseBox("R2-2",PLL[57],faceColors),
			new SetCaseBox("R2-3",PLL[58],faceColors),
			new SetCaseBox("R2-4",PLL[59],faceColors),
			new SetCaseBox("SKIP-1",PLL[60],faceColors),
			new SetCaseBox("SKIP-2",PLL[61],faceColors),
			new SetCaseBox("SKIP-3",PLL[62],faceColors),
			new SetCaseBox("SKIP-4",PLL[63],faceColors),
			new SetCaseBox("T-1",PLL[64],faceColors),
			new SetCaseBox("T-2",PLL[65],faceColors),
			new SetCaseBox("T-3",PLL[66],faceColors),
			new SetCaseBox("T-4",PLL[67],faceColors),
			new SetCaseBox("U1-1",PLL[68],faceColors),
			new SetCaseBox("U1-2",PLL[69],faceColors),
			new SetCaseBox("U1-3",PLL[70],faceColors),
			new SetCaseBox("U1-4",PLL[71],faceColors),
			new SetCaseBox("U2-1",PLL[72],faceColors),
			new SetCaseBox("U2-2",PLL[73],faceColors),
			new SetCaseBox("U2-3",PLL[74],faceColors),
			new SetCaseBox("U2-4",PLL[75],faceColors),
			new SetCaseBox("V-1",PLL[76],faceColors),
			new SetCaseBox("V-2",PLL[77],faceColors),
			new SetCaseBox("V-3",PLL[78],faceColors),
			new SetCaseBox("V-4",PLL[79],faceColors),
			new SetCaseBox("Y-1",PLL[80],faceColors),
			new SetCaseBox("Y-2",PLL[81],faceColors),
			new SetCaseBox("Y-3",PLL[82],faceColors),
			new SetCaseBox("Y-4",PLL[83],faceColors),
			new SetCaseBox("Z-1",PLL[84],faceColors),
			new SetCaseBox("Z-2",PLL[85],faceColors),
			new SetCaseBox("Z-3",PLL[86],faceColors),
			new SetCaseBox("Z-4",PLL[87],faceColors)
		}; //End of caseSelectArray

		//Set up the bindings
		A11.bind(caseSelectArray[0].caseBool);
		A12.bind(caseSelectArray[1].caseBool);
		A13.bind(caseSelectArray[2].caseBool);
		A14.bind(caseSelectArray[3].caseBool);
		A21.bind(caseSelectArray[4].caseBool);
		A22.bind(caseSelectArray[5].caseBool);
		A23.bind(caseSelectArray[6].caseBool);
		A24.bind(caseSelectArray[7].caseBool);
		E1.bind(caseSelectArray[8].caseBool);
		E2.bind(caseSelectArray[9].caseBool);
		E3.bind(caseSelectArray[10].caseBool);
		E4.bind(caseSelectArray[11].caseBool);
		F1.bind(caseSelectArray[12].caseBool);
		F2.bind(caseSelectArray[13].caseBool);
		F3.bind(caseSelectArray[14].caseBool);
		F4.bind(caseSelectArray[15].caseBool);
		G11.bind(caseSelectArray[16].caseBool);
		G12.bind(caseSelectArray[17].caseBool);
		G13.bind(caseSelectArray[18].caseBool);
		G14.bind(caseSelectArray[19].caseBool);
		G21.bind(caseSelectArray[20].caseBool);
		G22.bind(caseSelectArray[21].caseBool);
		G23.bind(caseSelectArray[22].caseBool);
		G24.bind(caseSelectArray[23].caseBool);
		G31.bind(caseSelectArray[24].caseBool);
		G32.bind(caseSelectArray[25].caseBool);
		G33.bind(caseSelectArray[26].caseBool);
		G34.bind(caseSelectArray[27].caseBool);
		G41.bind(caseSelectArray[28].caseBool);
		G42.bind(caseSelectArray[29].caseBool);
		G43.bind(caseSelectArray[30].caseBool);
		G44.bind(caseSelectArray[31].caseBool);
		H1.bind(caseSelectArray[32].caseBool);
		H2.bind(caseSelectArray[33].caseBool);
		H3.bind(caseSelectArray[34].caseBool);
		H4.bind(caseSelectArray[35].caseBool);
		J11.bind(caseSelectArray[36].caseBool);
		J12.bind(caseSelectArray[37].caseBool);
		J13.bind(caseSelectArray[38].caseBool);
		J14.bind(caseSelectArray[39].caseBool);
		J21.bind(caseSelectArray[40].caseBool);
		J22.bind(caseSelectArray[41].caseBool);
		J23.bind(caseSelectArray[42].caseBool);
		J24.bind(caseSelectArray[43].caseBool);
		N11.bind(caseSelectArray[44].caseBool);
		N12.bind(caseSelectArray[45].caseBool);
		N13.bind(caseSelectArray[46].caseBool);
		N14.bind(caseSelectArray[47].caseBool);
		N21.bind(caseSelectArray[48].caseBool);
		N22.bind(caseSelectArray[49].caseBool);
		N23.bind(caseSelectArray[50].caseBool);
		N24.bind(caseSelectArray[51].caseBool);
		R11.bind(caseSelectArray[52].caseBool);
		R12.bind(caseSelectArray[53].caseBool);
		R13.bind(caseSelectArray[54].caseBool);
		R14.bind(caseSelectArray[55].caseBool);
		R21.bind(caseSelectArray[56].caseBool);
		R22.bind(caseSelectArray[57].caseBool);
		R23.bind(caseSelectArray[58].caseBool);
		R24.bind(caseSelectArray[59].caseBool);
		SKIP1.bind(caseSelectArray[60].caseBool);
		SKIP2.bind(caseSelectArray[61].caseBool);
		SKIP3.bind(caseSelectArray[62].caseBool);
		SKIP4.bind(caseSelectArray[63].caseBool);
		T1.bind(caseSelectArray[64].caseBool);
		T2.bind(caseSelectArray[65].caseBool);
		T3.bind(caseSelectArray[66].caseBool);
		T4.bind(caseSelectArray[67].caseBool);
		U11.bind(caseSelectArray[68].caseBool);
		U12.bind(caseSelectArray[69].caseBool);
		U13.bind(caseSelectArray[70].caseBool);
		U14.bind(caseSelectArray[71].caseBool);
		U21.bind(caseSelectArray[72].caseBool);
		U22.bind(caseSelectArray[73].caseBool);
		U23.bind(caseSelectArray[74].caseBool);
		U24.bind(caseSelectArray[75].caseBool);
		V1.bind(caseSelectArray[76].caseBool);
		V2.bind(caseSelectArray[77].caseBool);
		V3.bind(caseSelectArray[78].caseBool);
		V4.bind(caseSelectArray[79].caseBool);
		Y1.bind(caseSelectArray[80].caseBool);
		Y2.bind(caseSelectArray[81].caseBool);
		Y3.bind(caseSelectArray[82].caseBool);
		Y4.bind(caseSelectArray[83].caseBool);
		Z1.bind(caseSelectArray[84].caseBool);
		Z2.bind(caseSelectArray[85].caseBool);
		Z3.bind(caseSelectArray[86].caseBool);
		Z4.bind(caseSelectArray[87].caseBool);

		//Results Table Stuff
		statTable = new TableView<>();
//		data = FXCollections.observableArrayList(); //Makes the observable list to put into the table
	    numCol = new TableColumn<>("Q"); // "Question"
	    timestampCol = new TableColumn<>("TS"); // "Timestamp"
	    drawCol = new TableColumn<>("Layout"); // "Layout"
	    nameCol = new TableColumn<>("PLL"); // "PLL"
	    guessCol = new TableColumn<>("Ans"); // "Guess"
	    timeCol = new TableColumn<>("T"); // "TimeFrame"
	    checkCol = new TableColumn<>("?"); // "Check"

	    //Center align the data in the columns
	    numCol.setStyle("-fx-alignment: CENTER;");
	    timestampCol.setStyle("-fx-alignment: CENTER;");
	    drawCol.setStyle("-fx-alignment: CENTER;");
	    nameCol.setStyle("-fx-alignment: CENTER;");
	    guessCol.setStyle("-fx-alignment: CENTER;");
	    timeCol.setStyle("-fx-alignment: CENTER;");
	    checkCol.setStyle("-fx-alignment: CENTER;");

	    //Make Table
	    statTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    setupTable();
	    sp = new ScrollPane(statTable); //A scroll pane for the table to go into
	    sp.setFitToWidth(true);
		sp.setFitToHeight(true);
	    sp.setMaxWidth(screenWidth); //Sets the size of the are of the scroll pane to equal that of the window
	    sp.setMaxHeight(400); //Sets the max length of the scroll pane

	    //Session Table Stuff - 2 tables
	    simpleSessionTable = new TableView<>();
	    fullSessionTable = new TableView<>();
	    //UNLIKE THE RESULTS TABLE, where the observable list shown is created anew each time,
	    //these tables keep the same data, which is just changed by parsing into the right things

	    sPllCol = new TableColumn<>("PLL");
	    sQCol = new TableColumn<>("Q");
	    sCCol = new TableColumn<>("\u2713");
	    sIcCol = new TableColumn<>("X");
	    sAccCol = new TableColumn<>("Acc");
	    sFCol = new TableColumn<>("F");
	    sSCol = new TableColumn<>("S");
	    sAvgCol = new TableColumn<>("Avg");
	    sStdCol = new TableColumn<>("StdT");
	    sMcgCol = new TableColumn<>("G"); //"MCG"

	    fPllCol = new TableColumn<>("PLL");
	    fQCol = new TableColumn<>("Q");
	    fCCol = new TableColumn<>("\u2713");
	    fIcCol = new TableColumn<>("X");
	    fAccCol = new TableColumn<>("Acc");
	    fFCol = new TableColumn<>("F");
	    fSCol = new TableColumn<>("S");
	    fAvgCol = new TableColumn<>("Avg");
	    fStdCol = new TableColumn<>("StdT");
	    fMcgCol = new TableColumn<>("G");

	    //Center align the data in the columns
	    sPllCol.setStyle("-fx-alignment: CENTER;");
	    sQCol.setStyle("-fx-alignment: CENTER;");
	    sCCol.setStyle("-fx-alignment: CENTER;");
	    sIcCol.setStyle("-fx-alignment: CENTER;");
	    sAccCol.setStyle("-fx-alignment: CENTER;");
	     sFCol.setStyle("-fx-alignment: CENTER;");
	    sSCol.setStyle("-fx-alignment: CENTER;");
	    sAvgCol.setStyle("-fx-alignment: CENTER;");
	    sStdCol.setStyle("-fx-alignment: CENTER;");
	    sMcgCol.setStyle("-fx-alignment: CENTER;");

	     fPllCol.setStyle("-fx-alignment: CENTER;");
	    fQCol.setStyle("-fx-alignment: CENTER;");
	    fCCol.setStyle("-fx-alignment: CENTER;");
	    fIcCol.setStyle("-fx-alignment: CENTER;");
	    fAccCol.setStyle("-fx-alignment: CENTER;");
	     fFCol.setStyle("-fx-alignment: CENTER;");
	    fSCol.setStyle("-fx-alignment: CENTER;");
	    fAvgCol.setStyle("-fx-alignment: CENTER;");
	    fStdCol.setStyle("-fx-alignment: CENTER;");
	    fMcgCol.setStyle("-fx-alignment: CENTER;");

	    sA11SE = new SessionEntry("A1-1");
	    sA12SE = new SessionEntry("A1-2");
	    sA13SE = new SessionEntry("A1-3");
	    sA14SE = new SessionEntry("A1-4");
	    sA21SE = new SessionEntry("A2-1");
	    sA22SE = new SessionEntry("A2-2");
	    sA23SE = new SessionEntry("A2-3");
	    sA24SE = new SessionEntry("A2-4");
	    sE1SE = new SessionEntry("E-1");
	    sE2SE = new SessionEntry("E-2");
	    sE3SE = new SessionEntry("E-3");
	    sE4SE = new SessionEntry("E-4");
	    sF1SE = new SessionEntry("F-1");
	    sF2SE = new SessionEntry("F-2");
	    sF3SE = new SessionEntry("F-3");
	    sF4SE = new SessionEntry("F-4");
	    sG11SE = new SessionEntry("G1-1");
	    sG12SE = new SessionEntry("G1-2");
	    sG13SE = new SessionEntry("G1-3");
	    sG14SE = new SessionEntry("G1-4");
	    sG21SE = new SessionEntry("G2-1");
	    sG22SE = new SessionEntry("G2-2");
	    sG23SE = new SessionEntry("G2-3");
	    sG24SE = new SessionEntry("G2-4");
	    sG31SE = new SessionEntry("G3-1");
	    sG32SE = new SessionEntry("G3-2");
	    sG33SE = new SessionEntry("G3-3");
	    sG34SE = new SessionEntry("G3-4");
	    sG41SE = new SessionEntry("G4-1");
	    sG42SE = new SessionEntry("G4-2");
	    sG43SE = new SessionEntry("G4-3");
	    sG44SE = new SessionEntry("G4-4");
	    sH1SE = new SessionEntry("H-1");
	    sH2SE = new SessionEntry("H-2");
	    sH3SE = new SessionEntry("H-3");
	    sH4SE = new SessionEntry("H-4");
	    sJ11SE = new SessionEntry("J1-1");
	    sJ12SE = new SessionEntry("J1-2");
	    sJ13SE = new SessionEntry("J1-3");
	    sJ14SE = new SessionEntry("J1-4");
	    sJ21SE = new SessionEntry("J2-1");
	    sJ22SE = new SessionEntry("J2-2");
	    sJ23SE = new SessionEntry("J2-3");
	    sJ24SE = new SessionEntry("J2-4");
	    sN11SE = new SessionEntry("N1-1");
	    sN12SE = new SessionEntry("N1-2");
	    sN13SE = new SessionEntry("N1-3");
	    sN14SE = new SessionEntry("N1-4");
	    sN21SE = new SessionEntry("N2-1");
	    sN22SE = new SessionEntry("N2-2");
	    sN23SE = new SessionEntry("N2-3");
	    sN24SE = new SessionEntry("N2-4");
	    sR11SE = new SessionEntry("R1-1");
	    sR12SE = new SessionEntry("R1-2");
	    sR13SE = new SessionEntry("R1-3");
	    sR14SE = new SessionEntry("R1-4");
	    sR21SE = new SessionEntry("R2-1");
	    sR22SE = new SessionEntry("R2-2");
	    sR23SE = new SessionEntry("R2-3");
	    sR24SE = new SessionEntry("R2-4");
	    sSKIP1SE = new SessionEntry("SKIP-1");
	    sSKIP2SE = new SessionEntry("SKIP-2");
	    sSKIP3SE = new SessionEntry("SKIP-3");
	    sSKIP4SE = new SessionEntry("SKIP-4");
	    sT1SE = new SessionEntry("T-1");
	    sT2SE = new SessionEntry("T-2");
	    sT3SE = new SessionEntry("T-3");
	    sT4SE = new SessionEntry("T-4");
	    sU11SE = new SessionEntry("U1-1");
	    sU12SE = new SessionEntry("U1-2");
	    sU13SE = new SessionEntry("U1-3");
	    sU14SE = new SessionEntry("U1-4");
	    sU21SE = new SessionEntry("U2-1");
	    sU22SE = new SessionEntry("U2-2");
	    sU23SE = new SessionEntry("U2-3");
	    sU24SE = new SessionEntry("U2-4");
	    sV1SE = new SessionEntry("V-1");
	    sV2SE = new SessionEntry("V-2");
	    sV3SE = new SessionEntry("V-3");
	    sV4SE = new SessionEntry("V-4");
	    sY1SE = new SessionEntry("Y-1");
	    sY2SE = new SessionEntry("Y-2");
	    sY3SE = new SessionEntry("Y-3");
	    sY4SE = new SessionEntry("Y-4");
	    sZ1SE = new SessionEntry("Z-1");
	    sZ2SE = new SessionEntry("Z-2");
	    sZ3SE = new SessionEntry("Z-3");
	    sZ4SE = new SessionEntry("Z-4");

	    fA11SE = new SessionEntry("A1-1");
	    fA12SE = new SessionEntry("A1-2");
	    fA13SE = new SessionEntry("A1-3");
	    fA14SE = new SessionEntry("A1-4");
	    fA21SE = new SessionEntry("A2-1");
	    fA22SE = new SessionEntry("A2-2");
	    fA23SE = new SessionEntry("A2-3");
	    fA24SE = new SessionEntry("A2-4");
	    fE1SE = new SessionEntry("E-1");
	    fE2SE = new SessionEntry("E-2");
	    fE3SE = new SessionEntry("E-3");
	    fE4SE = new SessionEntry("E-4");
	    fF1SE = new SessionEntry("F-1");
	    fF2SE = new SessionEntry("F-2");
	    fF3SE = new SessionEntry("F-3");
	    fF4SE = new SessionEntry("F-4");
	    fG11SE = new SessionEntry("G1-1");
	    fG12SE = new SessionEntry("G1-2");
	    fG13SE = new SessionEntry("G1-3");
	    fG14SE = new SessionEntry("G1-4");
	    fG21SE = new SessionEntry("G2-1");
	    fG22SE = new SessionEntry("G2-2");
	    fG23SE = new SessionEntry("G2-3");
	    fG24SE = new SessionEntry("G2-4");
	    fG31SE = new SessionEntry("G3-1");
	    fG32SE = new SessionEntry("G3-2");
	    fG33SE = new SessionEntry("G3-3");
	    fG34SE = new SessionEntry("G3-4");
	    fG41SE = new SessionEntry("G4-1");
	    fG42SE = new SessionEntry("G4-2");
	    fG43SE = new SessionEntry("G4-3");
	    fG44SE = new SessionEntry("G4-4");
	    fH1SE = new SessionEntry("H-1");
	    fH2SE = new SessionEntry("H-2");
	    fH3SE = new SessionEntry("H-3");
	    fH4SE = new SessionEntry("H-4");
	    fJ11SE = new SessionEntry("J1-1");
	    fJ12SE = new SessionEntry("J1-2");
	    fJ13SE = new SessionEntry("J1-3");
	    fJ14SE = new SessionEntry("J1-4");
	    fJ21SE = new SessionEntry("J2-1");
	    fJ22SE = new SessionEntry("J2-2");
	    fJ23SE = new SessionEntry("J2-3");
	    fJ24SE = new SessionEntry("J2-4");
	    fN11SE = new SessionEntry("N1-1");
	    fN12SE = new SessionEntry("N1-2");
	    fN13SE = new SessionEntry("N1-3");
	    fN14SE = new SessionEntry("N1-4");
	    fN21SE = new SessionEntry("N2-1");
	    fN22SE = new SessionEntry("N2-2");
	    fN23SE = new SessionEntry("N2-3");
	    fN24SE = new SessionEntry("N2-4");
	    fR11SE = new SessionEntry("R1-1");
	    fR12SE = new SessionEntry("R1-2");
	    fR13SE = new SessionEntry("R1-3");
	    fR14SE = new SessionEntry("R1-4");
	    fR21SE = new SessionEntry("R2-1");
	    fR22SE = new SessionEntry("R2-2");
	    fR23SE = new SessionEntry("R2-3");
	    fR24SE = new SessionEntry("R2-4");
	    fSKIP1SE = new SessionEntry("SKIP-1");
	    fSKIP2SE = new SessionEntry("SKIP-2");
	    fSKIP3SE = new SessionEntry("SKIP-3");
	    fSKIP4SE = new SessionEntry("SKIP-4");
	    fT1SE = new SessionEntry("T-1");
	    fT2SE = new SessionEntry("T-2");
	    fT3SE = new SessionEntry("T-3");
	    fT4SE = new SessionEntry("T-4");
	    fU11SE = new SessionEntry("U1-1");
	    fU12SE = new SessionEntry("U1-2");
	    fU13SE = new SessionEntry("U1-3");
	    fU14SE = new SessionEntry("U1-4");
	    fU21SE = new SessionEntry("U2-1");
	    fU22SE = new SessionEntry("U2-2");
	    fU23SE = new SessionEntry("U2-3");
	    fU24SE = new SessionEntry("U2-4");
	    fV1SE = new SessionEntry("V-1");
	    fV2SE = new SessionEntry("V-2");
	    fV3SE = new SessionEntry("V-3");
	    fV4SE = new SessionEntry("V-4");
	    fY1SE = new SessionEntry("Y-1");
	    fY2SE = new SessionEntry("Y-2");
	    fY3SE = new SessionEntry("Y-3");
	    fY4SE = new SessionEntry("Y-4");
	    fZ1SE = new SessionEntry("Z-1");
	    fZ2SE = new SessionEntry("Z-2");
	    fZ3SE = new SessionEntry("Z-3");
	    fZ4SE = new SessionEntry("Z-4");

	    //THESE WILL ACTUALLY STORE THE VALUES USED
	    simpCols = new SessionEntry[] {sA11SE,sA12SE,sA13SE,sA14SE,sA21SE,sA22SE,sA23SE,sA24SE,sE1SE,sE2SE,sE3SE,
				 sE4SE,sF1SE,sF2SE,sF3SE,sF4SE,sG11SE,sG12SE,sG13SE,sG14SE,sG21SE,sG22SE,
				 sG23SE,sG24SE,sG31SE,sG32SE,sG33SE,sG34SE,sG41SE,sG42SE,sG43SE,sG44SE,
				 sH1SE,sH2SE,sH3SE,sH4SE,sJ11SE,sJ12SE,sJ13SE,sJ14SE,sJ21SE,sJ22SE,sJ23SE,
				 sJ24SE,sN11SE,sN12SE,sN13SE,sN14SE,sN21SE,sN22SE,sN23SE,sN24SE,sR11SE,sR12SE,
				 sR13SE,sR14SE,sR21SE,sR22SE,sR23SE,sR24SE,sSKIP1SE,sSKIP2SE,sSKIP3SE,sSKIP4SE,sT1SE,
				 sT2SE,sT3SE,sT4SE,sU11SE,sU12SE,sU13SE,sU14SE,sU21SE,sU22SE,sU23SE,sU24SE,
				 sV1SE,sV2SE,sV3SE,sV4SE,sY1SE,sY2SE,sY3SE,sY4SE,sZ1SE,sZ2SE,sZ3SE,sZ4SE};
	    fullCols = new SessionEntry[] {fA11SE,fA12SE,fA13SE,fA14SE,fA21SE,fA22SE,fA23SE,fA24SE,fE1SE,fE2SE,fE3SE,
				 fE4SE,fF1SE,fF2SE,fF3SE,fF4SE,fG11SE,fG12SE,fG13SE,fG14SE,fG21SE,fG22SE,
				 fG23SE,fG24SE,fG31SE,fG32SE,fG33SE,fG34SE,fG41SE,fG42SE,fG43SE,fG44SE,
				 fH1SE,fH2SE,fH3SE,fH4SE,fJ11SE,fJ12SE,fJ13SE,fJ14SE,fJ21SE,fJ22SE,fJ23SE,
				 fJ24SE,fN11SE,fN12SE,fN13SE,fN14SE,fN21SE,fN22SE,fN23SE,fN24SE,fR11SE,fR12SE,
				 fR13SE,fR14SE,fR21SE,fR22SE,fR23SE,fR24SE,fSKIP1SE,fSKIP2SE,fSKIP3SE,fSKIP4SE,fT1SE,
				 fT2SE,fT3SE,fT4SE,fU11SE,fU12SE,fU13SE,fU14SE,fU21SE,fU22SE,fU23SE,fU24SE,
				 fV1SE,fV2SE,fV3SE,fV4SE,fY1SE,fY2SE,fY3SE,fY4SE,fZ1SE,fZ2SE,fZ3SE,fZ4SE};

	    simpleSessionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    fullSessionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    createSessionTables(); //MAKE NEW METHOD

	    sp2 = new ScrollPane(simpleSessionTable); //A scroll pane for the table to go into
	    sp2.setFitToWidth(true);
		sp2.setFitToHeight(true);
	    sp2.setMaxWidth(screenWidth); //Sets the size of the are of the scroll pane to equal that of the window
	    sp2.setMaxHeight(400); //Sets the max length of the scroll pane

	    sp3 = new ScrollPane(fullSessionTable); //A scroll pane for the table to go into
	    sp3.setFitToWidth(true);
		sp3.setFitToHeight(true);
	    sp3.setMaxWidth(screenWidth); //Sets the size of the are of the scroll pane to equal that of the window
	    sp3.setMaxHeight(400); //Sets the max length of the scroll pane

	    Label sessionsTitle = new Label("Session Statistics");
		sessionsTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 18)); //Makes the font bigger and bolded
	    Button simpSButton = new Button("Simple"); //Button to switch table to show simple Session
	    Button fullSButton = new Button("Full"); //Button to switch table to show simple Session
	    Button SButton = new Button("Show Full Stats"); //Name will change based on if it's clicked
	    Button CICSButton = new Button("Show X Column"); //Name will change based on if it's clicked
	    Label sessionDisplayLabel = new Label("Simple Statistics");
	    sessionShowSwitch = new SimpleBooleanProperty(false); //default to false, showing full
	    sessionCICSwitch  = new SimpleBooleanProperty(false); //default to false, showing correct column

	    Button mainMenu = new Button("Main Menu");
	    mainMenu.setOnAction( e -> {
	    	bp.getChildren().remove(0);
	    	bp.setCenter(initial);
	    });

	    Label SessionsDesc = new Label("** Q = Number of Cases, \u2713 = Number Correct, X = Number Incorrect **");
	    Label SessionsDesc2 = new Label("** Acc = Accuracy, F/S = Fastest/Slowest Times, Avg = Average Time **");
//	    Label SessionsDesc3 = new Label("** StdT = Standard Deviation of Time, MCG = Most Common Guess **");
	    Label SessionsDesc3 = new Label("** StdT = Standard Deviation of Time, G = Most Common Wrong Guess **");
	    SessionsDesc.setFont(Font.font("Verdana", FontPosture.ITALIC, 8));
	    SessionsDesc.setTextFill(Color.rgb(175, 175, 175)); //sets the text light gray
	    SessionsDesc2.setFont(Font.font("Verdana", FontPosture.ITALIC, 8));
	    SessionsDesc2.setTextFill(Color.rgb(175, 175, 175)); //sets the text light gray
	    SessionsDesc3.setFont(Font.font("Verdana", FontPosture.ITALIC, 8));
	    SessionsDesc3.setTextFill(Color.rgb(175, 175, 175)); //sets the text light gray
	    
		Button saveSession = new Button("Export...");
		saveSession.setOnAction(e -> {
			try {
				writeSessionToFile();
			} catch (Exception e1) {
				popupMessage.show(e1.getMessage());
				System.out.println("Unable to save file.");
			}
		});
		Button loadSession = new Button("Import...");
		loadSession.setOnAction(e -> {
			try {
				readSessionFromFile();
			} catch (Exception e1) {
//				System.out.println(e1.getMessage());
				popupMessage.show(e1.getMessage());
				System.out.println("Unable to load file.");
			}
		});
		
//		//The reset button that resets the stats for the session
//		Button resetSession = new Button("Reset");
//	///
//		Popup resetPopup = new Popup();
//		resetPopup.setAutoHide(true);
//		VBox resetPopupVbox = new VBox();
//		resetPopupVbox.setAlignment(Pos.CENTER);
//		resetPopupVbox.setSpacing(10);
//		Label resetPopupLabel = new Label("Are you sure you want to reset ALL\n"
//				+ "of the stats of the current session?");
//		//CHANGE LABEL FONT HERE IF NECESSARY
//		Button resetPopupExitButton = new Button("No");
//		
//		resetPopupExitButton.setOnAction(e1 -> {
//			resetPopup.hide();
//		});
//		
//		Button resetButton = new Button("Yes");
//		
//		resetButton.setOnAction(e1 -> {
//			//Reset all columns in simple
//			for(int i = 0; i < simpCols.length; i++) {
//					simpCols[i].reset();
//			}
//			
//			//Reset all non-changed columns in full
//			for(int i = 0; i < fullCols.length; i++) {
//					fullCols[i].reset();
//			}
//			
//			resetPopup.hide();
//			this.updateSessionTables();
//			popupMessage.show("Reset all session statistics!", primary.getX() + primary.getWidth() / 5 + 20, primary.getY() + primary.getHeight() / 3);
//		});
//		HBox resetPopupButtonsHbox = new HBox();
//		resetPopupButtonsHbox.getChildren().addAll(resetButton, resetPopupExitButton);
//		resetPopupButtonsHbox.setAlignment(Pos.CENTER);
//		resetPopupButtonsHbox.setSpacing(50);
//		resetPopupVbox.getChildren().addAll(resetPopupLabel, resetPopupButtonsHbox);
//		resetPopupVbox.setStyle("-fx-background-color: rgb(244, 244, 244);"
//					+ "-fx-background-radius: 10px;" //Radius of background around corners
//					+ "-fx-border-radius: 10px;\r\n" //Radius of border around corners - make same as ^
//					+ "-fx-border-width: 5px;\r\n" //Thickness of border
//					+ "-fx-border-color: black;" //Makes round border black
//					+ "-fx-padding: 10px;"); //Extra distance between stuff and borders 
//		resetPopup.getContent().add(resetPopupVbox);
//		
//	///
//		resetSession.setOnAction(e -> {
//			resetPopup.setAnchorX(primary.getX() /*- this.getWidth() / 2*/ + primary.getWidth() / 5 /*- 10*/);
//			resetPopup.setAnchorY(primary.getY() /*- this.getHeight() / 2*/ + primary.getHeight() / 3);
//			resetPopup.show(primary);
//		});
		
//		HBox sessionIOBox = new HBox(loadSession, resetSession, saveSession);
		HBox sessionIOBox = new HBox(loadSession, saveSession);
		sessionIOBox.setAlignment(Pos.CENTER); //Centers the elements inside the HBox
		sessionIOBox.setSpacing(215); //Spaces the 2 buttons apart -- BEFORE RESET BUTTON
//		sessionIOBox.setSpacing(85); //Spaces the 3 buttons apart

	    sessions = new VBox();
	    HBox sessionsBox = new HBox();
	    sessionsBox.setAlignment(Pos.CENTER);
	    sessionsBox.setSpacing(25); //Previously 50
	    //sessionsBox.getChildren().addAll(simpSButton,fullSButton);
	    sessionsBox.getChildren().addAll(SButton,sessionDisplayLabel,CICSButton);

	    sessions.setAlignment(Pos.CENTER);
	    sessions.getChildren().addAll(sessionsTitle,sessionIOBox,sessionsBox,sp2,SessionsDesc,SessionsDesc2,SessionsDesc3,new Label(),mainMenu); //new label used as simple spacer

	    simpSButton.setOnAction( e -> {
	    	sessions.getChildren().setAll(sessionsTitle,sessionIOBox,sessionsBox,sp2,SessionsDesc,SessionsDesc2,SessionsDesc3,new Label(),mainMenu);
	    });
	    fullSButton.setOnAction( e -> {
	    	sessions.getChildren().setAll(sessionsTitle,sessionIOBox,sessionsBox,sp3,SessionsDesc,SessionsDesc2,SessionsDesc3,new Label(),mainMenu);
	    });

	    SButton.setOnAction( e -> {
	    	if(sessionShowSwitch.get()) { //If true, should be showing Full
	    		sessionShowSwitch.set(false);
	    		sessionDisplayLabel.setText("Simple Statistics");
	    		SButton.setText("Show Full Stats");
	    		sessions.getChildren().setAll(sessionsTitle,sessionIOBox,sessionsBox,sp2,SessionsDesc,SessionsDesc2,SessionsDesc3,new Label(),mainMenu);
	    	}
	    	else { //If false, should be showing Simple
	    		sessionShowSwitch.set(true);
	    		sessionDisplayLabel.setText("Full Statistics");
	    		SButton.setText("Show Simple Stats");
	    		sessions.getChildren().setAll(sessionsTitle,sessionIOBox,sessionsBox,sp3,SessionsDesc,SessionsDesc2,SessionsDesc3,new Label(),mainMenu);
	    	}
	    });
	    CICSButton.setOnAction( e -> {
	    	if(sessionCICSwitch.get()) { //If true, should be showing Incorrect
	    		sessionCICSwitch.set(false);
	    		CICSButton.setText("Show X Column");
	    		simpleSessionTable.getColumns().setAll(sPllCol,sQCol,sCCol,sAccCol,sFCol,sSCol,sAvgCol,sStdCol,sMcgCol);
		        fullSessionTable.getColumns().setAll(fPllCol,fQCol,fCCol,fAccCol,fFCol,fSCol,fAvgCol,fStdCol,fMcgCol);
	    	}
	    	else { //If false, should be showing Correct
	    		sessionCICSwitch.set(true);
	    		CICSButton.setText("Show \u2713 Column");
	    		simpleSessionTable.getColumns().setAll(sPllCol,sQCol,sIcCol,sAccCol,sFCol,sSCol,sAvgCol,sStdCol,sMcgCol);
		        fullSessionTable.getColumns().setAll(fPllCol,fQCol,fIcCol,fAccCol,fFCol,fSCol,fAvgCol,fStdCol,fMcgCol);
	    	}

	    });

	}

////////////////////////////////////////////////////////////////////////////

	public void createButtonActions() {

		//////////////////////////////////
		//	Debug Menu					//
		//////////////////////////////////

			crossRefreshMenu.setOnAction( e -> { //Resets Colors
				if(crossBox.getValue().equals("White")) {crossColor = white;}
				else if(crossBox.getValue().equals("Red")) {crossColor = red;}
				else if(crossBox.getValue().equals("Blue")) {crossColor = blue;}
				else if(crossBox.getValue().equals("Green")) {crossColor = green;}
				else if(crossBox.getValue().equals("Orange")) {crossColor = orange;}
				else if(crossBox.getValue().equals("Yellow")) {crossColor = yellow;}
				else if(crossBox.getValue().equals("Color Neutral")) {crossColor = Color.rgb(0, 0, 0, 0.0);} //INVISIBLE COLOR IF COLOR NEUTAL

				if(crossColor.equals(Color.rgb(0, 0, 0, 0.0))) {newColors();} //If color neutral
				else {newColors(crossColor);} //If certain cross
				refresh();
			});
			PLLRefreshMenu.setOnAction( e -> { //Resets PLL
				newPLL();
				refresh();
			});
			caseRefreshMenu.setOnAction( e -> { //Resets PLL and Colors
				newCase();
			});

			startTimerMenu.setOnAction( e -> {
				timer.start();
				System.out.println("Started Timer");
			});
			stopTimerMenu.setOnAction( e -> {
				timer.stop();
				System.out.println("Stopped Timer");
			});
			getTimeMenu.setOnAction( e -> {
				System.out.printf("Current time is: %s\n", time.get());
			});
			incGuessNumMenu.setOnAction( e -> {
				guessNum += 1;
				drawGuessNumber();
			});

			returnToInitial.setOnAction( e -> {
				canGuess = false; //Reset canGuess
				modeValue = 0; //Set mode value back to normal
				bp.getChildren().remove(0); //Remove the current screen
				bp.setCenter(initial); //set the Initial screen as the current setup
			});

			debug.getItems().addAll(crossRefreshMenu,PLLRefreshMenu,caseRefreshMenu,startTimerMenu,stopTimerMenu,getTimeMenu,incGuessNumMenu,returnToInitial);

			mb.getMenus().add(debug);


		//////////////////////////////////
		//	Color Menu					//
		//////////////////////////////////

			whiteMenu.setOnHidden( e -> {
				updateColors();
				refresh();
			});
			redMenu.setOnHidden( e -> {
				updateColors();
				refresh();
			});
			blueMenu.setOnHidden( e -> {
				updateColors();
				refresh();
			});
			greenMenu.setOnHidden( e -> {
				updateColors();
				refresh();
			});
			orangeMenu.setOnHidden( e -> {
				updateColors();
				refresh();
			});
			yellowMenu.setOnHidden( e -> {
				updateColors();
				refresh();
			});

			colorMenu.getItems().addAll(whiteMenu,redMenu,blueMenu,greenMenu,orangeMenu,yellowMenu);
			mb.getMenus().add(colorMenu);

		//////////////////////////////////
		//	Difficulty Menu				//
		//////////////////////////////////

			//Gridpane Setup
			//MOVED TO ITS OWN COMMAND - createGuessButtons() - TO ACCOUNT FOR WINDOW SIZE CHANGE BASED ON MODES
//			createGuessButtons(); //Problems with using it once here

			//Button Action Setup
			
			  //defaults to simple
			
			simple.setOnAction( e -> {
				//Inverted to account for change when clicked activated
				if(simple.isSelected()) { //Run if simple is NOT selected
						System.out.println("Changed difficulty to Simple");
						master.getChildren().remove(fullGP);
						master.getChildren().add(simpleGP);
						//Put difficulty toggle right here
						difficultyValue = 0; //Change the difficulty
						activePLLname = activePLLname.substring(0,1);
					}
				else {/* Does nothing because it's already selected */}

				simple.setSelected(true); //set check on simple
				full.setSelected(false); //Remove check from full
			});

			full.setOnAction( e -> {
				//Inverted to account for change when clicked activated
				if(full.isSelected()) { //Run if full is NOT selected
						System.out.println("Changed difficulty to Full");
						master.getChildren().remove(simpleGP);
						master.getChildren().add(fullGP);
						//Put difficulty toggle right here
						difficultyValue = 1; //Change the mode
						activePLLname = PLLname[PLLnum/4]; //Update the active PLL name
					}
				else {/* Does nothing because it's already selected */}

				full.setSelected(true); //set check on simple
				simple.setSelected(false); //Remove check from full
			});

			difficulty.getItems().addAll(simple,full);
			mb.getMenus().add(difficulty);

		////////////////////////////////////////////////////
		// 	INITIAL MENU OF THE PROGRAM				   	///
		///////////////////////////////////////////////////

			startButton.setOnAction( e -> {
				startTesting(); //Made into its own function since this was pretty big
			});

			caseSelectButton.setOnAction( e -> {
				bp.getChildren().remove(0); //Remove initial screen from bp
				//GET THE NEW COLORS BASED ON THE CROSS COLOR BOX
				//Set Cross Color
				if(crossBox.getValue().equals("White")) {crossColor = white;}
				else if(crossBox.getValue().equals("Red")) {crossColor = red;}
				else if(crossBox.getValue().equals("Blue")) {crossColor = blue;}
				else if(crossBox.getValue().equals("Green")) {crossColor = green;}
				else if(crossBox.getValue().equals("Orange")) {crossColor = orange;}
				else if(crossBox.getValue().equals("Yellow")) {crossColor = yellow;}
				else if(crossBox.getValue().equals("Color Neutral")) {crossColor = Color.rgb(0, 0, 0, 0.0);} //INVISIBLE COLOR IF COLOR NEUTAL
				//Actually Set the new Colors

				if(crossColor.equals(Color.rgb(0, 0, 0, 0.0))) {newColors();} //If color neutral
				else {newColors(crossColor);} //If certain cross

				//FOR EACH SetCaseBox IN THE ARRAY, REDRAW(NEW FACECOLORS);
				for(SetCaseBox b: caseSelectArray) {b.redrawCase(faceColors);}
				bp.setCenter(caseSelection); //Set the VBox for case selection to bp
			});

			sessionStatsButton.setOnAction( e -> {
		    	bp.getChildren().remove(0);
		    	bp.setCenter(sessions);
		    });
	} //END OF CREATE BUTTON ACTIONS

////////////////////////////////////////////////////////////////////////////

	public void init() {
		//cubePen.strokeRect(0, 0, 400, 400);
		//drawCubeOutline(300,300,50);

		/* Face Colors: 0 - White
		 				1 - Red
		 				2 - Blue
		 				3 - Green
		 				4 - Orange
		 				5 - Yellow
		   Get the opposite color by doing 5-<color_index>
		   Cube Colors: 1 - Left Front
		   				2 - Right Front
		   				3 - Right Back
		   				4 - Left Back
		   				5 - Bottom
		   				6 - Top
		 */

/* CUBE LAYOUT - faces denoted by 'f#'
 * 0     5
 *  1	4
 *   2|3
 */

		//Initial Position for the cube
		//System.out.printf("%s, %s",cubeCanvas.getLayoutX(),cubeCanvas.getLayoutY());
		double cubePosX = cubeCanvasLength/2.0;//cubeCanvas.getLayoutX()-(screenLength/2.0);
		double cubePosY = 3*cubeCanvasWidth/4.0;//cubeCanvas.getLayoutY()-(screenWidth/2.0);
		double cubeLength = cubeSize;

		//Create Sides of RubiksCube
		Side right = new Side(cubePosX,cubePosY,cubeLength);
		Side left = new Side(cubePosX,cubePosY,-cubeLength);
		Top top = new Top(cubePosX,right.topY1,cubeLength);

		SideSquare f0 = new SideSquare(left.topX3,left.topY3,-cubeLength);
		SideSquare f1 = new SideSquare(left.topX2,left.topY2,-cubeLength);
		SideSquare f2 = new SideSquare(left.topX1,left.topY1,-cubeLength);
		SideSquare f3 = new SideSquare(right.topX1,right.topY1,cubeLength);
		SideSquare f4 = new SideSquare(right.topX2,right.topY2,cubeLength);
		SideSquare f5 = new SideSquare(right.topX3,right.topY3,cubeLength);

		//System.out.println("" + right.topX1 + ", " + right.topY1);

		//Add different faces to rubiksCube
		rubiksCube.add(f0);
		rubiksCube.add(f1);
		rubiksCube.add(f2);
		rubiksCube.add(f3);
		rubiksCube.add(f4);
		rubiksCube.add(f5);

		rubiksCube.add(left);
		rubiksCube.add(right);
		rubiksCube.add(top);

	} //END OF INIT

	//group of Titledpanes containing canvases of each case with a checkbox for if it's included or not
	//ALL inside a scrollpane
	//Colors of cases depend on cross color
	//This creates the menu for the case selection
	public void createCaseSelect() {
		//VBox caseSelection
		//ScrollPane cssp
		//CheckBox for all and None AT THE TOP
		//Button at the bottom for returning AND for expanding all TitledPanes

		//Title
		Label caseSelectionTitle = new Label("Case Selection");
		caseSelectionTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 18)); //Makes the font bigger and bolded

		//Scrollpane with stuff here
		ScrollPane cssp = new ScrollPane();
		cssp.setFitToWidth(true);
		cssp.setFitToHeight(true);
		cssp.setMaxWidth(screenWidth); //Sets the size of the are of the scroll pane to equal that of the window
		cssp.setMaxHeight(400); //Sets the max length of the scroll pane

		//I need to put everything into an array, so I can go through it and redraw cases when this this menu is loaded

		//TitledPane for each PLL, with 4 cases in each - each TitledPane is of a VBOX with 4 SetCaseBoxes, each with a different case
		//First check the cross color to influence cases drawn
		TitledPane A1 = new TitledPane("A1",new VBox(caseSelectArray[0],caseSelectArray[1],caseSelectArray[2],caseSelectArray[3]));
		TitledPane A2 = new TitledPane("A2",new VBox(caseSelectArray[4],caseSelectArray[5],caseSelectArray[6],caseSelectArray[7]));
		TitledPane E = new TitledPane("E",new VBox(caseSelectArray[8],caseSelectArray[9],caseSelectArray[10],caseSelectArray[11]));
		TitledPane F = new TitledPane("F",new VBox(caseSelectArray[12],caseSelectArray[13],caseSelectArray[14],caseSelectArray[15]));
		TitledPane G1 = new TitledPane("G1",new VBox(caseSelectArray[16],caseSelectArray[17],caseSelectArray[18],caseSelectArray[19]));
		TitledPane G2 = new TitledPane("G2",new VBox(caseSelectArray[20],caseSelectArray[21],caseSelectArray[22],caseSelectArray[23]));
		TitledPane G3 = new TitledPane("G3",new VBox(caseSelectArray[24],caseSelectArray[25],caseSelectArray[26],caseSelectArray[27]));
		TitledPane G4 = new TitledPane("G4",new VBox(caseSelectArray[28],caseSelectArray[29],caseSelectArray[30],caseSelectArray[31]));
		TitledPane H = new TitledPane("H",new VBox(caseSelectArray[32],caseSelectArray[33],caseSelectArray[34],caseSelectArray[35]));
		TitledPane J1 = new TitledPane("J1",new VBox(caseSelectArray[36],caseSelectArray[37],caseSelectArray[38],caseSelectArray[39]));
		TitledPane J2 = new TitledPane("J2",new VBox(caseSelectArray[40],caseSelectArray[41],caseSelectArray[42],caseSelectArray[43]));
		TitledPane N1 = new TitledPane("N1",new VBox(caseSelectArray[44],caseSelectArray[45],caseSelectArray[46],caseSelectArray[47]));
		TitledPane N2 = new TitledPane("N2",new VBox(caseSelectArray[48],caseSelectArray[49],caseSelectArray[50],caseSelectArray[51]));
		TitledPane R1 = new TitledPane("R1",new VBox(caseSelectArray[52],caseSelectArray[53],caseSelectArray[54],caseSelectArray[55]));
		TitledPane R2 = new TitledPane("R2",new VBox(caseSelectArray[56],caseSelectArray[57],caseSelectArray[58],caseSelectArray[59]));
		TitledPane SKIP = new TitledPane("SKIP",new VBox(caseSelectArray[60],caseSelectArray[61],caseSelectArray[62],caseSelectArray[63]));
		TitledPane T = new TitledPane("T",new VBox(caseSelectArray[64],caseSelectArray[65],caseSelectArray[66],caseSelectArray[67]));
		TitledPane U1 = new TitledPane("U1",new VBox(caseSelectArray[68],caseSelectArray[69],caseSelectArray[70],caseSelectArray[71]));
		TitledPane U2 = new TitledPane("U2",new VBox(caseSelectArray[72],caseSelectArray[73],caseSelectArray[74],caseSelectArray[75]));
		TitledPane V = new TitledPane("V",new VBox(caseSelectArray[76],caseSelectArray[77],caseSelectArray[78],caseSelectArray[79]));
		TitledPane Y = new TitledPane("Y",new VBox(caseSelectArray[80],caseSelectArray[81],caseSelectArray[82],caseSelectArray[83]));
		TitledPane Z = new TitledPane("Z",new VBox(caseSelectArray[84],caseSelectArray[85],caseSelectArray[86],caseSelectArray[87]));

		//Array of titlepanes to do things with
		TitledPane[] TPs = new TitledPane[] {A1, A2, E, F, G1, G2, G3, G4, H, J1, J2, N1, N2, R1, R2, SKIP, T, U1, U2, V, Y, Z};
		for(TitledPane tp: TPs) {tp.setExpanded(false);} //Start all of the titledpanes closed

		cssp.setContent(new VBox(
				A1, A2,
				E,
				F,
				G1, G2, G3, G4,
				H,
				J1, J2,
				N1, N2,
				R1, R2,
				SKIP,
				T,
				U1, U2,
				V,
				Y,
				Z
		)); //End of adding different cases to the scrollpane
		
		//Setup Preset Selection
		HBox presets1 = new HBox();
		HBox presets2 = new HBox();
		
		//H; SKIP; U1; U2; Z;
		CasesPresetCheckBox epll = new CasesPresetCheckBox(" EPLL ", new SetCaseBox[] {
				caseSelectArray[32],caseSelectArray[33],caseSelectArray[34],caseSelectArray[35], //H
				caseSelectArray[60],caseSelectArray[61],caseSelectArray[62],caseSelectArray[63], //Skip
				caseSelectArray[68],caseSelectArray[69],caseSelectArray[70],caseSelectArray[71], //U1
				caseSelectArray[72],caseSelectArray[73],caseSelectArray[74],caseSelectArray[75], //U2
				caseSelectArray[84],caseSelectArray[85],caseSelectArray[86],caseSelectArray[87] //Z
				}); //20 cases
		
		//E; N1; N2; V; Y;
		CasesPresetCheckBox dicp = new CasesPresetCheckBox("DiagCP", new SetCaseBox[] {
				caseSelectArray[8],caseSelectArray[9],caseSelectArray[10],caseSelectArray[11], //E
				caseSelectArray[44],caseSelectArray[45],caseSelectArray[46],caseSelectArray[47], //N1
				caseSelectArray[48],caseSelectArray[49],caseSelectArray[50],caseSelectArray[51], //N2
				caseSelectArray[76],caseSelectArray[77],caseSelectArray[78],caseSelectArray[79], //V
				caseSelectArray[80],caseSelectArray[81],caseSelectArray[82],caseSelectArray[83] //Y
				}); //20 cases
		
		//A1-1; A2-1; J1; J2;
		CasesPresetCheckBox acpa = new CasesPresetCheckBox("CP - A", new SetCaseBox[] {
				caseSelectArray[0], //A1-1
				caseSelectArray[4], //A2-1
				caseSelectArray[36],caseSelectArray[37],caseSelectArray[38],caseSelectArray[39], //J1
				caseSelectArray[40],caseSelectArray[41],caseSelectArray[42],caseSelectArray[43] //J2
				}); //10 Cases
		//A1-4; A2-2; G1-4; G3-3; T3,T4; R1-1; R2-4
		CasesPresetCheckBox acpb = new CasesPresetCheckBox("CP - B", new SetCaseBox[] {
				caseSelectArray[3], //A1-4
				caseSelectArray[5], //A2-2
				caseSelectArray[19], //G1-4
				caseSelectArray[26], //G3-3
				caseSelectArray[66],caseSelectArray[67], //T3,T4
				caseSelectArray[52], //R1-1
				caseSelectArray[59] //R2-4
				}); //8 Cases
		//A1-2; A2-4; F2,F3; G1-1; G2-1,G2-2; G3-2; G4-1,G4-2; R1-2; R2-3; T1,T2;
		CasesPresetCheckBox acpc = new CasesPresetCheckBox("CP - C", new SetCaseBox[] {
				caseSelectArray[1], //A1-2
				caseSelectArray[7], //A2-4
				caseSelectArray[13],caseSelectArray[14], //F2,F3
				caseSelectArray[16], //G1-1
				caseSelectArray[20],caseSelectArray[21], //G2-1,G2-2
				caseSelectArray[25], //G3-2
				caseSelectArray[28],caseSelectArray[29], //G4-1,G4-2
				caseSelectArray[53], //R1-2
				caseSelectArray[58], //R2-3
				caseSelectArray[64],caseSelectArray[65] //T1,T2
				}); //14 Cases
		//A1-3; A2-3; G1-3; G2-3,G2-4; G3-4; G4-3,G4-4; R1-4; R2-1;
		CasesPresetCheckBox acpd = new CasesPresetCheckBox("CP - D", new SetCaseBox[] {
				caseSelectArray[2], //A1-3
				caseSelectArray[6], //A2-3
				caseSelectArray[18], //G1-3
				caseSelectArray[22],caseSelectArray[23], //G2-3,G2-4
				caseSelectArray[27], //G3-4
				caseSelectArray[30],caseSelectArray[31], //G4-3,G4-4
				caseSelectArray[55], //R1-4
				caseSelectArray[56] //R2-1
				}); //10 Cases
		//F1,F4; G1-2; G3-1; R1-3; R2-2;
		CasesPresetCheckBox acpe = new CasesPresetCheckBox("CP - E", new SetCaseBox[] {
				caseSelectArray[12],caseSelectArray[15], //F1,F4
				caseSelectArray[17], //G1-2
				caseSelectArray[24], //G3-1
				caseSelectArray[54], //R1-3
				caseSelectArray[57] //R2-2
				}); //6 Cases

		//Select all button
		Button allButton = new Button("Select All");
		allButton.setOnAction( e -> {
			for(SetCaseBox c: caseSelectArray) {c.setSelected(true);} //Sets everything to be selected
			epll.setSelected(true); //Sets all presets to on
			dicp.setSelected(true);
			acpa.setSelected(true);
			acpb.setSelected(true);
			acpc.setSelected(true);
			acpd.setSelected(true);
			acpe.setSelected(true);
		});

		//Select None button
		Button noneButton = new Button("Select None");
		noneButton.setOnAction( e -> {
			for(SetCaseBox c: caseSelectArray) {c.setSelected(false);} //Sets everything to be unselected
			epll.setSelected(false); //Sets all presets to off
			dicp.setSelected(false);
			acpa.setSelected(false);
			acpb.setSelected(false);
			acpc.setSelected(false);
			acpd.setSelected(false);
			acpe.setSelected(false);
		});
		
		//Setup the pop-up for the legend 
		Popup legendPopup = new Popup();
		legendPopup.setAutoHide(true);
		
		Label epllT = new Label("EPLL");
		epllT.setFont(Font.font("Verdana", FontWeight.BOLD, 14)); //Makes the font bigger and bolded
		Label epllD = new Label("- Corners correctly placed"
				+ "\n- H, SKIP, Ua, Ub, H");
		Label dicpT = new Label("Diag. CP");
		dicpT.setFont(Font.font("Verdana", FontWeight.BOLD, 14)); //Makes the font bigger and bolded
		Label dicpD = new Label("- Diagonal block movement"
				+ "\n- E, N1, N2, V, Y");
		Label acpaT = new Label("Adj. CP - A");
		acpaT.setFont(Font.font("Verdana", FontWeight.BOLD, 14)); //Makes the font bigger and bolded
		Label acpaD = new Label("- No headlights, two blocks"
				+ "\n- A1-1, A2-1, J1, J2");
		Label acpbT = new Label("Adj. CP - B");
		acpbT.setFont(Font.font("Verdana", FontWeight.BOLD, 14)); //Makes the font bigger and bolded
		Label acpbD = new Label("- Headlights, one block"
				+ "\n- A1-4, A2-2, G1-4, G3-3, T3, T4, R1-1, R2-4");
		Label acpcT = new Label("Adj. CP - C");
		acpcT.setFont(Font.font("Verdana", FontWeight.BOLD, 14)); //Makes the font bigger and bolded
		Label acpcD = new Label("- No headlights, one block"
				+ "\n- A1-2, A2-4, F2, F3, G1-1, G2-1, G2-2, "
				+ "\n  G3-2, G4-1, G4-2, R1-2, R2-3, T1, T2");
		Label acpdT = new Label("Adj. CP - D");
		acpdT.setFont(Font.font("Verdana", FontWeight.BOLD, 14)); //Makes the font bigger and bolded
		Label acpdD = new Label("- Headlights, no blocks"
				+ "\n- A1-3, A2-3, G1-3, G2-3, G2-4, G3-4, G4-3, "
				+ "\n  G4-4, R1-4, R2-1");
		Label acpeT = new Label("Adj. CP - E");
		acpeT.setFont(Font.font("Verdana", FontWeight.BOLD, 14)); //Makes the font bigger and bolded
		Label acpeD = new Label("- No headlights, no blocks"
				+ "\n- F1 , F4, G1-2, G3-1, R1-3, R2-2");
		
		Button exitButton = new Button("Close");
		
		exitButton.setOnAction(e -> {
			legendPopup.hide();
		});
		
		Label attributionLabel = new Label("Categories setup as organized on:");
		attributionLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
		attributionLabel.setTextFill(Color.rgb(150, 150, 150));
		Hyperlink attributionLink = new Hyperlink("Sarah's Cubing Site");
		attributionLink.setOnAction( e -> getHostServices().showDocument("https://sarah.cubing.net/3x3x3/pll-recognition-guide")); //Makes the hyperlink clickable
		attributionLink.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
		attributionLink.setTextFill(Color.rgb(150, 150, 150));
		attributionLink.setUnderline(true); //Underlines the text
		
		VBox attributionBox = new VBox(attributionLabel, attributionLink);
		attributionBox.setAlignment(Pos.CENTER);
		attributionBox.setSpacing(0);
		
		
		VBox legendVbox = new VBox(epllT, epllD, dicpT, dicpD, acpaT, acpaD,
				acpbT, acpbD, acpcT, acpcD, acpdT, acpdD, acpeT, acpeD, exitButton, attributionBox);
		legendVbox.setAlignment(Pos.CENTER);
		legendVbox.setSpacing(5);
		
		legendVbox.setStyle("-fx-background-color: rgb(244, 244, 244);"
					+ "-fx-background-radius: 10px;" //Radius of background around corners
					+ "-fx-border-radius: 10px;\r\n" //Radius of border around corners - make same as ^
					+ "-fx-border-width: 5px;\r\n" //Thickness of border
					+ "-fx-border-color: black;" //Makes round border black
					+ "-fx-padding: 10px;"); //Extra distance between stuff and borders 
		legendPopup.getContent().add(legendVbox);
		
		Button presetsKey = new Button("Legend");
		presetsKey.setOnAction(e -> {
			legendPopup.setAnchorX(primary.getX() + 53);
			legendPopup.setAnchorY(primary.getY() + 20);
			legendPopup.show(primary);
		});
		
		presets1.setAlignment(Pos.CENTER);
		presets1.getChildren().setAll(epll, allButton, presetsKey, noneButton, dicp);
		presets1.setSpacing(5);
		
		presets2.setAlignment(Pos.CENTER);
		presets2.getChildren().setAll(acpa, acpb, acpc, acpd, acpe);
		presets2.setSpacing(16);

		//Setup the HBox with the buttons -- buttons moved to presets1
//		HBox buttonsBox = new HBox(allButton,noneButton);//,expandButton,retractButton); //Creates the HBox
//		buttonsBox.setAlignment(Pos.CENTER); //center aligns the HBox
//		buttonsBox.setSpacing(50);//20); //Spacing between the buttons

		Label errorLabel = new Label("** There is nothing being tested! **");
		Label errorLabel2 = new Label("** Please select at least 1 case to continue **");
		errorLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 11)); //make the text small and italicized
		errorLabel.setTextFill(Color.rgb(255, 102, 102)); //Set the color to light red
		errorLabel2.setFont(Font.font("Verdana", FontPosture.ITALIC, 11)); //make the text small and italicized
		errorLabel2.setTextFill(Color.rgb(255, 102, 102)); //Set the color to light red

		Button returnButton = new Button("Main Menu");

		//new Label()s is used as a simple spacer
//		caseSelection.getChildren().addAll(caseSelectionTitle,new Label(),buttonsBox,cssp,new Label(),returnButton,new Label());
		caseSelection.getChildren().addAll(caseSelectionTitle,presets1,presets2,cssp,new Label(),returnButton,new Label());
		

		returnButton.setOnAction( e -> {
//			for(SimpleBooleanProperty b: new SimpleBooleanProperty[] {A11,A12,A13,A14,A21,A22,A23,A24,E1,E2,E3,E4,
//					F1,F2,F3,F4,G11,G12,G13,G14,G21,G22,G23,G24,G31,G32,G33,G34,G41,G42,G43,G44,H1,H2,H3,H4,J11,J12,
//					J13,J14,J21,J22,J23,J24,N11,N12,N13,N14,N21,N22,N23,N24,R11,R12,R13,R14,R21,R22,R23,R24,SKIP1,SKIP2,
//					SKIP3,SKIP4,T1,T2,T3,T4,U11,U12,U13,U14,U21,U22,U23,U24,V1,V2,V3,V4,Y1,Y2,Y3,Y4,Z1,Z2,Z3,Z4}) {System.out.println(b.get());}
			// ^ prints which ones are set to true and false
			//a list of every array's boolean value -- used to check to make sure at least 1 case is present
			boolean[] boolArray = new boolean[] {A11.get(),A12.get(),A13.get(),A14.get(),A21.get(),A22.get(),A23.get(),A24.get(),E1.get(),E2.get(),
					E3.get(),E4.get(),F1.get(),F2.get(),F3.get(),F4.get(),G11.get(),G12.get(),G13.get(),G14.get(),G21.get(),G22.get(),
					G23.get(),G24.get(),G31.get(),G32.get(),G33.get(),G34.get(),G41.get(),G42.get(),G43.get(),G44.get(),H1.get(),H2.get(),
					H3.get(),H4.get(),J11.get(),J12.get(),J13.get(),J14.get(),J21.get(),J22.get(),J23.get(),J24.get(),N11.get(),N12.get(),
					N13.get(),N14.get(),N21.get(),N22.get(),N23.get(),N24.get(),R11.get(),R12.get(),R13.get(),R14.get(),R21.get(),
					R22.get(),R23.get(),R24.get(),SKIP1.get(),SKIP2.get(),SKIP3.get(),SKIP4.get(),T1.get(),T2.get(),T3.get(),T4.get(),
					U11.get(),U12.get(),U13.get(),U14.get(),U21.get(),U22.get(),U23.get(),U24.get(),V1.get(),V2.get(),V3.get(),V4.get(),
					Y1.get(),Y2.get(),Y3.get(),Y4.get(),Z1.get(),Z2.get(),Z3.get(),Z4.get()};
			boolean containsCase = false; //used to see if there is a case in the selected values
			for(boolean b: boolArray) {if(b) {containsCase = true; break;}} //if it contains a case, the loop is broken
			if(containsCase) { //Exit out if there is at least one case selected
//			if(Arrays.asList(boolArray).contains(true)) {
				caseSelection.getChildren().removeAll(errorLabel,errorLabel2);
				bp.getChildren().remove(0); //Removes caseSelection from the screen
				bp.setCenter(initial); //Sets the initial screen as the active screen
			}
			else {if(!(caseSelection.getChildren().contains(errorLabel))) {caseSelection.getChildren().addAll(errorLabel,errorLabel2);}} //Adds the error label if there are no cases selected
		});

	} //END OF createCaseSelect

	//This should actually add things to the PLL array
	//THIS SHOULD BE RUN WHEN START BUTTON IS HIT.
	public void initCases() {
		//Needs to ADD cases to PLL array if they're true
		//Needs to affect which buttons are shown and which keybinds are executable. make the things run under if statements?
		enabledPLLs = new ArrayList<>(); //resets the PLLs
		enabledPLLNames = new ArrayList<>(); //resets the PLLs

		if(A11.get()) {enabledPLLs.add(PLL[0]); enabledPLLNames.add("A1-1");}
		if(A12.get()) {enabledPLLs.add(PLL[1]); enabledPLLNames.add("A1-2");}
		if(A13.get()) {enabledPLLs.add(PLL[2]); enabledPLLNames.add("A1-3");}
		if(A14.get()) {enabledPLLs.add(PLL[3]); enabledPLLNames.add("A1-4");}
		if(A21.get()) {enabledPLLs.add(PLL[4]); enabledPLLNames.add("A2-1");}
		if(A22.get()) {enabledPLLs.add(PLL[5]); enabledPLLNames.add("A2-2");}
		if(A23.get()) {enabledPLLs.add(PLL[6]); enabledPLLNames.add("A2-3");}
		if(A24.get()) {enabledPLLs.add(PLL[7]); enabledPLLNames.add("A2-4");}
		if(E1.get()) {enabledPLLs.add(PLL[8]); enabledPLLNames.add("E-1");}
		if(E2.get()) {enabledPLLs.add(PLL[9]); enabledPLLNames.add("E-2");}
		if(E3.get()) {enabledPLLs.add(PLL[10]); enabledPLLNames.add("E-3");}
		if(E4.get()) {enabledPLLs.add(PLL[11]); enabledPLLNames.add("E-4");}
		if(F1.get()) {enabledPLLs.add(PLL[12]); enabledPLLNames.add("F-1");}
		if(F2.get()) {enabledPLLs.add(PLL[13]); enabledPLLNames.add("F-2");}
		if(F3.get()) {enabledPLLs.add(PLL[14]); enabledPLLNames.add("F-3");}
		if(F4.get()) {enabledPLLs.add(PLL[15]); enabledPLLNames.add("F-4");}
		if(G11.get()) {enabledPLLs.add(PLL[16]); enabledPLLNames.add("G1-1");}
		if(G12.get()) {enabledPLLs.add(PLL[17]); enabledPLLNames.add("G1-2");}
		if(G13.get()) {enabledPLLs.add(PLL[18]); enabledPLLNames.add("G1-3");}
		if(G14.get()) {enabledPLLs.add(PLL[19]); enabledPLLNames.add("G1-4");}
		if(G21.get()) {enabledPLLs.add(PLL[20]); enabledPLLNames.add("G2-1");}
		if(G22.get()) {enabledPLLs.add(PLL[21]); enabledPLLNames.add("G2-2");}
		if(G23.get()) {enabledPLLs.add(PLL[22]); enabledPLLNames.add("G2-3");}
		if(G24.get()) {enabledPLLs.add(PLL[23]); enabledPLLNames.add("G2-4");}
		if(G31.get()) {enabledPLLs.add(PLL[24]); enabledPLLNames.add("G3-1");}
		if(G32.get()) {enabledPLLs.add(PLL[25]); enabledPLLNames.add("G3-2");}
		if(G33.get()) {enabledPLLs.add(PLL[26]); enabledPLLNames.add("G3-3");}
		if(G34.get()) {enabledPLLs.add(PLL[27]); enabledPLLNames.add("G3-4");}
		if(G41.get()) {enabledPLLs.add(PLL[28]); enabledPLLNames.add("G4-1");}
		if(G42.get()) {enabledPLLs.add(PLL[29]); enabledPLLNames.add("G4-2");}
		if(G43.get()) {enabledPLLs.add(PLL[30]); enabledPLLNames.add("G4-3");}
		if(G44.get()) {enabledPLLs.add(PLL[31]); enabledPLLNames.add("G4-4");}
		if(H1.get()) {enabledPLLs.add(PLL[32]); enabledPLLNames.add("H-1");}
		if(H2.get()) {enabledPLLs.add(PLL[33]); enabledPLLNames.add("H-2");}
		if(H3.get()) {enabledPLLs.add(PLL[34]); enabledPLLNames.add("H-3");}
		if(H4.get()) {enabledPLLs.add(PLL[35]); enabledPLLNames.add("H-4");}
		if(J11.get()) {enabledPLLs.add(PLL[36]); enabledPLLNames.add("J1-1");}
		if(J12.get()) {enabledPLLs.add(PLL[37]); enabledPLLNames.add("J1-2");}
		if(J13.get()) {enabledPLLs.add(PLL[38]); enabledPLLNames.add("J1-3");}
		if(J14.get()) {enabledPLLs.add(PLL[39]); enabledPLLNames.add("J1-4");}
		if(J21.get()) {enabledPLLs.add(PLL[40]); enabledPLLNames.add("J2-1");}
		if(J22.get()) {enabledPLLs.add(PLL[41]); enabledPLLNames.add("J2-2");}
		if(J23.get()) {enabledPLLs.add(PLL[42]); enabledPLLNames.add("J2-3");}
		if(J24.get()) {enabledPLLs.add(PLL[43]); enabledPLLNames.add("J2-4");}
		if(N11.get()) {enabledPLLs.add(PLL[44]); enabledPLLNames.add("N1-1");}
		if(N12.get()) {enabledPLLs.add(PLL[45]); enabledPLLNames.add("N1-2");}
		if(N13.get()) {enabledPLLs.add(PLL[46]); enabledPLLNames.add("N1-3");}
		if(N14.get()) {enabledPLLs.add(PLL[47]); enabledPLLNames.add("N1-4");}
		if(N21.get()) {enabledPLLs.add(PLL[48]); enabledPLLNames.add("N2-1");}
		if(N22.get()) {enabledPLLs.add(PLL[49]); enabledPLLNames.add("N2-2");}
		if(N23.get()) {enabledPLLs.add(PLL[50]); enabledPLLNames.add("N2-3");}
		if(N24.get()) {enabledPLLs.add(PLL[51]); enabledPLLNames.add("N2-4");}
		if(R11.get()) {enabledPLLs.add(PLL[52]); enabledPLLNames.add("R1-1");}
		if(R12.get()) {enabledPLLs.add(PLL[53]); enabledPLLNames.add("R1-2");}
		if(R13.get()) {enabledPLLs.add(PLL[54]); enabledPLLNames.add("R1-3");}
		if(R14.get()) {enabledPLLs.add(PLL[55]); enabledPLLNames.add("R1-4");}
		if(R21.get()) {enabledPLLs.add(PLL[56]); enabledPLLNames.add("R2-1");}
		if(R22.get()) {enabledPLLs.add(PLL[57]); enabledPLLNames.add("R2-2");}
		if(R23.get()) {enabledPLLs.add(PLL[58]); enabledPLLNames.add("R2-3");}
		if(R24.get()) {enabledPLLs.add(PLL[59]); enabledPLLNames.add("R2-4");}
		if(SKIP1.get()) {enabledPLLs.add(PLL[60]); enabledPLLNames.add("SKIP-1");}
		if(SKIP2.get()) {enabledPLLs.add(PLL[61]); enabledPLLNames.add("SKIP-2");}
		if(SKIP3.get()) {enabledPLLs.add(PLL[62]); enabledPLLNames.add("SKIP-3");}
		if(SKIP4.get()) {enabledPLLs.add(PLL[63]); enabledPLLNames.add("SKIP-4");}
		if(T1.get()) {enabledPLLs.add(PLL[64]); enabledPLLNames.add("T-1");}
		if(T2.get()) {enabledPLLs.add(PLL[65]); enabledPLLNames.add("T-2");}
		if(T3.get()) {enabledPLLs.add(PLL[66]); enabledPLLNames.add("T-3");}
		if(T4.get()) {enabledPLLs.add(PLL[67]); enabledPLLNames.add("T-4");}
		if(U11.get()) {enabledPLLs.add(PLL[68]); enabledPLLNames.add("U1-1");}
		if(U12.get()) {enabledPLLs.add(PLL[69]); enabledPLLNames.add("U1-2");}
		if(U13.get()) {enabledPLLs.add(PLL[70]); enabledPLLNames.add("U1-3");}
		if(U14.get()) {enabledPLLs.add(PLL[71]); enabledPLLNames.add("U1-4");}
		if(U21.get()) {enabledPLLs.add(PLL[72]); enabledPLLNames.add("U2-1");}
		if(U22.get()) {enabledPLLs.add(PLL[73]); enabledPLLNames.add("U2-2");}
		if(U23.get()) {enabledPLLs.add(PLL[74]); enabledPLLNames.add("U2-3");}
		if(U24.get()) {enabledPLLs.add(PLL[75]); enabledPLLNames.add("U2-4");}
		if(V1.get()) {enabledPLLs.add(PLL[76]); enabledPLLNames.add("V-1");}
		if(V2.get()) {enabledPLLs.add(PLL[77]); enabledPLLNames.add("V-2");}
		if(V3.get()) {enabledPLLs.add(PLL[78]); enabledPLLNames.add("V-3");}
		if(V4.get()) {enabledPLLs.add(PLL[79]); enabledPLLNames.add("V-4");}
		if(Y1.get()) {enabledPLLs.add(PLL[80]); enabledPLLNames.add("Y-1");}
		if(Y2.get()) {enabledPLLs.add(PLL[81]); enabledPLLNames.add("Y-2");}
		if(Y3.get()) {enabledPLLs.add(PLL[82]); enabledPLLNames.add("Y-3");}
		if(Y4.get()) {enabledPLLs.add(PLL[83]); enabledPLLNames.add("Y-4");}
		if(Z1.get()) {enabledPLLs.add(PLL[84]); enabledPLLNames.add("Z-1");}
		if(Z2.get()) {enabledPLLs.add(PLL[85]); enabledPLLNames.add("Z-2");}
		if(Z3.get()) {enabledPLLs.add(PLL[86]); enabledPLLNames.add("Z-3");}
		if(Z4.get()) {enabledPLLs.add(PLL[87]); enabledPLLNames.add("Z-4");}
	}

	//First Time drawing the cube
	public void drawCubeStart() {
		if(crossBox.getValue().equals("White")) {crossColor = white;}
		else if(crossBox.getValue().equals("Red")) {crossColor = red;}
		else if(crossBox.getValue().equals("Blue")) {crossColor = blue;}
		else if(crossBox.getValue().equals("Green")) {crossColor = green;}
		else if(crossBox.getValue().equals("Orange")) {crossColor = orange;}
		else if(crossBox.getValue().equals("Yellow")) {crossColor = yellow;}
		else if(crossBox.getValue().equals("Color Neutral")) {crossColor = Color.rgb(0, 0, 0, 0.0);} //INVISIBLE COLOR IF COLOR NEUTAL

		if(crossColor.equals(Color.rgb(0, 0, 0, 0.0))) {newColors();} //If color neutral
		else {newColors(crossColor);} //If certain cross
		refreshPLL();
		refresh();
	}

	//Will refresh the canvas after new colors are set for the cube
	public void refresh() {
		cubePen.setFill(Color.WHITE);
		cubePen.fillRect(0,0,cubeCanvas.getHeight(),cubeCanvas.getWidth()); //Clears the Canvas
		rubiksCube.forEach(face -> face.draw(cubePen)); //Redraws the Cube
		drawTimer();
		drawGuessNumber();
	}

	//Will determine the third color of the cube from 2 colors
	public void thirdColor(Color c1, Color c2) {
		//c1 is the left side, c2 is the right side

		if (c1 == white) {
			if (c2 == red) {faceColors = new Color[] {white,red,yellow,orange,green,blue};}
			if (c2 == blue) {faceColors = new Color[] {white,blue,yellow,green,red,orange};}
			if (c2 == orange) {faceColors = new Color[] {white,orange,yellow,red,blue,green};}
			if (c2 == green) {faceColors = new Color[] {white,green,yellow,blue,orange,red};}
		} //End of WHITE
		if (c1 == red) {
			if (c2 == white) {faceColors = new Color[] {red,white,orange,yellow,blue,green};}
			if (c2 == green) {faceColors = new Color[] {red,green,orange,blue,white,yellow};}
			if (c2 == yellow) {faceColors = new Color[] {red,yellow,orange,white,green,blue};}
			if (c2 == blue) {faceColors = new Color[] {red,blue,orange,green,yellow,white};}
		} //End of RED
		if (c1 == blue) {
			if (c2 == white) {faceColors = new Color[] {blue,white,green,yellow,orange,red};}
			if (c2 == red) {faceColors = new Color[] {blue,red,green,orange,white,yellow};}
			if (c2 == yellow) {faceColors = new Color[] {blue,yellow,green,white,red,orange};}
			if (c2 == orange) {faceColors = new Color[] {blue,orange,green,red,yellow,white};}
		} //End of BLUE
		if (c1 == green) {
			if (c2 == orange) {faceColors = new Color[] {green,orange,blue,red,white,yellow};}
			if (c2 == yellow) {faceColors = new Color[] {green,yellow,blue,white,orange,red};}
			if (c2 == red) {faceColors = new Color[] {green,red,blue,orange,yellow,white};}
			if (c2 == white) {faceColors = new Color[] {green,white,blue,yellow,red,orange};}
		} //End of GREEN
		if (c1 == orange) {
			if (c2 == yellow) {faceColors = new Color[] {orange,yellow,red,white,blue,green};}
			if (c2 == green) {faceColors = new Color[] {orange,green,red,blue,yellow,white};}
			if (c2 == white) {faceColors = new Color[] {orange,white,red,yellow,green,blue};}
			if (c2 == blue) {faceColors = new Color[] {orange,blue,red,green,white,yellow};}
		} //End of ORANGE
		if (c1 == yellow) {
			if (c2 == green) {faceColors = new Color[] {yellow,green,white,blue,red,orange};}
			if (c2 == orange) {faceColors = new Color[] {yellow,orange,white,red,green,blue};}
			if (c2 == blue) {faceColors = new Color[] {yellow,blue,white,green,orange,red};}
			if (c2 == red) {faceColors = new Color[] {yellow,red,white,orange,blue,green};}
		} //End of YELLOW
	}//end of program

	//Will randomly assign colors to faceColors arraylist - if Color Neutral
	public void newColors() {
		ArrayList<Color> tempColors = new ArrayList<Color>(Arrays.asList(colors));
		int c1N = rand.nextInt(6); //Gets the index for the first color
		Color c1 = tempColors.get(c1N); //Will get the first color
		Color rColor2 = tempColors.get(5-c1N); //Stores the second color to be removed - opposite of the first one
		tempColors.remove(c1N); //Removes the color
		tempColors.remove(rColor2); //Removes the color opposite of the first color
		int c2N = rand.nextInt(4); //Gets the index of the second side of the face
		Color c2 = tempColors.get(c2N); //Will get the 2nd Number
		thirdColor(c1,c2); //Defines the colors for the rubiks cube using the selected colors
		//Now set the colors of the different parts of the cube using the new colors array
		rubiksCube.get(6).setColor(faceColors[0]); //Set Right side color
		rubiksCube.get(7).setColor(faceColors[1]); //Set left side color
		rubiksCube.get(8).setColor(faceColors[5]); //Set top color
		refreshPLL(); //Refreshes the PLL to match the new layout colors
	}

	//Cross on a certain color
	public void newColors(Color crossColor) {
		ArrayList<Color> tempColors = new ArrayList<Color>(Arrays.asList(colors));
		int c1N = tempColors.indexOf(crossColor); //Gets the index for the first color
		Color c1 = crossColor; //Will get the first color
		Color rColor2 = tempColors.get(5-c1N); //Stores the second color to be removed - opposite of the first one
		tempColors.remove(c1N); //Removes the color
		tempColors.remove(rColor2); //Removes the color opposite of the first color
		int c2N = rand.nextInt(4); //Gets the index of the second side of the face
		Color c2 = tempColors.get(c2N); //Will get the 2nd Number
		thirdColor(c1,c2); //Defines the colors for the rubiks cube using the selected colors
		//Now set the colors of the different parts of the cube using the new colors array
		//Switch the faces of the colors so that the cross is now on the bottom
		faceColors = new Color[] {faceColors[5],faceColors[1],faceColors[4],faceColors[3],faceColors[0],faceColors[2]};
		rubiksCube.get(6).setColor(faceColors[0]); //Set Right side color
		rubiksCube.get(7).setColor(faceColors[1]); //Set left side color
		rubiksCube.get(8).setColor(faceColors[5]); //Set top color
		refreshPLL(); //Refreshes the PLL to match the new layout colors
	}

	public void newPLL() {
	//	PLLnum = rand.nextInt(PLL.length); //Gets random PLL case integer - OLD: uses full list of PLL instead of active ones
		PLLnum = rand.nextInt(enabledPLLs.size()); //Gets random PLL case integer
	//	activePLL = PLL[PLLnum]; //Sets the active PLL - OLD: uses full list of PLL instead of active ones
		activePLL = enabledPLLs.get(PLLnum); //Sets the active PLL
	//	activePLLname = PLLname[PLLnum/4]; //Sets the name of the PLL case - OLD: uses full list of PLL instead of active ones
		activePLLFullName = enabledPLLNames.get(PLLnum); //Gets the full name of the PLL - removes the last 2 letter to get the actual case
		activePLLname = activePLLFullName.substring(0,activePLLFullName.length()-2); //Sets the name of the PLL case
		if(difficultyValue == 0 && !activePLLname.equals("SKIP")) {activePLLname = activePLLname.substring(0,1);} //IF SIMPLE DIFFICULTY, WILL REMOVE THE PERM NUMBER
		//Randomize the PLL case Colors
		int startColor = rand.nextInt(4); //Picks starting number between 0 and 3
		//Add 1 to increment each index and mods by 4 to keep it in range
		PLLCaseColors = new int[] {startColor, (startColor+1)%4,(startColor+2)%4,(startColor+3)%4};

		//Set the colors of the PLL cases
		rubiksCube.get(0).setColor(faceColors[PLLCaseColors[activePLL[0]]]);
		rubiksCube.get(1).setColor(faceColors[PLLCaseColors[activePLL[1]]]);
		rubiksCube.get(2).setColor(faceColors[PLLCaseColors[activePLL[2]]]);
		rubiksCube.get(3).setColor(faceColors[PLLCaseColors[activePLL[3]]]);
		rubiksCube.get(4).setColor(faceColors[PLLCaseColors[activePLL[4]]]);
		rubiksCube.get(5).setColor(faceColors[PLLCaseColors[activePLL[5]]]);
	}

	public void refreshPLL() {
		//Resets the colors of the PLL cases
		rubiksCube.get(0).setColor(faceColors[PLLCaseColors[activePLL[0]]]);
		rubiksCube.get(1).setColor(faceColors[PLLCaseColors[activePLL[1]]]);
		rubiksCube.get(2).setColor(faceColors[PLLCaseColors[activePLL[2]]]);
		rubiksCube.get(3).setColor(faceColors[PLLCaseColors[activePLL[3]]]);
		rubiksCube.get(4).setColor(faceColors[PLLCaseColors[activePLL[4]]]);
		rubiksCube.get(5).setColor(faceColors[PLLCaseColors[activePLL[5]]]);
	}

	/**
	 * Updates colors for the entire cube, based on the colors given to it from the initCOLORs.
	 */
	public void updateColors() {
		if(modeValue == 0) {colors = new Color[] {initWhite.getColor(),initRed.getColor(),initBlue.getColor(),initGreen.getColor(),initOrange.getColor(),initYellow.getColor()};} //Normal Mode
		else if(modeValue == 1) {colors = new Color[] {whiteMenu.getColor(),redMenu.getColor(),blueMenu.getColor(),greenMenu.getColor(),orangeMenu.getColor(),yellowMenu.getColor()};} //IF IN DEBUG MODE

		//Updating colors array updates changing cube colors

		//Update the face colors for the cube
		for(int i = 0;i<faceColors.length;i++) {
			if(faceColors[i] == white) {faceColors[i] = colors[0];}
			if(faceColors[i] == red) {faceColors[i] = colors[1];}
			if(faceColors[i] == blue) {faceColors[i] = colors[2];}
			if(faceColors[i] == green) {faceColors[i] = colors[3];}
			if(faceColors[i] == orange) {faceColors[i] = colors[4];}
			if(faceColors[i] == yellow) {faceColors[i] = colors[5];}
		} //END OF FOR LOOP

		//Reset the Global Colors
		white = colors[0];
		red = colors[1];
		blue = colors[2];
		green = colors[3];
		orange = colors[4];
		yellow = colors[5];

		rubiksCube.get(6).setColor(faceColors[0]); //Set Right side color
		rubiksCube.get(7).setColor(faceColors[1]); //Set left side color
		rubiksCube.get(8).setColor(faceColors[5]); //Set top color
		refreshPLL(); //Refreshes the PLL to match the new layout colors
	}

	public void drawTimer() {
//		double timerSize = 75*scale; //MODIFY THIS TO CHANGE THE SIZE OF THE TIMER //DEPRECATED
		cubePen.setFill(Color.WHITE);
//        cubePen.fillRect(40.0*cubeCanvas.getWidth()/100.0, 18.0*cubeCanvas.getHeight()/20.0, timerSize, 2.0*cubeCanvas.getHeight()/20.0); //DEPRECATED
        cubePen.fillRect(0.0, 18.0*cubeCanvas.getHeight()/20.0, cubeCanvas.getWidth(), 2.0*cubeCanvas.getHeight()/20.0); //Fixed. Now should cover all values
        cubePen.setFill(Color.BLACK);
        cubePen.setFont(new Font(18.0*scale)); //test font size at 18
        cubePen.fillText(timeLabel.getText(), 45.0*cubeCanvas.getWidth()/100.0, 19.25*cubeCanvas.getHeight()/20.0); //MIGHT NEED TO DO SOMETHING TO MAKE DISPLAY 3 DECIMAL POINTS
	}

	public void drawGuessNumber() {
		double numberSize = 50*scale; //MODIFY THIS TO CHANGE THE SIZE OF THE TIMER
		cubePen.setFill(Color.WHITE);
        cubePen.fillRect((0.5/16.0)*cubeCanvas.getWidth(), (16.0/20.0)*cubeCanvas.getHeight(), numberSize, (4.0/20.0)*cubeCanvas.getHeight());
        cubePen.setFill(Color.BLACK);
        cubePen.setFont(new Font(36.0*scale)); //test font size at 18
        cubePen.fillText("" + guessNum, (1.0/16.0)*cubeCanvas.getWidth(), (18.0/20.0)*cubeCanvas.getHeight()); //MIGHT NEED TO DO SOMETHING TO MAKE DISPLAY 3 DECIMAL POINTS

	}

	//Checks and displays if the guess is correct - ONLY USED IN DEBUG MODE
	public void checkGuess() {
		double squareSizeRatio = 1.0/8.0;
		if(PLLGuess.equals(activePLLname)) {
			//Correct PLL Guessed
			cubePen.setFill(Color.GREEN);
			cubePen.fillRect(cubeCanvasLength-(squareSizeRatio*cubeCanvasLength), cubeCanvasWidth-(squareSizeRatio*cubeCanvasWidth), squareSizeRatio*cubeCanvasLength, squareSizeRatio*cubeCanvasWidth);
		}
		else {
			//Wrong PLL Guessed
			cubePen.setFill(Color.RED);
			cubePen.fillRect(cubeCanvasLength-(squareSizeRatio*cubeCanvasLength), cubeCanvasWidth-(squareSizeRatio*cubeCanvasWidth), squareSizeRatio*cubeCanvasLength, squareSizeRatio*cubeCanvasWidth);
			System.out.printf("You guessed: %s  |  The correct PLL is: %s\n", PLLGuess,activePLLname);
		}
	}

	//Used to get the next PLL case
	public void newCase() {
		//New case
		if(crossColor.equals(Color.rgb(0, 0, 0, 0.0))) {newColors();} //If color neutral
		else {newColors(crossColor);} //If certain cross
		newPLL();
		//Increment guess num
		guessNum += 1;
		refresh();
	}

	public void drawCubeCase(GraphicsContext casePen,int[] pLLCaseArray,int[] uRotArray,Color[] faceColorsArray) {

		Drawable[] cubeParts;
		double canvasSize = casePen.getCanvas().getWidth(); //Assumes the canvas is a square

		//Initial Location
		double XPos = canvasSize/2.0;
		double YPos = 3*canvasSize/4.0;
		double sideLength = canvasSize/7.0;

		//Create Sides of RubiksCube
		Side rightSide = new Side(XPos,YPos,sideLength);
		Side leftSide = new Side(XPos,YPos,-sideLength);
		Top topSide = new Top(XPos,rightSide.topY1,sideLength);

		SideSquare face0 = new SideSquare(leftSide.topX3,leftSide.topY3,-sideLength);
		SideSquare face1 = new SideSquare(leftSide.topX2,leftSide.topY2,-sideLength);
		SideSquare face2 = new SideSquare(leftSide.topX1,leftSide.topY1,-sideLength);
		SideSquare face3 = new SideSquare(rightSide.topX1,rightSide.topY1,sideLength);
		SideSquare face4 = new SideSquare(rightSide.topX2,rightSide.topY2,sideLength);
		SideSquare face5 = new SideSquare(rightSide.topX3,rightSide.topY3,sideLength);

		//Add different faces to rubiksCube
		cubeParts = new Drawable[] {face0,face1,face2,face3,face4,face5,leftSide,rightSide,topSide};

		//Set colors of the sides
		cubeParts[6].setColor(faceColorsArray[0]); //Set Right side color
		cubeParts[7].setColor(faceColorsArray[1]); //Set left side color
		cubeParts[8].setColor(faceColorsArray[5]); //Set top color

		//set colors of the PLL squares
		cubeParts[0].setColor(faceColorsArray[uRotArray[pLLCaseArray[0]]]);
		cubeParts[1].setColor(faceColorsArray[uRotArray[pLLCaseArray[1]]]);
		cubeParts[2].setColor(faceColorsArray[uRotArray[pLLCaseArray[2]]]);
		cubeParts[3].setColor(faceColorsArray[uRotArray[pLLCaseArray[3]]]);
		cubeParts[4].setColor(faceColorsArray[uRotArray[pLLCaseArray[4]]]);
		cubeParts[5].setColor(faceColorsArray[uRotArray[pLLCaseArray[5]]]);

		//Draw the cube
		for(Drawable part: cubeParts) {part.draw(casePen);}
	}

	//Will be used to save the values of other thing
	public void saveValues() {
		//RESET THE HISTORIES
		double newTime = time.get(); //Gets the current time of the
		if(PLLGuess.equals(activePLLname)) {totalCorrect += 1;} //Incremented if guessed correctly //The number of PLLs guessed correctly - Used to calculate accuracy
		GuessNumHist.add(guessNum);//Record the guess number
    	PLLHist.add(activePLL);//Record the PLL setup
    	PLLFullNameHist.add(activePLLFullName);//Record the full PLL name
		PLLNameHist.add(activePLLname);//Record the PLL name
		FaceColorHist.add(faceColors);//Record the PLLFaceColors - (rotation of the top)
		URotHist.add(PLLCaseColors); //Record the Rotation of U (PLLCaseColors)
		GuessHist.add(PLLGuess);//Record the Guess Name
		TimestampHist.add(newTime);//Record the TIMESTAMP - Time at which the PLL was identified
		TimeHist.add((double) (Math.round((float) 10000.0*(newTime-prevTime)))/10000.0);//Record the TIME - time it took to analyze the PLL
		prevTime = newTime; //The previous time - used to calculate the time it took to do each PLL - MAKE SURE TO SET PREVTIME TO 0 INITIALLY
	}

	//Creates the results table
	public void setupTable() {
        //Create the Columns for the Table
        //Sets the value property 'guessNumber' from 'StatEntry' as the data to go into the numCol
        numCol.setCellValueFactory(new PropertyValueFactory<StatEntry, Integer>("guessNumber"));
        timestampCol.setCellValueFactory(new PropertyValueFactory<StatEntry, Double>("guessTimestamp"));
        drawCol.setCellValueFactory(new PropertyValueFactory<StatEntry, Canvas>("caseDisp"));
        nameCol.setCellValueFactory(new PropertyValueFactory<StatEntry, String>("pLLActiveName"));
        guessCol.setCellValueFactory(new PropertyValueFactory<StatEntry, String>("guessName"));
        timeCol.setCellValueFactory(new PropertyValueFactory<StatEntry, Double>("guessTime"));
        checkCol.setCellValueFactory(new PropertyValueFactory<StatEntry, String>("checkString"));

        //Make Table
        timestampCol.setMinWidth(20);
        drawCol.setMinWidth(75);
        statTable.getColumns().addAll(numCol,timestampCol,drawCol,nameCol,guessCol,timeCol,checkCol); //Add columns to table
    }

	//Updates the result table data
	public void updateTableData() {
    	ObservableList<StatEntry> data = FXCollections.observableArrayList(); //Makes the observable list to put into the table
    	for(int i = 0;i<GuessNumHist.size();i++) {data.add(new StatEntry(GuessNumHist.get(i),TimestampHist.get(i),PLLHist.get(i),PLLNameHist.get(i),FaceColorHist.get(i),URotHist.get(i),GuessHist.get(i),TimeHist.get(i)));} //adds data into the observable list
        statTable.setItems(data); //Add data to table
        updateSessionTables(); //Updates the session tables
    }

	//Creates buttons inside the Menu
	public void createGuessButtons() {
		//Redefine these as new so all the steps can be done again, as to prevent conflict for running the program multiple times
		simpleGP = new GridPane();
		fullGP = new GridPane();

		int menubarwidth; //temp var for size of
		if(modeValue == 1) {menubarwidth  = 25;} //if in debug mode
		else {menubarwidth = 0;}

		simpleGP.setPrefSize(screenWidth, screenLength-cubeCanvasLength-menubarwidth);//-25);//screenLength-cubeCanvasLength-25,screenWidth);
		simpleGP.setHgap(0.05*screenWidth);

		fullGP.setPrefSize(screenWidth, screenLength-cubeCanvasLength-menubarwidth);//-25);//screenLength-cubeCanvasLength-25,screenWidth);
		fullGP.setHgap(0.05*screenWidth);

	//	System.out.printf("%sx%s ;; %sx%s", simpleGP.getPrefWidth(),simpleGP.getPrefHeight(),fullGP.getPrefWidth(),fullGP.getPrefHeight());

		ColumnConstraints[] SallCols = new ColumnConstraints[] {c00,c01,c02,c03,c04};
		RowConstraints[] SallRows = new RowConstraints[] {r00,r01,r02,r03};
		for(int i = 0;i<SallCols.length;i++) {
		//	SallCols[i].setPrefWidth((simpleGP.getPrefWidth())/5.0);
			SallCols[i].setPrefWidth(simpleGP.getPrefWidth()/((double) SallCols.length));
			//System.out.printf("%s ;; ",simpleGP.getPrefWidth());
			//System.out.printf("%s ;; ", allCols[i].getPrefWidth());
		//	SallCols[i].setPercentWidth(20); //Sets all the columns to be about (1/5) of the screen, accounting for spacers
			SallCols[i].setPercentWidth(100.0/((double) SallCols.length));
			//System.out.printf("-- %s ;;", allCols[i].getPrefWidth());
		}
		for(int i = 0;i<SallRows.length;i++) {
		//	SallRows[i].setPrefHeight((screenLength-cubeCanvasLength-25)/4);
		//	SallRows[i].setPercentHeight(25); //Takes up 1/3 of the screen, each row is 1/4 of the area
			SallRows[i].setPrefHeight(simpleGP.getPrefHeight()/((double) SallRows.length));
			SallRows[i].setPercentHeight(100.0/((double) SallRows.length));
			//allCols[i].setPercentWidth((screenLength/3.0)/4); //Takes up 1/3 of the screen, each row is 1/4 of the area
		}

		ColumnConstraints[] FallCols = new ColumnConstraints[] {c10,c11,c12,c13};//,c14,c15,c16};
		RowConstraints[] FallRows = new RowConstraints[] {r10,r11,r12,r13,r14,r15,r16,r17};
		for(int i = 0;i<FallCols.length;i++) {
			FallCols[i].setPrefWidth(fullGP.getPrefWidth()/((double) FallCols.length));
			//System.out.printf("%s ;;  %s \n",fullGP.getPrefWidth()/((double) FallCols.length),100.0/((double) FallCols.length));
			FallCols[i].setPercentWidth(100.0/((double) FallCols.length)); //Sets all the columns to be about (1/7) of the screen, accounting for spacers
			//System.out.printf("-- %s ;;", allCols[i].getPrefWidth());
		}
		for(int i = 0;i<FallRows.length;i++) {
			FallRows[i].setPrefHeight(fullGP.getPrefHeight()/((double) FallRows.length));
			FallRows[i].setPercentHeight(100.0/((double) FallRows.length)); //Takes up 1/ of the screen, each row is 1/4 of the area
	//		System.out.printf("%s ;; %s ;;  %s \n",fullGP.getPrefHeight(),fullGP.getPrefHeight()/((double) FallRows.length),100.0/((double) FallRows.length));
		}

		simpleGP.getColumnConstraints().addAll(c00,c01,c02,c03,c04);
		simpleGP.getRowConstraints().addAll(r00,r01,r02,r03);
		fullGP.getColumnConstraints().addAll(c10,c11,c12,c13);//,c14,c15,c16);
		fullGP.getRowConstraints().addAll(r10,r11,r12,r13,r14,r15,r16,r17);

//		simpleGP.getColumnConstraints().setAll(c00,c01,c02,c03,c04);
//		simpleGP.getRowConstraints().setAll(r00,r01,r02,r03);
//		fullGP.getColumnConstraints().setAll(c10,c11,c12,c13);//,c14,c15,c16);
//		fullGP.getRowConstraints().setAll(r10,r11,r12,r13,r14,r15,r16,r17);

		// SIMPLE GP //

		SKIPs = new GuessButton("SKIP",c00,r01);
		Es = new GuessButton("E",c00,r02);
		Us = new GuessButton("U",c01,r00);
		Hs = new GuessButton("H",c01,r01);
		Zs = new GuessButton("Z",c01,r02);
		As = new GuessButton("A",c02,r00);
		Ts = new GuessButton("T",c02,r01);
		Rs = new GuessButton("R",c02,r02);
		Gs = new GuessButton("G",c02,r03);
		Ys = new GuessButton("Y",c03,r00);
		Fs = new GuessButton("F",c03,r01);
		Vs = new GuessButton("V",c03,r02);
		Js = new GuessButton("J",c04,r01);
		Ns = new GuessButton("N",c04,r02);

		SKIPf = new GuessButton("SKIP",c10,r10);
		Ef = new GuessButton("E",c10,r11);
		J1f = new GuessButton("J1",c10,r13);
		J2f = new GuessButton("J2",c10,r14);
		N1f = new GuessButton("N1",c10,r16);
		N2f = new GuessButton("N2",c10,r17);
		U1f = new GuessButton("U1",c11,r10);
		U2f = new GuessButton("U2",c11,r11);
		Hf = new GuessButton("H",c11,r13);
		Zf = new GuessButton("Z",c11,r14);
		Ff = new GuessButton("F",c11,r16);
		Vf = new GuessButton("V",c11,r17);
		A1f = new GuessButton("A1",c12,r10);
		A2f = new GuessButton("A2",c12,r11);
		R1f = new GuessButton("R1",c12,r13);
		R2f = new GuessButton("R2",c12,r14);
		Tf = new GuessButton("T",c12,r16);
		Yf = new GuessButton("Y",c12,r17);
		G1f = new GuessButton("G1",c13,r12);
		G2f = new GuessButton("G2",c13,r13);
		G3f = new GuessButton("G3",c13,r14);
		G4f = new GuessButton("G4",c13,r15);

		//First 2
		if((SKIP1.get()) || (SKIP2.get()) || (SKIP3.get()) || (SKIP4.get())) {simpleGP.add(SKIPs, 0, 1);}
		if((E1.get()) || (E2.get()) || (E3.get()) || (E4.get())) {simpleGP.add(Es, 0, 2);}

		//Next 3
		if((U11.get()) || (U12.get()) || (U13.get()) || (U14.get()) ||
			(U21.get()) || (U22.get()) || (U23.get()) || (U24.get())) {simpleGP.add(Us, 1, 0);}
		if((H1.get()) || (H2.get()) || (H3.get()) || (H4.get())) {simpleGP.add(Hs, 1, 1);}
		if((Z1.get()) || (Z2.get()) || (Z3.get()) || (Z4.get())) {simpleGP.add(Zs, 1, 2);}

		//Middle 4
		if((A11.get()) || (A12.get()) || (A13.get()) || (A14.get()) ||
			(A21.get()) || (A22.get()) || (A23.get()) || (A24.get())) {simpleGP.add(As, 2, 0);}
		if((T1.get()) || (T2.get()) || (T3.get()) || (T4.get())) {simpleGP.add(Ts, 2, 1);}
		if((R11.get()) || (R12.get()) || (R13.get()) || (R14.get()) ||
			(R21.get()) || (R22.get()) || (R23.get()) || (R24.get())) {simpleGP.add(Rs, 2, 2);}
		if((G11.get()) || (G12.get()) || (G13.get()) || (G14.get()) ||
			(G21.get()) || (G22.get()) || (G23.get()) || (G24.get()) ||
			(G31.get()) || (G32.get()) || (G33.get()) || (G34.get()) ||
			(G41.get()) || (G42.get()) || (G43.get()) || (G44.get())) {simpleGP.add(Gs, 2, 3);}

		//Following 3
		if((Y1.get()) || (Y2.get()) || (Y3.get()) || (Y4.get())) {simpleGP.add(Ys, 3, 0);}
		if((F1.get()) || (F2.get()) || (F3.get()) || (F4.get())) {simpleGP.add(Fs, 3, 1);}
		if((V1.get()) || (V2.get()) || (V3.get()) || (V4.get())) {simpleGP.add(Vs, 3, 2);}

		//Last 2
		if((J11.get()) || (J12.get()) || (J13.get()) || (J14.get()) ||
			(J21.get()) || (J22.get()) || (J23.get()) || (J24.get())) {simpleGP.add(Js, 4, 1);}
		if((N11.get()) || (N12.get()) || (N13.get()) || (N14.get()) ||
				(N21.get()) || (N22.get()) || (N23.get()) || (N24.get())) {simpleGP.add(Ns, 4, 2);}

	// FULL GP //

		//First Column
		if((SKIP1.get()) || (SKIP2.get()) || (SKIP3.get()) || (SKIP4.get())) {fullGP.add(SKIPf, 0, 0);}
		if((E1.get()) || (E2.get()) || (E3.get()) || (E4.get())) {fullGP.add(Ef, 0, 1);}

		if((J11.get()) || (J12.get()) || (J13.get()) || (J14.get())) {fullGP.add(J1f, 0, 3);}
		if((J21.get()) || (J22.get()) || (J23.get()) || (J24.get())) {fullGP.add(J2f, 0, 4);}

		if((N11.get()) || (N12.get()) || (N13.get()) || (N14.get())) {fullGP.add(N1f, 0, 6);}
		if((N21.get()) || (N22.get()) || (N23.get()) || (N24.get())) {fullGP.add(N2f, 0, 7);}

		//Second Column
		if((U11.get()) || (U12.get()) || (U13.get()) || (U14.get())) {fullGP.add(U1f, 1, 0);}
		if((U21.get()) || (U22.get()) || (U23.get()) || (U24.get())) {fullGP.add(U2f, 1, 1);}

		if((H1.get()) || (H2.get()) || (H3.get()) || (H4.get())) {fullGP.add(Hf, 1, 3);}
		if((Z1.get()) || (Z2.get()) || (Z3.get()) || (Z4.get())) {fullGP.add(Zf, 1, 4);}

		if((F1.get()) || (F2.get()) || (F3.get()) || (F4.get())) {fullGP.add(Ff, 1, 6);}
		if((V1.get()) || (V2.get()) || (V3.get()) || (V4.get())) {fullGP.add(Vf, 1, 7);}

		//Third Column
		if((A11.get()) || (A12.get()) || (A13.get()) || (A14.get())) {fullGP.add(A1f, 2, 0);}
		if((A21.get()) || (A22.get()) || (A23.get()) || (A24.get())) {fullGP.add(A2f, 2, 1);}

		if((R11.get()) || (R12.get()) || (R13.get()) || (R14.get())) {fullGP.add(R1f, 2, 3);}
		if((R21.get()) || (R22.get()) || (R23.get()) || (R24.get())) {fullGP.add(R2f, 2, 4);}

		if((T1.get()) || (T2.get()) || (T3.get()) || (T4.get())) {fullGP.add(Tf, 2, 6);}
		if((Y1.get()) || (Y2.get()) || (Y3.get()) || (Y4.get())) {fullGP.add(Yf, 2, 7);}

		//Fourth Column
		if((G11.get()) || (G12.get()) || (G13.get()) || (G14.get())) {fullGP.add(G1f, 3, 2);}
		if((G21.get()) || (G22.get()) || (G23.get()) || (G24.get())) {fullGP.add(G2f, 3, 3);}

		if((G31.get()) || (G32.get()) || (G33.get()) || (G34.get())) {fullGP.add(G3f, 3, 4);}
		if((G41.get()) || (G42.get()) || (G43.get()) || (G44.get())) {fullGP.add(G4f, 3, 5);}

		createKeybinds(initial.getScene());

	} //END OF CREATE GUESSBUTTONS

	//Creates keybinds
	public void createKeybinds(Scene s) {

		/* Default Keybinds
			1, 2, 3, and 4 are modifier keys for FULL
			If pressed, their modifier will be set to true, which will be necessary for <PERM># to be triggered
			If multiple are pressed, the last one pressed will be the active modifier

			All keys correspond to their letters on the keyboard.
			SKIP corresponds to S
		*/

		//Need to use a object property variable since I'm changing something in an event
		SimpleObjectProperty<KeyCode> storedLetter = new SimpleObjectProperty<>();

		//Key events are events from the scene -> trigger the buttons for each case
		s.setOnKeyPressed( e -> {
			if(canGuess) { //Will only work if you CanGuess
				//e.getCode() is the current key pressed
				//storedLetter.get() is the previous letter pressed
				//Will keep the last letter pressed that's not a modifier stored
				if(!((e.getCode() == KeyCode.DIGIT1) || (e.getCode() == KeyCode.DIGIT2) ||
						(e.getCode() == KeyCode.DIGIT3) || (e.getCode() == KeyCode.DIGIT4))) {storedLetter.set(e.getCode());}

				//Modifier numbers - set if pressed
				//1
				if(e.getCode() == KeyCode.DIGIT1) {
					mod1 = true;
					mod2 = false;
					mod3 = false;
					mod4 = false;
				}
				//2
				if(e.getCode() == KeyCode.DIGIT2) {
					mod1 = false;
					mod2 = true;
					mod3 = false;
					mod4 = false;
				}
				//3
				if(e.getCode() == KeyCode.DIGIT3) {
					mod1 = false;
					mod2 = false;
					mod3 = true;
					mod4 = false;
				}
				//4
				if(e.getCode() == KeyCode.DIGIT4) {
					mod1 = false;
					mod2 = false;
					mod3 = false;
					mod4 = true;
				}

				//Case Keys

				//A
				if(storedLetter.get() == KeyCode.A) {
					if(difficultyValue == 0) {
						if((A11.get()) || (A12.get()) || (A13.get()) || (A14.get()) ||
								(A21.get()) || (A22.get()) || (A23.get()) || (A24.get())) {As.fire();}
						} //Fire if in simple
					else { //Fire if in full
						if(mod1) {if((A11.get()) || (A12.get()) || (A13.get()) || (A14.get())) {A1f.fire();}}
						else if(mod2) {if((A21.get()) || (A22.get()) || (A23.get()) || (A24.get())) {A2f.fire();}}
					}
				} //End of A
				//E
				if(storedLetter.get() == KeyCode.E) {if((E1.get()) || (E2.get()) || (E3.get()) || (E4.get())) {

					if(difficultyValue == 0) {Es.fire();} //Fire if in simple
					else { //Fire if in full
						Ef.fire();
					}
				}} //End of E
				//F
				if(storedLetter.get() == KeyCode.F) {if((F1.get()) || (F2.get()) || (F3.get()) || (F4.get())) {
					if(difficultyValue == 0) {Fs.fire();} //Fire if in simple
					else { //Fire if in full
						Ff.fire();
					}
				}} //End of F
				//G
				if(storedLetter.get() == KeyCode.G) {
					if(difficultyValue == 0) {if((G11.get()) || (G12.get()) || (G13.get()) || (G14.get()) ||
							(G21.get()) || (G22.get()) || (G23.get()) || (G24.get()) ||
							(G31.get()) || (G32.get()) || (G33.get()) || (G34.get()) ||
							(G41.get()) || (G42.get()) || (G43.get()) || (G44.get())) {Gs.fire();}} //Fire if in simple
					else { //Fire if in full
						if(mod1) {if((G11.get()) || (G12.get()) || (G13.get()) || (G14.get())) {G1f.fire();}}
						else if(mod2) {if((G21.get()) || (G22.get()) || (G23.get()) || (G24.get())) {G2f.fire();}}
						else if(mod3) {if((G31.get()) || (G32.get()) || (G33.get()) || (G34.get())) {G3f.fire();}}
						else if(mod4) {if((G41.get()) || (G42.get()) || (G43.get()) || (G44.get())) {G4f.fire();}}
					}
				} //End of G
				//H
				if(storedLetter.get() == KeyCode.H) {if((H1.get()) || (H2.get()) || (H3.get()) || (H4.get())) {
					if(difficultyValue == 0) {Hs.fire();} //Fire if in simple
					else { //Fire if in full
						Hf.fire();
					}
				}} //End of H
				//J
				if(storedLetter.get() == KeyCode.J) {
					if(difficultyValue == 0) {if((J11.get()) || (J12.get()) || (J13.get()) || (J14.get()) ||
							(J21.get()) || (J22.get()) || (J23.get()) || (J24.get())) {Js.fire();}} //Fire if in simple
					else { //Fire if in full
						if(mod1) {if((J11.get()) || (J12.get()) || (J13.get()) || (J14.get())) {J1f.fire();}}
						else if(mod2) {if((J21.get()) || (J22.get()) || (J23.get()) || (J24.get())) {J2f.fire();}}
					}
				} //End of J
				//N
				if(storedLetter.get() == KeyCode.N) {
					if(difficultyValue == 0) {if((N11.get()) || (N12.get()) || (N13.get()) || (N14.get()) ||
							(N21.get()) || (N22.get()) || (N23.get()) || (N24.get())) {Ns.fire();}} //Fire if in simple
					else { //Fire if in full
						if(mod1) {if((N11.get()) || (N12.get()) || (N13.get()) || (N14.get())) {N1f.fire();}}
						else if(mod2) {if((N21.get()) || (N22.get()) || (N23.get()) || (N24.get())) {N2f.fire();}}
					}
				} //End of N
				//R
				if(storedLetter.get() == KeyCode.R) {
					if(difficultyValue == 0) {if((R11.get()) || (R12.get()) || (R13.get()) || (R14.get()) ||
							(R21.get()) || (R22.get()) || (R23.get()) || (R24.get())) {Rs.fire();}} //Fire if in simple
					else { //Fire if in full
						if(mod1) {if((R11.get()) || (R12.get()) || (R13.get()) || (R14.get())) {R1f.fire();}}
						else if(mod2) {if((R21.get()) || (R22.get()) || (R23.get()) || (R24.get())) {R2f.fire();}}
					}
				} //End of R
				//SKIP
				if(storedLetter.get() == KeyCode.S) {if((SKIP1.get()) || (SKIP2.get()) || (SKIP3.get()) || (SKIP4.get())) {
					if(difficultyValue == 0) {SKIPs.fire();} //Fire if in simple
					else { //Fire if in full
						SKIPf.fire();
					}
				}} //End of Y
				//T
				if(storedLetter.get() == KeyCode.T) {if((T1.get()) || (T2.get()) || (T3.get()) || (T4.get())) {
					if(difficultyValue == 0) {Ts.fire();} //Fire if in simple
					else { //Fire if in full
						Tf.fire();
					}
				}} //End of T
				//U
				if(storedLetter.get() == KeyCode.U) {
					if(difficultyValue == 0) {if((U11.get()) || (U12.get()) || (U13.get()) || (U14.get()) ||
							(U21.get()) || (U22.get()) || (U23.get()) || (U24.get())) {Us.fire();}} //Fire if in simple
					else { //Fire if in full
						if(mod1) {if((U11.get()) || (U12.get()) || (U13.get()) || (U14.get())) {U1f.fire();}}
						else if(mod2) {if((U21.get()) || (U22.get()) || (U23.get()) || (U24.get())) {U2f.fire();}}
					}
				} //End of U
				//V
				if(storedLetter.get() == KeyCode.V) {if((V1.get()) || (V2.get()) || (V3.get()) || (V4.get())) {
					if(difficultyValue == 0) {Vs.fire();} //Fire if in simple
					else { //Fire if in full
						Vf.fire();
					}
				}} //End of V
				//Y
				if(storedLetter.get() == KeyCode.Y) {if((Y1.get()) || (Y2.get()) || (Y3.get()) || (Y4.get())) {
					if(difficultyValue == 0) {Ys.fire();} //Fire if in simple
					else { //Fire if in full
						Yf.fire();
					}
				}} //End of Y
				//Z
				if(storedLetter.get() == KeyCode.Z) {if((Z1.get()) || (Z2.get()) || (Z3.get()) || (Z4.get())) {
					if(difficultyValue == 0) {Zs.fire();} //Fire if in simple
					else { //Fire if in full
						Zf.fire();
					}
				}} //End of Z
//				System.out.printf("%s | %s -- %s | %s | %s | %s\n",e.getCode(),storedLetter.get(),mod1,mod2,mod3,mod4);
			}
		}); //END OF setOnKeyPressed

		//Unsets the modifer keys if they are released
		s.setOnKeyReleased( e -> {
			//Modifier numbers
			//1
			if(e.getCode() == KeyCode.DIGIT1) {
				mod1 = false;
			}
			//2
			if(e.getCode() == KeyCode.DIGIT2) {
				mod2 = false;
			}
			//3
			if(e.getCode() == KeyCode.DIGIT3) {
				mod3 = false;
			}
			//4
			if(e.getCode() == KeyCode.DIGIT4) {
				mod4 = false;
			}
			//Case keys
			if(e.getCode() == storedLetter.get()) {storedLetter.set(null);} //Remove the letter from stored letter if released
			// ^ was done so that the keys wouldn't get "stuck" down. Works for every key

		}); //End of setOnKeyReleased

	}

	//Starts the program after the initial menu
	public void startTesting() {
		//Set the mode
		initCases(); //Set the PLL cases

		if(modeBox.getValue() == "Debug") {
			modeValue = 1; //Set to debug mode
			createGuessButtons(); //Needs to be run AFTER modeValue is set
			whiteMenu.setColor(initWhite.getRedInt(), initWhite.getGreenInt(), initWhite.getBlueInt());
			redMenu.setColor(initRed.getRedInt(), initRed.getGreenInt(), initRed.getBlueInt());
			blueMenu.setColor(initBlue.getRedInt(), initBlue.getGreenInt(), initBlue.getBlueInt());
			greenMenu.setColor(initGreen.getRedInt(), initGreen.getGreenInt(), initGreen.getBlueInt());
			orangeMenu.setColor(initOrange.getRedInt(), initOrange.getGreenInt(), initOrange.getBlueInt());
			yellowMenu.setColor(initYellow.getRedInt(), initYellow.getGreenInt(), initYellow.getBlueInt());
			updateColors(); //Sets the face colors
			canGuess = true; //Makes the buttons work
//			master.getChildren().setAll(mb,cubeCanvas,simpleGP); //sets the master vbox
//			bp.setTop(master); //Sets the master menu to the BP
			if(difficultyBox.getValue().equals("Simple")) {
				difficultyValue = 0;
				//SHOULD ALREADY BE SETUP IN SIMPLE
				master.getChildren().setAll(mb,cubeCanvas,simpleGP);
				simple.setSelected(true);
				full.setSelected(false);
				}
			else if(difficultyBox.getValue().equals("Full")) {
				difficultyValue = 1;
				master.getChildren().setAll(mb,cubeCanvas,fullGP);
				full.setSelected(true);
				simple.setSelected(false);
				}
			bp.getChildren().remove(0); //Removes Initial menu
			bp.setTop(master); //Sets the master menu to the BP
			drawCubeStart();
		} //Runs the program like it has been, with menu on the top.
		else { //WILL RUN IF NOT IN DEBUG
			modeValue = 0; //Set to normal mode
			createGuessButtons();//Needs to be run AFTER modeValue is set
			//Set Face Colors
			updateColors(); //Accounts for update colors ONLY in normal mode
			//Set Cross Color
			if(crossBox.getValue().equals("White")) {crossColor = white;}
			else if(crossBox.getValue().equals("Red")) {crossColor = red;}
			else if(crossBox.getValue().equals("Blue")) {crossColor = blue;}
			else if(crossBox.getValue().equals("Green")) {crossColor = green;}
			else if(crossBox.getValue().equals("Orange")) {crossColor = orange;}
			else if(crossBox.getValue().equals("Yellow")) {crossColor = yellow;}
			else if(crossBox.getValue().equals("Color Neutral")) {crossColor = Color.rgb(0, 0, 0, 0.0);} //INVISIBLE COLOR IF COLOR NEUTAL
			//Set Difficulty
			if(difficultyBox.getValue().equals("Simple")) {
				difficultyValue = 0;
				master.getChildren().setAll(cubeCanvas,simpleGP); //Resets the master layout for the simple setup
				}
			else if(difficultyBox.getValue().equals("Full")) {
				difficultyValue = 1;
				master.getChildren().setAll(cubeCanvas,fullGP); //Resets the master layout for the full setup
				}
			//Set Total Number of Cases
			if(Integer.parseInt(countBox.getText()) == 0) {caseCount = 1;} //sets the case count to 1 if left on 0
			else {caseCount = Integer.parseInt(countBox.getText());} //Sets the number of cases to do from within the count box
			//Set Guess Number to 0 for start
			guessNum = 0; //INCREMENTS ON START CASE

			bp.getChildren().remove(0); //Removes Initial menu
			master.getChildren().remove(mb); //Removes the menu bar from the normal version
			bp.setTop(master);

			//Create Countdown timer for 4 seconds until start
			DoubleProperty practiceStartTime = new SimpleDoubleProperty();
			Label startTimeLabel = new Label();
			startTimeLabel.textProperty().bind(practiceStartTime.asString("The Practice Starts in %.3f Seconds")); //Binds the label to the timer
			AnimationTimer startTimer = new AnimationTimer() {

				private long startTime;

				private double initTime = 4.0; //Countdown time length

				@Override
	            public void start() { //What it does when the timer starts
	                startTime = System.currentTimeMillis();
	                super.start();
	            }

	            @Override
	            public void stop() { //What happens when the timer stops
	                super.stop();
	            }
			 	@Override
	            public void handle(long timestamp) { //While the timer is running
	                long now = System.currentTimeMillis();
	                practiceStartTime.set(initTime-((now - startTime) / 1000.0));
	              //MARK ON CANVAS
	                //double timerSize = 250*scale; //MODIFY THIS TO CHANGE THE SIZE OF THE TIMER
	        		cubePen.setFill(Color.WHITE);
	                //cubePen.fillRect(15.0*cubeCanvas.getWidth()/100.0, 8.0*cubeCanvas.getHeight()/20.0, timerSize, 2.0*cubeCanvas.getHeight()/20.0);
	                cubePen.fillRect(0, 0, cubeCanvas.getWidth(), cubeCanvas.getHeight());
	        		cubePen.setFill(Color.BLACK);
	                cubePen.setFont(new Font(14.0*scale)); //test font size at 18
	                cubePen.fillText(startTimeLabel.getText(), 20.0*cubeCanvas.getWidth()/100.0, 9.0*cubeCanvas.getHeight()/20.0); //MIGHT NEED TO DO SOMETHING TO MAKE DISPLAY 3 DECIMAL POINTS
	                if(practiceStartTime.get() <= 0) { //Stop the timer at 0 and generate the cube
	                	super.stop();
	                	//RESET THE HISTORIES
	                	GuessNumHist = new ArrayList<Integer>();//Record the guess number
	                	PLLHist = new ArrayList<int[]>();//Record the PLL setup
	                	PLLFullNameHist = new ArrayList<String>();//Record the Full PLL name
	            		PLLNameHist = new ArrayList<String>();//Record the PLL name
	            		FaceColorHist = new ArrayList<Color[]>();//Record the PLLFaceColors - (rotation of the top)
	            		URotHist = new ArrayList<int[]>(); //Record the Rotation of U (PLLCaseColors)
	            		GuessHist = new ArrayList<String>();//Record the Guess Name
	            		TimestampHist = new ArrayList<Double>();//Record the TIMESTAMP - Time at which the PLL was identified
	            		TimeHist = new ArrayList<Double>();//Record the TIME - time it took to analyze the PLL
	            		prevTime = 0.0; //SET PREVIOUS TIME INITIALLY
	            		totalCorrect = 0; //Reset the total number of correct guesses
	//            		CheckHist = new ArrayList<Boolean>();//Check the guess - RECORD IF RIGHT OR WRONG -DEPRECATED-

	            		//Setup the Initial case
	            		newCase(); //Initial case
	                	canGuess = true; //Make the guess Buttons Work
	                	timer.start(); //START THE PRACTICE TIMER
	                } //END OF STOP TIMER AT 0
	            }
			};

			startTimer.start(); //START THE TIMER

		} //END OF NORMAL MODE
	}

	//This functions is run at the end
	public void showResults() {
		timer.stop(); //stop the timer

		//Generate results in table
		updateTableData();

		//Prepare results screen Stuff
		bp.getChildren().remove(0); //Removes the guessing setup from the border pane
		VBox results = new VBox(); //Creates the final results VBox
		results.setAlignment(Pos.CENTER);

		//Title
		Label ResultsTitle = new Label("Results");
		ResultsTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 18)); //Make the font bigger -- 18 is pretty good

		//Analysis
		Label correct = new Label("Total Correct: " + totalCorrect + "    Total Cases: " + caseCount);
		Label avg = new Label("Average Recognition Time: " + (Math.round(1000.0*(TimestampHist.get(TimestampHist.size()-1) / (double) caseCount))/1000.0));
		//System.out.printf("%s / %s -> %s", totalCorrect, caseCount, Math.round(100.0*(100*((double) totalCorrect / (double) caseCount)))/100.0);
		Label acc = new Label("Accuracy: " + Math.round(100.0*(100*((double) totalCorrect / (double) caseCount)))/100.0 + " %");

			//Get the values/indicies of the most/least values -- NEED TO CHECK IF CORRECT
		int minIndex = -1; //Starts off assuming there is none
		int maxIndex = -1; //Starts off assuming there is none
		for(int i = 0;i<TimeHist.size();i++) {
			if((minIndex == -1) && (GuessHist.get(i).equals(PLLNameHist.get(i)))) {
				//Set the max and min index to the same the new index, since that number exists.
				minIndex = i;
				maxIndex = i;
			} // ^ will not run anymore once the indicies have been set
			if(minIndex != -1) {
				if((TimeHist.get(i) < TimeHist.get(minIndex)) && (GuessHist.get(i).equals(PLLNameHist.get(i)))) {minIndex = i;} //Will reset the minIndex if there is a number smaller that the current one
				if((TimeHist.get(i) > TimeHist.get(maxIndex)) && (GuessHist.get(i).equals(PLLNameHist.get(i)))) {maxIndex = i;} //Will reset the maxIndex if there is a number larger that the current one
			}
		} //END OF FOR TimeHist

		HBox fastest = new HBox();
		fastest.setAlignment(Pos.CENTER); //Centers the text
		HBox slowest = new HBox();
		slowest.setAlignment(Pos.CENTER); //Centers the text

		if(minIndex == -1) {
			//If there is not min or max, meaning that the user got EVERYTHING WRONG
			fastest.getChildren().addAll(new Label("Best Case and Time:  "),new Label("N/A"), new Label(" - "),new Label("N/A"));
			slowest.getChildren().addAll(new Label("Worst Case and Time: "),new Label("N/A"), new Label(" - "),new Label("N/A"));
		}
		else {
			//Best time/case
			Label bc = new Label(PLLNameHist.get(minIndex));
			bc.setTextFill(Color.rgb(50,205,50));//Color.rgb(144, 238, 144)); //sets color to light green
			Label bt = new Label(""+ TimeHist.get(minIndex)); //Gets the shortest time
			bt.setTextFill(Color.rgb(50,205,50));//Color.rgb(144, 238, 144)); //Set the color to light green
			fastest.getChildren().addAll(new Label("Best Case and Time:  "),bc, new Label(" - "),bt); //put bc and bt in HBox together

			//Worst time/case
		Label wc = new Label(PLLNameHist.get(maxIndex));
		wc.setTextFill(Color.rgb(255, 102, 102)); //sets color to light red
		Label wt = new Label(""+ TimeHist.get(maxIndex)); //Gets the shortest time
		wt.setTextFill(Color.rgb(255, 102, 102)); //Set the color to light red
		slowest.getChildren().addAll(new Label("Worst Case and Time: "),wc, new Label(" - "),wt); //put bc and bt in HBox together
		}

//		Label desc = new Label("** Q = Question Number, TS = Timestamp, T = Time, ? = right/wrong **");
//		desc.setFont(Font.font("Verdana", FontPosture.ITALIC, 8));
//		desc.setTextFill(Color.rgb(175, 175, 175)); //sets the text light gray
		
		Label desc = new Label("** Q = Question Number, TS = Timestamp, PLL = Correct Case **");
		desc.setFont(Font.font("Verdana", FontPosture.ITALIC, 8));
		desc.setTextFill(Color.rgb(175, 175, 175)); //sets the text light gray
		
		Label desc2 = new Label("** Ans = Answered Case, T = Time, ? = right/wrong **");
		desc2.setFont(Font.font("Verdana", FontPosture.ITALIC, 8));
		desc2.setTextFill(Color.rgb(175, 175, 175)); //sets the text light gray

		Button retryButton = new Button("Test Again");
		retryButton.setOnAction( e2 -> {
			//DOESNT WORK. NEED SOMETHING TO ACTUALLY RESET THE VARIABLES??
			bp.getChildren().remove(0); //Remove results screen from BP
			bp.setCenter(initial); //Set the initial screen up again
		});

		//Spacer empty Label for simplicity
		results.getChildren().addAll(ResultsTitle,correct,avg,acc,fastest,slowest,sp,desc,desc2,new Label(),retryButton);
		bp.setCenter(results); //Displays the Final Results VBox
		//Print results
		//System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",GuessNumHist,PLLHist,PLLNameHist,FaceColorHist,URotHist,GuessHist,TimeHist,CheckHist); //CheckHist is DEPREACATED
		//System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n",GuessNumHist,PLLHist,PLLNameHist,FaceColorHist,URotHist,GuessHist,TimeHist);
	}

	//method use to create the session tables
	public void createSessionTables() {
		//Create the Columns for the Table
        //Sets the value property 'guessNumber' from 'StatEntry' as the data to go into the numCol

		sPllCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("fullPermName"));
		sQCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("questions"));
		sCCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("correct"));
		sIcCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("incorrect"));
		sAccCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("accuracy"));
		sFCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("fastest"));
		sSCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("slowest"));
		sAvgCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("averageT"));
		sStdCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("stdDevT"));
		sMcgCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("mostWrongGuess"));

		fPllCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("fullPermName"));
		fQCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("questions"));
		fCCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("correct"));
		fIcCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("incorrect"));
		fAccCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("accuracy"));
		fFCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("fastest"));
		fSCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("slowest"));
		fAvgCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("averageT"));
		fStdCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("stdDevT"));
		fMcgCol.setCellValueFactory(new PropertyValueFactory<SessionEntry, String>("mostWrongGuess"));

        //Make Table
//        sMcgCol.setMinWidth(5);
		sMcgCol.setMinWidth(20);
        sMcgCol.setMaxWidth(30);
        fMcgCol.setMinWidth(20);
        fMcgCol.setMaxWidth(30);
		simpleSessionTable.getColumns().setAll(sPllCol,sQCol,sCCol,sAccCol,sFCol,sSCol,sAvgCol,sStdCol,sMcgCol);
        fullSessionTable.getColumns().setAll(fPllCol,fQCol,fCCol,fAccCol,fFCol,fSCol,fAvgCol,fStdCol,fMcgCol);
	}

	//Method used for updating the session tables
	public void updateSessionTables() {
		//Create new observable lists
		ObservableList<SessionEntry> simpleData = FXCollections.observableArrayList(); //Makes the observable list to the table
	    ObservableList<SessionEntry> fullData = FXCollections.observableArrayList(); //Makes the observable list to the table

	    //Updates the stats for each object
	    if(difficultyValue == 0) {
	    	//Update values for Simple table
	    	for(int x = 0;x<GuessNumHist.size();x++) {
	    		//search through all of them until I get to one where the PLLFullNameHist.get(i).equals(fullPermName
	    		for(int i = 0;i<simpCols.length;i++) {
	    			if(PLLFullNameHist.get(x).equals(simpCols[i].getFullPermName())) { //GOES THROUGH AND CHECKS TO FIND WHICH CASE IS BEING CHANGED
	    				simpCols[i].setQuestionsNUM(simpCols[i].getQuestionsNUM()+1); //Add 1 to questionsNUM
	    				if(GuessHist.get(x).equals(PLLNameHist.get(x))) { //Checks for the correct Guess
	    					simpCols[i].setCorrectNUM(simpCols[i].getCorrectNUM()+1); //Adds 1 to the correctNUM
	    					simpCols[i].addTime(TimeHist.get(x)); //Adds the time since it's correct
	    				}//END OF IF CORRECT
	    				else { //IF INCORRECT
	    					simpCols[i].addWrongGuess(GuessHist.get(x)); //Adds the guessed case if incorrect guess
	    				}//END IF INCORRECT
	    			} //END OF ADDING STUFF
	    		}//End of for simpCols
	    	}//END OF FOR
	    }//END OF SIMPLE
	    else if(difficultyValue == 1) {
	    	//Update values for Full table
	    	for(int x = 0;x<GuessNumHist.size();x++) {
	    		//search through all of them until I get to one where the PLLFullNameHist.get(i).equals(fullPermName
	    		for(int i = 0;i<fullCols.length;i++) {
	    			if(PLLFullNameHist.get(x).equals(fullCols[i].getFullPermName())) { //GOES THROUGH AND CHECKS TO FIND WHICH CASE IS BEING CHANGED
	    				fullCols[i].setQuestionsNUM(fullCols[i].getQuestionsNUM()+1); //Add 1 to questionsNUM
	    				if(GuessHist.get(x).equals(PLLNameHist.get(x))) { //Checks for the correct Guess
	    					fullCols[i].setCorrectNUM(fullCols[i].getCorrectNUM()+1); //Adds 1 to the correctNUM
	    					fullCols[i].addTime(TimeHist.get(x)); //Adds the time since it's correct
	    				}//END OF IF CORRECT
	    				else { //IF INCORRECT
	    					fullCols[i].addWrongGuess(GuessHist.get(x)); //Adds the guessed case if incorrect guess
	    				}//END IF INCORRECT
	    			} //END OF ADDING STUFF
	    		}//End of for simpCols
	    	}//END OF FOR
	    }//END OF FULL

	    //Set the data
	    for(SessionEntry s: simpCols) {simpleData.add(s);}
	    for(SessionEntry s: fullCols) {fullData.add(s);}

	    //Add datasets to their respective tables
	    simpleSessionTable.setItems(simpleData);
	    fullSessionTable.setItems(fullData);

	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void start(Stage primary) {
		this.primary = primary;
		newColors();
		createCaseSelect();
		createButtonActions(); //Moved to own method for to simplify start

		updateSessionTables(); //Set initial part for sessions in the tables

		bp.setCenter(initial); //Sets the initial menu

		Scene s = new Scene(bp,screenWidth,screenLength);

		primary.setScene(s);
		primary.setTitle("2-Side PLL Practice");
		primary.getIcons().add(new Image("R2Perm32x32-1.png"));
		primary.setResizable(false); //Make the window non-resizable
		//Maybe add something for the icon
		
		popupMessage = new ErrorMessagePopup(primary);
		
		primary.show();
	} //END OF START

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////

	public class GuessButton extends Button {

		//private GuessButton(String name,KeyCombination key,ColumnConstraints lengthRow,ColumnConstraints widthColumn) {
		private GuessButton(String name,ColumnConstraints widthColumn,RowConstraints lengthRow) {
			super(name);
		//	setMinSize(50,25);
			//setMinSize(widthColumn.getMinWidth(),lengthRow.getMinHeight());
		//	setMinSize(widthColumn.getPrefWidth(),lengthRow.getPrefHeight());
			setPrefSize(widthColumn.getPrefWidth(),lengthRow.getPrefHeight());
		//	setMaxSize(widthColumn.getPrefWidth(),lengthRow.getPrefHeight());
			//setMaxSize(widthColumn.getMaxWidth(),lengthRow.getMaxHeight());
			setOnAction( e -> {
				if(canGuess) {
						PLLGuess = name; //Set the PLL GUESS
						if (modeValue == 1) {checkGuess();} //Check the guess - Shows IF RIGHT OR WRONG ONLY IN DEBUG
						else { //modeValue == 0
						saveValues(); //Save the Values
						//Will generate a new case until the number of cases stated initially has been met
	//					System.out.printf("%s, %s\n", PLLGuess, activePLLname);
						if(guessNum < caseCount) {//if (modeValue == 0) {
							newCase();
							//}
						}//Generate a new case - ONLY IN NORMAL MODE
						else { // THIS WILL BE RUN WHEN FOR THE END
							canGuess = false; //Reset canGuess
							showResults(); //Moved to its own method for simplicity
						}
					}
				}
			});
		}

	} //END OF GUESSBUTTON

////////////////////////////////////////////////////////////////

	public class StatEntry { //this is the data structure of the data put into the final table

			private int guessNumber;
			private double guessTimestamp;

			private int canvasSize = 100; //Size of canvas
			private int[] pLLCaseArray;
			private String pLLActiveName;
			private Color[] faceColorsArray;
			private int[] uRotArray;

			private Canvas caseDisp;
			private GraphicsContext casePen;
			private String guessName;
			private double guessTime;
			private String checkString;

			StatEntry(int GuessNumber,double GuessTimeStamp,int[] PLLCaseArray,String PLLActiveName,Color[] FaceColorsArray,
					int[] URotArray,String GuessName,double GuessTime) { //Removed boolean Check to auto check in here
				//Set Values
				this.guessNumber = GuessNumber;
				this.guessTimestamp = GuessTimeStamp;
				this.pLLCaseArray = PLLCaseArray;
				this.pLLActiveName = PLLActiveName;
				this.faceColorsArray = FaceColorsArray;
				this.uRotArray = URotArray;
				this.guessName = GuessName;
				this.guessTime = GuessTime;

				//Set Check String
				checkString = new String("");
				if(GuessName.equals(PLLActiveName)) {checkString = "\u2713";} //Set to check if true
				else {checkString = "X";} //Set to X if false

				caseDisp = new Canvas(canvasSize,canvasSize); //Create the canvas
				casePen = caseDisp.getGraphicsContext2D(); //Set graphicscontext
				drawCubeCase(casePen,pLLCaseArray,uRotArray,faceColorsArray); //Draws cube on canvas
			}

			//Getters - required for the table
			public int getGuessNumber() {return guessNumber;}
			public void setGuessTimestamp(double newGuessTimestamp) {guessTimestamp = newGuessTimestamp;}
			public double getGuessTimestamp() {return guessTimestamp;}
			public Canvas getCaseDisp() {return caseDisp;}
			public String getPLLActiveName() {return pLLActiveName;}
			public String getGuessName() {return guessName;}
			public double getGuessTime() {return guessTime;}
			public String getCheckString() {return checkString;}


			//Setters - required for the table
			public void setGuessNumber(int newGuessNumber) {guessNumber = newGuessNumber;}
			public void setCaseDisp(Canvas newCaseDisp) {caseDisp = newCaseDisp;}
			public void setPLLActiveName(String newPLLActiveName) {pLLActiveName = newPLLActiveName;}
			public void setGuessName(String newGuessName) {guessName = newGuessName;}
			public void setGuessTime(double newGuessTime) {guessTime = newGuessTime;}
			public void setCheckString(String newCheckString) {checkString = newCheckString;}

		}

///////////////////////////////////////////////////////////////

	//Will be used to make each case inside the title panes for case selection
	public class SetCaseBox extends HBox {

		private double canvasSize = 100; //Size of canvas for displaying case
		private int[] URotArray = new int[] {0,1,2,3}; //Will always be in the same rotation for consistency with case selection
		private Canvas caseDisp; //canvas for the cube
		private CheckBox caseBox; //Checkbox for the case's inclusion
		private int[] PLLCaseArray;
		private SimpleBooleanProperty caseBool; //This is the property that each checkbox will be bound to

		//Constructor
//		SetCaseBox(String caseName,SimpleBooleanProperty caseBool,int[] PLLCaseArray,Color[] FaceColorsArray) {
		public SetCaseBox(String caseName,int[] PLLCaseArray,Color[] FaceColorsArray) {
			//Create the checkbox
			caseBox = new CheckBox(caseName);
			caseBox.setSelected(true); //Set it selected by default
			caseBool = new SimpleBooleanProperty(true); //default argument
			caseBool.bind(caseBox.selectedProperty()); //Bind the caseBool to the case Check
			//Draw the cube case
			this.PLLCaseArray = PLLCaseArray; //Store the PLLCase for each instance
			caseDisp = new Canvas(canvasSize,canvasSize);//drawCubeCase(canvasSize,PLLCaseArray,uRotArray,FaceColorsArray);
			drawCubeCase(caseDisp.getGraphicsContext2D(),PLLCaseArray,URotArray,FaceColorsArray);
			//Put everything together
			setSpacing(50); //Spacing between the checkbox and the canvas
			getChildren().addAll(caseBox,caseDisp);
		} //end of constructor

		//Method for redrawing the canvas
		public void redrawCase(Color[] FaceColorsArray) {
			caseDisp.getGraphicsContext2D().clearRect(0, 0, canvasSize, canvasSize); //Clears the canvas
			drawCubeCase(caseDisp.getGraphicsContext2D(),PLLCaseArray,URotArray,FaceColorsArray); //redraws the cube on the canvas
		}

		//Method to modify the checkbox
		public void setSelected(boolean bool) {
			caseBox.setSelected(bool);
		}

		public boolean getCaseBool() {
			return caseBool.get();
		}

	} //END OF SetCaseBox
	
////////////////////////////////////////////////////////////////
	
	public class CasesPresetCheckBox extends CheckBox{
		//Preset cases will only leave the cases checked for whichever preset checkboxes are active
		//After the preset(s) are chosen, other cases can be set by clicking them. Otherwise, they will be reset upon a click of the preset.
		
		//Must call outer method to set everything
		//Inner list contains cases it has
		
		SetCaseBox[] cases;
		
		/**
		 * Creates a case preset checkbox, which is used to set presets for cases in the Case Select menu.
		 * @param preset
		 * @param cases
		 */
		public CasesPresetCheckBox(String preset, SetCaseBox[] cases) {
			super(preset);
			this.cases = cases;
			this.setSelected(true); //is selected by default
			
			this.setOnAction(e -> {
//				System.out.println(preset + " is checked? " + this.isSelected());
				for(int i = 0; i < this.cases.length; i++) { //sets all of the cases to what it
					this.cases[i].setSelected(this.isSelected());
				}
				
			});
		}
		
//		public void updateCasesFromPresets() {
//			
//		}
		
		
//		//Method to modify the checkbox
//		public void setSelected(boolean bool) {
//			caseBox.setSelected(bool);
//		}
//
//		public boolean getCaseBool() {
//			return caseBool.get();
//		}

		
	}

////////////////////////////////////////////////////////////////

	public class SessionEntry { //this is the data structure of the data put into the Session Stats table - STIRNG VARS ARE USED IN THE TABLE

		private String fullPermName;
		private int questionsNUM;
		private int correctNUM;
		private int incorrectNUM;
		private double accuracyNUM;

		private String questions;
		private String correct;
		private String incorrect;
		private String accuracy;

		private ArrayList<Double> times; //ONLY TIMES FROM CORRECT GUESSES
		private String fastest;
		private String slowest;
		private String averageT;
		private String stdDevT; //Standard deviation

		private ArrayList<String> wrongGuesses;
		private String mostWrongGuess;

		//Used to create this object. Will be modified by adding things to the ArrayLists
		public SessionEntry(String fullPermName) {
			this.fullPermName = fullPermName;
			questionsNUM = 0;
			correctNUM = 0;
			incorrectNUM = 0;
			accuracyNUM = 0;

			questions = "N/A";
			correct = "N/A";
			incorrect = "N/A";
			accuracy = "N/A";

			this.times = new ArrayList<Double>();
			fastest = "N/A";
			slowest = "N/A";
			averageT = "N/A";
			stdDevT = "N/A";
			this.wrongGuesses = new ArrayList<String>();
			mostWrongGuess = "N/A";
		}
		
		/**
		 * Constructor used when creating it with data already in it. Most things are calculated from 
		 * what is given, but if no data is given to it, "N/A" will be left in it's place.
		 * @param fullPermName the full name of the permutation.
		 * @param correctTimes double array of the times of the correct guesses made.
		 * @param wrongGuesses String array of all of the wrong guesses made.
		 */
		public SessionEntry(String fullPermName, Double[] correctTimes, String[] wrongGuesses) {
			this.fullPermName = fullPermName;
			questionsNUM = correctTimes.length + wrongGuesses.length;
			correctNUM = correctTimes.length;
			incorrectNUM = wrongGuesses.length;
			
			if(questionsNUM == 0) {
				accuracyNUM = 0;
			} else {
				accuracyNUM = correctNUM / questionsNUM;
			}

			questions = getQuestions();
			correct = getCorrect();
			incorrect = getIncorrect();
			accuracy = getAccuracy();

			times = new ArrayList<Double>();
			for(int i = 0; i < correctTimes.length; i++) {
				times.add(correctTimes[i]);
			}
			fastest = getFastest();
			slowest = getSlowest();
			averageT = getAverageT();
			stdDevT = getStdDevT();
			this.wrongGuesses = new ArrayList<String>();
			for(int i = 0; i < wrongGuesses.length; i++) {
				this.wrongGuesses.add(wrongGuesses[i]);
			}
			mostWrongGuess = getMostWrongGuess();
		}
		
		/**
		 * Resets all of the data in the column to that of a new column with no data.
		 */
		public void reset() {
			questionsNUM = 0;
			correctNUM = 0;
			incorrectNUM = 0;
			accuracyNUM = 0;

			questions = "N/A";
			correct = "N/A";
			incorrect = "N/A";
			accuracy = "N/A";

			this.times = new ArrayList<Double>();
			fastest = "N/A";
			slowest = "N/A";
			averageT = "N/A";
			stdDevT = "N/A";
			this.wrongGuesses = new ArrayList<String>();
			mostWrongGuess = "N/A";
		}

		//Returns the max time - slowest time
		private String getMaxTime() {
			if(times.size() == 0) {return "N/A";}
			int maxIndex = 0; //At least 1 thing is in the times array
			for(int i = 0;i<times.size();i++) {
				if(times.get(i) > times.get(maxIndex)) {maxIndex = i;}
			}
			return times.get(maxIndex) + "";
		}

		//Returns the Min time - fastest time
		private String getMinTime() {
			if(times.size() == 0) {return "N/A";}
			int minIndex = 0; //At least 1 thing is in the times array
			for(int i = 0;i<times.size();i++) {
				if(times.get(i) < times.get(minIndex)) {minIndex = i;}
			}
			return times.get(minIndex) + "";
		}

		//Returns the average time
		private String getAvgTime() {
			if(times.size() == 0) {return "N/A";}
			double total = 0; //will be the sum of the times
			for(int i = 0;i<times.size();i++) {
				total += times.get(i);
			}
			return total/times.size() + "";
		}

		//Returns the standard deviation of times
		private String getStdTime() {
			if(times.size() == 0) {return "N/A";}
			double total = 0; //will be the sum of the times
			for(int i = 0;i<times.size();i++) {total += times.get(i);}
			double mean = total/times.size();
			total = 0; //resets the total to be used again
			for(int i = 0;i<times.size();i++) {total+=Math.pow((times.get(i)-mean),2);}
			return Math.pow(total/((double)times.size()-1.0), 0.5) + "";
		}

		//Add a time to the times
		public void addTime(double time) {
			times.add(time);
		}

		//Add a wrong guess to wrongGuesses
		public void addWrongGuess(String guess) {
			wrongGuesses.add(guess);
		}

		public String getMostWrong() {
			if(wrongGuesses.size() == 0) {return "N/A";}
			Map<String,Integer> freq = new HashMap<String,Integer>();
			wrongGuesses.forEach(guess -> { //does for each guess in wrongGuesses
				if(freq.containsKey(guess)) { //If the key exists in the hashMap,
					freq.put(guess,freq.get(guess)+1); //Increment it by 1
				}
				else { //If it doesn't exist in the hashmap
					freq.put(guess,1); //add it to the hashmap
				}
			}); //end of forEach loop
			//will return the first max
			int maxCount = 0;
			String wrongGuessLetter = "";
	        for(Entry<String,Integer> val : freq.entrySet()) {
	            if (maxCount < val.getValue()) { //checks to see if the next freq is larger
	            	wrongGuessLetter = val.getKey(); //Resets the max value's letter
	                maxCount = val.getValue(); //resets the max value
	            }
	        }
	        return wrongGuessLetter; //returns the max value's letter
		} //END OF getMostWrong()

		//Getters - required for the table
		public String getFullPermName() {return fullPermName;}
		public int getQuestionsNUM() {return questionsNUM;}
		public int getCorrectNUM() {return correctNUM;}
		public int getIncorrectNUM() {incorrectNUM = questionsNUM-correctNUM; return incorrectNUM;}
		public double getAccuracyNUM() {accuracyNUM = ((double) correctNUM / (double) questionsNUM); return accuracyNUM;}

		public String getQuestions() {if(questionsNUM == 0) {return "N/A";} return questionsNUM + "";}
		public String getCorrect() {if(correctNUM == 0) {return "N/A";} return correctNUM + "";}
		public String getIncorrect() {incorrectNUM = questionsNUM-correctNUM; if(incorrectNUM==0) {return "N/A";} return incorrectNUM + "";}
		public String getAccuracy() {accuracyNUM = ((double) correctNUM / (double) questionsNUM); if(questionsNUM == 0) {return "N/A";} return Math.round(accuracyNUM*100) + "%";}

		public String getFastest() {return getMinTime();}
		public String getSlowest() {return getMaxTime();}
		public String getAverageT() {return getAvgTime();}
		public String getStdDevT() {if(questionsNUM < 2) {return "N/A";} return getStdTime();}
		public String getMostWrongGuess() {return getMostWrong();}

		public ArrayList<Double> getTimes() {return times;}
		public ArrayList<String> getWrongGuesses() {return wrongGuesses;}

		//Setters - required for the table
		public void setFullPermName(String a) {fullPermName = a;}
		public void setQuestionsNUM(int a) {questionsNUM = a;}
		public void setCorrectNUM(int a) {correctNUM = a;}
		public void setIncorrectNUM(int a) {incorrectNUM = a;}
		public void setAccuracyNUM(double a) {accuracyNUM = a;}

		public void setQuestions(String a) {questions = a;}
		public void setCorrect(String a) {correct = a;}
		public void setIncorrect(String a) {incorrect = a;}
		public void setAccuracy(String a) {accuracy = a;}

		public void setFastest(String a) {fastest = a;}
		public void setSlowest(String a) {slowest = a;}
		public void setAverageT(String a) {averageT = a;}
		public void setStdDevT(String a) {stdDevT = a;}
		public void setMostWrongGuess(String a) {mostWrongGuess = a;}

		public void setTimes(ArrayList<Double> a) {times = a;}
		public void setWrongGuesses(ArrayList<String> a) {wrongGuesses = a;}
		
		//removed * -totalGuesses, totalCorrectGuesses due to it being unnecessary
		/**
		 * Returns the cell and data in the format:
		 * *fullPermName
		 * -time1,time2,timeN...
		 * -wrongGuess1,wrongGuess2,wrongGuessN...
		 * 
		 * @return returns the sessionStats in the specified String format.
		 */
		@Override
		public String toString() {
			String outString = "*" + fullPermName + 
					//"\n-" + questionsNUM + "," + correctNUM + REMOVED FOR BEING UNNECESSARY
					"\n-";
			for(int i = 0; i < times.size(); i++) {
				outString += times.get(i);
				if(i < times.size() - 1) { //- 1 to remove the last comma. removed because delimiter needs that last comma to work
					outString += ",";
				}
			}
			outString += "\n-";
			for(int i = 0; i < wrongGuesses.size(); i++) {
				outString += wrongGuesses.get(i);
				if(i < wrongGuesses.size() - 1) { //- 1 to remove the last comma. removed because delimiter needs that last comma to work
					outString += ",";
				}
			}
//			outString +="\n"; //Maybe remove? using this so that next text will be on the next line.
			return outString;
		}
	}

///////////////////////////////////////////////////////////////
	
	public void writeColorsToFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setInitialFileName("PLLSessionSave.txt");
		
//		File saveFile = fileChooser.showSaveDialog(new Stage());
		File saveFile = fileChooser.showSaveDialog(primary);
		
		if(saveFile != null) {
			//temporarily save contents of other file
			String tempString = "";
			try {
				Scanner tempWriter = new Scanner(saveFile);
				
				for(int i = 0; i < 6; i++) { //skips the first 6 lines, which will be overwritten with the new colors.
					if(tempWriter.hasNextLine()) {
						tempWriter.nextLine();
					}
				}
				while(tempWriter.hasNextLine()) { //adds an extra \n at the end of the file
					String temp = tempWriter.nextLine();
					if(!temp.isBlank()) {
						tempString += temp.strip() + "\n";
					}
				}
				tempString = tempString.substring(0, tempString.length() - 1); //Removes the extra \n
				tempWriter.close();
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("Unable to write colors to file.");
			}
			
			try {
				PrintStream fileWriter = new PrintStream(saveFile);
				
				fileWriter.println("+" + initWhite.getRedInt() + "," + initWhite.getGreenInt() + "," + initWhite.getBlueInt() + /*"," + */
						"\n+" + initRed.getRedInt() + "," + initRed.getGreenInt() + "," + initRed.getBlueInt() + /*"," + */
						"\n+" + initBlue.getRedInt() + "," + initBlue.getGreenInt() + "," + initBlue.getBlueInt() + /*"," + */
						"\n+" + initGreen.getRedInt() + "," + initGreen.getGreenInt() + "," + initGreen.getBlueInt() + /*"," + */
						"\n+" + initOrange.getRedInt() + "," + initOrange.getGreenInt() + "," + initOrange.getBlueInt() + /*"," + */
						"\n+" + initYellow.getRedInt() + "," + initYellow.getGreenInt() + "," + initYellow.getBlueInt() /*+ ","*/);
				
				System.out.println("+" + initWhite.getRedInt() + "," + initWhite.getGreenInt() + "," + initWhite.getBlueInt() + /*"," + */
						"\n+" + initRed.getRedInt() + "," + initRed.getGreenInt() + "," + initRed.getBlueInt() + /*"," + */
						"\n+" + initBlue.getRedInt() + "," + initBlue.getGreenInt() + "," + initBlue.getBlueInt() + /*"," + */
						"\n+" + initGreen.getRedInt() + "," + initGreen.getGreenInt() + "," + initGreen.getBlueInt() + /*"," + */
						"\n+" + initOrange.getRedInt() + "," + initOrange.getGreenInt() + "," + initOrange.getBlueInt() + /*"," + */
						"\n+" + initYellow.getRedInt() + "," + initYellow.getGreenInt() + "," + initYellow.getBlueInt() /*+ ","*/);
				
				fileWriter.println(tempString);
				System.out.println(tempString);
				fileWriter.close();
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("Unable to write colors to file.");
			}
		}
	}
	
	public void loadColorsFromFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		
		File openFile = fileChooser.showOpenDialog(primary);
		
		if(openFile != null) {
			try {
				Scanner fileReader = new Scanner(new FileInputStream(openFile));
				
				fileReader.useDelimiter("\\+");
				String[] newColors = new String[6];
				for(int i = 0; i < 6; i++) {
					if(fileReader.hasNext()) {
						newColors[i] = fileReader.nextLine().strip(); //used nextLine instead of next due to problems with last color including extra data
					} else {
						fileReader.close();
						throw new IllegalArgumentException("Missing Cube Colors in file.");
					}
				}
				
				try {
					readColors(newColors);
				} catch (Exception e) {
					fileReader.close();
					throw new IllegalArgumentException(e.getMessage());
				}
				
				fileReader.close();
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("Unable to load colors from file.");
			}
		}
	}
	
	public void writeSessionToFile() throws IOException {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setInitialFileName("PLLSessionSave.txt");
		
//		File saveFile = fileChooser.showSaveDialog(new Stage());
		File saveFile = fileChooser.showSaveDialog(primary);
		
		if(saveFile != null) {
//			PrintStream fileWriter = new PrintStream(new File(fileName));
			PrintStream fileWriter = new PrintStream(saveFile);
			
			//SAVE COLORS OF CUBE FIRST w r b g o y
			fileWriter.println("+" + initWhite.getRedInt() + "," + initWhite.getGreenInt() + "," + initWhite.getBlueInt() + /*"," + */
					"\n+" + initRed.getRedInt() + "," + initRed.getGreenInt() + "," + initRed.getBlueInt() + /*"," + */
					"\n+" + initBlue.getRedInt() + "," + initBlue.getGreenInt() + "," + initBlue.getBlueInt() + /*"," + */
					"\n+" + initGreen.getRedInt() + "," + initGreen.getGreenInt() + "," + initGreen.getBlueInt() + /*"," + */
					"\n+" + initOrange.getRedInt() + "," + initOrange.getGreenInt() + "," + initOrange.getBlueInt() + /*"," + */
					"\n+" + initYellow.getRedInt() + "," + initYellow.getGreenInt() + "," + initYellow.getBlueInt() /*+ ","*/);
			
			System.out.println("+" + initWhite.getRedInt() + "," + initWhite.getGreenInt() + "," + initWhite.getBlueInt() + /*"," + */
					"\n+" + initRed.getRedInt() + "," + initRed.getGreenInt() + "," + initRed.getBlueInt() + /*"," + */
					"\n+" + initBlue.getRedInt() + "," + initBlue.getGreenInt() + "," + initBlue.getBlueInt() + /*"," + */
					"\n+" + initGreen.getRedInt() + "," + initGreen.getGreenInt() + "," + initGreen.getBlueInt() + /*"," + */
					"\n+" + initOrange.getRedInt() + "," + initOrange.getGreenInt() + "," + initOrange.getBlueInt() + /*"," + */
					"\n+" + initYellow.getRedInt() + "," + initYellow.getGreenInt() + "," + initYellow.getBlueInt() /*+ ","*/);
			
			for(int i = 0; i < simpCols.length; i++) {
				System.out.println("*s" + simpCols[i].toString());
				fileWriter.println("*s" + simpCols[i].toString());
			}
			for(int i = 0; i < fullCols.length; i++) {
				System.out.println("*f" + fullCols[i].toString());
				fileWriter.println("*f" + fullCols[i].toString());
			}
			
			fileWriter.close();
		}
	}
	
	/**
	 * Reads both cube colors and session stats for both simple and full from a save file.
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 */
	public void readSessionFromFile() throws FileNotFoundException, IllegalArgumentException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		
		File openFile = fileChooser.showOpenDialog(primary);
		
		if(openFile != null) {
			Scanner fileReader = new Scanner(new FileInputStream(openFile));
			//Below ArrayLists are used to keep track of entries that have been changed from loading from the file
			ArrayList<String> addedSimpEntries = new ArrayList<String>();
			ArrayList<String> addedFullEntries = new ArrayList<String>();
			
			fileReader.useDelimiter("\\+");
			String[] newColors = new String[6];
			for(int i = 0; i < 6; i++) {
				if(fileReader.hasNext()) {
					newColors[i] = fileReader.nextLine().strip(); //used nextLine instead of next due to problems with last color including extra data
				} else {
					fileReader.close();
					throw new IllegalArgumentException("Missing Cube Colors in file.");
				}
			}
			
			try {
				readColors(newColors);
			} catch (Exception e) {
				fileReader.close();
	//			throw new IllegalArgumentException("Invalid Color(s) in file.");
				throw new IllegalArgumentException(e.getMessage());
	//			System.out.println("Failed to make colors");
	//			e.printStackTrace();
			}
			
			fileReader.close();
			fileReader = new Scanner(new FileInputStream(openFile));
			
			fileReader.useDelimiter("\\n[*]"); //("\\\n\\*[sf]\\*");
			
	//		System.out.println(fileReader.hasNext() + " " + fileReader.next());
			fileReader.next(); //Skips the lines where cube colors are stored
			
			//Current Potential Issue:
		//	What about the entries that are not changed? -- Reset to a new column with no data.
		//	What about the same entry being put in multiple times? -- The last entry's data will be used.
		//	Assumed that illegal entries are ignored -- IllegalArgumentException is thrown if illegal entries are detected.
			
			while(fileReader.hasNext()) {
				String text = fileReader.next();
				String outString = "";
				if(text.substring(0,2).matches("s\\*")) {
					System.out.println("SIMPLE: " + text.substring(2));
					outString = readEntry(simpCols, text.substring(2));
					if(!addedSimpEntries.contains(outString)) {
						addedSimpEntries.add(outString);
					}
				} else if(text.substring(0,2).matches("f\\*")) {
					System.out.println("FULL: " + text.substring(2));
					outString = readEntry(fullCols, text.substring(2));
					if(outString != null && !addedFullEntries.contains(outString)) {
						addedFullEntries.add(outString);
					}
				} else {
					fileReader.close();
					throw new IllegalArgumentException("Invalid entries in file");
				}
			}
			fileReader.close(); //FileReader closed because no longer needed.
			
			//Reset all non-changed columns in simple
			for(int i = 0; i < simpCols.length; i++) {
				if(!addedSimpEntries.contains(simpCols[i].getFullPermName())) {
					simpCols[i].reset();
				}
			}
			
			//Reset all non-changed columns in full
			for(int i = 0; i < fullCols.length; i++) {
				if(!addedFullEntries.contains(fullCols[i].getFullPermName())) {
					fullCols[i].reset();
				}
			}
			
			updateSessionTables();
		}
	}
	
	/**
	 * Private helper method used to set the colors of the cube from the values given in the file. It is called in 
	 * readSessionFromFile() where the cube colors should be the first things in the file.
	 * @param colors should be an array of 6 elements with values representing the RGB values of each.
	 */
	private void readColors(String[] colors) { //WORKS
		InitColor[] colorVars = new InitColor[] {initWhite, initRed, initBlue, initGreen, initOrange, initYellow};
		for(int i = 0; i < 6; i++) {
			String[] splitVals = colors[i].split(",");
			System.out.println(splitVals.length);
			if(splitVals.length != 3) { //check to make sure only 3 numbers exist
				throw new IllegalArgumentException("Invalid Color(s) in file.");
			}
			try {
				System.out.println(splitVals[0] + " - " + splitVals[1] + " - " + splitVals[2]); //Problem is not stripping the \n off of last vals
				colorVars[i].setColor(Integer.parseInt(splitVals[0]), Integer.parseInt(splitVals[1]), Integer.parseInt(splitVals[2]));
			} catch (Exception e) { //Number format + whatever wrong values are thrown into the setColor
				throw new IllegalArgumentException("Illegal Color Value(s) in file.");
			}
		}
	}
		
	/**
	 * Private helper method used to process the entries from a String read for the entry from the file 
	 * within the readSessionFromFile() method.
	 * @param typeCol the type of columns that it is a part of, which could be either simpCols or fullCols
	 * @param entry the String representing the entry which is being processed
	 * @return a String of the full entry name if it was processed correctly.
	 * @throws IllegalArgumentException if the entry, or anything in it, is not valid. 
	 */
	private String readEntry(SessionEntry[] typeCol, String entry) throws IllegalArgumentException {
		Scanner entryReader = new Scanner(entry);
		entryReader.useDelimiter("\\n\\-");
		
		String fullCaseName; //name of the case
		String correctTimes; //times of the correct guesses
		String wrongGuesses; //wrong guesses made
		
		try {
		/*
			 R1-3
			 -
			 -
		 */
//			System.out.println("SYSOUT1: " + entryReader.next());
//			System.out.println("SYSOUT2: " + entryReader.next());
			fullCaseName = entryReader.next().strip();
			correctTimes = entryReader.next().strip();
			wrongGuesses = entryReader.next().strip();
		} catch (NoSuchElementException e) {
			entryReader.close();
			throw new IllegalArgumentException("Invalid Entry in file.");
		}
		entryReader.close(); //closing entryReader because everything needed should've been recorded
		
		if(TwoSidePLLPractice.isValidFullCaseName(fullCaseName)) { //Checks for if it's a valid case.
			//If valid, it will change the settings of that case in the typeCol
			String[] timeStrings = correctTimes.split(",");
			if(timeStrings.length == 1 && timeStrings[0].isBlank()) { //In case it's empty, to get rid of the ""
				timeStrings = new String[0];
			}
			Double[] newTimes = new Double[timeStrings.length];
			for(int i = 0; i < timeStrings.length; i++) {
				try {
					newTimes[i] = Double.parseDouble(timeStrings[i]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Illegal Time in file.");
				}
			}
			String[] newWrongGuesses = wrongGuesses.split(",");
			if(newWrongGuesses.length == 1 && newWrongGuesses[0].isBlank()) { //In case it's empty, to get rid of the ""
				newWrongGuesses = new String[0];
			}
			for(int i = 0; i < newWrongGuesses.length; i++) { //check to make sure all wrong guesses are WRONG and VALID
				System.out.println(newWrongGuesses.length);
				if(fullCaseName.substring(fullCaseName.length() - 2).equals(newWrongGuesses[i]) || !TwoSidePLLPractice.isValidSimpCaseName(newWrongGuesses[i])) {
					//PROBLEM: fullCaseName will NEVER equal wrongGuess, because wrongGuess is SIMPLE and NOT FULL.
//					System.out.println("Invalid case detected in file: " + newWrongGuesses[i]);
					throw new IllegalArgumentException("Illegal Wrong Guess Case in file.");
				}
			}
			// Replaces the column with the new data loaded from the entry
			for(int i = 0; i < typeCol.length; i++) {
				if(typeCol[i].getFullPermName().equals(fullCaseName)) {
					typeCol[i] = new SessionEntry(fullCaseName, newTimes, newWrongGuesses);
					break; //Stops the for loop since there should only be one case to change
				}
			}
			return fullCaseName;
		} else { //if the case is not valid...
//			return null; //Returns null if the case is not valid. 
			throw new IllegalArgumentException("Invalid PLL Case in file."); //Throws an illegalArgumentException if a case in the file isn't valid.
		}
	}
	
	/**
	 * Static method used to return a boolean if the full case name is a valid one or not.
	 * @param caseName String of the case name to be checked to see if it's a valid case name.
	 * @return boolean for if the full case name is valid or not.
	 */
	private static boolean isValidFullCaseName(String caseName) {
		// "A1-1", "A1-2", "A1-3", "A1-4", "A2-1", "A2-2", "A2-3", "A2-4", "E-1", "E-2", "E-3", "E-4", "F-1", "F-2", "F-3", "F-4", "G1-1", "G1-2", "G1-3", "G1-4", "G2-1", "G2-2", "G2-3", "G2-4", "G3-1", "G3-2", "G3-3", "G3-4", "G4-1", "G4-2", "G4-3", "G4-4", "H-1", "H-2", "H-3", "H-4", "J1-1", "J1-2", "J1-3", "J1-4", "J2-1", "J2-2", "J2-3", "J2-4", "N1-1", "N1-2", "N1-3", "N1-4", "N2-1", "N2-2", "N2-3", "N2-4", "R1-1", "R1-2", "R1-3", "R1-4", "R2-1", "R2-2", "R2-3", "R2-4", "SKIP-1", "SKIP-2", "SKIP-3", "SKIP-4", "T-1", "T-2", "T-3", "T-4", "U1-1", "U1-2", "U1-3", "U1-4", "U2-1", "U2-2", "U2-3", "U2-4", "V-1", "V-2", "V-3", "V-4", "Y-1", "Y-2", "Y-3", "Y-4", "Z-1", "Z-2", "Z-3", "Z-4"
		
		/*"A1-1", "A1-2", "A1-3", "A1-4", "A2-1", "A2-2", "A2-3", "A2-4", "E-1", "E-2", "E-3", "E-4", "F-1", "F-2", "F-3", "F-4", 
		 * "G1-1", "G1-2", "G1-3", "G1-4", "G2-1", "G2-2", "G2-3", "G2-4", "G3-1", "G3-2", "G3-3", "G3-4", "G4-1", "G4-2", "G4-3", 
		 * "G4-4", "H-1", "H-2", "H-3", "H-4", "J1-1", "J1-2", "J1-3", "J1-4", "J2-1", "J2-2", "J2-3", "J2-4", "N1-1", "N1-2", "N1-3", 
		 * "N1-4", "N2-1", "N2-2", "N2-3", "N2-4", "R1-1", "R1-2", "R1-3", "R1-4", "R2-1", "R2-2", "R2-3", "R2-4", "SKIP-1", "SKIP-2", 
		 * "SKIP-3", "SKIP-4", "T-1", "T-2", "T-3", "T-4", "U1-1", "U1-2", "U1-3", "U1-4", "U2-1", "U2-2", "U2-3", "U2-4", "V-1", "V-2", 
		 * "V-3", "V-4", "Y-1", "Y-2", "Y-3", "Y-4", "Z-1", "Z-2", "Z-3", "Z-4"
		 */
		
		if(caseName.matches("^[AJNRU][12]\\-[1-4]$|^G[1-4]\\-[1-4]$|^[EFHTVYZ]\\-[1-4]$|^SKIP\\-[1-4]$")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Static method used to return a boolean if the simple case name is a valid one or not.
	 * @param caseName String of the case name to be checked to see if it's a valid case name.
	 * @return boolean for if the simple case name is valid or not.
	 */
	private static boolean isValidSimpCaseName(String caseName) {
		if(caseName.matches("^[AJNRU][12]?$|^G[1-4]?$|^[EFHTVYZ]$|^SKIP$")) {
			return true;
		}
		return false;
	}
		

////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {launch(args);}
////////////////////////////////////////////////////////////////////////


	/*
	 Sources for code I've adapted code from:
		 Timer - https://stackoverflow.com/questions/34801227/fast-counting-timer-in-javafx
		 BorderBox - https://www.geeksforgeeks.org/javafx-combobox-with-examples/
		 ScrollPane idea from 2nd reply here- https://stackoverflow.com/questions/38912047/how-to-fix-javafx-tableview-size-to-current-window-size
		 Help with some font stuffs - https://docs.oracle.com/javafx/2/text/jfxpub-text.htm
		 Center aligning things in tableview - https://stackoverflow.com/questions/13455326/javafx-tableview-text-alignment
		 Idea behind my keybinds functionality - https://stackoverflow.com/questions/23052257/multiple-key-press-on-javafx-scene
	 	 TitledPane help - https://o7planning.org/en/11059/javafx-titledpane-tutorial
	 	 Finding most frequent element in an array - https://www.geeksforgeeks.org/frequent-element-array/
	 */
}