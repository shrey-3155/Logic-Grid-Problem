# Logic-Grid-Problem
The domain of logic grid problems calls for an intellectual journey: a quest to find one's way  across an artfully and carefully designed state space. It was during the 20th century that these  puzzles grew in popularity as a leisurely pastime, challenging participants with logical  reasoning from a series of given clues.
The **Logic-Grid-Problem** is a Java-based application designed to solve classic logic grid puzzles. These puzzles challenge participants to use logical reasoning to deduce relationships between different categories based on a series of clues. Popularized in the 20th century, logic grid puzzles have become a staple in the realm of intellectual challenges, offering a structured yet open-ended framework for problem-solving.

## Project Overview

Logic grid puzzles present scenarios where participants must match elements across different categories (e.g., people, pets, activities) using a grid to track possible and impossible combinations. The grid allows solvers to visually mark deductions, using checkmarks for confirmed matches and Xs for negated possibilities. The ultimate goal is to logically connect all elements and complete the puzzle tableau.

In this project, the logic grid problem is approached as a search space problem, where each possible solution is explored through deductive reasoning. The project uses a combination of data structures, including hash maps, sets, and arrays, to optimize the solution process and ensure robustness across various input types.

## Key Features

- **Category and Value Management**: Dynamically set up categories and their possible values, establishing logical orders and constraints based on provided clues.
- **Logical Deduction**: Use deductive reasoning to eliminate impossible combinations and confirm possible ones, ensuring that the solution aligns with all given clues.
- **Solution Validation**: Validate proposed solutions against the puzzle's constraints to ensure consistency and correctness.
- **Optimized Data Structures**: Efficiently manage the puzzle's data using hash maps, linked lists, and sets, ensuring quick access and manipulation of clues and possible solutions.

## System Modules

### 1. **LogicGridPuzzle Class**
- The main class responsible for orchestrating the puzzle-solving process. It interacts with various helper classes to manage categories, values, and logical deductions.

- Methods include:
    - `boolean setCategory(String categoryName, List<String> categoryValues)`: Creates a new category with a specified set of values. Ensures all categories have an equal number of values and returns true if the category is successfully established.
    - `boolean valuePossibilities(String baseValue, Set<String> options, Set<String> exclusions)`: Defines possible and excluded values for a given base value. Returns true if the puzzle can work with the provided clue.
    - `boolean listOrder(String orderCategory, String firstValue, String secondValue, int orderGap)`: Sets up a sequence within a category, determining the order of values. Returns true if the order fits into the puzzle's framework.
    - `Map<String, Map<String, String>> solve(String rowCategory)`: Attempts to solve the puzzle by logically deducing relationships between values. Returns a nested map structure representing the solution, or null if the puzzle is unsolvable.
    - `boolean check(String rowCategory, Map<String, Map<String, String>> solution)`: Validates a proposed solution against the puzzle's constraints. Returns true if the solution is correct, false otherwise.

### 2. **Helper Classes**
- **addCategory**: Ensures categories and their values are added correctly to the internal data structures.
- **listOrder**: Handles the ordering of values within a category based on provided clues.
- **possibleValues**: Manages the possible and excluded values for each clue, updating the internal state of the puzzle.
- **logicalDeduction**: Performs logical deductions to eliminate impossible combinations and confirm valid ones.

## Data Structures

- **HashMap**: Used to store cross-relationships between clues and their values. The key is an array of strings representing all values across different categories.
- **LinkedList**: Stores category values in a specific order, which is used in the `setCategory` and `listOrder` methods.
- **HashSet**: Maintains unique sets of values for efficient lookup and manipulation during the deduction process.

These data structures work in tandem to efficiently manage the clues and possible solutions, ensuring the puzzle is solved in an optimized manner.

## Conclusion

The **Logic-Grid-Problem** project offers a robust framework for solving logic grid puzzles. By leveraging efficient data structures and logical deduction techniques, the project provides a powerful tool for tackling these intellectual challenges. The modular design allows for easy extension and adaptation to different types of logic puzzles, making it a versatile solution in the realm of logical reasoning games.