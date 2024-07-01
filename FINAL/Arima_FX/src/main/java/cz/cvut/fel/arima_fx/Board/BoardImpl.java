package cz.cvut.fel.arima_fx.Board;

import cz.cvut.fel.arima_fx.Enums.Figure_type;
import cz.cvut.fel.arima_fx.Figures.*;

import java.util.Arrays;
import java.util.logging.Logger;

public class BoardImpl implements Board{
    public Box[][] play_desk;

    private int[] invalid = {9,9};
    public int[] last_move = invalid;
        public void setLast_move(int[] last_move) {
            this.last_move = last_move;
        }
    public short power_of_pushed = 0;
        public short power_of_last = 0;
    public int[] push_position = invalid;
        public void setPush_position(int[] push_position) {
        this.push_position = push_position;
    }



    /**
     list_of_trap_safety pořadí pozic trap = [2,2],[2,5],[5,2],[5,5]
     */
    private boolean[][] list_of_trap_safty = {{false,false},{false,false},{false,false},{false,false}};
        public boolean[][] getList_of_trap_safty() {
            return list_of_trap_safty;
        }

    private static final Logger LOGGER = Logger.getLogger(BoardImpl.class.getName());

    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED = "\033[0;31m";     // RED
    public static final String FROZEN = "\033[0;35m";   // White

//    White/Black Figures Initial
    Figure W_Elephant = new Elephant(true);     Figure B_Elephant = new Elephant(false);
    Figure W_Camel = new Camel(true);           Figure B_Camel = new Camel(false);
    Figure W_Horse = new Horse(true);           Figure B_Horse = new Horse(false);
    Figure W_Dog = new Dog(true);               Figure B_Dog = new Dog(false);
    Figure W_Cat = new Cat(true);               Figure B_Cat = new Cat(false);
    Figure W_Bunny = new Bunny(true);           Figure B_Bunny = new Bunny(false);

    public Figure[] Gold_figure_list = {W_Elephant,W_Camel,W_Horse,W_Dog,W_Cat,W_Bunny};
    public Figure[] Silver_figure_list = {B_Elephant,B_Camel,B_Horse,B_Dog,B_Cat,B_Bunny};


    public BoardImpl() {
        creat_board();
    }

    /**
     * Kontrolni board printer
     */
    public void print_board(){
        char fild = (char) 127;
        System.out.println('\n');
        for (int i = 7; i > -1; i-- ) {
            System.out.print("  "+i+"  ");
            for (int j = 0; j < 8; j++) {
                if(play_desk[i][j].on_box == null){
                    if (!play_desk[i][j].safe){

                        System.out.print(RED + "  #  "+RESET);
                    }else{
                        System.out.print("  "+ fild +"  ");}
                }else{
                    if (play_desk[i][j].on_box.color) {
                        if (play_desk[i][j].Frozen){
                            System.out.print(FROZEN + play_desk[i][j].on_box.name + " " + RESET);
                        }else{System.out.print(YELLOW + play_desk[i][j].on_box.name + " " + RESET);}
                    }else{
                        if (play_desk[i][j].Frozen){
                            System.out.print(FROZEN + play_desk[i][j].on_box.name + " " + RESET);
                        }else{System.out.print(BLUE + play_desk[i][j].on_box.name + " " + RESET);}
                    }


                }

                if (j==7){
                    System.out.println('\n');
                }
            }
        }
        System.out.print("    ");
        for (int i = 0; i < 8; i++) {
            System.out.print("  "+i+"  ");
        }
    }


    /**
     * Creates/sets
     * přímá vazba na paly_board její počáteční inicializaci.
     */
    @Override
    public void creat_board() {
        Box[][] desk = {{null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null}
                        };
        for (int i = 0; i < 8; i++ ) {
//            System.out.print(i + ": ");

            for (int j = 0; j < 8; j++) {

//                System.out.print(j);
                if(j==2 || j == 5) {
                    if (i == 2 || i == 5) {
                        desk[i][j] = new Box(false, i, j);
                        continue;
                    }
                }
                    desk[i][j] = new Box(true, i, j);
            }
//            System.out.println("\n");
        }

        this.play_desk  = desk;
    }

