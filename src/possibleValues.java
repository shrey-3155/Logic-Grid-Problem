
import java.util.*;

/**
 * This class manages potential values for categories in a logic puzzle, allowing
 * for the deduction of relationships between different values within the puzzle.
 * All the clues are given by this class functions only and mostly deductions are done here.
 */
public class possibleValues {

    /**
     * Constructor for initializing the possibleValues object.
     */
    public possibleValues() {
    }

    logicalDeductions logicalDeductions = new logicalDeductions();

    /**
     * Evaluates and updates the possible values and exclusions for a given base value within the puzzle's context.
     * It updates the mapping based on options and exclusions provided, marking non-options as "No" for the base value
     * and vice versa, then proceeds to refine the puzzle's logic by deducing direct and indirect relationships.
     *
     * @param categoryList    The current list of categories and their associated values.
     * @param baseValue       The value for which possibilities are being evaluated.
     * @param options         A set of values considered to be potential matches for the base value.
     * @param exclusions      A set of values to be explicitly excluded from matching with the base value.
     * @param oneToOneMapping A map holding potential relationships between values across different categories.
     * @param sizeOfCategory  The size of the category to which the base value belongs.
     * @return A boolean indicating if the operation was successful.
     */
    public boolean newValues(Map<String, List<String>> categoryList, String baseValue, Set<String> options, Set<String> exclusions, Map<String, Map<String, String>> oneToOneMapping, int sizeOfCategory) {
        List<String> testArr = new ArrayList<>();
        Set<String> categoriesOfOptions = new HashSet<>(); // To track categories that contain the options

        for (String option : options) {
            for (Map.Entry<String, List<String>> entry : categoryList.entrySet()) {
                if (entry.getValue().contains(option)) {
                    categoriesOfOptions.add(entry.getKey()); // Add the category of this option
                }
            }
        }
        for (String category : categoriesOfOptions) {
            List<String> itemsInCategory = categoryList.get(category);
            for (String item : itemsInCategory) {
                if (!options.contains(item)) { // Add the item if it's not an option
                    testArr.add(item);
                }
            }
        }
        for (String value : oneToOneMapping.keySet()) {
            if (value.equals(baseValue)) {
                for (String s : testArr) {
                    if (!options.contains(s)) {
                        oneToOneMapping.get(baseValue).put(s, "No");
                        oneToOneMapping.get(s).put(baseValue, "No");
                    }
                }
            }
        }
        for (String value : oneToOneMapping.keySet()) {
            if (value.equals(baseValue)) {
                if (exclusions != null) {
                    newPossibleValues(categoryList, baseValue, null, exclusions, oneToOneMapping, sizeOfCategory);
                }
            }
        }

        logicalDeductions.deduceNoRelation(oneToOneMapping);
        logicalDeductions.deduceRelationships(oneToOneMapping);
        return true;
    }

    /**
     * Updates the puzzle's logic based on new sets of possible values or exclusions, marking direct relationships as "Yes"
     * and direct exclusions as "No". This method focuses on refining the mappings in 'oneToOneMapping' based on provided options and
     * exclusions, further processing them to deduce additional logical deductions.
     *
     * @param categoryList    The current list of categories and their associated values.
     * @param baseValue       The value for which new possibilities or exclusions are being added.
     * @param options         A set of new possible values to be associated with the base value.
     * @param exclusions      A set of values to be excluded in relation to the base value.
     * @param oneToOneMapping A map of potential relationships to be updated based on the new information.
     * @param sizeOfCategory  The number of elements in each category, used for logical deductions.
     * @return A boolean indicating if the updates were successful.
     */
    public boolean newPossibleValues(Map<String, List<String>> categoryList, String baseValue, Set<String> options, Set<String> exclusions, Map<String, Map<String, String>> oneToOneMapping, int sizeOfCategory) {
        for (String value : oneToOneMapping.keySet()) {
            if (value.equals(baseValue)) {
                if (options != null && options.size() == 1) {
                    for (String s : options) {
                        oneToOneMapping.get(baseValue).put(s, "Yes");
                        oneToOneMapping.get(s).put(baseValue, "Yes");
                    }
                }
            }
        }
        for (String value : oneToOneMapping.keySet()) {
            if (value.equals(baseValue)) {
                if (exclusions != null) {
                    for (String s : exclusions) {
                        oneToOneMapping.get(baseValue).put(s, "No");
                        oneToOneMapping.get(s).put(baseValue, "No");
                    }
                }
            }
        }

        markHorizontalAndVerticalNo(categoryList, baseValue, options, oneToOneMapping);
        deduceWhenTwoNo(categoryList, oneToOneMapping, sizeOfCategory);

        if (options != null) {
            for (String s : options) {
                newPossibleValues(categoryList, s, null, exclusions, oneToOneMapping, sizeOfCategory);
            }
        }

        logicalDeductions.deduceNoRelation(oneToOneMapping);
        logicalDeductions.deduceRelationships(oneToOneMapping);
        return true;
    }

