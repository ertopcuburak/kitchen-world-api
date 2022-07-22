package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.jpa_repo.RecipeRepository;
import com.bertopcu.KitchenWorld.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    public List<Recipe> listAllRecipes() {
        return recipeRepository.findAll();
    }

    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public Recipe getRecipe(Integer id) {
        return recipeRepository.findById(id).get();
    }

    public void deleteRecipe(Integer id) {
        recipeRepository.deleteById(id);
    }
}
