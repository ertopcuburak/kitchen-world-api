package com.bertopcu.KitchenWorld.jpa_repo;

import com.bertopcu.KitchenWorld.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    @Query(value = "SELECT * FROM favorite WHERE user_id = ?1 ORDER BY fav_date DESC", nativeQuery = true)
    List<Favorite> getAllFavsByUserId(Integer userId);
}