    @Override
    public void set_board(int[][] coord_list){
        creat_board();

        //Elephant
        try {
            play_desk[coord_list[0][0]][coord_list[0][1]].setOn_box(W_Elephant);
        } catch (Exception e) {}

        try {
            play_desk[coord_list[16][0]][coord_list[16][1]].setOn_box(B_Elephant);
        } catch (Exception e) {}


        //Camel
        try {
            play_desk[coord_list[1][0]][coord_list[1][1]].setOn_box(W_Camel);
        } catch (Exception e) {}

        try {
            play_desk[coord_list[17][0]][coord_list[17][1]].setOn_box(B_Camel);
        } catch (Exception e) {}

        //Horse
        for (int i = 2; i < 4; i++) {
            try {
                play_desk[coord_list[i][0]][coord_list[i][1]].setOn_box(W_Horse);
            }catch (Exception e){}
        }
        for (int i = 18; i < 20; i++) {
            try {
                play_desk[coord_list[i][0]][coord_list[i][1]].setOn_box(B_Horse);
            } catch (Exception e){}
        }
        //Dog
        for (int i = 4; i < 6; i++) {
            try {
                play_desk[coord_list[i][0]][coord_list[i][1]].setOn_box(W_Dog);
            } catch (Exception e) {}
        }
        for (int i = 20; i < 22; i++) {
            try {
                play_desk[coord_list[i][0]][coord_list[i][1]].setOn_box(B_Dog);
            } catch (Exception e) {}

        }
        //Cat
        for (int i = 6; i < 8; i++) {
            try {
                play_desk[coord_list[i][0]][coord_list[i][1]].setOn_box(W_Cat);
            } catch (Exception e) {}
        }
        for (int i = 22; i < 24; i++) {
            try {
                play_desk[coord_list[i][0]][coord_list[i][1]].setOn_box(B_Cat);
            } catch (Exception e) {}
        }
        //Bunny
        for (int i = 8; i < 16; i++) {
            try {
                play_desk[coord_list[i][0]][coord_list[i][1]].setOn_box(W_Bunny);
            } catch (Exception e) {}

        }
        for (int i = 24; i < 32; i++) {
            try {
                play_desk[coord_list[i][0]][coord_list[i][1]].setOn_box(B_Bunny);
            } catch (Exception e) {}

        }
    }




    /**
     * MOVES
     */
    @Override
    public boolean MOVE(Box box_from, Box box_to){
        if (!Arrays.equals(push_position, invalid)) {
            if (box_to.VERTICAL == push_position[0] && box_to.HORIZONTAL == push_position[1]){
                if(box_from.on_box.power > power_of_pushed){return Match_PUSH(box_from,box_to);}
                LOGGER.info(box_from.on_box.name + " do not have enough power, need more than " + power_of_pushed);
                return false;
            }else{
                LOGGER.info("Move must match PUSH attempt");
                return false;
            }
        }

        if (box_from.Frozen){
            LOGGER.info("Cant move from "+ box_from.VERTICAL +"|"+ box_from.HORIZONTAL +" due to freeze");
            return false;
        }
        if(box_from.on_box.name == Figure_type.BUNNY){
            int[] coorstrue = box_from.legal_moves[3];
            int[] coorsfalse = box_from.legal_moves[2];
            if (box_from.on_box.color){
                if (coorstrue[0] == box_to.VERTICAL && coorstrue[1] == box_to.HORIZONTAL){
//                    LOGGER.info("Figure BUNNY can not go backwards");
                    return false;
                }
            }else{
                if (coorsfalse[0] == box_to.VERTICAL && coorsfalse[1] == box_to.HORIZONTAL){
//                    LOGGER.info("Figure BUNNY can not go backwards");
                    return false;
                }
            }
        }

        move_from_to(box_from, box_to);
        power_of_last = box_to.on_box.power;
        return true;
    }

