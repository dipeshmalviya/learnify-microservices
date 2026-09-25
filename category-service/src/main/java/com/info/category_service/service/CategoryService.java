package com.info.category_service.service;

import com.info.category_service.dto.CategoryResponseDTO;
import com.info.category_service.entity.Category;
import com.info.category_service.exception.ResourceAlreadyExistsException;
import com.info.category_service.exception.ResourceNotFoundException;
import com.info.category_service.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo repo;

    public CategoryResponseDTO create(Category category, Long userId){

        if(repo.existsByNameIgnoreCase(category.getName())){
            throw new ResourceAlreadyExistsException("Category already exists");
        }

        category.setCreatedBy(userId);

        Category saved = repo.save(category);

        return map(saved);
    }

    public List<CategoryResponseDTO> getAll(){

        return repo.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public CategoryResponseDTO getById(Long id){

        Category cat = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return map(cat);
    }

    private CategoryResponseDTO map(Category c){

        CategoryResponseDTO dto = new CategoryResponseDTO();

        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setDescription(c.getDescription());
        dto.setCreatedAt(c.getCreatedAt());

        return dto;
    }
}
