package search;

import java.util.*;

public class AnyStrategy implements SearchStrategy {
    @Override
    public Set<String> getRequestData(String request,
                                       Map<String, Set<Integer>> indexData,
                                       Map<Integer, String> sourceData) {
        Set<String> results = new HashSet<>();
        Set<String> keyWords = new HashSet<>(Arrays.asList(request.split("\\s+")));
        for(String key : keyWords) {
            if (indexData.containsKey(key)) {
                Set<Integer> indexies = indexData.get(key);
                for (Integer index : indexies) {
                    results.add(sourceData.get(index));
                }
            }
        }
        return results;
    }
}
