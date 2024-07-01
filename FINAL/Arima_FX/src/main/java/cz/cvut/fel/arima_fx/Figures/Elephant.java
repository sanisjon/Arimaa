package cz.cvut.fel.arima_fx.Figures;

import cz.cvut.fel.arima_fx.Enums.Figure_type;

public class Elephant extends Figure {
    public Elephant(boolean color) {
        this.color = color;
        this.name = Figure_type.ELEPHANT;
        this.power = 6;
        setImg();
    }

}
