import java.util.ArrayList;

public class Logic extends Sudoku {

    ArrayList<Cell> unit = new ArrayList<Cell>();



    boolean isCorrect() {
        ArrayList<Integer> retArray = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            if (unit.get(i).getValue() != 0) retArray.add(unit.get(i).getValue());
        }
        for (int j = 0; j < retArray.size(); j++) {
            for (int k = j + 1; k < retArray.size(); k++) {
                if (retArray.get(k).equals(retArray.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    Cell getCell(int index) {
        return unit.get(index);
    }
}
