package cz.cvut.fel.arima_fx.Board;

import cz.cvut.fel.arima_fx.Figures.Bunny;
import cz.cvut.fel.arima_fx.Figures.Figure;
import javafx.scene.layout.StackPane;

public class Box extends StackPane {
    public Figure on_box = null;
    public int[][] legal_moves;


//    State of box
    public final boolean safe;
    public boolean Frozen;


//    Position on board
    public final int HORIZONTAL;
    public final int  VERTICAL;



    /**
     * @param safe
     * true --> place
     * false --> trap
     */
    public Box(boolean safe, int vertical, int horizontal) {
        this.Frozen = false;
        this.safe = safe;
        this.HORIZONTAL = horizontal;
        this.VERTICAL = vertical;
        get_moves(vertical, horizontal);
    }





    public void setOn_box(Figure figure) {
        this.on_box = figure;
    }
    public boolean unset_box() {
        this.on_box = null;
        return true;
    }

    public void freeze(){this.Frozen = true;}
    public void un_Freeze(){this.Frozen = false;}


    /**
     * @param VERTICAL
     * @param HORIZONTAL
     * Modifikuje list validnich pozic pro pohyb figurky.
     * Validuje pouze zda neni pohyb mimo hraci plochu
     * {Right Left Up Down}
     */
    public void get_moves(int VERTICAL, int HORIZONTAL){
        int[][] moves ={{0,0},{0,0},{0,0},{0,0}};
        for (int i = 0; i < 4; i++) {
            switch (i){
//                Right
                case 0:
                    moves[0][0] = VERTICAL;
                    moves[0][1] = HORIZONTAL + 1;
//                Left
                case 1:
                    moves[1][0] = VERTICAL;
                    moves[1][1] = HORIZONTAL - 1;
//                Up
                case 2:
                    moves[2][0] = VERTICAL + 1;
                    moves[2][1] = HORIZONTAL;
//               Down
                case 3:
                    moves[3][0] = VERTICAL - 1;
                    moves[3][1] = HORIZONTAL;
            }
            if(!verify_position_index(moves[i])){
                moves[i][0] = -1;
                moves[i][1] = -1;
            };
        }
        this.legal_moves = moves;
    }

    /**
     *
     * @param xy pozice[horiyontalni, verticalni]
     * @return true --> validni
     *         false --> nevalidni
     * Verifikuje validitu pozice vzhledem k indexu na sachovnici
     */
    private boolean verify_position_index(int[] xy){
        if(xy[0] < 8 && xy[0] > -1){
            return xy[1] < 8 && xy[1] > -1;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Box";
    }
}