    @Override
    public void move_from_to(Box box_from, Box box_to){
        for (int[] coord: box_from.legal_moves) {
            if(coord[0] == box_to.VERTICAL && coord[1] == box_to.HORIZONTAL) {
                if (validate_isFree(box_to)) {
                    play_desk[box_to.VERTICAL][box_to.HORIZONTAL].setOn_box(box_from.on_box);
                    box_from.unset_box();

                    int[] last = {box_from.VERTICAL, box_from.HORIZONTAL};
                    setLast_move(last);
                    return;
                } else {
//                    LOGGER.info("Cant move, target box " + box_to.VERTICAL + "|" + box_to.HORIZONTAL + " is not empty");
                    return;
                }
            }
        }
//        LOGGER.info("Move is not legal");
    }

    @Override
    public boolean MOVE_OPONENT(Box box_from, Box box_to) {
        if(!Arrays.equals(push_position, invalid)){return false;}
        if(Arrays.equals(last_move, invalid)){return PUSH(box_from,box_to);
        }
        if (box_to.VERTICAL == last_move[0] && box_to.HORIZONTAL == last_move[1]) {
            return PULL(box_from,box_to);
        }else{
            return PUSH(box_from,box_to);
        }
    }

        @Override
        public boolean PULL(Box box_from, Box box_to) {
            if(power_of_last > box_from.on_box.power){
            move_from_to(box_from, box_to);
            setLast_move(invalid);
            LOGGER.info("PULL MATCHED");
            return true;
            }
            LOGGER.info(power_of_last+ " is not enough power to move " + box_from.on_box.name + ", need more than " + box_from.on_box.power );
            return false;
        }
        @Override
        public boolean PUSH(Box box_from, Box box_to) {
            LOGGER.info("PUSH attempt");

            power_of_pushed = box_from.on_box.power;

            move_from_to(box_from, box_to);

            int[] last = {box_from.VERTICAL, box_from.HORIZONTAL};
            setPush_position(last);

            setLast_move(invalid);
            return true;
        }
            public boolean Match_PUSH(Box box_from, Box box_to){
                LOGGER.info("Matching PUSH attempt");
                move_from_to(box_from, box_to);
                setPush_position(invalid);
                setLast_move(invalid);
                return true;
            }



    /**
     * Validators / Checks
     */
    @Override
    public Boolean validate_isFree(Box box) {
        return box.on_box == null;
    }

    @Override
    public Boolean did_bunny_cross() {
        for (Box box:play_desk[0]) {
            if(B_Bunny.equals(box.on_box)){
                return false;
            };
        }
        for (Box box:play_desk[7]) {
            if(W_Bunny.equals(box.on_box)){
                return true;
            };
        }
        return null;
    }


    /**
     * Modification
     * changing variables of board od individual boxes
     */


    @Override
    public void set_trap_for() {
        int i = 0;
        int[] illegal = {2,5};
        boolean[] result = {false,false};
        for (int y: illegal){
            for (int x: illegal){
                list_of_trap_safty[i][0] = for_white(play_desk[y][x]);
                list_of_trap_safty[i][1] = for_black(play_desk[y][x]);
                i++;
            }
        }
    }
        private boolean for_white(Box trap_box){
            int[][] legal = trap_box.legal_moves;
            for (int[] coord : legal) {
                if(play_desk[coord[0]][coord[1]].on_box != null){
                    if(play_desk[coord[0]][coord[1]].on_box.color){
                        return false;
                    }
                }
            }
            return true;
        }
        private boolean for_black(Box trap_box){
            int[][] legal = trap_box.legal_moves;
            for (int[] coord : legal) {
                if(play_desk[coord[0]][coord[1]].on_box != null){
                    if(!play_desk[coord[0]][coord[1]].on_box.color){
                        return false;
                    }
                }
            }
            return true;
        }

