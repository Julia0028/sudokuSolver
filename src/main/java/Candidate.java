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

    @Override
    public boolean equals(Object obj) {
        Candidate cand = (Candidate) obj;
        if (cand.getCountOfCandidates() == this.getCountOfCandidates()) {
            for (int i = 0; i < this.getCountOfCandidates(); i++) {
                if (this.getCandidates().get(i) != cand.getCandidates().get(i)) return false;
            }
            return true;
        }
        return false;
    }
}
