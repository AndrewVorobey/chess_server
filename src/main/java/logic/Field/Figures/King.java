package logic.Field.Figures;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * Created by andrew on 31.12.14.
 */
public class King extends Figure {
    public King(Color color) {
        super(color);
    }

    @Override
    public boolean CanIGo(Point from, Point to) {
        if (abs(from.x - to.x) <= 1 && abs(from.y - to.y) <= 1) {
            if (owner.owner.at(to.y, to.x).isEmpty()
                    || owner.owner.at(to.y, to.x).getFigure().color != color) {
                return true;
            } else
                return false;
        }
        return false;
    }

    @Override
    public Names WhoAmI() {
        return Names.KING;
    }

}
