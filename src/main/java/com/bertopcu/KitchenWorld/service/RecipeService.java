package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.jpa_repo.RecipeMaterialRepository;
import com.bertopcu.KitchenWorld.jpa_repo.RecipeRepository;
import com.bertopcu.KitchenWorld.model.Recipe;
import com.bertopcu.KitchenWorld.model.RecipeMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeMaterialRepository recipeMaterialRepository;
    public List<Recipe> listAllRecipes() {
        return recipeRepository.findAll();
    }

    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public Recipe getRecipe(Integer id) {
        Recipe recipe = recipeRepository.findById(id).get();
        recipe.setMaterialList(this.getRecipeMaterials(id));
        return recipe;
    }

    public void deleteRecipe(Integer id) {
        recipeRepository.deleteById(id);
    }

    public List<RecipeMaterial> getRecipeMaterials(Integer recipeId) {
        List<RecipeMaterial> recipeMaterialList = (List<RecipeMaterial>) recipeMaterialRepository.findByRecipeId(recipeId);
        return recipeMaterialList;
    }
}
