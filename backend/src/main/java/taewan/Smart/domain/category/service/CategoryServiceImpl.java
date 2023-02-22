package taewan.Smart.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.category.dto.CategoryInfoDto;
import taewan.Smart.domain.category.dto.CategorySaveDto;
import taewan.Smart.domain.category.entity.Category;
import taewan.Smart.domain.category.repository.CategoryItemRepository;
import taewan.Smart.domain.category.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static taewan.Smart.global.error.ExceptionStatus.CATEGORY_NAME_DUPLICATE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;

    @Transactional
    public Long save(CategorySaveDto dto) {
        Optional<Category> found = categoryRepository.findByName(dto.getClassification());

        categoryItemRepository.findByName(dto.getMiddleClassification())
                .ifPresent(c -> {throw CATEGORY_NAME_DUPLICATE.exception();});

        String categoryCode = found.isPresent() ?
                found.get().getCode() : Character.toString('A' + (int)categoryRepository.count());
        String categoryItemCode = found.isPresent() ?
                String.format("02d", found.get().getCategoryItems().size() + 1) : "01";

        return categoryRepository.save(dto.toEntity(categoryCode, categoryItemCode)).getCategoryId();
    }

    public List<CategoryInfoDto> findAll() {
        return categoryRepository.findAll(Sort.by("code"))
                .stream().map(CategoryInfoDto::new).collect(Collectors.toList());
    }
}
