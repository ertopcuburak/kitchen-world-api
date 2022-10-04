package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.jpa_repo.MaterialRepository;
import com.bertopcu.KitchenWorld.model.Material;
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
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    Logger logger = LoggerFactory.getLogger(MaterialService.class);
    public List<Material> listAllMaterials() {
        return materialRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void saveMaterial(Material material) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::saveMaterial starts:: ::loggedinUser:: {} ::paramName:: {}", currentPrincipalName, material.getName());
        try {
            materialRepository.save(material);
            logger.debug("::saveMaterial success:: {}", material.getName());
        } catch (Exception e) {
            logger.error("::error at saveMaterial::", e);
            throw e;
        }
    }

    public Material getMaterial(Integer id) {
        return materialRepository.findById(id).get();
    }

    public void deleteMaterial(Integer id) {
        materialRepository.deleteById(id);
    }
}
