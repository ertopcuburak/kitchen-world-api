package com.bertopcu.KitchenWorld.jpa_repo;

import com.bertopcu.KitchenWorld.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query(value = "SELECT recipe.*, COUNT(favorite.id) as fav_count FROM recipe LEFT JOIN favorite on favorite.recipe_id = recipe.id WHERE recipe.id = ?1", nativeQuery = true)
    Recipe findByRecipeId(Integer id);

    @Query(value = "SELECT recipe.*, COUNT(favorite.id) as fav_count FROM recipe LEFT JOIN favorite on favorite.recipe_id = recipe.id WHERE category_id = ?1 GROUP BY recipe.id ORDER BY id DESC", nativeQuery = true)
    List<Recipe> findByCategoryId(Integer categoryId);

    @Query(value = "SELECT recipe.*, COUNT(favorite.id) as fav_count FROM recipe LEFT JOIN favorite on favorite.recipe_id = recipe.id WHERE name LIKE %?1% GROUP BY recipe.id ORDER BY id DESC", nativeQuery = true)
    List<Recipe> findByRecipeName(String recipeName);

    @Query(value = "SELECT DISTINCT recipe.*, COUNT(favorite.id) as fav_count FROM recipe INNER JOIN recipe_material ON recipe_material.recipe_id = recipe.id LEFT JOIN material ON recipe_material.material_id = material.id LEFT JOIN favorite on favorite.recipe_id = recipe.id WHERE material.id IN (?1) GROUP BY recipe.id ORDER BY COUNT(*) DESC", nativeQuery = true)
    List<Recipe> findByMaterials(ArrayList<Integer> materialIds);

    @Query(value = "SELECT recipe.*, (SELECT COUNT(id) FROM favorite WHERE recipe_id = recipe.id) as fav_count FROM recipe LEFT JOIN favorite ON favorite.recipe_id = recipe.id WHERE favorite.user_id = ?1 GROUP BY recipe.id ORDER BY favorite.fav_date DESC", nativeQuery = true)
    List<Recipe> getFavRecipes(Integer userId);
}
