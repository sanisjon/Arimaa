package cz.cvut.fel.arima_fx;

import cz.cvut.fel.arima_fx.Board.Box;
import cz.cvut.fel.arima_fx.Enums.Figure_type;
import cz.cvut.fel.arima_fx.Figures.Bunny;
import cz.cvut.fel.arima_fx.Figures.Figure;
import cz.cvut.fel.arima_fx.GAME_save.Game_saver;
import cz.cvut.fel.arima_fx.GAME_save.Geme_reader;
import cz.cvut.fel.arima_fx.GAME_save.game;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Arima_Game {
    public static Boolean currentPlayer;
    public int move_count;


    public static Box currentSquare;
    public static Figure currentFigure;


    private static Box ToGoR = null;
    private static Box ToGoL = null;
    private static Box ToGoUp = null;
    private static Box ToGoDown = null;
    private static Box[] ToGoList = {ToGoR,ToGoL,ToGoUp,ToGoDown};


    public static Arima_GUI ArimaGUI;
    public static Game_saver GameSAVER;
    public static Geme_reader GameREADER;



    public VBox Eliminated_GOLD;
    public VBox Eliminated_SILVER;
    public StackPane TopBar;
    public HBox Prew_Next = new HBox();
        public Button prew;
        public Button next;
        public Button set;


    public static Arima_GUI_mini Arima_mini;
    private int back_count = 0;
    public List<game> list_of_moves;


    public boolean game_won;
    public boolean WINNER;

    private static final Logger LOGGER = Logger.getLogger(Arima_Game.class.getName());

//############################### CONSTRUCTOR ###########################################
    public Arima_Game(GridPane grid, VBox Eliminated_GOLD, VBox Eliminated_SILVER, StackPane TopBar, int[][] start_setup) {
        this.game_won = true;

        ArimaGUI = new Arima_GUI(grid, start_setup);
        Arima_mini = new Arima_GUI_mini();

        GameSAVER = new Game_saver();
        GameREADER = new Geme_reader();

        this.Eliminated_GOLD = Eliminated_GOLD;
            Eliminated_GOLD.setPrefWidth(100);
        this.Eliminated_SILVER = Eliminated_SILVER;
            Eliminated_SILVER.setPrefWidth(100);
        this.TopBar = TopBar;
            TopBar.setPrefHeight(120);

        currentSquare = null;
        currentFigure = null;

        currentPlayer = true;

        this.move_count = 0;


        addEventHandlers(ArimaGUI.Arimaa_board);
        create_PrewNext();
        addEventHandlersButton();

        show_currentPlayer();

        list_of_moves = read();
    }



//HANDLERS
    private void addEventHandlers(GridPane grid) {
        grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EventTarget eventTarget = event.getTarget();
                resetSelects();
//                Clicked on square
                if(eventTarget.toString().equals("Box")){
                    Box square = (Box) eventTarget;
//                    LOGGER.info("Click box");

                    if(square.on_box != null){

                       setCurents(square);

                       selectPiece(game_won);
                    }
                }else if (eventTarget instanceof ImageView){

                    // Clicked on Figure
                    ImageView eventTarget2 = (ImageView) eventTarget;
                    Box square = (Box) eventTarget2.getParent();
//
//                    LOGGER.info("Click figure");
                    if(square.on_box != null){
                        setCurents(square);

                        selectPiece(game_won);
                    }
                }else{LOGGER.info("Click out");}
            }
        });
    }
        private void setCurents(Box square){
            currentFigure = (Figure) square.on_box;
            currentSquare = square;
            LOGGER.info("Current figure: "+ currentFigure.name + " on " + currentSquare.VERTICAL + " / " + currentSquare.HORIZONTAL);
        }
    private void addEventHandlersSelected(GridPane grid) {
        grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EventTarget eventTarget = event.getTarget();
                // Clicked on square
                if(eventTarget.toString().equals("Box")){
                    Box square = (Box) eventTarget;
                    if (square.on_box == null){
                        for (Box togo: ToGoList){
                            if (square.equals(togo)){
                                LOGGER.info("Select box to go: " + square.VERTICAL+" | "+ square.HORIZONTAL );
                                if (Move(square)){
                                    move_count++;

                                    System.out.println("After MOVE before SAVE - LM: "+Arrays.toString(ArimaGUI.Model.last_move) + " PP: " + Arrays.toString(ArimaGUI.Model.push_position) + " Pop: " +ArimaGUI.Model.power_of_pushed+ " PoL: " +ArimaGUI.Model.power_of_last);

                                    save();
                                    list_of_moves = read();
                                    Arima_mini.update_mini_board(list_of_moves.get(list_of_moves.size()-1).playDesk);

                                    if (move_count == 4){
                                            currentPlayer =  (currentPlayer ?  false : true);
                                            move_count = 0;
                                            show_currentPlayer();
                                        }

                                    resetSelects();
                                    checkers();
                                    addEventHandlers(ArimaGUI.Arimaa_board);
                                    return;
                                }else{
                                    resetSelects();
                                    checkers();
                                    addEventHandlers(ArimaGUI.Arimaa_board);
                                    return;
                                }

                            }
                        }
                        LOGGER.info("Not possible move");
                    }
                }else{LOGGER.info("Click out of select");
                    resetSelects();
                    addEventHandlers(ArimaGUI.Arimaa_board);
                }
            }
        });
    }



