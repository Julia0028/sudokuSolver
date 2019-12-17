import java.util.ArrayList;
import java.util.List;

public class Candidate {
    private ArrayList<Integer> candidates = new ArrayList<Integer>();

    Candidate(List<Integer> list) {
        candidates.addAll(list);
    }

    ArrayList<Integer> getCandidates() {
        return candidates;
    }

    int getCountOfCandidates() {return candidates.size();}




}
