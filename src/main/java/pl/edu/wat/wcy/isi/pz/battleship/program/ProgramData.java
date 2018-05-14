package pl.edu.wat.wcy.isi.pz.battleship.program;

import lombok.Data;

@Data
public class ProgramData {
    private final int size;
    private final double side;

    public ProgramData(int size, double side) {
        this.size = size;
        this.side = side;
    }
}
