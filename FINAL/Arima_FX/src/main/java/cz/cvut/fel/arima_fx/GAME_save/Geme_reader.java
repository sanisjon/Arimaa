package cz.cvut.fel.arima_fx.GAME_save;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geme_reader {

    public List<game> read_games(){
        JSONParser parser = new JSONParser();

        try {
            // Read the JSON file
            Object games = parser.parse(new FileReader("games/game_list.json"));

            // Convert the JSON object to a JSON array
            JSONArray jsonArray = (JSONArray) games;

            // Create an ArrayList to store the Game objects
            List<game> gameList = new ArrayList<>();

            // Iterate through the JSON array
            for (Object gameObj : jsonArray) {
                JSONObject gameJson = (JSONObject) gameObj;

                // Extract the properties from the JSON object
                boolean player = Boolean.parseBoolean((String) gameJson.get("Player"));
                int moveCount = Integer.parseInt((String) gameJson.get("Moves"));
                String playDesk = (String) gameJson.get("Desk");
                int[][] Desk = convert_to_int(playDesk, parser);

                String LM = (String) gameJson.get("last_move");
                int[] last_move = convert(LM);


                short power_of_pushed = Short.parseShort((String) gameJson.get("power_of_pushed"));
                short power_of_last = Short.parseShort((String) gameJson.get("power_of_last"));

                String PP = (String) gameJson.get("push_position");
                int[] push_position = convert(PP);



//                  Create the Game object and add it to the list
                game game = new game(player, moveCount, Desk, last_move, power_of_pushed, power_of_last,push_position);
                gameList.add(game);
            }
            return gameList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private int[][] convert_to_int (String input, JSONParser parser) throws ParseException {

            JSONArray jsonArray = (JSONArray) parser.parse(input);

            int[][] result = new int[jsonArray.size()][2];

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONArray point = (JSONArray) jsonArray.get(i);
                int x = Integer.parseInt(point.get(0).toString());
                int y = Integer.parseInt(point.get(1).toString());
                result[i][0] = x;
                result[i][1] = y;
            }

            return result;
        }
    private int[] convert (String input){
        input = input.replace("[", "").replace("]", "");

        String[] elements = input.split(",");
        int[] result = new int[elements.length];

        for (int i = 0; i < elements.length; i++) {
            result[i] = Integer.parseInt(elements[i].trim());
        }
        return result;

    }
}


