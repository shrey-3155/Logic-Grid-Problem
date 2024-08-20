import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for managing a category list with the ability to add new categories and ensure
 * unique category values. It also supports creating mappings between category items.
 */
public class addCategory {
    /**
     * Map holding categories with their respective list of strings.
     */
    public Map<String, List<String>> categoryList;

    /**
     * The expected size of the list of strings for each category.
     */
    public int sizeOfList;

    /**
     * A test map for internal data mapping, not directly related to the main category list.
     */
    public Map<String, Map<String, String>> oneToOneMapping;

    /**
     * Constructor initializing an empty category list, sets the initial size of the list to 0,
     * and prepares the test map for data mapping.
     */
    public addCategory() {
        this.categoryList = new HashMap<>();
        this.sizeOfList = 0;
        this.oneToOneMapping = new HashMap<>();
    }

    /**
     * Adds a new category with its values to the category list. Ensures that all categories
     * have values lists of the same size and that within a category, values are unique.
     *
     * @param categoryName   The name of the category to add.
     * @param categoryValues A list of strings representing the values for the category.
     * @return boolean True if the category is successfully added or updated, false if the
     * operation violates the size constraint or the uniqueness of the values within a category.
     */
    public boolean addToMap(String categoryName, List<String> categoryValues) {
        if (categoryList.isEmpty()) {
            sizeOfList = categoryValues.size();
        }
        if (!categoryList.isEmpty() && categoryValues.size() != sizeOfList) {
            return false;
        }
        for (String value : categoryValues) {
            if (value == null || value.isEmpty()) {
                return false;
            }
        }
        if (categoryList.containsKey(categoryName)) {
            // If the category already has values assigned, ensure that the new values match the existing ones
            if (!categoryList.get(categoryName).equals(categoryValues)) {
                return false; // Return false if the values do not match
            }
        } else {
            // If the category name is new, add it to the categories map
            List<String> uniqueElements = new ArrayList<>();
            uniqueElements.add(categoryValues.get(0));
            for (int i = 1; i < categoryValues.size(); i++) {
                String s = categoryValues.get(i);
                // If the element is already present in the HashSet, it means it's not unique
                if (!uniqueElements.contains(s)) {
                    uniqueElements.add(s);
                } else {
                    return false;
                }
            }
            categoryList.put(categoryName, categoryValues);
        }
        createDataMapping(categoryList);
        return true; // Return true if the category is successfully set
    }

    /**
     * Creates mappings for items across different categories. Each item from every category is mapped
     * to every other item in different categories. Initial mappings are set to null.
     *
     * @param data The category list with its respective list of strings to be used for creating mappings.
     */
    private void createDataMapping(Map<String, List<String>> data) {
        // For each category, map each item to items in other categories
        data.forEach((category, items) -> {
            items.forEach(item -> {
                Map<String, String> itemMappings = oneToOneMapping.computeIfAbsent(item, k -> new HashMap<>());
                data.forEach((otherCategory, otherItems) -> {
                    if (!otherCategory.equals(category)) { // Ensure mapping only to different categories
                        otherItems.forEach(otherItem -> itemMappings.put(otherItem, null)); // Initial "null" mapping
                    }
                });
            });
        });
    }
}
