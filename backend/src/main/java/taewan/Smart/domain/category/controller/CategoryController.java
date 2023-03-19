package taewan.Smart.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.category.dto.CategoryInfoDto;
import taewan.Smart.domain.category.dto.CategorySaveDto;
import taewan.Smart.domain.category.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryInfoDto> searchAll() {
        return categoryService.findAll();
    }

    @PostMapping
    public void upload(@ModelAttribute CategorySaveDto dto) {
        categoryService.save(dto);
    }
}
