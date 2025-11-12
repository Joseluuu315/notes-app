package com.joseluu.notesapp.repository;

import com.joseluu.notesapp.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
