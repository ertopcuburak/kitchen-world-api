package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.jpa_repo.FavoriteRepository;
import com.bertopcu.KitchenWorld.model.Favorite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    Logger logger = LoggerFactory.getLogger(FavoriteService.class);
    public List<Favorite> listAllFavorites() {
        return favoriteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void saveFavorite(Favorite favorite) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::saveFavorite starts:: ::loggedinUser:: {} ::param:: {}", currentPrincipalName, favorite.getRecipeId());
        try {
            favoriteRepository.save(favorite);
            logger.debug("::saveFavorite success for:: {}", favorite.getRecipeId());
        } catch (Exception e) {
            logger.error("::error at saveFavorite::", e);
            throw e;
        }
    }

    public Favorite getFavorite(Integer id) {
        return favoriteRepository.findById(id).get();
    }

    public void deleteFavorite(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::deleteFavorite starts:: ::loggedinUser:: {} ::param:: {}", currentPrincipalName, id);
        try {
            favoriteRepository.deleteById(id);
            logger.debug("::deleteFavorite success for:: {}", id);
        } catch (Exception e) {
            logger.error("::error at deleteFavorite::", e);
            throw e;
        }
    }

    public List<Favorite> listAllFavsByUserId(Integer userId) {
        return favoriteRepository.getAllFavsByUserId(userId);
    }

    public void deleteFavByUserAndRecipe(Integer userId, Integer recipeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::deleteFavByUserAndRecipe starts:: ::loggedinUser:: {} ::param-recipe:: {}", currentPrincipalName, recipeId);
        try {
            favoriteRepository.deleteByUserAndRecipe(userId, recipeId);
            logger.debug("::deleteFavByUserAndRecipe success for:: {}", recipeId);
        } catch (Exception e) {
            logger.error("::error at deleteFavByUserAndRecipe::", e);
            throw e;
        }
    }
}
