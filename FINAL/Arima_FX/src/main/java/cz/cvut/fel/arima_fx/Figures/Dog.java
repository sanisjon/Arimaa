package cz.cvut.fel.arima_fx.Figures;


import cz.cvut.fel.arima_fx.Enums.Figure_type;

public class Dog extends Figure {
    public Dog(boolean color) {
        this.color = color;
        this.name = Figure_type.DOG;
        this.power = 3;
        setImg();
    }
}
