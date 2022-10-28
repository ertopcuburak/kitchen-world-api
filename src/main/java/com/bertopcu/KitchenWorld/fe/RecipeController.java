package com.bertopcu.KitchenWorld.fe;

import com.bertopcu.KitchenWorld.model.Recipe;
import com.bertopcu.KitchenWorld.service.RecipeService;
import com.bertopcu.KitchenWorld.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @PostMapping("/all")
    public List<Recipe> list() {
        return recipeService.listAllRecipes();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/{id}")
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
    @PostMapping("/categorized/{categoryId}")
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/getFavorites")
    public List<Recipe> getFavorites(@RequestBody Map<String, Integer> body) {
        Integer userId = body.get("userId");
        return recipeService.getFavoriteRecipes(userId);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/saveRecipeImg/{recipeId}")
    public ResponseEntity<?> saveRecipeImg(@RequestParam("image") MultipartFile multipartFile, @PathVariable Integer recipeId) throws IOException {
        Recipe recipe = recipeService.getRecipe(recipeId);
        if(recipe == null || multipartFile == null || multipartFile.getOriginalFilename() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String uploadDir = "src/main/resources/public/images/recipe-photos/" + recipe.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            String dbDir = "/public/images/recipe-photos/" + recipe.getId() + "/" + fileName;
            recipeService.updateRecipeImg(dbDir, recipe.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
