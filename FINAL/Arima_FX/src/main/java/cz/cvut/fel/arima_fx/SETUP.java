package cz.cvut.fel.arima_fx;

import cz.cvut.fel.arima_fx.Board.BoardImpl;
import cz.cvut.fel.arima_fx.Board.Box;
import cz.cvut.fel.arima_fx.Figures.Figure;
import cz.cvut.fel.arima_fx.GAME_save.game;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.logging.Logger;

public class SETUP {

    public BorderPane root;
    public GridPane SET_UP_board = new GridPane();
    public GridPane GOLD_pool = new GridPane();
    public GridPane SILVER_pool = new GridPane();
    BoardImpl Model;

    public static Box selected_from = null;

    private static final Logger LOGGER = Logger.getLogger(SETUP.class.getName());

    public Button button  = new Button("Start");
//    ################## START #############
    public SETUP() {
        Model = new BoardImpl();
        Model.creat_board();

        root = new BorderPane();
        root.getStyleClass().add("root");
            make_set_up_board();
                SET_UP_board.setPrefSize(800, 800);
                SET_UP_board.getStyleClass().add("Set_up_board");
            make_Silver_pool();
                SILVER_pool.getStyleClass().add("Pool");
            make_Gold_pool();
                GOLD_pool.getStyleClass().add("Pool");

        root.setMaxSize(880, 880);
        root.setCenter(SET_UP_board);
        root.setTop(GOLD_pool);
        root.setBottom(SILVER_pool);

        addEventHandlersPool(SILVER_pool);
        addEventHandlersPool(GOLD_pool);

    }





//    HANDLERS
    private void addEventHandlersPool(GridPane grid) {
        grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EventTarget eventTarget = event.getTarget();
//                Clicked on square
                if(eventTarget.toString().equals("Box")){
                    Box square = (Box) eventTarget;

                    if(square.on_box != null){
                        selected_from = square;
                        LOGGER.info(square.on_box.name + " " + square.on_box.color);
                        EventHandlersPlace(SET_UP_board);
                    }
                }else if(eventTarget instanceof ImageView){

                    // Clicked on Figure
                    ImageView eventTarget2 = (ImageView) eventTarget;
                    Box square = (Box) eventTarget2.getParent();

                    if(square.on_box != null){
                        selected_from = square;
                        LOGGER.info(square.on_box.name + " " + square.on_box.color);
                        EventHandlersPlace(SET_UP_board);
                    }
                }
            }
        });
    }

    private void EventHandlersPlace(GridPane grid) {
        grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EventTarget eventTarget = event.getTarget();
                if (selected_from != null){
//                Clicked on square
                    if(eventTarget.toString().equals("Box")){
                        Box square = (Box) eventTarget;
                        move_to_board(square);
                    }
                }
                if(eventTarget instanceof ImageView){

                    // Clicked on Figure
                    ImageView eventTarget2 = (ImageView) eventTarget;
                    Box square = (Box) eventTarget2.getParent();
                    LOGGER.info("Want to reset " + square.on_box.name);

                    if (square.on_box.color){resetGold(square);}else{resetSilver(square);}
                }
            }
        });
    }

//    MOVE
    private void move_to_board(Box Square_to){

        ImageView lastChild = (ImageView) selected_from.getChildren().get(0);

        Square_to.getChildren().add(lastChild);
        Square_to.setOn_box(selected_from.on_box);

        selected_from.getChildren().clear();
        selected_from.unset_box();

        selected_from = null;
    }

//    RESET
    private void resetSilver(Box square){
        for (int i = 0; i < 16; i++) {
            Box cur = (Box)SILVER_pool.getChildren().get(i);
            if (cur.on_box == null){

                ImageView lastChild = (ImageView) square.getChildren().get(0);

                cur.getChildren().add(lastChild);
                cur.setOn_box(square.on_box);

                square.getChildren().clear();
                square.unset_box();
                return;
            }
        }
    }
    private void resetGold(Box square){
        for (int i = 0; i < 16; i++) {
            Box cur = (Box)GOLD_pool.getChildren().get(i);
            if (cur.on_box == null){

                ImageView lastChild = (ImageView) square.getChildren().get(0);

                cur.getChildren().add(lastChild);
                cur.setOn_box(square.on_box);

                square.getChildren().clear();
                square.unset_box();
                return;
            }
        }
    }

