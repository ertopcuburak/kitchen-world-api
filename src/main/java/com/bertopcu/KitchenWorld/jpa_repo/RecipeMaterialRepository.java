package com.bertopcu.KitchenWorld.jpa_repo;

import com.bertopcu.KitchenWorld.model.RecipeMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeMaterialRepository extends JpaRepository<RecipeMaterial, Integer> {
    @Query(value = "SELECT rm.id, rm.recipe_id, rm.material_id, rm.quantity, ma.name as material_name FROM recipe_material as rm INNER JOIN material as ma ON rm.material_id = ma.id WHERE rm.recipe_id = ?1", nativeQuery = true)
    List<RecipeMaterial> findByRecipeId(Integer recipeId);
}
