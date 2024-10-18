package com.haile.exe101.depairapplication.controllers.api;

import com.haile.exe101.depairapplication.models.entity.Category;
import com.haile.exe101.depairapplication.models.request.CategoryRequest;
import com.haile.exe101.depairapplication.models.response.BaseResponse;
import com.haile.exe101.depairapplication.services.interfaces.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<BaseResponse<Category>> getCategories() {
        return ResponseEntity.ok(new BaseResponse<>(200, "Get all categories successfully", categoryService.getAllCategories(), null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Category>> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new BaseResponse<>(200, "Get category by id successfully", null, categoryService.getCategoryById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Category>> addCategory(@RequestBody @Valid CategoryRequest category) {
        try {
            Category newCategory = new Category();
            newCategory.setName(category.getCategoryName());

            Category createdCategory = categoryService.createOrUpdateCategory(newCategory);
            return ResponseEntity.ok(new BaseResponse<>(200, "Category added successfully", null, createdCategory));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(500, "Failed to create category: " + ex.getMessage(), null, null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Category>> updateCategory(@PathVariable("id") Long id, @RequestBody @Valid CategoryRequest category) {
        try {
            Category existingCategory = categoryService.getCategoryById(id);
            if (existingCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(404, "Category not found", null, null));
            }
            existingCategory.setName(category.getCategoryName());
            Category createdCategory = categoryService.createOrUpdateCategory(existingCategory);

            return ResponseEntity.ok(new BaseResponse<>(200, "Category added successfully", null, createdCategory));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(500, "Failed to create category: " + ex.getMessage(), null, null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Category>> deleteCategory(@PathVariable("id") Long id) {
        try {
            Category existingCategory = categoryService.getCategoryById(id);
            if (existingCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(404, "Category not found", null, null));
            } else {
                if (!existingCategory.getProducts().isEmpty()) {
                    return ResponseEntity.ok(new BaseResponse<>(400, "This category is linking with other products", null, null));
                }
                categoryService.deleteCategoryById(id);
                return ResponseEntity.ok(new BaseResponse<>(200, "Category deleted successfully", null, null));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(500, "Failed to delete category: " + ex.getMessage(), null, null));
        }
    }
}