//SELECTORS
    private void selectPiece(boolean game){
        System.out.println("On SELECT before MOVE - LM: "+Arrays.toString(ArimaGUI.Model.last_move) + " PP: " + Arrays.toString(ArimaGUI.Model.push_position) + " Pop: " +ArimaGUI.Model.power_of_pushed+ " PoL: " +ArimaGUI.Model.power_of_last);

        if(!game){
            currentFigure = null;
            currentSquare = null;
            return;
        }
        currentSquare.getStyleClass().clear();
        currentSquare.getStyleClass().add("selected");

        setToGoList();
        showPosibleMoves();
        addEventHandlersSelected(ArimaGUI.Arimaa_board);


    }

        private void setToGoList(){


            for (int i = 0; i < 4; i++) {
                int[] position = currentSquare.legal_moves[i];


                //Move is legal
                if (position[0] == -1){ToGoList[i] = null; continue;}
                //BUNNY can't go back
                ToGoList[i] = getSquareByCoord(position[0],position[1]);
            }

            if(currentFigure instanceof Bunny){
                if (currentFigure.color != currentPlayer){return;}
                if (currentFigure.color){
                    ToGoList[3] = null;
                }else ToGoList[2] = null;
            }

        }
        private void showPosibleMoves(){

            for (Box togo: ToGoList) {

                if(togo != null && togo.on_box == null){
                    togo.getStyleClass().clear();
                    togo.getStyleClass().add("togo");
                }
            }
        }
            private Box getSquareByCoord (int i, int j){
                for (Box square: ArimaGUI.squares) {
                    if (GridPane.getColumnIndex(square) == j && GridPane.getRowIndex(square) == i){
                        return square;
                    }
                }
                return null;
            }


//MOVE
    private boolean Move(Box Square_to) {
//        currentFigure.color == currentPlayer
        if (currentFigure.color == currentPlayer) {

            if(ArimaGUI.Model.MOVE(currentSquare, Square_to)){
                ImageView lastChild = (ImageView) currentSquare.getChildren().get(0);
                Square_to.getChildren().add(lastChild);

                currentSquare.getChildren().clear();
                return true;
            }else return false;

        }else{
            if (move_count == 3){
                LOGGER.info("Can't move oponent figure");
                return false;}
            if(ArimaGUI.Model.MOVE_OPONENT(currentSquare, Square_to)){
                ImageView lastChild = (ImageView) currentSquare.getChildren().get(0);
                Square_to.getChildren().add(lastChild);

                currentSquare.getChildren().clear();
                return true;
            }else return false;
        }
    }


//    MODIFICATION
    private void resetSelects(){
        if(currentSquare != null) {
            currentSquare.getStyleClass().clear();
            currentSquare.getStyleClass().add("square");
        }
        for (Box togo: ToGoList) {
            if(togo != null) {
                togo.getStyleClass().clear();
                togo.getStyleClass().add("square");
            }
            togo = null;
        }
        for (Box square: ArimaGUI.squares){
            if (!square.safe){
                square.getStyleClass().add("trap");
            }
        }
        currentSquare = null;
        currentFigure = null;
    }
    private void show_currentPlayer(){

        String player;
        if (currentPlayer){
            player = "Gold";
            this.TopBar.getStyleClass().clear();
            this.TopBar.getStyleClass().add("Gold_topBar");

        }
        else{player = "Silver";
            this.TopBar.getStyleClass().clear();
            this.TopBar.getStyleClass().add("Silver_topBar");
        };

        Label TopBarText = new Label(player + " PLAYER IS ON MOVE");
        TopBarText.getStyleClass().add("TopLabel");
        this.TopBar.getChildren().clear();
        this.TopBar.getChildren().add(TopBarText);
    }

