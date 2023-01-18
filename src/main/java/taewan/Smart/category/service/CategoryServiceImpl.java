package taewan.Smart.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taewan.Smart.category.entity.Category;
import taewan.Smart.category.repository.CategoryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Map<String, Map<String, Long>> searchAll() {
        List<Category> found = categoryRepository.findAll();
        Map<String, Map<String, Long>> result = new HashMap<>();

        for (Category c : found) {
            if (!result.containsKey(c.getType1()))
                result.put(c.getType1(), new HashMap<>());
            result.get(c.getType1()).put(c.getType2(), c.getCode());
        }
        return result;
    }
}
