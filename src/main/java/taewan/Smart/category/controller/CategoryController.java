package taewan.Smart.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taewan.Smart.category.service.CategoryService;
import taewan.Smart.category.service.CategoryServiceImpl;

import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Map<String, Map<String, Long>> searchAll() {
        return categoryService.searchAll();
    }
}