    @Override
    public Boolean[] check_traps() {
        Boolean[] removed = {null, null, null, null};
        for (int i = 0; i < 4; i++) {
            boolean[] safty_state_of_trap = list_of_trap_safty[i];
            switch (i) {
                case (0):
                    removed[0] = remove_from_trap(play_desk[2][2], safty_state_of_trap);
                    break;
                case (1):
                    removed[1] = remove_from_trap(play_desk[2][5], safty_state_of_trap);
                    break;
                case (2):
                    removed[2] = remove_from_trap(play_desk[5][2], safty_state_of_trap);
                    break;
                case (3):
                    removed[3] = remove_from_trap(play_desk[5][5], safty_state_of_trap);
                    break;
            }
        }
        return removed;
    }
        private Boolean remove_from_trap(Box trap, boolean[] safty_state_of_trap){
        if(trap.on_box == null){
//            LOGGER.info(trap.VERTICAL+" / "+trap.HORIZONTAL + "   Trap is empty");
            return null;}

        boolean color = trap.on_box.color;

        //IF White
        if(color) {
            if (color == safty_state_of_trap[0]){
                play_desk[trap.VERTICAL][trap.HORIZONTAL].unset_box();
                LOGGER.info(trap.VERTICAL+" / "+trap.HORIZONTAL + "    White figure removed");
                return true;
            }
//                LOGGER.info(trap.VERTICAL+" / "+trap.HORIZONTAL + "    Trap is safe for white");
        //IF Black
        }else{
            if (color != safty_state_of_trap[1]){
                play_desk[trap.VERTICAL][trap.HORIZONTAL].unset_box();
                LOGGER.info(trap.VERTICAL+" / "+trap.HORIZONTAL + "   Black figure removed");
                return false;
            }
//                LOGGER.info(trap.VERTICAL+" / "+trap.HORIZONTAL + "   Trap is safe for black");
        }
        return null;
        }


    @Override
    public void check_freeze() {
        for (Box[] row : play_desk) {
            for (Box box: row) {
                if (box.on_box != null){
                    if(!box_freeze_check(box)){
                        box.un_Freeze();
                    }else{
                        box.freeze();
                    }
                }
            }
        }
    }
        private boolean box_freeze_check(Box box){
        int[][] legal = box.legal_moves;
        boolean state = false;
        for (int[] coord : legal) {
            if (coord[0] != -1) {
                if (play_desk[coord[0]][coord[1]].on_box != null) {
                    if (play_desk[coord[0]][coord[1]].on_box.color == box.on_box.color) {
                        return false;
                    } else if (play_desk[coord[0]][coord[1]].on_box.power > box.on_box.power) {
                        state = true;
                    }
                }
            }
        }
        return state;
    }

    public int[][] curent_state(){
        int[][] board = {{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
                {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
                {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
                {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1}};
        int W_Dog = 0;
        int W_Cat = 0;
        int W_Horse = 0;
        int W_Bun = 0;
        int B_Dog = 0;
        int B_Cat = 0;
        int B_Horse = 0;
        int B_Bun = 0;

            Figure current;
            for (Box[] row: play_desk) {
                for (Box box: row){
                    if(box.on_box != null){
                        current = box.on_box;
                        int[] acc = {box.VERTICAL,box.HORIZONTAL};

                        if (current instanceof Elephant){
                            if (current.color){
                                board[0] = acc;
                            }else{
                                board[16]=acc;
                            }
                        }
                        if (current instanceof Camel){
                            if (current.color){
                                board[1] = acc;
                            }else{
                                board[17]=acc;
                            }
                        }
                        if (current instanceof Horse){
                            if (current.color){
                                board[2+W_Horse] = acc;
                                    W_Horse++;
                            }else{
                                board[18+B_Horse]=acc;
                                    B_Horse++;
                            }
                        }
                        if (current instanceof Dog){
                            if (current.color){
                                board[4 + W_Dog] = acc;
                                    W_Dog++;
                            }else{
                                board[20 + B_Dog]=acc;
                                    B_Dog++;
                            }
                        }
                        if (current instanceof Cat){
                            if (current.color){
                                board[6 + W_Cat] = acc;
                                    W_Cat++;
                            }else{
                                board[22 + B_Cat]=acc;
                                    B_Cat++;
                            }
                        }
                        if (current instanceof Bunny){
                            if (current.color){
                                board[8 + W_Bun] = acc;
                                W_Bun++;
                            }else{
                                board[24 + B_Bun]=acc;
                                B_Bun++;
                            }
                        }
                    }else continue;
                }
            }



        return board;
    }



}
