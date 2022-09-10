package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.jpa_repo.FavoriteRepository;
import com.bertopcu.KitchenWorld.jpa_repo.RecipeMaterialRepository;
import com.bertopcu.KitchenWorld.jpa_repo.RecipeRepository;
import com.bertopcu.KitchenWorld.jpa_repo.UserRepository;
import com.bertopcu.KitchenWorld.model.Recipe;
import com.bertopcu.KitchenWorld.model.RecipeMaterial;
import com.bertopcu.KitchenWorld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeMaterialRepository recipeMaterialRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    public List<Recipe> listAllRecipes() {
        List<Recipe> recipeList = recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        for(Recipe recipe : recipeList) {
            recipe.setMaterialList(this.getRecipeMaterials(recipe.getId()));
            recipe.setRecipeOwner(this.getRecipeOwner(recipe.getUserId()));
            recipe.setFavCount(this.favoriteRepository.getFavCountForRecipe(recipe.getId()));
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
        recipe.setRecipeOwner(this.getRecipeOwner(recipe.getUserId()));
        recipe.setFavCount(this.favoriteRepository.getFavCountForRecipe(recipe.getId()));
        return recipe;
    }

    public void deleteRecipe(Integer id) {
        Recipe recipe = recipeRepository.findById(id).get();
        recipe.setMaterialList(this.getRecipeMaterials(recipe.getId()));
        for(RecipeMaterial rm : recipe.getMaterialList()) {
            recipeMaterialRepository.deleteById(rm.getId());
        }
        recipeRepository.deleteRecipeFavs(id);
        recipeRepository.deleteById(id);
    }

    public List<RecipeMaterial> getRecipeMaterials(Integer recipeId) {
        List<RecipeMaterial> recipeMaterialList = (List<RecipeMaterial>) recipeMaterialRepository.findByRecipeId(recipeId);
        return recipeMaterialList;
    }

    public List<Recipe> getRecipesByCategory(int categoryId) {
        List<Recipe> recipeList = recipeRepository.findByCategoryId(categoryId);
        for(Recipe recipe : recipeList) {
            recipe.setMaterialList(this.getRecipeMaterials(recipe.getId()));
            recipe.setRecipeOwner(this.getRecipeOwner(recipe.getUserId()));
            recipe.setFavCount(this.favoriteRepository.getFavCountForRecipe(recipe.getId()));
        }
        return recipeList;
    }

    public List<Recipe> getRecipesByName(String recipeName) {
        List<Recipe> recipeList = recipeRepository.findByRecipeName(recipeName);
        for(Recipe recipe : recipeList) {
            recipe.setMaterialList(this.getRecipeMaterials(recipe.getId()));
            recipe.setRecipeOwner(this.getRecipeOwner(recipe.getUserId()));
            recipe.setFavCount(this.favoriteRepository.getFavCountForRecipe(recipe.getId()));
        }
        return recipeList;
    }

    public List<Recipe> getRecipesByMaterials(ArrayList<Integer> materialIds) {
        List<Recipe> recipeList = recipeRepository.findByMaterials(materialIds);
        for(Recipe recipe : recipeList) {
            recipe.setMaterialList(this.getRecipeMaterials(recipe.getId()));
            recipe.setRecipeOwner(this.getRecipeOwner(recipe.getUserId()));
            recipe.setFavCount(this.favoriteRepository.getFavCountForRecipe(recipe.getId()));
        }
        return recipeList;
    }

    public List<Recipe> getFavoriteRecipes(Integer userId) {
        List<Recipe> recipeList = recipeRepository.getFavRecipes(userId);
        for(Recipe recipe : recipeList) {
            recipe.setMaterialList(this.getRecipeMaterials(recipe.getId()));
            recipe.setRecipeOwner(this.getRecipeOwner(recipe.getUserId()));
            recipe.setFavCount(this.favoriteRepository.getFavCountForRecipe(recipe.getId()));
        }
        return recipeList;
    }

    public User getRecipeOwner(Integer userId) {
        User recipeOwner = userRepository.findById(userId).get();
        return recipeOwner;
    }
}
