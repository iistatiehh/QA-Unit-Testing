package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Disabled;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;
import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class RecipeBookTest {

    private RecipeBook recipeBook;

    @BeforeAll
    static void initAll() {
        System.out.println("Setup complete - Before All");
    }

    @BeforeEach
    void init() {
        recipeBook = new RecipeBook();
        System.out.println("Setup complete - Before Each");
    }

    @Test
    @DisplayName("Valid Recipe Addition")
    void testAddRecipeValid() {
        Recipe recipe = new Recipe();
        recipe.setName("Espresso");
        boolean result = recipeBook.addRecipe(recipe);
        assertTrue(result, "Recipe should be added successfully.");
    }

    @Test
    @DisplayName("Invalid Recipe Addition (Duplicate Recipe)")
    void testAddRecipeDuplicate() {
        Recipe recipe = new Recipe();
        recipe.setName("Espresso");
        recipeBook.addRecipe(recipe); // First add
        boolean result = recipeBook.addRecipe(recipe); // Attempting to add again
        assertFalse(result, "Duplicate recipe should not be added.");
    }

    @ParameterizedTest
    @DisplayName("Parameterized Test for Recipe Deletion")
    @CsvSource({"0, manssaf", "1, maqluba"})
    void testDeleteRecipe(int index, String expectedRecipeName) {
        // Add both "Espresso" and "Latte" before performing the deletion test
        Recipe mansaf = new Recipe();
        mansaf.setName("manssaf");
        recipeBook.addRecipe(mansaf);

        Recipe maqlouba = new Recipe();
        maqlouba.setName("maqluba");
        recipeBook.addRecipe(maqlouba);

        // Delete the recipe at the given index
        String deletedRecipeName = recipeBook.deleteRecipe(index);

        // Assert the deleted recipe name matches the expected name
        assertEquals(expectedRecipeName, deletedRecipeName, "Deleted recipe name should match");
    }


    @Test
    @Timeout(2)
    @DisplayName("Timeout Test - Ensure Add Recipe Doesn't Take Too Long")
    @Tag("recipe")

    void testTimeoutAddRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Americano");
        assertDoesNotThrow(() -> recipeBook.addRecipe(recipe));
    }

    @Test
    //@Disabled("This test is intentionally failing as we expect an out-of-bounds access error.")
    @Tag("recipe")
    void testDeleteRecipeInvalidIndex() {
        // Trying to delete a recipe at an invalid index (out of bounds)
        String result = recipeBook.deleteRecipe(2); // Index 10 is out of bounds
        assertNull(result, "Should return null for invalid index.");
    }

    @Test
    @DisplayName("Edit Recipe with Valid Input")
    @Tag("recipe")

    void testEditRecipeValid() {
        Recipe recipe = new Recipe();
        recipe.setName("coffee");
        recipeBook.addRecipe(recipe);
        Recipe newRecipe = new Recipe();
        newRecipe.setName("tea");
        String oldRecipeName = recipeBook.editRecipe(0, newRecipe);
        assertEquals("coffee", oldRecipeName, "The old recipe name should be returned.");}

    @Test
    @DisplayName("Edit Recipe with Invalid Index")
    //@Disabled
    @Tag("recipe")
    void testEditRecipeInvalidIndex() {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Mocha");
        String result = recipeBook.editRecipe(3, newRecipe); // Invalid index
        assertNull(result, "Should return null for invalid index.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Tear down - After Each");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Tear down - After All");
    }
}



