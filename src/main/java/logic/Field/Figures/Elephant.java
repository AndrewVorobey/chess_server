package logic.Field.Figures;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * Created by andrew on 31.12.14.
 */
public class Elephant extends Figure {
    public Elephant(Color color) {
        super(color);
    }

    @Override
    public boolean CanIGo(Point from, Point to) {
        if (abs(from.x - to.x) == abs(from.y - to.y)) {
            int deltaX = to.x > from.x ? 1 : -1;
            int deltaY = to.y > from.y ? 1 : -1;
            int j = from.y + deltaY;
            for (int i = from.x + deltaX; i != to.x; i += deltaX, j += deltaY) {
                if (!owner.owner.at(j, i).isEmpty()) {
                    return false;
                }
            }

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
        return Names.ELEPHANT;
    }

}
