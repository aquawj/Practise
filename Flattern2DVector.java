import java.util.*;

public class Flattern2DVector implements Iterator<Integer> {
    private Iterator<List<Integer>> rowIter;
    private Iterator<Integer> colIter;
    public Flattern2DVector(List<List<Integer>> vec2d) {
        rowIter = vec2d.iterator();
        colIter = Collections.emptyIterator();
    }
    @Override
    public Integer next() {
        return colIter.next();
    }
    @Override
    public boolean hasNext() {
        while ((colIter == null || !colIter.hasNext()) && rowIter.hasNext())
            colIter = rowIter.next().iterator();
        return colIter != null && colIter.hasNext();
    }
    @Override
    public void remove() {
        while (colIter == null && rowIter.hasNext())
            colIter = rowIter.next().iterator();
        if (colIter != null)
            colIter.remove();
    }
}