//    MAKERS
    public void make_set_up_board(){
        for(int i = 0; i<8; i++){
            for(int j=0; j<8; j++){
                ImageView figure;
                Box square = Model.play_desk[i][j];
                square.getStyleClass().add("square_setup");
                if (!square.safe){square.getStyleClass().add("trap_setup");}
                if (i < 2){
                    square.getStyleClass().clear();
                    square.getStyleClass().add("square_setup_gold");
                } else if (i > 5){
                    square.getStyleClass().clear();
                    square.getStyleClass().add("square_setup_silver");
                }

                square.setPrefSize(100, 100);
                square.setMinSize(85, 85);

                square.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                SET_UP_board.add(square, j, i, 1, 1);
            }
        }
    }
    public void make_Silver_pool() {
        for (int i = 1; i < 7; i++) {
            ImageView figure;
////            CAT
            if (i == 6) {
                for (int j = 0; j < 2; j++) {
                    Box box = new Box(true, 1, 1);
                    box.setOn_box(Model.Silver_figure_list[4]);
                    box.setPrefSize(100, 100);
                    figure = new ImageView(box.on_box.figure_img_location);
                    box.getChildren().add(figure);

                    figure.setFitWidth(95);
                    figure.setFitHeight(95);

                    SILVER_pool.add(box, j+6, 0);
                }
            }
////            DOG
            if (i == 5) {
                for (int j = 0; j < 2; j++) {
                    Box box = new Box(true, 1, 1);
                    box.setOn_box(Model.Silver_figure_list[3]);
                    box.setPrefSize(100, 100);

                    figure = new ImageView(box.on_box.figure_img_location);
                    box.getChildren().add(figure);

                    figure.setFitWidth(95);
                    figure.setFitHeight(95);

                    SILVER_pool.add(box, j+4, 0);
                }

            }
////            HORSE
            if (i == 4) {
                for (int j = 0; j < 2; j++) {
                    Box box = new Box(true, 1, 1);
                    box.setOn_box(Model.Silver_figure_list[2]);
                    box.setPrefSize(100, 100);

                    figure = new ImageView(box.on_box.figure_img_location);
                    box.getChildren().add(figure);

                    figure.setFitWidth(95);
                    figure.setFitHeight(95);

                    SILVER_pool.add(box, j+2, 0);
                }
            }
////            CAMEL
            if (i == 3) {
                Box box = new Box(true, 1, 1);
                box.setOn_box(Model.Silver_figure_list[1]);
                box.setPrefSize(100, 100);

                figure = new ImageView(box.on_box.figure_img_location);
                box.getChildren().add(figure);

                figure.setFitWidth(95);
                figure.setFitHeight(95);

                SILVER_pool.add(box, 1, 0);
            }
////            ELEPHANT
            if (i == 2) {
                Box box = new Box(true, 1, 1);
                box.setOn_box(Model.Silver_figure_list[0]);
                box.setPrefSize(100, 100);

                figure = new ImageView(box.on_box.figure_img_location);
                box.getChildren().add(figure);

                figure.setFitWidth(95);
                figure.setFitHeight(95);

                SILVER_pool.add(box, 0, 0);

            }
//            BUNNY
            if (i == 1) {
                for (int j = 0; j < 8; j++) {
                    Box box = new Box(true, 1, 1);
                    box.setOn_box(Model.Silver_figure_list[5]);
                    box.setPrefSize(100, 100);

                    figure = new ImageView(box.on_box.figure_img_location);
                    box.getChildren().add(figure);

                    figure.setFitWidth(95);
                    figure.setFitHeight(95);

                    SILVER_pool.add(box, j, 1);
                }
            }


        }
    }
    public void make_Gold_pool() {
        for (int i = 1; i < 7; i++) {
            ImageView figure;
//            CAT
            if (i == 6) {
                for (int j = 0; j < 2; j++) {
                    Box box = new Box(true, 1, 1);
                    box.setOn_box(Model.Gold_figure_list[4]);
                    box.setPrefSize(100, 100);

                    figure = new ImageView(box.on_box.figure_img_location);
                    box.getChildren().add(figure);

                    figure.setFitWidth(95);
                    figure.setFitHeight(95);

                    GOLD_pool.add(box, j + 6, 1);
                }
            }
//            DOG
            if (i == 5) {
                for (int j = 0; j < 2; j++) {
                    Box box = new Box(true, 1, 1);
                    box.setOn_box(Model.Gold_figure_list[3]);
                    box.setPrefSize(100, 100);

                    figure = new ImageView(box.on_box.figure_img_location);
                    box.getChildren().add(figure);

                    figure.setFitWidth(95);
                    figure.setFitHeight(95);

                    GOLD_pool.add(box, j + 4, 1);
                }
            }
//            HORSE
            if (i == 4) {
                for (int j = 0; j < 2; j++) {
                    Box box = new Box(true, 1, 1);
                    box.setOn_box(Model.Gold_figure_list[2]);

                    box.setPrefSize(100, 100);
                    figure = new ImageView(box.on_box.figure_img_location);
                    box.getChildren().add(figure);

                    figure.setFitWidth(95);
                    figure.setFitHeight(95);

                    GOLD_pool.add(box, j + 2, 1);
                }
            }
//            CAMEL
            if (i == 3) {
                Box box = new Box(true, 1, 1);
                box.setOn_box(Model.Gold_figure_list[1]);
                box.setPrefSize(100, 100);

                figure = new ImageView(box.on_box.figure_img_location);
                box.getChildren().add(figure);

                figure.setFitWidth(95);
                figure.setFitHeight(95);

                GOLD_pool.add(box, 1, 1);
            }
//            ELEPHANT
            if (i == 2) {
                Box box = new Box(true, 1, 1);
                box.setOn_box(Model.Gold_figure_list[0]);
                box.setPrefSize(100, 100);

                figure = new ImageView(box.on_box.figure_img_location);
                box.getChildren().add(figure);

                figure.setFitWidth(95);
                figure.setFitHeight(95);

                GOLD_pool.add(box, 0, 1);

            }
//            BUNNY
            if (i == 1) {
                for (int j = 0; j < 8; j++) {
                    Box box = new Box(true, 1, 1);
                    box.setOn_box(Model.Gold_figure_list[5]);
                    box.setPrefSize(100, 100);

                    figure = new ImageView(box.on_box.figure_img_location);
                    box.getChildren().add(figure);

                    figure.setFitWidth(95);
                    figure.setFitHeight(95);

                    GOLD_pool.add(box, j, 0);
                }
            }
        }
    }


    public int[][] get_SETUP(){
        return Model.curent_state();
    }
}


