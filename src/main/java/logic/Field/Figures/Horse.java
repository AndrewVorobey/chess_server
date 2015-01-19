package logic.Field.Figures;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * Created by andrew on 31.12.14.
 */
public class Horse extends Figure {
    public Horse(Color color) {
        super(color);
    }

    @Override
    public boolean CanIGo(Point from, Point to) {
        if (abs(from.x - to.x) + abs(from.y - to.y) == 3
                && abs(from.x - to.x) != 0
                && abs(from.y - to.y) != 0) {
            if (owner.owner.at(to.y, to.x).isEmpty()) {
                return true;
            } else if (owner.owner.at(to.y, to.x).getFigure().color != color) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public Names WhoAmI() {
        return Names.HORSE;
    }

}
