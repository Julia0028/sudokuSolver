import java.util.ArrayList;
import java.util.List;

public class Block extends Logic{

    Block(Cell[][] cell, int val) {
        for(int i = 0; i < boardSize; i++) {
            unit[i] = cell[i / blockWidth + blockWidth * (val / blockWidth)][blockWidth * val % 9 + i % blockWidth];
        }
    }


}
