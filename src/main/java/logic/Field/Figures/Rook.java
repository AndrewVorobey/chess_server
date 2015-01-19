package logic.Field.Figures;

import java.awt.*;

/**
 * Created by andrew on 31.12.14.
 */
public class Rook extends Figure {
    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean CanIGo(Point from, Point to) {
        if (from.y == to.y) {
            int delta = (from.x < to.x) ? 1 : -1;
            for (int i = from.x + delta; i != to.x; i += delta) {
                if (!owner.owner.at(to.y, i).isEmpty())
                    return false;
            }
            if (owner.owner.at(to.y, to.x).isEmpty()
                    || owner.owner.at(to.y, to.x).getFigure().color != color) {
                return true;
            } else
                return false;

        }
        if (from.x == to.x) {

            int delta = (from.y < to.y) ? 1 : -1;
            for (int i = from.y + delta; i != to.y; i += delta) {
                if (!owner.owner.at(i, to.x).isEmpty())
                    return false;
            }
            if (owner.owner.at(to.y, to.x).isEmpty()
                    || owner.owner.at(to.y, to.x).getFigure().color != color) {
                return true;
            } else {
                return false;
            }

        }

        return false;
    }

    @Override
    public Names WhoAmI() {
        return Names.ROOK;
    }

}
