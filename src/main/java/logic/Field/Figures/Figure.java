package logic.Field.Figures;

import logic.Field.Cell;

import java.awt.*;

/**
 * Created by andrew on 31.12.14.
 */
public abstract class Figure {
    public Cell owner;
    public final static Color whiteColor = Color.white;
    public final static Color blackColor = Color.black;

    public static enum Names {PAWN, HORSE, ROOK, ELEPHANT, QUEEN, KING}

    Color color;

    public Figure(Color color) {
        this.color = color;
    }

    public abstract boolean CanIGo(Point from, Point to);

    public Color getColor() {
        return color;
    }

    public abstract Names WhoAmI();

    public String toString(){
        return String.valueOf(WhoAmI()) + "isWhite:" + (color == whiteColor);
    }

    public static Figure make(String str){
        String[] splited = str.split("isWhite:");
        Color color = (splited[1] == String.valueOf(true)) ? whiteColor:blackColor;
        if(splited[0] == String.valueOf(Names.PAWN)) {
            return new Pawn(color);
        } else if(splited[0] == String.valueOf(Names.ELEPHANT)) {
            return new Elephant(color);
        } else if(splited[0] == String.valueOf(Names.HORSE)) {
            return new Horse(color);
        } else if(splited[0] == String.valueOf(Names.KING)) {
            return new King(color);
        } else if(splited[0] == String.valueOf(Names.QUEEN)) {
            return new Queen(color);
        } else if(splited[0] == String.valueOf(Names.ROOK)) {
            return new Rook(color);
        } else return null;
    }

}
