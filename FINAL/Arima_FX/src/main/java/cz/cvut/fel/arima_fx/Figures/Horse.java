package cz.cvut.fel.arima_fx.Figures;


import cz.cvut.fel.arima_fx.Enums.Figure_type;

public class Horse extends Figure {
    public Horse(boolean color) {
        this.color = color;
        this.name = Figure_type.HORSE;
        this.power = 4;
        setImg();
    }
}
