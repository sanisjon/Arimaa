package cz.cvut.fel.arima_fx.Figures;

import cz.cvut.fel.arima_fx.Enums.Figure_type;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
//    implements Positionable

public abstract class Figure extends ImageView {
    public Figure_type name;
    public short power;
    public boolean color;

    public String figure_img_location;

    public void setImg(){
        this.figure_img_location = name + String.valueOf(color) + ".png";
    }

}
