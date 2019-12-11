import java.util.ArrayList;
import java.util.List;

public class Block extends Logic{

    Block(Cell[][] cell, int val) {
        for(int i = 0; i < 9; i++) {
            unit[i] = cell[i / 3 + 3 * (val / 3)][3 * val % 9 + i % 3];
        }
    }


}
