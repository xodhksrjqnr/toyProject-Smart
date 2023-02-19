package taewan.Smart.domain.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.category.dto.CategoryInfoDto;
import taewan.Smart.domain.category.dto.CategorySaveDto;
import taewan.Smart.domain.category.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public List<CategoryInfoDto> searchAll() {
        return categoryService.findAll();
    }

    @PostMapping
    public Long upload(@ModelAttribute CategorySaveDto dto) {
        return categoryService.save(dto);
    }
}
