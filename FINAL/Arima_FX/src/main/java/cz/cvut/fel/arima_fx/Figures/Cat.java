package cz.cvut.fel.arima_fx.Figures;


import cz.cvut.fel.arima_fx.Enums.Figure_type;

public class Cat extends Figure {
    public Cat(boolean color) {
        this.color = color;
        this.name = Figure_type.CAT;
        this.power = 2;
        setImg();
    }
}
