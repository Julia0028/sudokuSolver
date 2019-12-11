import java.util.ArrayList;
import java.util.List;

public class Candidate {
    private ArrayList<Integer> candidates = new ArrayList<Integer>();

    public Candidate(List<Integer> list) {
        candidates.addAll(list);
    }

    public ArrayList<Integer> getCandidates() {
        return candidates;
    }

    public void removeAllCandidates() {
        for (int i = 0; i < candidates.size() + 1; i ++) {
            candidates.remove(i);
        }
    }


}
