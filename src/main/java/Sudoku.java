import java.util.HashSet;

public class Sudoku {

    Cell[][] sudoku;
    static int blockWidth = 3;
    static int boardSize = 9;

    Sudoku() {
        sudoku = new Cell[9][9];
    }


    Sudoku(int[][] board) {
        this();
        int numbBlock = 0;

        boardValidation(board);

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

            }
        }
    }

    public Sudoku(Sudoku other) {
        this();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.sudoku[i][j] = other.getSudoku()[i][j];
            }
        }
    }

    private void boardValidation(int[][] board) {
        int sumRow = 0;
        int sumColumn = 0;
        for (int i = 0; i < board.length; i++) {
            sumRow++;
            for (int j = 0; j < board[0].length; j++) {
                int a = board[0].length;
                sumColumn++;
               if (j == board[0].length - 1 && sumColumn != 9) throw new
                       IllegalArgumentException("A column must have 9 cells");
            }
            sumColumn = 0;
            if (i == board.length - 1 && sumRow != 9) throw new
                    IllegalArgumentException("A row must have 9 cells");
        }
    }



    String getSolution() {
        Solver solver = new Solver(this);
        return solver.solve();
    }

    Cell[][] getSudoku() {
        return sudoku;
    }
}