//    CHECK
    private void checkers(){
        ArimaGUI.Model.set_trap_for();
        check_traps_GUI();

        ArimaGUI.Model.check_freeze();
        check_freeze_GUI();

        if(ArimaGUI.Model.did_bunny_cross() != null){
            this.game_won = false;
            WINNER = ArimaGUI.Model.did_bunny_cross();
            String Winer_color = (WINNER? "Gold" : "Silver");
            Alert winDialog = new Alert(Alert.AlertType.INFORMATION);
            winDialog.setContentText(Winer_color + " WON!");
            winDialog.showAndWait();
        }
        if(are_there_any_bunny() != null){
            this.game_won = false;
            WINNER = are_there_any_bunny();
            String Winer_color = (WINNER? "Gold" : "Silver");
            Alert winDialog = new Alert(Alert.AlertType.INFORMATION);
            winDialog.setContentText(Winer_color + " WON!");
            winDialog.showAndWait();
        }
    }
        private void check_traps_GUI(){
        Boolean[] removed_list = ArimaGUI.Model.check_traps();

        for (Box square: ArimaGUI.squares) {
            if(!square.safe){
                if (square.VERTICAL == 2){
                    if (square.HORIZONTAL == 2){
                        move_to_removed(square, removed_list[0]);
                    }else{
                        move_to_removed(square, removed_list[1]);
                    }
                }else{
                    if (square.HORIZONTAL == 2){
                        move_to_removed(square, removed_list[2]);
                    }else{
                        move_to_removed(square, removed_list[3]);
                    }
                }
            }
        }

    }
            private void move_to_removed(Box square, Boolean color){
        if (color == null){return;}
        if (color){
            StackPane removed = new StackPane(square.getChildren().get(0));
            square.getChildren().clear();

            this.Eliminated_GOLD.getChildren().add(removed);
        }else{
            StackPane removed = new StackPane(square.getChildren().get(0));
            square.getChildren().clear();

            this.Eliminated_SILVER.getChildren().add(removed);
        }
    }
        private void check_freeze_GUI() {
        for (Box square : ArimaGUI.squares) {
            if (square.Frozen && square.on_box != null) {

                square.getStyleClass().clear();
                square.getStyleClass().add("Freeze");

            } else if (!square.Frozen && square.safe){
                square.getStyleClass().clear();
                square.getStyleClass().add("square");
            }
        }
    }

        private Boolean are_there_any_bunny(){
            boolean gold = false;
            boolean silver = false;
            for (Box square: ArimaGUI.squares) {
                if(gold && silver){return null;}
                if (square.on_box != null && square.on_box.name == Figure_type.BUNNY){
                    if(square.on_box.color){gold = true;}else{silver = true;}
                }
            }
            if(gold && silver){return null;}
            if (!gold){return false;}
            if (!silver){return true;}
            return null;
        }


//    SAVE AND READ
    public void save(){
        GameSAVER.create_new_move(currentPlayer, move_count, ArimaGUI.Model.curent_state(), ArimaGUI.Model.last_move,
                                  ArimaGUI.Model.power_of_pushed,ArimaGUI.Model.power_of_last,ArimaGUI.Model.push_position);
    }
    public List<game> read(){ return GameREADER.read_games();}



//    CONTROLER
    public void create_PrewNext(){
        Prew_Next = new HBox();
        prew = new Button("< Prew");
            prew.getStyleClass().add("pre");
            prew.setPrefSize(95,50);
        set = new Button();
            set.getStyleClass().add("set");
            set.setPrefSize(50,50);
        next = new Button("Next >");
            next.getStyleClass().add("next");
            next.setPrefSize(95,50);

        Prew_Next.getChildren().add(prew);
        Prew_Next.getChildren().add(set);
        Prew_Next.getChildren().add(next);
    }

    public game PREW(){
        back_count++;
        if (back_count < list_of_moves.size()){
            game selected = list_of_moves.get((list_of_moves.size()-1)-back_count);
            return selected;
        }
        back_count--;
        return null;
    }
    public game NEXT(){
        back_count--;
        if (back_count > -1){
            game selected = list_of_moves.get((list_of_moves.size()-1)-back_count);
            return selected;
        }
        back_count++;
        return null;
    }
    public void SET(){
        game selected = list_of_moves.get((list_of_moves.size()-1)-back_count);
        currentPlayer = selected.Player;
        move_count = selected.moveCount;

        ArimaGUI.Model.setLast_move(selected.last_move);
        ArimaGUI.Model.setPush_position(selected.push_position);
        ArimaGUI.Model.power_of_pushed = selected.power_of_pushed;
        ArimaGUI.Model.power_of_last = selected.power_of_last;






//        REMOVE REDUNDANT
        for (int i = 0; i < back_count; i++) {
            list_of_moves.remove(list_of_moves.size()-1);
        }
//        SAVE
        GameSAVER.save_from_list(list_of_moves);

        ArimaGUI.update_BIG_board(selected.playDesk);
        back_count = 0;
    }


    public void addEventHandlersButton() {
        prew.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                game current = PREW();

                if (current != null) {
                    Arima_mini.update_mini_board(current.playDesk);
                }
            }
        });
        set.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SET();
                checkers();
                show_currentPlayer();
                addEventHandlers(ArimaGUI.Arimaa_board);
            }
        });
        next.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                game current = NEXT();

                if (current != null) {
                    Arima_mini.update_mini_board(current.playDesk);
                }
            }
        });
    }





}