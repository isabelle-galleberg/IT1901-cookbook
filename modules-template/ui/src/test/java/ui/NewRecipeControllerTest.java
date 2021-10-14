package ui;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import json.FileHandler;
import ui.NewRecipeController;

public class NewRecipeControllerTest extends AbstractAppTest {

    FileHandler handler = new FileHandler();

    private NewRecipeController controller;
    private Recipe recipe1;
    private Ingredient ing1, ing2;
    private List<Ingredient> ingredients = new ArrayList<>();
    private Cookbook cookbook = new Cookbook();

    @Override
    public void start(final Stage stage) throws Exception {

        URL fxmlLocation = getClass().getResource("NewRecipe_test.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        // this.cookbook = this.controller.getCookbook();
        Scene scene = new Scene(root);

        stage.setTitle("Cookbook<3");

        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setupItems() {
        recipe1 = new Recipe("Eple", 2);
        ing1 = new Ingredient("Eple", 3, "stk");
        ingredients.add(ing1);

        recipe1.addIngredient(ing1);
        recipe1.setDescription("Epler...");
    }

    @Test
    public void testNewIngredient() {
        clickOn("#ingredientTitle").write("Eple");
        clickOn("#ingredientAmount").write("3");
        clickOn("#ingredientUnit").write("stk");
        clickOn("#addIngredient");
        checkIngredient(controller.getIngredients().get(0), ing1);
    }

    @Test
    public void testNewRecipe() throws FileNotFoundException {
        clickOn("#recipeTitle").write("Eple");
        clickOn("#recipePortions").write("2");
        clickOn("#ingredientTitle").write("Eple");
        clickOn("#ingredientAmount").write("3");
        clickOn("#ingredientUnit").write("stk");
        clickOn("#addIngredient");
        clickOn("#recipeDescription").write("Epler...");
        clickOn("#createRecipe");
        handler.readRecipesFromFile("src/main/resources/ui/test.txt", cookbook);
        checkRecipe(cookbook.getRecipes().get(cookbook.getRecipes().size() - 1), recipe1);
        cookbook.getRecipes().remove(cookbook.getRecipes().size() - 1);
        handler.writeRecipesToFile("src/main/resources/ui/test.txt", cookbook);
    }

}