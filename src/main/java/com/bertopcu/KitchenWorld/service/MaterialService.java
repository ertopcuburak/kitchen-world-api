package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.jpa_repo.MaterialRepository;
import com.bertopcu.KitchenWorld.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    public List<Material> listAllMaterials() {
        return materialRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void saveMaterial(Material material) {
        materialRepository.save(material);
    }

    public Material getMaterial(Integer id) {
        return materialRepository.findById(id).get();
    }

    public void deleteMaterial(Integer id) {
        materialRepository.deleteById(id);
    }
}
