public class Main {
    public static void main(String[] args) {
        int[][] board2 = {
                {2, 0, 4, 9, 5, 0, 0, 0, 3},
                {6, 0, 8, 1, 7, 3, 0, 0, 5},
                {1, 5, 3, 0, 0, 0, 0, 7, 9},
                {0, 0, 0, 0, 1, 2, 0, 0, 4},
                {9, 0, 0, 0, 8, 5, 0, 0, 0},
                {3, 4, 2, 0, 0, 7, 0, 0, 8},
                {0, 0, 0, 5, 6, 0, 0, 0, 0},
                {0, 0, 0, 7, 0, 9, 0, 5, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0}
        };

        int[][] board1 = {
                {0, 0, 0, 0, 5, 0, 0, 8, 0},
                {4, 0, 5, 0, 8, 6, 0, 0, 2},
                {0, 0, 8, 0, 9, 0, 0, 0, 5},
                {0, 0, 2, 5, 7, 0, 0, 0, 3},
                {0, 8, 0, 0, 0, 0, 0, 7, 0},
                {6, 0, 0, 0, 3, 4, 1, 0, 0},
                {7, 0, 0, 0, 1, 0, 4, 0, 0},
                {5, 0, 0, 2, 6, 0, 9, 0, 7},
                {0, 3, 0, 0, 4, 0, 0, 0, 0}
        };

        int[][] board3 = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 9},
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] board4 = {
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        long startTime = System.currentTimeMillis();

        {
            Sudoku sudoku = new Sudoku(board4);

            System.out.println(sudoku.getSolution());

        }

        //время затраченное на выполнение кода
        long time = System.currentTimeMillis() - startTime;
       // System.out.println(time);

    }

}


