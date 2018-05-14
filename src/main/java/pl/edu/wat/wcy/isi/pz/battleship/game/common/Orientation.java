package pl.edu.wat.wcy.isi.pz.battleship.game.common;

import lombok.Getter;

@Getter
public enum Orientation {
    TOP(0.0, false), RIGHT(90.0, true), BOTTOM(180.0, false), LEFT(270.0, true);

    private double rotate;

    private Orientation next;

    private boolean vertical;

    static {
        TOP.next = RIGHT;
        RIGHT.next = BOTTOM;
        BOTTOM.next = LEFT;
        LEFT.next = TOP;
    }

    Orientation(double rotate, boolean vertical) {
        this.rotate = rotate;
        this.vertical = vertical;
    }
}