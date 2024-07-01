package cz.cvut.fel.arima_fx;
// Java Program to show the four
// buttons on the GridPane

import cz.cvut.fel.arima_fx.Board.BoardImpl;
import cz.cvut.fel.arima_fx.Board.Box;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Arima_GUI_mini {

    public GridPane Arimaa_board_mini;
    public BoardImpl Model;
    private static int[][] initial_board = {{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
            {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
            {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
            {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1}};
    public Arima_GUI_mini(){
        Arimaa_board_mini = new GridPane();
        Arimaa_board_mini.getStyleClass().add("Arimaa_board_mini");
        Insets padding = new Insets(0, 0, 0, 20);
        Arimaa_board_mini.setMaxSize(240, 240);
        Arimaa_board_mini.setMinSize(200, 200);

        this.Model = new BoardImpl();

        update_mini_board(initial_board);
    }

    private void makeBoard(){
        for(int i = 0; i<8; i++){
            for(int j=0; j<8; j++){
                ImageView figure;
                Box square = Model.play_desk[i][j];
                square.getStyleClass().add("square_mini");
                if (!square.safe){square.getStyleClass().add("trap_mini");}

                square.setPrefSize(30, 30);

                square.setMinSize(25, 25);

                square.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                if (square.on_box != null) {
                    figure = new ImageView(square.on_box.figure_img_location);
                    square.getChildren().add(figure);

                    figure.setFitWidth(25);
                    figure.setFitHeight(25);

                }
                Arimaa_board_mini.add(square, j, i, 1, 1);
            }
        }
    }
    public void update_mini_board(int[][] input){
        Arimaa_board_mini.getChildren().clear();
//        Model.set_board(initial_board);
        Model.set_board(input);
        makeBoard();
    }

}
