import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class logicalDeductions {

    public logicalDeductions(){

    }
    /**
     * Deduces relationships where no direct relation exists between entities.
     *
     * @param oneToOneMapping The map containing relationships to evaluate.
     */
    public void deduceNoRelation(Map<String, Map<String, String>> oneToOneMapping) {
        for (Map.Entry<String, Map<String, String>> entry : oneToOneMapping.entrySet()) {
            String outerKey = entry.getKey();
            Map<String, String> nestedMap = entry.getValue();

            List<String> keysWithNoValue = new ArrayList<>();

            for (Map.Entry<String, String> nestedEntry : nestedMap.entrySet()) {
                if ("No".equals(nestedEntry.getValue())) {
                    keysWithNoValue.add(nestedEntry.getKey());
                }
            }

            for (String keyWithNo : keysWithNoValue) {
                Map<String, String> yesMap = oneToOneMapping.get(keyWithNo);
                if (yesMap != null) {
                    for (Map.Entry<String, String> yesEntry : yesMap.entrySet()) {
                        if ("Yes".equals(yesEntry.getValue())) {
                            nestedMap.put(yesEntry.getKey(), "No");
                        }
                    }
                }
            }

            // Update the outer map (optional, depending on whether you need to mutate the original map)
            oneToOneMapping.put(outerKey, nestedMap);
        }


    }

    /**
     * Deduces direct relationships between entities based on existing data.
     *
     * @param oneToOneMapping The map containing relationships to evaluate.
     */
    public void deduceRelationships(Map<String, Map<String, String>> oneToOneMapping) {
        for (Map.Entry<String, Map<String, String>> entry : oneToOneMapping.entrySet()) {
            String outerKey = entry.getKey();
            Map<String, String> nestedMap = entry.getValue();

            List<String> keysWithYesValue = new ArrayList<>();

            for (Map.Entry<String, String> nestedEntry : nestedMap.entrySet()) {
                if ("Yes".equals(nestedEntry.getValue())) {
                    keysWithYesValue.add(nestedEntry.getKey()); // Add keys
                }
            }

            for (String keyWithYes : keysWithYesValue) {
                Map<String, String> yesMap = oneToOneMapping.get(keyWithYes);
                if (yesMap != null) {
                    for (Map.Entry<String, String> yesEntry : yesMap.entrySet()) {
                        if ("Yes".equals(yesEntry.getValue())) {
                            nestedMap.put(yesEntry.getKey(), "Yes");
                        }
                    }
                }
            }

            // Update the outer map (optional, depending on whether you need to mutate the original map)
            oneToOneMapping.put(outerKey, nestedMap);
        }

    }
}
