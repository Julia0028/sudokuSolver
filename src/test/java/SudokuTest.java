import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuTest {

    @Test
    public void solv1() {
        Sudoku sudoku1 = new Sudoku(board1);
        assertEquals(sudoku1.getSolution(), board1Answer);
    }
    @Test
    public void solv2() {
        Sudoku sudoku2 = new Sudoku(board2);
        assertEquals(sudoku2.getSolution(), board2Answer);
    }

    private int[][] board1 = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    private int[][] board2 = {
            {0, 0, 0, 0, 0, 3, 0, 0, 0},
            {0, 0, 8, 1, 9, 0, 3, 6, 7},
            {0, 6, 9, 0, 0, 2, 0, 0, 1},
            {6, 7, 0, 0, 0, 0, 4, 8, 0},
            {0, 3, 5, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 3, 8, 0, 2, 0, 0},
            {0, 4, 0, 0, 5, 1, 0, 0, 0},
            {9, 1, 0, 0, 0, 0, 5, 2, 0},
            {0, 0, 0, 0, 4, 0, 0, 9, 6}
    };

    private String board1Answer =
            "5 3 4 6 7 8 9 1 2 \n" +
            "6 7 2 1 9 5 3 4 8 \n" +
            "1 9 8 3 4 2 5 6 7 \n" +
            "8 5 9 7 6 1 4 2 3 \n" +
            "4 2 6 8 5 3 7 9 1 \n" +
            "7 1 3 9 2 4 8 5 6 \n" +
            "9 6 1 5 3 7 2 8 4 \n" +
            "2 8 7 4 1 9 6 3 5 \n" +
            "3 4 5 2 8 6 1 7 9 \n" ;

    private String board2Answer =
            "7 5 1 8 6 3 9 4 2 \n" +
            "4 2 8 1 9 5 3 6 7 \n" +
            "3 6 9 4 7 2 8 5 1 \n" +
            "6 7 2 5 1 9 4 8 3 \n" +
            "8 3 5 7 2 4 6 1 9 \n" +
            "1 9 4 3 8 6 2 7 5 \n" +
            "2 4 6 9 5 1 7 3 8 \n" +
            "9 1 7 6 3 8 5 2 4 \n" +
            "5 8 3 2 4 7 1 9 6 \n" ;

}
