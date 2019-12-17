import java.util.ArrayList;
import java.util.List;

public class Column extends Logic{

    Column(Cell[][] cell, int j) {
        for (int i = 0; i < boardSize; i++) {
            unit[i] = cell[i][j];
        }
    }


}
