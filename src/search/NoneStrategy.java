package search;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NoneStrategy implements SearchStrategy {
    @Override
    public Set<String> getRequestData(String request, Map<String, Set<Integer>> indexData, Map<Integer, String> sourceData) {
        Set<String> results = new HashSet<>(sourceData.values());
        SearchStrategy anyStrategy = new AnyStrategy();
        results.removeAll(anyStrategy.getRequestData(request, indexData, sourceData));
        return results;
    }
}
