package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findCategoryByCategoryName(String categoryName);
    Category findCategoryByCategoryID(int categoryID);
}
