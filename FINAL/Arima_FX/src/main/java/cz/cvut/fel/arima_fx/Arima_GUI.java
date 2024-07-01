package cz.cvut.fel.arima_fx;
// Java Program to show the four
// buttons on the GridPane
import cz.cvut.fel.arima_fx.Board.BoardImpl;
import cz.cvut.fel.arima_fx.Board.Box;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


import javafx.geometry.Insets;
        import javafx.scene.layout.*;
        import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Arima_GUI{

    GridPane Arimaa_board;
    BoardImpl Model;
    private static int[][] initial_board = {{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},
            {1,0},{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{1,7},
            {6,0},{6,1},{6,2},{6,3},{6,4},{6,5},{6,6},{6,7},
            {7,0},{7,1},{7,2},{7,3},{7,4},{7,5},{7,6},{7,7},
    };
    public ArrayList<Box> squares = new ArrayList<>();
    Color color = Color.web("#cbb7ae");
//    ############### CONSTRUCTOR ###############
    public Arima_GUI(GridPane grid, int[][] start_setup){
        this.Arimaa_board = grid;
            Arimaa_board.getStyleClass().add("Arimaa_board");
            Arimaa_board.setMaxSize(880, 880);
            Arimaa_board.setMinSize(680, 680);

        this.Model = new BoardImpl();
        if(start_setup != null ){Model.set_board(start_setup);}else{Model.set_board(initial_board);}


        makeBoard();
    }

    private void makeBoard(){
        squares.clear();
        for(int i = 0; i<8; i++){
            for(int j=0; j<8; j++){
                ImageView figure;
                Box square = Model.play_desk[i][j];
                square.getStyleClass().add("square");
                if (!square.safe){square.getStyleClass().add("trap");}

                square.setPrefSize(110, 110);

                square.setMinSize(85, 85);

                square.setMaxSize(130,130);

                square.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                square.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

                if (square.on_box != null) {
                    figure = new ImageView(square.on_box.figure_img_location);
                    square.getChildren().add(figure);

                    figure.setFitWidth(95);
                    figure.setFitHeight(95);

                }
                squares.add(square);
                Arimaa_board.add(square, j, i, 1, 1);
            }
        }
    }
    public void update_BIG_board(int[][] input){
        Arimaa_board.getChildren().clear();
        Model.set_board(input);
        makeBoard();
    }
}