    /**
     * Marks relationships as 'No' horizontally and vertically for a base value in relation to a set of options
     * within the puzzle's category list and updates these relationships in the oneToOneMapping.
     *
     * @param categoryList    A map where each key represents a category name, and its associated values
     * @param baseValue       The string value representing the base puzzle element from which relationships are being evaluated.
     * @param options         A set of string values representing the options being considered for association or disassociation
     *                        with the baseValue.
     * @param oneToOneMapping A nested map structure where each key (a puzzle element) maps to another map, which contains
     *                        potential relationships with other elements.
     */
    private static void markHorizontalAndVerticalNo(Map<String, List<String>> categoryList, String baseValue, Set<String> options, Map<String, Map<String, String>> oneToOneMapping) {
        List<String> newArr = new ArrayList<>();
        if (options != null) {
            for (String value : options) {
                for (List<String> s : categoryList.values()) {
                    if (s.contains(value)) {
                        newArr.addAll(s);
                    }
                }
            }
        }

        for (String value : oneToOneMapping.keySet()) {
            if (value.equals(baseValue)) {
                for (String s : newArr) {
                    if (!options.contains(s)) {
                        oneToOneMapping.get(baseValue).put(s, "No");
                        oneToOneMapping.get(s).put(baseValue, "No");
                    }
                }
            }
        }
        List<String> testArr = new ArrayList<>();
        for (List<String> s : categoryList.values()) {
            if (s.contains(baseValue)) {
                testArr.addAll(s);
            }
        }
        for (String value : oneToOneMapping.keySet()) {
            if (testArr.contains(value)) {
                for (String s : testArr) {
                    if (options != null) {
                        for (String o : options) {
                            if (!s.contains(baseValue)) {
                                oneToOneMapping.get(s).put(o, "No");
                                oneToOneMapping.get(o).put(s, "No");
                            }
                        }
                    }

                }
            }
        }
    }

    /**
     * Deduces and updates relationships within the puzzle when exactly two 'No' relationships are identified
     * among a set of potential relationships, and sets the remaining undefined relationship to 'Yes'.
     *
     * @param categoryList    A map of categories with their respective list of values. Each category is key, and its values are listed.
     * @param oneToOneMapping A nested map where each key (a puzzle element) maps to another map. The inner map's keys are potential
     *                        related elements, and values are the relationships ('Yes', 'No', or undefined).
     * @param sizeOfCategory  The size of each category, used to determine the deduction logic threshold.
     */
    private void deduceWhenTwoNo(Map<String, List<String>> categoryList, Map<String, Map<String, String>> oneToOneMapping, int sizeOfCategory) {
        for (Map<String, String> test : oneToOneMapping.values()) {
            List<String> firstThreeKeys = new ArrayList<>(); // Get the first three keys or less if size < 3
            for (String s : categoryList.keySet()) {
                List<String> sgf = categoryList.get(s);
                int noCount = 0;
                String nullKey = null; // Keep track of the key with a null value
                firstThreeKeys.addAll(sgf);
                // Iterate over the first three keys
                for (String str : firstThreeKeys) {
                    String value = test.get(str);
                    if ("No".equals(value)) {
                        noCount++; // Count 'No' values
                    } else if (value == null) {
                        nullKey = str; // Remember the key with a null value
                    }
                    if (noCount == (sizeOfCategory - 1) && nullKey != null) {
                        test.put(nullKey, "Yes");
                    }
                }
                // If there are exactly two 'No' values and one null value among the first three, set the null value to 'Yes'
                firstThreeKeys.clear();
            }
        }
        // Main logic to remove elements of the same category under specific conditions
        for (Map.Entry<String, Map<String, String>> entry : oneToOneMapping.entrySet()) {
            String baseKey = entry.getKey(); // The base key you're working with in oneToOneMapping
            Map<String, String> test = entry.getValue(); // The sub-map for this base key
            String baseKeyCategory = findCategoryOfKey(baseKey, categoryList);

            // Directly manipulate 'test' if it includes keys of the same category as 'baseKey'
            List<String> keysToRemove = new ArrayList<>(); // List to hold keys of the same category to be removed
            for (String key : test.keySet()) {
                String keyCategory = findCategoryOfKey(key, categoryList);
                if (baseKeyCategory.equals(keyCategory)) {
                    // Add to list of keys to remove, since it's the same category as 'baseKey'
                    keysToRemove.add(key);
                }
            }
            // Now remove all keys identified for removal from 'test'
            for (String keyToRemove : keysToRemove) {
                test.remove(keyToRemove);
            }
        }

        for (String outerKey : oneToOneMapping.keySet()) {
            Map<String, String> test = oneToOneMapping.get(outerKey); // Get the map associated with the outer key
            // Now, iterate over the entry set of the inner map to check each value
            for (String entry : test.keySet()) {
                if ("Yes".equals(test.get(entry))) {
                    oneToOneMapping.get(entry).put(outerKey, "Yes");
                }
            }
        }
    }


    /**
     * Finds the category of a given key (value) from the list of categories.
     *
     * @param key          The key (value) for which the category is to be found.
     * @param categoryList The list of categories and their respective values.
     * @return String The category name if found, otherwise null.
     */
    private String findCategoryOfKey(String key, Map<String, List<String>> categoryList) {
        for (Map.Entry<String, List<String>> entry : categoryList.entrySet()) {
            if (entry.getValue().contains(key)) {
                return entry.getKey(); // Return the category name
            }
        }
        return null; // Key not found in any category
    }


}
