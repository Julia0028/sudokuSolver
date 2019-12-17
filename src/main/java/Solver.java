import java.util.*;

class Solver extends Sudoku {


    private Row[] rows = new Row[boardSize];
    private Column[] columns = new Column[boardSize];
    private Block[] blocks = new Block[boardSize];
    private Candidate[][] candidates = new Candidate[boardSize][boardSize];


    Solver(Sudoku other) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                sudoku[i][j] = other.getSudoku()[i][j];
            }
        }

        updateFields(sudoku);

    }


    private Cell[][] algoLoop() {

        Cell[][] oldSudoku;
        int idx = 0;
        while (!isSudokuComplete()) {
            oldSudoku = copyCell(sudoku);
            idx++;

            System.out.println("------" + idx + "------");
            System.out.println(sudokuToString());

            candidates = formCandidateMatrix();

            System.out.println("---— Candidates —----");
            System.out.println(candidatesToString());

            sudoku = insertHeroes(sudoku);
            updateFields(sudoku);

            System.out.println("----— New board —----");
            System.out.println(sudokuToString());

            candidates = formCandidateMatrix();

            System.out.println("---— Candidates —----");
            System.out.println(candidatesToString());

            candidates = findUnitHeroes(rows);

            System.out.println("---— Row heroes —----");
            System.out.println(candidatesToString());

            sudoku = insertHeroes(sudoku);
            updateFields(sudoku);

            System.out.println("----— New board —----");
            System.out.println(sudokuToString());

            candidates = formCandidateMatrix();

            System.out.println("---— Candidates —----");
            System.out.println(candidatesToString());

            candidates = findUnitHeroes(columns);

            System.out.println("---— Column heroes —----");
            System.out.println(candidatesToString());

            sudoku = insertHeroes(sudoku);
            updateFields(sudoku);

            System.out.println("----— New board —----");
            System.out.println(sudokuToString());

            candidates = formCandidateMatrix();

            System.out.println("---— Candidates —----");
            System.out.println(candidatesToString());

            candidates = findUnitHeroes(blocks);

            System.out.println("---— Block heroes —----");
            System.out.println(candidatesToString());

            sudoku = insertHeroes(sudoku);
            updateFields(sudoku);

            System.out.println("----— New board —----");
            System.out.println(sudokuToString());


            if (equalsSudoku(oldSudoku, sudoku)) {
                sudoku = insertForsedHeroes(sudoku);
                updateFields(sudoku);
            }
        }

        return sudoku;
    }




    private ArrayList<Integer> decrUnit(List<Integer> cand, int i, Logic[] logic) {
        ArrayList<Integer> candidates = new ArrayList<Integer>(cand);
        for (int value = 1; value < 10; value++) {
            Cell[] mas = logic[i].getUnit();
            for (int k = 0; k < boardSize; k++) {
                if (mas[k].getValue() == value) {
                    for (int a = 0; a < candidates.size(); a++) {
                        if (candidates.get(a) == value) candidates.remove(a);
                    }

                }
            }
        }
        return candidates;
    }



    private Candidate[][] findUnitHeroes(Logic[] logic) {
        Candidate[][] cand = copyCandidates(candidates);
        ArrayList<Integer> allCandidates;
        for (int i = 0; i < boardSize; i ++) {
            Cell[] cells = logic[i].getUnit();
            allCandidates = new ArrayList<Integer>();

            for (Cell cell: cells) {
                int row = cell.getNumbRow();
                int column = cell.getNumbColumn();
                allCandidates.addAll(cand[row][column].getCandidates());
            }
            List<Integer> values = isCout(allCandidates);
            for (int val: values) {
                for (Cell cell : cells) {
                    int column = cell.getNumbColumn();
                    int row = cell.getNumbRow();
                    if (cand[row][column].getCandidates().contains(val)) {
                        cand[row][column].getCandidates().clear();
                        cand[row][column].getCandidates().add(val);
                        break;
                    }
                }
            }
        }
        return cand;
    }

    private Candidate[][] formCandidateMatrix () {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int row = sudoku[i][j].getNumbRow();
                int col = sudoku[i][j].getNumbColumn();
                int block = sudoku[i][j].getNumbBlock();
                if (sudoku[row][col].isSetValue())
                    candidates[row][col] = new Candidate(new ArrayList<Integer>());
                else {
                    List<Integer> cand = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
                    cand = decrUnit(cand, row, rows);
                    cand = decrUnit(cand, col, columns);
                    cand = decrUnit(cand, block, blocks);
                    candidates[i][j] = new Candidate(cand);

                }
            }
        }
        return candidates;
    }



    private Cell[][] insertHeroes(Cell[][] cells) {
        Cell[][] cells1 = copyCell(cells);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (candidates[i][j].getCountOfCandidates() == 1)
                    cells1[i][j].setValue(candidates[i][j].getCandidates().get(0));
            }
        }
        return cells1;
    }

    private Cell[][] insertForsedHeroes(Cell[][] cells) {
        Cell[][] cells1 = copyCell(cells);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (candidates[i][j].getCandidates().size() > 1) {
                    cells1[i][j].setValue(candidates[i][j].getCandidates().get(0));
                    return cells1;
                }
            }
        }
        return cells1;
    }


    private boolean equalsSudoku(Cell[][] cell, Cell[][] cell1) {
        for (int i = 0; i < boardSize; i++) {
            for (int j =0; j < boardSize; j++) {
                if (cell[i][j].getValue() != cell1[i][j].getValue())
                    return false;
            }
        }
        return true;
    }



    private boolean isSudokuComplete(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (!sudoku[i][j].isSetValue())
                    return false;
            }
        }
        return true;
    }

    String solve() {
        String s = "";
        Cell[][] res = algoLoop();
        if (!isSolutionCorrect()) return "failed to get sudoku solution";
        for (int i =0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                s = s + res[i][j].getValue() + " ";
                if (j == 8) s += "\n";
            }
        }
        return s;
    }

    private boolean isSolutionCorrect() {
        for (int i = 0; i < boardSize; i++) {
            boolean isCorrectRow = rows[i].isCorrect();
            boolean isCorrectColumn = columns[i].isCorrect();
            boolean isCorrectBlock = blocks[i].isCorrect();

            if (!isCorrectBlock || !isCorrectRow || !isCorrectColumn) return false;
        }

        return true;
    }


    private void updateFields(Cell[][] cells) {
        for (int i = 0; i < boardSize; i++) {
            this.rows[i] = new Row(cells, i);
            this.columns[i] = new Column(cells, i);
            this.blocks[i] = new Block(cells, i);
        }
    }

    private List<Integer> isCout(ArrayList<Integer> list) {
        List<Integer> res = new ArrayList<Integer>();
        int[] mas = new int[10];
        for (int i = 0; i < list.size(); i++) {
            int ind = list.get(i);
            switch(ind){
                case 0: mas[ind]++;
                    break;
                case 1: mas[ind]++;
                    break;
                case 2: mas[ind]++;
                    break;
                case 3: mas[ind]++;
                    break;
                case 4: mas[ind]++;
                    break;
                case 5: mas[ind]++;
                    break;
                case 6: mas[ind]++;
                    break;
                case 7: mas[ind]++;
                    break;
                case 8: mas[ind]++;
                    break;
                case 9: mas[ind]++;
                    break;
            }
        }
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] == 1) {
                res.add(i);
            }
        }
        return res;
    }




    private Candidate[][] copyCandidates(Candidate[][] first) {
        Candidate copy[][] = new Candidate[9][9];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ArrayList<Integer> cand = first[i][j].getCandidates();
                copy[i][j] = new Candidate(cand);
            }
        }
        return copy;
    }

    private Cell[][] copyCell(Cell[][] first) {
        Cell copy[][] = new Cell[9][9];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {

                int row = first[i][j].getNumbRow();
                int coloumn = first[i][j].getNumbColumn();
                int bl = first[i][j].getNumbBlock();
                int value = first[i][j].getValue();
                copy[i][j] = new Cell(row, coloumn, bl, value);
            }
        }
        return copy;
    }


    private String sudokuToString() {
        String s = "";
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                s = s + sudoku[i][j].getValue() + " ";
                if (j == 8) s += "\n";
            }
        }
        return s;
    }

    private String candidatesToString() {
        String s = "";
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                s = s + candidates[i][j].getCandidates() + "   ";
                if (j == 8) s += "\n\n";

            }
        }
        return s;
    }
}
