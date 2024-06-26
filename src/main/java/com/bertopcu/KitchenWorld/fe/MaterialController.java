package com.bertopcu.KitchenWorld.fe;

import com.bertopcu.KitchenWorld.model.Material;
import com.bertopcu.KitchenWorld.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/materials")
public class MaterialController {
    @Autowired
    MaterialService materialService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/all")
    public List<Material> list() {
        return materialService.listAllMaterials();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/{id}")
    public ResponseEntity<Material> get(@PathVariable Integer id) {
        try {
            Material material = materialService.getMaterial(id);
            return new ResponseEntity<Material>(material, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Material>(HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/")
    public void add(@RequestBody Material material) {
        materialService.saveMaterial(material);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Material material, @PathVariable Integer id) {
        try {
            Material existMaterial = materialService.getMaterial(id);
            material.setId(id);
            materialService.saveMaterial(material);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        materialService.deleteMaterial(id);
    }
}
