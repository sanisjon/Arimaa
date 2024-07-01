package cz.cvut.fel.arima_fx;

import cz.cvut.fel.arima_fx.GAME_save.game;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.nio.file.attribute.AclEntry;
import java.util.Arrays;

public class Play extends Application {
//    GAME SECTON
    private static final VBox sideNumbers = new VBox();
    private static final HBox TopAlphabet = new HBox();
    private static final HBox BottomAlphabet = new HBox();
    private static final VBox SideNumbersR = new VBox();
    // TopBar
    private static final StackPane TopBar = new StackPane();
    //      Initial Pane for Arima_Game

    private static final GridPane grid = new GridPane();
    //            Eliminated
        private static final VBox GOLD = new VBox();
        private static final VBox SILVER = new VBox();

    //      Initial Pane for Arima_Game
    private Arima_Game arima_game = new Arima_Game(grid, GOLD, SILVER, TopBar,start_setup);

//    SETUP SECTION
    private  static Button StartButton = new Button("START");

    private SETUP setUP  = new SETUP();
    private static int[][] start_setup = null;


//    PANESETUP
    private static BorderPane main = new BorderPane();
    private static BorderPane root = new BorderPane();


    @Override
    public void start(Stage primaryStage) throws Exception {
//        #################### SCEAN SETUP ####################

        BorderPane SETUP_WINDOW = new BorderPane();
        SETUP_WINDOW.setCenter(setUP.root);
        SETUP_WINDOW.setRight(StartButton);
            StartButton.getStyleClass().add("Start_Button");
            StartButton.setPrefSize(120,  120);
            StartButton.setTranslateX(-50);
            StartButton.setTranslateY(50);




//    #################### SCEAN GAME ####################
        make_sideNum();
        make_topAlphabet();
        make_sideNumR();

        //        Make Panes
        BorderPane main = new BorderPane();
        BorderPane root = new BorderPane();

        //        Set CSS class
        root.getStyleClass().add("root");
        main.getStyleClass().add("main");



//        Setting root
        root.setCenter(arima_game.ArimaGUI.Arimaa_board);
        root.setLeft(sideNumbers);
        root.setTop(TopAlphabet);
        root.setRight(SideNumbersR);
        root.setBottom(BottomAlphabet);
        root.setMaxSize(980, 980);

//        Setting main
        main.setCenter(root);
            HBox Eliminated = new HBox(arima_game.Eliminated_GOLD,arima_game.Eliminated_SILVER);
        main.setLeft(Eliminated);

        main.setTop(arima_game.TopBar);
            VBox controler = new VBox(Arima_Game.Arima_mini.Arimaa_board_mini, arima_game.Prew_Next);
                controler.setTranslateX(-50);
                controler.setTranslateY(50);
                controler.setPrefWidth(300);
        main.setRight(controler);


//      #################  SCEANS #################
        Scene SetUP_SCEAN = new Scene(SETUP_WINDOW,Double.MAX_VALUE, Double.MAX_VALUE);
            SetUP_SCEAN.getStylesheets().add("stylesheet.css");
        Scene Game_SCEAN = new Scene(main, Double.MAX_VALUE, Double.MAX_VALUE);
            Game_SCEAN.getStylesheets().add("stylesheet.css");


        StartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                start_setup = setUP.get_SETUP();
                arima_game.ArimaGUI.update_BIG_board(start_setup);
                arima_game.save();

                primaryStage.setScene(Game_SCEAN);
            }
        });




        primaryStage.setTitle("Arimaa");
        primaryStage.setScene(SetUP_SCEAN);
        primaryStage.show();
    }






    private void make_sideNum (){
        for (int i = 1; i < 9; i++) {
            StackPane num = new StackPane();
            num.setPrefSize(50, 110);
            num.getStyleClass().add("coord_box");

            Label n = new Label(String.valueOf(i));
            n.getStyleClass().add("side_coord");

            num.getChildren().add(n);
            sideNumbers.getChildren().add(num);
        }
    }
    private void make_sideNumR (){
        for (int i = 1; i < 9; i++) {
            StackPane num = new StackPane();
            num.setPrefSize(50, 110);
            num.getStyleClass().add("coord_box");

            Label n = new Label(String.valueOf(i));
            n.getStyleClass().add("side_coord");

            num.getChildren().add(n);
            SideNumbersR.getChildren().add(num);
        }
    }
    private void make_topAlphabet (){
        char [] alphabet = {'A','B','C','D','E','F','G','H'};
        for (int i = 0; i < 8; i++) {
            StackPane leter = new StackPane();
            leter.setPrefSize(110, 50);
            leter.getStyleClass().add("coord_box");

            Label n = new Label(String.valueOf(alphabet[i]));
            n.getStyleClass().add("side_coord");

            leter.getChildren().add(n);
            TopAlphabet.getChildren().add(leter);
        }
        TopAlphabet.getStyleClass().add("Alphabet_bar");

        for (int i = 0; i < 8; i++) {
            StackPane leter = new StackPane();
            leter.setPrefSize(110, 50);
            leter.getStyleClass().add("coord_box");

            Label n = new Label(String.valueOf(alphabet[i]));
            n.getStyleClass().add("side_coord");

            leter.getChildren().add(n);
            BottomAlphabet.getChildren().add(leter);
        }
        BottomAlphabet.getStyleClass().add("Alphabet_bar");
    }

}


