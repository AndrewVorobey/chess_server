package logic.Field.Figures;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * Created by andrew on 31.12.14.
 */
public class Pawn extends Figure {
    boolean moved = false;

    public static enum AllowdMovies {Nothing, GO, EAT, EATandGO}

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean CanIGo(Point from, Point to) {
        AllowdMovies allowdMovies = AllowdMovies.Nothing;
        if (from.y < to.y ^ this.color == this.whiteColor) {
            allowdMovies = AllowdMovies.Nothing;
        } else {
            if (from.x == to.x) {
                if (abs(from.y - to.y) == 1) {
                    if (owner.owner.at(to.y, to.x).isEmpty())
                        allowdMovies = AllowdMovies.GO;
                }
                if (abs(from.y - to.y) == 2 && !moved) {
                    if (owner.owner.at(to.y, to.x).isEmpty()
                            && owner.owner.at((to.y + from.y) / 2, (to.x + from.x) / 2).isEmpty()) {
                        allowdMovies = AllowdMovies.GO;
                    }
                }
            } else if (abs(from.x - to.x) == 1 && abs(from.y - to.y) == 1) {
                if (!owner.owner.at(to.y, to.x).isEmpty()) {
                    if (this.color != owner.owner.at(to.y, to.x).getFigure().color)
                        allowdMovies = AllowdMovies.EAT;
                    else
                        allowdMovies = AllowdMovies.Nothing;
                }
            }
        }

        if (allowdMovies != AllowdMovies.Nothing)
            this.moved = true;

        return allowdMovies != AllowdMovies.Nothing;
    }

    @Override
    public Names WhoAmI() {
        return Names.PAWN;
    }

}
