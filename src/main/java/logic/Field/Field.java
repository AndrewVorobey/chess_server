package logic.Field;


import logic.Field.Figures.*;

/**
 * Created by andrew on 31.12.14.
 */
public class Field {
    Field instance;
    public final int size = 8;
    private Cell[][] Cells = new Cell[size][size];
    final public int CellsWidth = 50;
    boolean isWhitMustGo;

    public Field() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cells[i][j] = new Cell(this, i, j, CellsWidth);
            }
        }
        setFiguresOnDefinePositions();
    }

    void setFiguresOnDefinePositions() {

        for (int j = 0; j < size; j++) {
            at(1, j).setFigure(new Pawn(Figure.whiteColor));
            at(size - 2, j).setFigure(new Pawn(Figure.blackColor));
        }

        at(0, size - 5).setFigure(new King(Figure.whiteColor));
        at(size - 1, size - 5).setFigure(new King(Figure.blackColor));

        at(0, size - 4).setFigure(new Queen(Figure.whiteColor));
        at(size - 1, size - 4).setFigure(new Queen(Figure.blackColor));

        at(0, 2).setFigure(new Elephant(Figure.whiteColor));
        at(0, size - 3).setFigure(new Elephant(Figure.whiteColor));
        at(size - 1, 2).setFigure(new Elephant(Figure.blackColor));
        at(size - 1, size - 3).setFigure(new Elephant(Figure.blackColor));

        at(0, 1).setFigure(new Horse(Figure.whiteColor));
        at(0, size - 2).setFigure(new Horse(Figure.whiteColor));
        at(size - 1, 1).setFigure(new Horse(Figure.blackColor));
        at(size - 1, size - 2).setFigure(new Horse(Figure.blackColor));

        at(0, 0).setFigure(new Rook(Figure.whiteColor));
        at(0, size - 1).setFigure(new Rook(Figure.whiteColor));
        at(size - 1, 0).setFigure(new Rook(Figure.blackColor));
        at(size - 1, size - 1).setFigure(new Rook(Figure.blackColor));

    }

    public Cell at(int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size)
            throw new RuntimeException("Invalid index in the field");
        return Cells[row][col];
    }

    public boolean tryToGO(int fromRow, int fromCol, int toRow, int toCol, boolean isWhit) {
        if (isWhitMustGo == isWhit) {
            return at(fromRow, fromCol).tryGoTo(at(toRow, toCol));
        } else {
            return false;
        }
    }
}
