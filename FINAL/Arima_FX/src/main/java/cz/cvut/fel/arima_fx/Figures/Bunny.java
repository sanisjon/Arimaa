package cz.cvut.fel.arima_fx.Figures;

import cz.cvut.fel.arima_fx.Enums.Figure_type;
import javafx.scene.image.Image;

import java.util.Objects;

public class Bunny extends Figure {
    public Bunny(boolean color) {
        this.color = color;
        this.name = Figure_type.BUNNY;
        this.power = 1;
        setImg();
    }
}
