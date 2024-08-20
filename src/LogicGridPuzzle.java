/**
 * Author: Shrey Nimeshkumar Patel
 * Banner-Id: B00960433, email id: sh644024@dal.ca
 * Assignment 4, The Logic Grid Puzzle Problem
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class LogicGridPuzzle {

    // Member classes for solving different aspects of the puzzle
    public LogicGridPuzzle() {
    }

    addCategory addCategory = new addCategory();
    possibleValues possibleValues = new possibleValues();
    listOrder listOrder = new listOrder();
    logicalDeductions logicalDeductions = new logicalDeductions();

    /**
     * Adds a category with its values to the puzzle.
     *
     * @param categoryName   The name of the category to add.
     * @param categoryValues A list of values belonging to the category.
     * @return boolean indicating success (true) or failure (false) of addition.
     */
    public boolean setCategory(String categoryName, List<String> categoryValues) {
        if (categoryName == null) {
            return false;
        }
        if (categoryValues == null || categoryValues.isEmpty()) {
            return false;
        }
        return addCategory.addToMap(categoryName, categoryValues);
    }

    /**
     * Checks if a given value belongs to a specified category.
     *
     * @param value        The value to check.
     * @param category     The category against which to check the value.
     * @param categoryList The list of all categories and their values.
     * @return boolean indicating if the value belongs to the category.
     */
    private boolean belongsToCategory(String value, String category, Map<String, List<String>> categoryList) {
        return categoryList.getOrDefault(category, Collections.emptyList()).contains(value);
    }

    /**
     * Evaluates the possibilities for a base value considering given options and exclusions.
     *
     * @param baseValue  The base value to evaluate possibilities for.
     * @param options    A set of options that could be related to the base value.
     * @param exclusions A set of exclusions that are not related to the base value.
     * @return boolean indicating if the evaluation was successful.
     */
    public boolean valuePossibilities(String baseValue, Set<String> options, Set<String> exclusions) {
        if (options != null && exclusions != null) {
            for (String S : options) {
                if (exclusions.contains(S)) {
                    return false;
                }
            }
        }
        if (options != null) {
            if (options.isEmpty() && ((exclusions == null) || exclusions.isEmpty())) {
                return false;
            }
        }
        if (exclusions != null) {
            if (exclusions.isEmpty() && ((options == null) || options.isEmpty())) {
                return false;
            }
        }
        if (isInputValid(baseValue, options, exclusions)) return false;
        Map<String, Set<String>> categoryToOptions = new HashMap<>();
        if (exclusions != null) {
            possibleValues.newPossibleValues(addCategory.categoryList, baseValue, options, exclusions, addCategory.oneToOneMapping, addCategory.sizeOfList);
            logicalDeductions.deduceNoRelation(addCategory.oneToOneMapping);
            logicalDeductions.deduceRelationships(addCategory.oneToOneMapping);
        }
        // Assuming a method exists that returns the category for a given option
        if (options != null) {
            for (String option : options) {
                String optionCategory = getCategoryForOption(option, addCategory.categoryList);
                if (optionCategory != null) {
                    // Add the option to the appropriate set in the map
                    categoryToOptions.computeIfAbsent(optionCategory, k -> new HashSet<>()).add(option);
                }
            }
        }

        for (Set<String> test : categoryToOptions.values()) {
            if (test != null && test.size() > 1) {
                if (possibleValues.newValues(addCategory.categoryList, baseValue, test, exclusions, addCategory.oneToOneMapping, addCategory.sizeOfList)) {
                    logicalDeductions.deduceNoRelation(addCategory.oneToOneMapping);
                    logicalDeductions.deduceRelationships(addCategory.oneToOneMapping);
                }
            }
            if (test != null && test.size() == 1) {
                if (possibleValues.newPossibleValues(addCategory.categoryList, baseValue, test, exclusions, addCategory.oneToOneMapping, addCategory.sizeOfList)) {
                    logicalDeductions.deduceNoRelation(addCategory.oneToOneMapping);
                    logicalDeductions.deduceRelationships(addCategory.oneToOneMapping);
                }
            }
        }
        return true;
    }


    /**
     * Evaluates the validity for base value, options and exclusions
     *
     * @param baseValue  The base value to evaluate possibilities for.
     * @param options    A set of options that could be related to the base value.
     * @param exclusions A set of exclusions that are not related to the base value.
     * @return boolean indicating if the validity is correct.
     */
    private boolean isInputValid(String baseValue, Set<String> options, Set<String> exclusions) {
        boolean baseValueFound = false;
        String baseValueCategory = null;
        if (baseValue == null) {
            return true;
        }
        if (options == null && exclusions == null) {
            return true;
        }
        for (Map.Entry<String, List<String>> entry : addCategory.categoryList.entrySet()) {
            if (entry.getValue().contains(baseValue)) {
                baseValueFound = true;
                baseValueCategory = entry.getKey();
                break;
            }
        }
        if (!baseValueFound) {
            return true;
        }

        // Check if options and exclusions are consistent and not in the same category as baseValue
        if (options != null && !options.isEmpty()) {
            for (String option : options) {
                if (belongsToCategory(option, baseValueCategory, addCategory.categoryList)) {
                    return true;
                }
            }
        }

        if (exclusions != null && !exclusions.isEmpty()) {
            for (String exclusion : exclusions) {
                if (belongsToCategory(exclusion, baseValueCategory, addCategory.categoryList)) {
                    return true;
                }
            }
        }
        Set<String> containsKey = addCategory.oneToOneMapping.keySet();
        if (exclusions != null) {
            for (String S : exclusions) {
                if (!containsKey.contains(S)) {
                    return true;
                }
            }
        }
        if (options != null) {
            for (String S : options) {
                if (!containsKey.contains(S)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves the category for a given option.
     *
     * @param option       The option for which to find the category.
     * @param categoryList The list of all categories and their values.
     * @return The category of the option, or null if it doesn't belong to any.
     */
    private String getCategoryForOption(String option, Map<String, List<String>> categoryList) {
        for (Map.Entry<String, List<String>> entry : categoryList.entrySet()) {
            if (entry.getValue().contains(option)) {
                return entry.getKey(); // Return the category of the option
            }
        }
        return null; // Return null if the option doesn't belong to any category
    }

    /**
     * Determines the order of values within a category based on a specified gap.
     *
     * @param orderCategory The category in which to order values.
     * @param firstValue    The first value in the order.
     * @param secondValue   The second value in the order.
     * @param orderGap      The gap between the two values in the order.
     * @return boolean indicating if the ordering was successful.
     */
    public boolean listOrder(String orderCategory, String firstValue, String secondValue, int orderGap) {
        if (orderCategory == null || firstValue == null || secondValue == null) {
            return false;
        }

        return listOrder.listOfValues(addCategory.categoryList, orderCategory, firstValue, secondValue, orderGap, addCategory.oneToOneMapping);
    }

    /**
     * Solves the puzzle for a given row category and constructs the solution.
     *
     * @param rowCategory The category based on which to solve the puzzle.
     * @return A map representing the final solution of the puzzle.
     */
    public Map<String, Map<String, String>> solve(String rowCategory) {
        if (rowCategory == null || !addCategory.categoryList.containsKey(rowCategory)) {
            return null;
        }
        logicalDeductions.deduceNoRelation(addCategory.oneToOneMapping);
        logicalDeductions.deduceRelationships(addCategory.oneToOneMapping);
        possibleValues.newPossibleValues(addCategory.categoryList, null, null, null, addCategory.oneToOneMapping, addCategory.sizeOfList);
        Map<String, Map<String, String>> finalSolution = new HashMap<>();
        // Iterate over the entries in the rowCategory
        if (addCategory.categoryList.containsKey(rowCategory)) {
            for (String entity : addCategory.categoryList.get(rowCategory)) {
                Map<String, String> entityMappings = new HashMap<>();
                // Iterate over all categories except the rowCategory
                for (String category : addCategory.categoryList.keySet()) {
                    if (!category.equals(rowCategory)) {
                        // For each category, find the entity that is linked to the current entity with a "Yes"
                        for (String possibleMatch : addCategory.categoryList.get(category)) {
                            // Retrieve the mapping for the possible match
                            Map<String, String> matchoneToOneMapping = addCategory.oneToOneMapping.get(possibleMatch);
                            if (matchoneToOneMapping != null && "Yes".equals(matchoneToOneMapping.get(entity))) {
                                // If a "Yes" is found, save this mapping
                                entityMappings.put(category, possibleMatch);
                                break; // Assuming only one match per category is needed
                            }
                        }
                    }
                }
                // Add the entity and its mappings to the final solution
                finalSolution.put(entity, entityMappings);
            }

        }
        return finalSolution;
    }



    /**
     * Checks if the given solution matches the solved state of the puzzle.
     *
     * @param rowCategory The category based on which the puzzle was solved.
     * @param solution    The proposed solution to check against the solved puzzle.
     * @return boolean indicating if the given solution matches the solved puzzle.
     */
    public boolean check(String rowCategory, Map<String, Map<String, String>> solution) {

        if (rowCategory == null || !addCategory.categoryList.containsKey(rowCategory)) {
            return false;
        }
        // Solve the puzzle for the given rowCategory
        Map<String, Map<String, String>> solutionOfRowCategory = solve(rowCategory);

        // Check if either solution is null or they don't have the same size, which indicates a mismatch
        if (solutionOfRowCategory == null || solution == null || solutionOfRowCategory.size() != solution.size()) {
            return false;
        }

        // Iterate over the provided solution and compare it to the solved solution
        for (Map.Entry<String, Map<String, String>> entry : solution.entrySet()) {
            String key = entry.getKey();
            Map<String, String> valueMap = entry.getValue();

            // Check if both solutions contain the same keys with the same values
            Map<String, String> solvedValueMap = solutionOfRowCategory.get(key);
            if (solvedValueMap == null || !solvedValueMap.equals(valueMap)) {
                return false;
            }
            for (String S : solvedValueMap.keySet()) {
                if (solvedValueMap.get(S) != null) {
                    if (!valueMap.get(S).equals(solvedValueMap.get(S))) {
                        return false;
                    }
                }
            }
        }

        // If all keys and corresponding values match, the solutions are equivalent
        return true;
    }


}
