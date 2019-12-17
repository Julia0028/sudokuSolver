import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Logic extends Sudoku {

    Cell[] unit = new Cell[boardSize];

    Cell[] getUnit() {
        return unit;
    }

    boolean isCorrect() {
        HashSet set = new HashSet();
        for (int i = 0; i < 9; i++) {
            set.add(unit[i].getValue());
        }
        if (set.size() != 9) return false;
        return true;
    }



}
