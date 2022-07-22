package com.bertopcu.KitchenWorld.jpa_repo;

import com.bertopcu.KitchenWorld.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
}
