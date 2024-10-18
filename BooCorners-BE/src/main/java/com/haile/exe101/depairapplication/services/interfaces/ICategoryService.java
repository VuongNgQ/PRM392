package com.haile.exe101.depairapplication.services.interfaces;

import com.haile.exe101.depairapplication.models.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category createOrUpdateCategory(Category category);
    boolean deleteCategoryById(Long id);
}
