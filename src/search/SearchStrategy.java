package search;

import java.util.Map;
import java.util.Set;

public interface SearchStrategy {
    Set<String> getRequestData(String request, Map<String, Set<Integer>> indexData, Map<Integer, String> sourceData);
}
