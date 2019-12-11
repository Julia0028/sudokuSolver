import java.util.ArrayList;
import java.util.List;

public class Row extends Logic{

    Row(Cell[][] cell, int i ) {
        for (int j = 0; j < 9; j++) {
            unit[j] = cell[i][j];
        }
    }

}

