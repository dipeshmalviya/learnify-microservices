package com.info.category_service.repo;


import com.info.category_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase(String name);


}
