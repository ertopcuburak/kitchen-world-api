package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.jpa_repo.CategoryRepository;
import com.bertopcu.KitchenWorld.model.Category;
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
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    Logger logger = LoggerFactory.getLogger(CategoryService.class);
    public List<Category> listAllCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "orderVal"));
    }

    public void saveCategory(Category category) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::saveCategory starts:: ::loggedinUser:: {}", currentPrincipalName);
        try {
            categoryRepository.save(category);
            logger.debug("::saveCategory success:: {}", category.getName());
        } catch (Exception e) {
            logger.error("::error at saveCategory::", e);
            throw e;
        }
    }

    public Category getCategory(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public void deleteCategory(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::deleteCategory starts:: ::loggedinUser:: {} :: param(s):: {}", currentPrincipalName, id);
        try {
            categoryRepository.deleteById(id);
            logger.debug("::deleteCategory success:: {}", id);
        } catch (Exception e) {
            logger.error("::error at deleteCategory::", e);
            throw e;
        }
    }
}
