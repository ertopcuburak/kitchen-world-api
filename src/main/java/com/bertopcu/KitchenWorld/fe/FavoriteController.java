package com.bertopcu.KitchenWorld.fe;

import com.bertopcu.KitchenWorld.model.Favorite;
import com.bertopcu.KitchenWorld.model.Material;
import com.bertopcu.KitchenWorld.model.Recipe;
import com.bertopcu.KitchenWorld.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/")
    public void add(@RequestBody Favorite favorite) {
        favoriteService.saveFavorite(favorite);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        favoriteService.deleteFavorite(id);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/getFavsByUser")
    public List<Favorite> getFavorites(@RequestBody Map<String, Integer> body) {
        Integer userId = body.get("userId");
        return favoriteService.listAllFavsByUserId(userId);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/deleteFav")
    public void deleteFavByUserAndRecipe(@RequestBody Map<String, Integer> body) {
        Integer userId = body.get("userId");
        Integer recipeId = body.get("recipeId");
        this.favoriteService.deleteFavByUserAndRecipe(userId, recipeId);
    }
}
