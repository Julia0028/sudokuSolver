import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Solver extends Sudoku{

    private Row[] rows = new Row[boardSize];
    private Column[] columns = new Column[boardSize];
    private Block[] blocks = new Block[boardSize];
    private Candidate[][] candidates = new Candidate[boardSize][boardSize];
    private ArrayList<Fork> savepoints = new ArrayList<Fork>();


        Solver(Sudoku other) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    sudoku[i][j] = new Cell(i, j, other.getCell(i, j).getNumbBlock(), other.getCell(i, j).getValue());
                }
            }

            updateFields(sudoku);

        }


         void algoLoop() {

            Cell[][] oldSudoku;
            int idx = 0;

            while (!isSudokuComplete()) {

                if (!boardIsValid()) {
                 //  System.out.println("conflict sudoku \n" + sudokuToString());
                   //System.out.println("conflict matrix \n" + Candidate.candMatrixToString(candidates));
                    sudoku = loadSavepoint();
                    updateFields(sudoku);
                    sudoku = insertForcedHeroes(sudoku);
                    updateFields(sudoku);

                    System.out.println("---— Candidates —----");
                    System.out.println(candidatesToString());

                }

                oldSudoku = copyCell(sudoku);

                idx++;
                System.out.println("------" + idx + "------");
                System.out.println(sudokuToString());

                candidates = formCandidateMatrix();
                candidates = findOpenVal();

                System.out.println("---— Candidates —----");
                System.out.println(candidatesToString());

                candidates = findUnitHeroes();

                System.out.println("-----Row Col Block Heroes-----");
                System.out.println(candidatesToString());
                sudoku = insertHeroes(sudoku);
                updateFields(sudoku);

                System.out.println("----— New board —----");
                System.out.println(sudokuToString());



                if (equalsSudoku(oldSudoku, sudoku)) {
                    sudoku = insertForcedHeroes(sudoku);
                    updateFields(sudoku);

                    System.out.println("---— Candidates —----");
                    System.out.println(candidatesToString());

                }

            }

        }


    private Candidate[][] findUnitHeroes() {
        Candidate[][] cand = copyCandMatrix(candidates);
        ArrayList<Integer> allCandidates;
        HashSet<Cell> allCells;
        ArrayList<Integer> candOfCells;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (sudoku[i][j].isSetValue()) continue;
                Cell cell = sudoku[i][j];
                allCells = new HashSet<Cell>();
                allCandidates = new ArrayList<Integer>();
                candOfCells = new ArrayList<Integer>();
                candOfCells.addAll(cand[i][j].getCandidates());

                for (int k = 0; k < boardSize; k++) {
                    allCells.add(rows[i].getCell(k));
                    allCells.add(columns[j].getCell(k));
                    allCells.add(blocks[cell.getNumbBlock()].getCell(k));
                }

                for (Cell cell1: allCells) {
                    int row = cell1.getNumbRow();
                    int col = cell1.getNumbColumn();
                    allCandidates.addAll(cand[row][col].getCandidates());
                }
                List<Integer> values = isCout(allCandidates);
                for (int candidate: candOfCells) {
                    if (values.contains(candidate)) {
                        cand[i][j].getCandidates().clear();
                        cand[i][j].getCandidates().add(candidate);
                    }
                }
            }
        }
        return cand;
    }

    private Candidate[][] findOpenVal() {
        Candidate[][] cand = copyCandMatrix(candidates);

        for (int i = 0; i < boardSize; i++) {
            openVal(rows[i], 4, cand);
            openVal(rows[i], 3, cand);
            openVal(rows[i], 2, cand);
            openVal(columns[i], 4, cand);
            openVal(columns[i], 3, cand);
            openVal(columns[i], 2, cand);
            openVal(blocks[i], 4, cand);
            openVal(blocks[i], 3, cand);
            openVal(blocks[i], 2, cand);

        }
        return cand;
    }


        private void openVal(Logic logic, int size, Candidate[][] cand) {
            ArrayList<Cell> cellsToCompare = new ArrayList<Cell>();
            ArrayList<Cell> otherCells = new ArrayList<Cell>();
            for (int i = 0; i < boardSize; i++) {
                Cell cell = logic.getCell(i);
                int row = cell.getNumbRow();
                int col = cell.getNumbColumn();
                if (cand[row][col].getCountOfCandidates() == size) {
                    cellsToCompare.add(cell);
                } else if (cand[row][col].getCountOfCandidates() > size)otherCells.add(cell);
            }


            for (int i = 0; i < cellsToCompare.size(); i++) {

                if (cellIsCount(cellsToCompare, cellsToCompare.get(i), size)) {
                    for (int j = 0; j < otherCells.size(); j++) {
                        longestCommonSubSequence(cand[cellsToCompare.get(i).getNumbRow()][cellsToCompare.get(i).getNumbColumn()],
                                cand[otherCells.get(j).getNumbRow()][otherCells.get(j).getNumbColumn()]);
                    }
                }
            }
        }

        private boolean cellIsCount(ArrayList<Cell> list, Cell cell, int size) {
            int a = 0;
            int row = cell.getNumbRow();
            int col = cell.getNumbColumn();
            for (int i = 0; i < list.size(); i++) {
                if (candidates[list.get(i).getNumbRow()][list.get(i).getNumbColumn()].equals(candidates[row][col]))
                    a++;
            }
            return a >= size;
        }

        private void longestCommonSubSequence(Candidate cand1, Candidate cand2) {
            int len1 = cand1.getCountOfCandidates();
            int len2 = cand2.getCountOfCandidates();
            int[][] len = new int[len1 + 1][len2 + 1];
            for (int i = 1; i < len1 + 1; i++) {
                for (int j = 1; j < len2 + 1; j++) {
                    if (cand1.getCandidates().get(i - 1) != cand2.getCandidates().get(j - 1)) len[i][j] = Math.max(len[i - 1][j], len[i][j - 1]);
                    else len[i][j] = len[i - 1][j - 1] + 1;
                }
            } //O(n*m)
            subSequence(len.length - 1, len[0].length - 1, len, cand1, cand2);
        }


        private static void subSequence(int i, int j, int[][] len, Candidate cand1, Candidate cand2) {
            if (i == 0 || j == 0) return;
            if (cand1.getCandidates().get(i - 1) == cand2.getCandidates().get(j - 1)) {
                cand2.getCandidates().remove(j - 1);
                subSequence(i - 1, j - 1, len, cand1, cand2);
            } else {
                if (len[i - 1][j] >= len[i][j-1]) subSequence(i - 1, j, len, cand1, cand2);
                else subSequence(i, j - 1, len, cand1, cand2);
            }
        }


        private ArrayList<Integer> decrUnit(List<Integer> cand, int i, Logic[] logic) {
            ArrayList<Integer> candidates = new ArrayList<Integer>(cand);
            for (int value = 1; value < 10; value++) {
                for (int k = 0; k < boardSize; k++) {
                    if (logic[i].getCell(k).getValue() == value) {
                        for (int a = 0; a < candidates.size(); a++) {
                            if (candidates.get(a) == value) candidates.remove(a);
                        }

                    }
                }
            }
            return candidates;
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
                        candidates[row][col] = new Candidate(cand);

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

        private void saveCurrentField(Cell[][] cells) {
            Fork fork = new Fork(copyCell(cells));
            savepoints.add(fork);
        }

        private ArrayList<Integer> validCandidates(ArrayList<Integer> candidates) {
            ArrayList<Integer> savedCandidates = new ArrayList<Integer>(savepoints.get(savepoints.size() - 1).getCheckedCandidates());
            ArrayList<Integer> returnCandidates = new ArrayList<Integer>();
            for (int i = 0; i < candidates.size(); i++) {
                if (!savedCandidates.contains((candidates.get(i)))) returnCandidates.add(candidates.get(i));
            }
            return returnCandidates;
        }

        private Cell[][] loadSavepoint() {
            System.out.println("Board load from forcedHeroes");
            System.out.println("Candidates in savepoint: " + savepoints.get(savepoints.size() - 1).getCheckedCandidates());
            System.out.println("Savepoints: " + savepoints.size());
            return copyCell(savepoints.get(savepoints.size() - 1).getSavedCells());
        }

        private void updateSavepoint(Cell cell) {
            savepoints.get(savepoints.size() - 1).addCandidate(cell, cell.getValue());
            System.out.println("Current savepoint updated");
            System.out.println("Cell: [" + cell.getNumbRow() + "][" + cell.getNumbColumn() + "]");
            System.out.println("Candidates in savepoint: " + savepoints.get(savepoints.size() - 1).getCheckedCandidates());
            System.out.println("Savepoints: " + savepoints.size());
        }

        private void deleteSavepoint() {
            savepoints.remove(savepoints.size() - 1);
        }

        private Cell[][] insertForcedHeroes(Cell[][] cells) {
            if (savepoints.size() == 0) saveCurrentField(cells);
            else if (!equalsSudoku(cells, savepoints.get(savepoints.size() - 1).getSavedCells())) saveCurrentField(cells);
            Cell[][] newCells = copyCell(cells);
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (candidates[i][j].getCountOfCandidates() > 1) {
                        ArrayList<Integer> insertCandidates = new ArrayList<Integer>(validCandidates(candidates[i][j].getCandidates()));
                        if (insertCandidates.size() > 0) {
                            newCells[i][j].setValue(insertCandidates.get(0));
                            updateSavepoint(newCells[i][j]);
                            return newCells;
                        } else {
                            System.out.println("No unchecked candidates");
                            deleteSavepoint();
                            return loadSavepoint();
                        }
                    }
                }
            }
            System.out.println("No candidates");
            deleteSavepoint();
            return loadSavepoint();
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



         boolean boardIsValid() {
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


     Candidate[][] copyCandMatrix(Candidate[][] first) {
        Candidate copy[][] = new Candidate[9][9];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ArrayList<Integer> cand = first[i][j].getCandidates();
                copy[i][j] = new Candidate(cand);
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

