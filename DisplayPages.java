import java.util.*;

public class DisplayPages {
    public List<String> displayPages(List<String> input, int pageSize) {
        List<String> res = new ArrayList<>();
        Iterator<String> iter = input.iterator();
        Set<String> set = new HashSet<>();
        boolean reachEnd = false;
        int counter = 0;
        while (iter.hasNext()) {
            String cur = iter.next();
            String id = (cur.split(","))[0];
            if (!set.contains(id) || reachEnd) {
                res.add(cur);
                set.add(id);
                iter.remove();
                counter++;
            }
            if (counter == pageSize) {
                if (!input.isEmpty())
                    res.add(" ");
                set.clear();
                counter = 0;
                reachEnd = false;
                iter = input.iterator();
            }
            if (!iter.hasNext()) {
                reachEnd = true;
                iter = input.iterator();
            }
        }
        return res;
    }
}
