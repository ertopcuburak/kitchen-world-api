package com.bertopcu.KitchenWorld.fe;

import com.bertopcu.KitchenWorld.model.Favorite;
import com.bertopcu.KitchenWorld.model.Material;
import com.bertopcu.KitchenWorld.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
