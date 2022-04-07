package search;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AllStrategy implements SearchStrategy {
    @Override
    public Set<String> getRequestData(String request, Map<String, Set<Integer>> indexData, Map<Integer, String> sourceData) {
        Set<String> results = new HashSet<>();
        Set<String> keyWords = new HashSet<>(Arrays.asList(request.split("\\s+")));
        boolean isFirst = true;
        Set<Integer> indexies = new HashSet<>();
        for (String key : keyWords) {
            if (indexData.containsKey(key)) {
                if (isFirst) {
                    indexies.addAll(indexData.get(key));
                    isFirst = false;
                } else {
                    indexies.retainAll((indexData.get(key)));
                }
            }
        }
        for (Integer index : indexies) {
            results.add(sourceData.get(index));
        }
        return results;
    }
}
