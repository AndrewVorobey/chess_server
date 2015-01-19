package logic.Field;

import logic.Field.Figures.Figure;

import java.awt.*;

/**
 * Created by andrew on 31.12.14.
 */
public class Cell {
    static Cell currentCell = null;
    Figure figure;
    int row, col;
    public Field owner;

    public Cell(Field owner, int row, int col, int size) {
        this.row = row;
        this.col = col;
        this.owner = owner;
    }

    public boolean isEmpty() {
        return figure == null;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
        figure.owner = this;
    }

    public Figure getFigure() {
        return figure;
    }

    void selectCurrent() {
        if (this.figure != null) {
            if (currentCell != null)
                currentCell.dropSelection();
            currentCell = this;
        }
    }

    void dropSelection() {
        currentCell = null;
    }

    private boolean canGo(Cell to) {
        return figure.CanIGo(new Point(col, row), new Point(to.col, to.row));
    }

    public boolean tryGoTo(Cell to) {
        if (canGo(to)) {
            to.figure = figure;
            figure = null;
            return true;
        } else {
            return false;
        }

    }


}
