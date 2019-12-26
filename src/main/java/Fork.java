import java.util.ArrayList;

public class Fork {

    private Cell[][] savedCells;
    private ArrayList<Cell> usedCandidates = new ArrayList<Cell>();

    Fork(Cell[][] sudoku) {
        savedCells = sudoku;
    }

    public void addCandidate(Cell cell, int candidate) {
        usedCandidates.add(new Cell(cell.getNumbRow(), cell.getNumbColumn(), cell.getNumbBlock(), candidate));
    }


    public ArrayList<Integer> getCheckedCandidates() {
        ArrayList<Integer> candidates = new ArrayList<Integer>();
        for (int i = 0; i < usedCandidates.size(); i++) {
            candidates.add(usedCandidates.get(i).getValue());
        }
        return candidates;
    }

    public Cell[][] getSavedCells() {
        return this.savedCells;
    }

}
