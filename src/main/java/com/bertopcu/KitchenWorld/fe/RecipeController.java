package com.bertopcu.KitchenWorld.fe;

import com.bertopcu.KitchenWorld.model.Recipe;
import com.bertopcu.KitchenWorld.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("")
    public List<Recipe> list() {
        return recipeService.listAllRecipes();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> get(@PathVariable Integer id) {
        try {
            Recipe recipe = recipeService.getRecipe(id);
            return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/")
    public void add(@RequestBody Recipe recipe) {
        recipeService.saveRecipe(recipe);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Recipe recipe, @PathVariable Integer id) {
        try {
            Recipe existRecipe = recipeService.getRecipe(id);
            recipe.setId(id);
            recipeService.saveRecipe(recipe);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        recipeService.deleteRecipe(id);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/categorized/{categoryId}")
    public List<Recipe> listByCategory(@PathVariable Integer categoryId) {
        return recipeService.getRecipesByCategory(categoryId);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/searchByName")
    public List<Recipe> listByName(@RequestBody Map<String, String> searchTextMap) {
        String name = searchTextMap.get("searchText");
        return recipeService.getRecipesByName(name);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/searchByMaterials")
    public List<Recipe> listByMaterials(@RequestBody Map<String, ArrayList<Integer>> materials) {
        ArrayList<Integer> materialIds = materials.get("materials");
        return recipeService.getRecipesByMaterials(materialIds);
    }
}
