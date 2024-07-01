package cz.cvut.fel.arima_fx.GAME_save;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game_saver {
    public ArrayList<game> Games = new ArrayList<>();
    public JSONArray list_of_games = new JSONArray();

    public void create_new_move(boolean player, int moveCount, int[][] playDesk,int[] last_move, short power_of_pushed, short power_of_last, int[] push_position ){
        game game = new game(player, moveCount, playDesk, last_move, power_of_pushed, power_of_last, push_position);

        JSONObject object = new JSONObject();
            object.put("Player", String.valueOf(game.Player));
            object.put("Moves", String.valueOf(game.moveCount));
            object.put("Desk", Arrays.deepToString(game.playDesk));
            object.put("last_move", Arrays.toString(game.last_move));
            object.put("power_of_pushed", String.valueOf(game.power_of_pushed));
            object.put("power_of_last", String.valueOf(game.power_of_last));
            object.put("push_position", Arrays.toString(game.push_position));


        list_of_games.add(object);

        String folderPath = "games/";
        String fileName = "game_list.json";

        File directory = new File(folderPath);
        if (! directory.exists()){
            directory.mkdir();
        }

        try{
                BufferedWriter writer = new BufferedWriter(new FileWriter(folderPath + fileName));
                writer.write(list_of_games.toString());
                writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void save_from_list(List<game> list){
        list_of_games.clear();
        for (game g:list) {
            JSONObject object = new JSONObject();
            object.put("Player", String.valueOf(g.Player));
            object.put("Moves", String.valueOf(g.moveCount));
            object.put("Desk", Arrays.deepToString(g.playDesk));
            object.put("last_move", Arrays.toString(g.last_move));
            object.put("power_of_pushed", String.valueOf(g.power_of_pushed));
            object.put("power_of_last", String.valueOf(g.power_of_last));
            object.put("push_position", Arrays.toString(g.push_position));

            list_of_games.add(object);
        }
        String folderPath = "games/";
        String fileName = "game_list.json";

        File directory = new File(folderPath);
        if (! directory.exists()){
            directory.mkdir();
        }

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(folderPath + fileName));
            writer.write(list_of_games.toString());
            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


