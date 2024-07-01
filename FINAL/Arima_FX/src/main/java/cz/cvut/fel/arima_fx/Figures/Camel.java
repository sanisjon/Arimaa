package cz.cvut.fel.arima_fx.Figures;

import cz.cvut.fel.arima_fx.Enums.Figure_type;

public class Camel extends Figure {
    public Camel(boolean color) {
        this.color = color;
        this.name = Figure_type.CAMEL;
        this.power = 5;
        setImg();
    }
}
