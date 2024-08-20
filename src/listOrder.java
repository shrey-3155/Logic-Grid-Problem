import java.util.*;

/**
 * Class for managing and applying order constraints to values within a specific category in a Logic Grid Puzzle.
 * This class is specificallu used when there is gap given in clues.
 */
public class listOrder {
    possibleValues possibleValues = new possibleValues();


    /**
     * Constructor for the listOrder class.
     */
    public listOrder() {
    }

    /**
     * Applies order constraints between two values within a given category, based on a specified gap.
     *
     * @param categoryList    A map of categories with their respective list of values.
     * @param orderCategory   The category within which the order constraints are to be applied.
     * @param firstValue      The value that is supposed to come first in the order.
     * @param secondValue     The value that is supposed to come after the first value, based on the order gap.
     * @param orderGap        The gap between the first and second value within the list of values for the category.
     *                        A gap of 0 means they are consecutive.
     * @param oneToOneMapping A map for testing potential relationships and constraints between values across categories.
     * @return boolean True if the order constraint can be applied successfully, false otherwise, for example,
     * if the category does not exist or the orderGap is invalid.
     */
    public boolean listOfValues(Map<String, List<String>> categoryList, String orderCategory, String firstValue, String secondValue, int orderGap, Map<String, Map<String, String>> oneToOneMapping) {
        // Check if the specified category exists in the category list
        if (!categoryList.containsKey(orderCategory)) {
            return false;
        }
        if(orderGap<0){
            return false;
        }
        // Create a mapping of indices to values for the order category
        Map<Integer, String> indexMap = new HashMap<>();
        for (String S : categoryList.get(orderCategory)) {
            int x = categoryList.get(orderCategory).indexOf(S);
            indexMap.put(x, S);
        }

        // Verify that the order gap is within the valid range
        if (orderGap >= categoryList.values().size()) {
            return false;
        }

        // Handle specific cases for orderGap to apply constraints appropriately
        if (orderGap == 0 || orderGap == 1) {
            // Case when the values are adjacent or exactly one apart
            // Utilizes newPossibleValues to set exclusions based on the order gap
            possibleValues.newPossibleValues(categoryList, secondValue, null, new HashSet<>(Arrays.asList(indexMap.get(0))), oneToOneMapping, categoryList.get(orderCategory).size());
            possibleValues.newPossibleValues(categoryList, firstValue, null, new HashSet<>(Arrays.asList(indexMap.get(categoryList.get(orderCategory).size() - 1))), oneToOneMapping, categoryList.get(orderCategory).size());
        }
        if (orderGap != 0 && orderGap != 1) {
            // General case for non-adjacent values
            for (int i = 0; i < indexMap.size(); i++) {
                if (i < orderGap) {
                    possibleValues.newPossibleValues(categoryList, secondValue, null, new HashSet<>(Arrays.asList(indexMap.get(i))), oneToOneMapping, categoryList.get(orderCategory).size());
                }
                if (i >= categoryList.get(orderCategory).size() - orderGap) {
                    possibleValues.newPossibleValues(categoryList, firstValue, null, new HashSet<>(Arrays.asList(indexMap.get(i))), oneToOneMapping, categoryList.get(orderCategory).size());
                }

            }
        }
        return true;
    }

}
