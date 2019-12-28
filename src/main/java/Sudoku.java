import java.util.HashSet;

public class Sudoku {

    Cell[][] sudoku;
    final static int blockWidth = 3;
    final static int boardSize = 9;

    Sudoku() {
        sudoku = new Cell[boardSize][boardSize];
    }


    Sudoku(int[][] board) {
        this();
        int numbBlock = 0;

        sizebBoardValid(board);

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

                valueValid(board[i][j]);
                this.sudoku[i][j] = new Cell(i, j, numbBlock, board[i][j]);

            }
        }
    }

    private void valueValid(int value) {
        if (value < 0 || value > 9) throw new IllegalArgumentException("cells can take values from 0 to 9");
    }

    public Sudoku(Sudoku other) {
        this();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.sudoku[i][j] = new Cell(i, j, other.getCell(i, j).getNumbBlock(), other.getCell(i, j).getValue());
            }
        }
    }

    private void sizebBoardValid(int[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (j == board[0].length - 1 && j != 8) throw new
                       IllegalArgumentException("A column must have 9 cells");
            }

            if (i == board.length - 1 && i != 8) throw new
                    IllegalArgumentException("A row must have 9 cells");
        }
    }



    Sudoku getSolution() {
        Solver solver = new Solver(this);
        if (!solver.boardIsValid()) throw new IllegalArgumentException("input board is incorrect");
        solver.algoLoop();
        return solver;
    }





    Cell getCell(int row, int col) {
        return sudoku[row][col];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                sb.append(sudoku[i][j].getValue());
                sb.append(" ");
                if (j == 8) sb.append("\n");
            }
        }
        return sb.toString();
    }
}

