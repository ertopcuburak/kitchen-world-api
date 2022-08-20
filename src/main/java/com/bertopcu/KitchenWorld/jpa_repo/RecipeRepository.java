package com.bertopcu.KitchenWorld.jpa_repo;

import com.bertopcu.KitchenWorld.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query(value = "SELECT * FROM recipe WHERE category_id = ?1 ORDER BY id DESC", nativeQuery = true)
    List<Recipe> findByCategoryId(Integer categoryId);

    @Query(value = "SELECT * FROM recipe WHERE name LIKE %?1% ORDER BY id DESC", nativeQuery = true)
    List<Recipe> findByRecipeName(String recipeName);

    @Query(value = "SELECT recipe.* FROM recipe INNER JOIN recipe_material ON recipe_material.recipe_id = recipe.id LEFT JOIN material ON recipe_material.material_id = material.id WHERE material.id IN (?1) ORDER BY recipe.id DESC", nativeQuery = true)
    List<Recipe> findByMaterials(ArrayList<Integer> materialIds);
}
