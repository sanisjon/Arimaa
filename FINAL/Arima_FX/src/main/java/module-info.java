module cz.cvut.fel.gui.arima_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    requires json.simple;



    opens cz.cvut.fel.arima_fx to javafx.fxml;
    exports cz.cvut.fel.arima_fx;
    exports cz.cvut.fel.arima_fx.GAME_save;
    opens cz.cvut.fel.arima_fx.GAME_save to javafx.fxml;
}