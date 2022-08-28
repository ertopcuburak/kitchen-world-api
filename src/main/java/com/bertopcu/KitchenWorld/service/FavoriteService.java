package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.jpa_repo.FavoriteRepository;
import com.bertopcu.KitchenWorld.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    public List<Favorite> listAllFavorites() {
        return favoriteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void saveFavorite(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    public Favorite getFavorite(Integer id) {
        return favoriteRepository.findById(id).get();
    }

    public void deleteFavorite(Integer id) {
        favoriteRepository.deleteById(id);
    }

    public List<Favorite> listAllFavsByUserId(Integer userId) {
        return favoriteRepository.getAllFavsByUserId(userId);
    }
}
