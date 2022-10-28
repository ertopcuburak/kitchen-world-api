package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.jpa_repo.*;
import com.bertopcu.KitchenWorld.model.Category;
import com.bertopcu.KitchenWorld.model.Recipe;
import com.bertopcu.KitchenWorld.model.RecipeMaterial;
import com.bertopcu.KitchenWorld.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private CategoryRepository categoryRepository;

    Logger logger = LoggerFactory.getLogger(RecipeService.class);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::saveRecipe starts:: {} ::loggedinUser:: {}", recipe.getName(), currentPrincipalName);
        try {
            recipeRepository.save(recipe);
            recipeRepository.flush();
            int recipeId = recipe.getId();
            //System.out.println("::saveRecipe - recipeId:: "+recipeId);
            for(RecipeMaterial rm : recipe.getMaterialList()) {
                //System.out.println("::saveRecipe - materialList item:: "+rm.getMaterialName());
                rm.setRecipeId(recipeId);
                recipeMaterialRepository.save(rm);
            }
            logger.debug("::saveRecipe:: {}", recipe.getName());
        } catch(Exception e) {
            logger.error("::error at saveRecipe::", e);
            throw e;
        }
    }

    public Recipe getRecipe(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Recipe recipe = null;
        logger.debug("::getRecipe starts ::loggedinUser:: {} parameter(s):: {}", currentPrincipalName, id);
        try {
            recipe = recipeRepository.findById(id).get();
            recipe.setMaterialList(this.getRecipeMaterials(id));
            recipe.setRecipeOwner(this.getRecipeOwner(recipe.getUserId()));
            recipe.setFavCount(this.favoriteRepository.getFavCountForRecipe(recipe.getId()));
            logger.debug("::getRecipe for:: {}", recipe.getName());
        } catch(Exception e) {
            logger.error("::error at getRecipe::", e);
            throw e;
        }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::getRecipesByCategory starts:: ::param(s):: {} ::loggedinUser:: {}", categoryId, currentPrincipalName);
        List<Recipe> recipeList = recipeRepository.findByCategoryId(categoryId);
        for(Recipe recipe : recipeList) {
            recipe.setMaterialList(this.getRecipeMaterials(recipe.getId()));
            recipe.setRecipeOwner(this.getRecipeOwner(recipe.getUserId()));
            recipe.setFavCount(this.favoriteRepository.getFavCountForRecipe(recipe.getId()));
        }
        Category selectedCategory = categoryRepository.findById(categoryId).get();
        logger.debug("::getRecipesByCategory:: {} ::total size:: {}", selectedCategory.getName(), recipeList.size());
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        List<Recipe> recipeList = null;
        logger.debug("::getRecipesByMaterials ::loggedinUser:: {} parameter(s):: {}", currentPrincipalName, materialIds);
        try {
            recipeList = recipeRepository.findByMaterials(materialIds);
            for(Recipe recipe : recipeList) {
                recipe.setMaterialList(this.getRecipeMaterials(recipe.getId()));
                recipe.setRecipeOwner(this.getRecipeOwner(recipe.getUserId()));
                recipe.setFavCount(this.favoriteRepository.getFavCountForRecipe(recipe.getId()));
            }
            logger.debug("::getRecipesByMaterials resultSize:: {} ", recipeList.size());
        } catch(Exception e) {
            logger.error("::error at getRecipesByMaterials::", e);
            throw e;
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

    public void updateRecipeImg(String imgUrl,Integer id) {
        recipeRepository.updateRecipeImg(imgUrl, id);
    }
}
