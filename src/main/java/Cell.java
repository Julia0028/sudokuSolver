import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Cell {
    private int numbRow;
    private int numbColumn;
    private int numbBlock;
    private int value;
    private ArrayList<Integer> possibleValues;
    private ArrayList<Integer> uniqueValues;

    private ArrayList<Integer> candidates;



    Cell(int i, int j, int numbBlock, int value) {
        this.numbRow = i;
        this.numbColumn = j;
        this.numbBlock = numbBlock;
        this.value = value;





    }



    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getNumbBlock() {
        return numbBlock;
    }

    public int getNumbRow() {
        return numbRow;
    }

    public int getNumbColumn() {
        return numbColumn;
    }

    public boolean isSetValue() {
        return this.value != 0;
    }




}
