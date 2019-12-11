import java.util.*;

public class Solver {

    private Cell[][] sudoku = new Cell[9][9];
    private Row[] rows = new Row[9];
    private Column[] columns = new Column[9];
    private Block[] blocks = new Block[9];
    private int openedVal = 0;


    Solver(int[][] board) {
        int numbBlock = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (i < 3) {
                    if (j < 3) numbBlock = 0;
                    if (j > 2 && j < 6) numbBlock = 1;
                    else if (j > 5 ) numbBlock = 2;
                }
                if (i > 2 && i < 6) {
                    if (j < 3) numbBlock = 3;
                    if (j > 2 && j < 6) numbBlock = 4;
                    else if (j > 5 ) numbBlock = 5;
                }
                if (i > 5) {
                    if (j < 3) numbBlock = 6;
                    if (j > 2 && j < 6) numbBlock = 7;
                    else if (j > 5 ) numbBlock = 8;
                }
                this.sudoku[i][j] = new Cell(i, j, numbBlock, board[i][j]);
                if (sudoku[i][j].getValue() != 0) openedVal++;

            }
        }
        for (int i = 0; i < 9; i++){
            this.rows[i] = new Row(this.sudoku, i);
            this.columns[i] = new Column(this.sudoku, i);
            this.blocks[i] = new Block(this.sudoku, i);
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



    private ArrayList<Integer> decrRow(List<Integer> cand, int i) {
        ArrayList<Integer> candidates = new ArrayList<Integer>(cand);
        for (int value = 1; value < 10; value++) {
            Cell[] rowMas = rows[i].getUnit();
            for (int k =0; k < 9; k++) {
                if (rowMas[k].getValue() == value) {
                    for (int a = 0; a < candidates.size(); a++) {
                        if (candidates.get(a) == value) candidates.remove(a);
                    }

                }
            }
        }
        return candidates;
    }

    private ArrayList<Integer> decrColumn(List<Integer> cand, int j) {
        ArrayList<Integer> candidates = new ArrayList<Integer>(cand);
        for (int value = 1; value < 10; value++) {
            Cell[] columnMas = columns[j].getUnit();
            for (int k = 0; k < 9; k++) {
                if (columnMas[k].getValue() == value) {
                    for (int a = 0; a < candidates.size(); a++) {
                        if (candidates.get(a) == value) candidates.remove(a);
                    }
                }
            }
        }
        return candidates;
    }

    private ArrayList<Integer> decrBlock(List<Integer> cand, int bl) {
        ArrayList<Integer> candidates = new ArrayList<Integer>(cand);
        for (int value = 1; value < 10; value++) {
            Cell[] blockMas = blocks[bl].getUnit();
            for (int k =0; k < 9; k++) {
                if (blockMas[k].getValue() == value) {
                    for (int a = 0; a < candidates.size(); a++) {
                        if (candidates.get(a) == value) candidates.remove(a);
                    }

                }
            }
        }
        return candidates;
    }




    private Candidate[][] findRowHeroes(Candidate[][] cand) {
        Candidate[][] candidates = copyArray(cand);
        for (int i = 0; i < 9; i ++) {
            Cell[] cells = rows[i].getUnit();
            ArrayList<Integer> allCandidates = new ArrayList<Integer>();
            for (Cell cell: cells) {
                int column = cell.getNumbColumn();
                allCandidates.addAll(candidates[i][column].getCandidates());
            }

            List<Integer> values = isCout(allCandidates);
            for (int val: values) {
                for (Cell cell : cells) {
                    int column = cell.getNumbColumn();
                    if (candidates[i][column].getCandidates().contains(val)) {
                        candidates[i][column].getCandidates().clear();
                        candidates[i][column].getCandidates().add(val);
                        break;
                    }
                }
            }
        }
        return candidates;
    }

    private Candidate[][] findColumnHeroes(Candidate[][] cand) {
        Candidate[][] candidates = copyArray(cand);
        for (int i = 0; i < 9; i ++) {
            Cell[] cells = columns[i].getUnit();
            ArrayList<Integer> allCandidates = new ArrayList<Integer>();
            for (Cell cell: cells) {
                int row = cell.getNumbRow();
                allCandidates.addAll(candidates[row][i].getCandidates());
            }
            List<Integer> values = isCout(allCandidates);
            for (int val: values) {
                for (Cell cell : cells) {
                    int row = cell.getNumbRow();
                    if (candidates[row][i].getCandidates().contains(val)) {
                        candidates[row][i].getCandidates().clear();
                        candidates[row][i].getCandidates().add(val);
                        break;
                    }
                }
            }
        }
        return candidates;
    }

    private Candidate[][] findBlockHeroes(Candidate[][] cand) {
        Candidate[][] candidates = copyArray(cand);
        for (int i = 0; i < 9; i ++) {
            Cell[] cells = blocks[i].getUnit();
            ArrayList<Integer> allCandidates = new ArrayList<Integer>();
            for (Cell cell: cells) {
                int row = cell.getNumbRow();
                int column = cell.getNumbColumn();
                allCandidates.addAll(candidates[row][column].getCandidates());
            }
            List<Integer> values = isCout(allCandidates);
            for (int val: values) {
                for (Cell cell : cells) {
                    int column = cell.getNumbColumn();
                    int row = cell.getNumbRow();
                    if (candidates[row][column].getCandidates().contains(val)) {
                        candidates[row][column].getCandidates().clear();
                        candidates[row][column].getCandidates().add(val);
                        break;
                    }
                }
            }
        }
        return candidates;
    }

    private Candidate[][] formCandidateMatrix (Cell[][] cells) {
        Candidate[][] candidates = new Candidate[9][9];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                int row = cells[i][j].getNumbRow();
                int col = cells[i][j].getNumbColumn();
                int block = cells[i][j].getNumbBlock();
                if (cells[row][col].isSetValue()) candidates[row][col] = new Candidate(new ArrayList<Integer>());
                else {
                    List<Integer> cand = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
                    cand = decrRow(cand, i);
                    cand = decrColumn(cand, j);
                    cand = decrBlock(cand, block);
                    candidates[i][j] = new Candidate(cand);

                }
            }
        }
        return candidates;
    }


    private Cell[][] insertHeroes(Cell[][] cells, Candidate[][] candidate) {
        Cell[][] cells1 = copyCell(cells);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (candidate[i][j].getCandidates().size() == 1)
                    cells1[i][j].setValue(candidate[i][j].getCandidates().get(0));
            }
        }
        return cells1;
    }

    private Cell[][] insertForsedHeroes(Cell[][] cells, Candidate[][] candidate) {
        Cell[][] cells1 = copyCell(cells);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (candidate[i][j].getCandidates().size() > 1) {
                    cells1[i][j].setValue(candidate[i][j].getCandidates().get(0));
                    return cells1;
                }
            }
        }
        return cells1;
    }


    public boolean equalsSudoku(Cell[][] cell, Cell[][] cell1) {
        for (int i = 0; i < 9; i++) {
            for (int j =0; j < 9; j++) {
                if (cell[i][j].getValue() != cell1[i][j].getValue())
                    return false;
            }
        }
        return true;
    }

    private Cell[][] algoLoop(Cell[][] sudoku) {
        Candidate[][] matrix;
        Cell[][] oldSudoku;
        int idx = 0;
        while (!complete(sudoku)) {
            oldSudoku = copyCell(sudoku);
            idx++;
            matrix = formCandidateMatrix(sudoku);
            sudoku = insertHeroes(sudoku, matrix);
            updateFields(sudoku);

            matrix = formCandidateMatrix(sudoku);
            matrix = findRowHeroes(matrix);
            sudoku = insertHeroes(sudoku, matrix);
            updateFields(sudoku);

            matrix = formCandidateMatrix(sudoku);
            matrix = findColumnHeroes(matrix);
            sudoku = insertHeroes(sudoku, matrix);
            updateFields(sudoku);

            matrix = formCandidateMatrix(sudoku);
            matrix = findBlockHeroes(matrix);
            sudoku = insertHeroes(sudoku, matrix);
            updateFields(sudoku);


           // System.out.println(toStr(sudoku));
            if (equalsSudoku(oldSudoku, sudoku)) {
                sudoku = insertForsedHeroes(sudoku, matrix);
                //System.out.println(toStr(sudoku));
            }
        }

        return sudoku;
    }

    public boolean complete(Cell[][] cell){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!cell[i][j].isSetValue())
                    return false;
            }
        }
        return true;
    }

    public String getSolution() {
        Cell[][] res = algoLoop(sudoku);

        String s = "";
        for (int i =0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s = s + res[i][j].getValue() + " ";
                if (j == 8) s += "\n";
            }
        }
        return s;
    }

    private void updateFields(Cell[][] cells) {
        for (int i = 0; i < 9; i++) {
            this.rows[i] = new Row(cells, i);
            this.columns[i] = new Column(cells, i);
            this.blocks[i] = new Block(cells, i);
        }
    }




    private Candidate[][] copyArray(Candidate[][] copy) {
        Candidate res[][] = new Candidate[9][9];
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy.length; j++) {
                ArrayList<Integer> cand = copy[i][j].getCandidates();
                res[i][j] = new Candidate(cand);
            }
        }
        return res;
    }

    private Cell[][] copyCell(Cell[][] copy) {
        Cell res[][] = new Cell[9][9];
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy.length; j++) {
                int row = copy[i][j].getNumbRow();
                int coloumn = copy[i][j].getNumbColumn();
                int bl = copy[i][j].getNumbBlock();
                int value = copy[i][j].getValue();
                res[i][j] = new Cell(row, coloumn, bl, value);
            }
        }
        return res;
    }


    public String toStr(Cell[][] sudoku) {
        String s = "";
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                s = s + sudoku[i][j].getValue() + " ";
                if (j == 8) s += "\n";
            }
        }
        return s;
    }

    public String toStrCand(Candidate[][] cand) {
        String s = "";
        for (int i = 0; i < cand.length; i++) {
            for (int j = 0; j < cand.length; j++) {
                s = s + cand[i][j].getCandidates() + "   ";
                if (j == 8) s += "\n\n";

            }
        }
        return s;
    }
}
