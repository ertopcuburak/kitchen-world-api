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
        List<Recipe> recipeList = recipeRepository.findAll();
        for(Recipe recipe : recipeList) {
            recipe.setMaterialList(this.getRecipeMaterials(recipe.getId()));
        }
        return recipeList;
    }

    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
        recipeRepository.flush();
        int recipeId = recipe.getId();
        //System.out.println("::saveRecipe - recipeId:: "+recipeId);
        for(RecipeMaterial rm : recipe.getMaterialList()) {
            //System.out.println("::saveRecipe - materialList item:: "+rm.getMaterialName());
            rm.setRecipeId(recipeId);
            recipeMaterialRepository.save(rm);
        }
    }

    public Recipe getRecipe(Integer id) {
        Recipe recipe = recipeRepository.findById(id).get();
        recipe.setMaterialList(this.getRecipeMaterials(id));
        return recipe;
    }

    public void deleteRecipe(Integer id) {
        Recipe recipe = recipeRepository.findById(id).get();
        recipe.setMaterialList(this.getRecipeMaterials(recipe.getId()));
        for(RecipeMaterial rm : recipe.getMaterialList()) {
            recipeMaterialRepository.deleteById(rm.getId());
        }
        recipeRepository.deleteById(id);
    }

    public List<RecipeMaterial> getRecipeMaterials(Integer recipeId) {
        List<RecipeMaterial> recipeMaterialList = (List<RecipeMaterial>) recipeMaterialRepository.findByRecipeId(recipeId);
        return recipeMaterialList;
    }
}
