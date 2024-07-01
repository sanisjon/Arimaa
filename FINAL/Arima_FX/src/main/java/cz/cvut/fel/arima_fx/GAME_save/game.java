package cz.cvut.fel.arima_fx.GAME_save;
public class game {
    public boolean Player;
    public int moveCount;
    public int[][] playDesk;

    public int[] last_move;
    public short power_of_pushed;
    public short power_of_last;
    public int[] push_position = null;

    public game(boolean player, int moveCount, int[][] playDesk, int[] last_move, short power_of_pushed, short power_of_last, int[] push_position) {
        Player = player;
        this.moveCount = moveCount;
        this.playDesk = playDesk;
        this.last_move = last_move;
        this.power_of_pushed = power_of_pushed;
        this.power_of_last = power_of_last;
        this.push_position = push_position;
    }
}
