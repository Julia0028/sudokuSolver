public class Sudoku {

    protected Cell[][] sudoku;

    public Sudoku() {
        sudoku = new Cell[9][9];
    }


    public Sudoku(int[][] board) {
        this();
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

            }
        }
    }

    public Sudoku(Sudoku other) {
        this();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.sudoku[i][j] = other.getSudoku()[i][j];
            }
        }
    }

    public String getSolution() {
        Solver solver = new Solver(this);
        Cell[][] res = solver.solve();
        String s = "";
        for (int i =0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s = s + res[i][j].getValue() + " ";
                if (j == 8) s += "\n";
            }
        }
        return s;
    }

    public Cell[][] getSudoku() {
        return sudoku;
    }
}

