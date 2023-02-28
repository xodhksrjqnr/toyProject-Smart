package taewan.Smart.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.category.dto.CategoryInfoDto;
import taewan.Smart.domain.category.dto.CategorySaveDto;
import taewan.Smart.domain.category.repository.CategoryItemRepository;
import taewan.Smart.domain.category.repository.CategoryRepository;

import java.util.List;
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
        categoryItemRepository.findByName(dto.getMiddleClassification())
                .ifPresent(c -> {throw CATEGORY_NAME_DUPLICATE.exception();});

        StringBuilder categoryCode = new StringBuilder("");
        StringBuilder categoryItemCode = new StringBuilder("");

        categoryRepository.findByName(dto.getClassification())
                .ifPresentOrElse(
                        c -> {
                            categoryCode.append(c.getCode());
                            categoryItemCode.append(String.format("%02d", c.getCategoryItems().size() + 1));
                        },
                        () -> {
                            categoryCode.append(Character.toString('A' + (int)categoryRepository.count()));
                            categoryItemCode.append("01");
                        });

        return categoryRepository.save(dto.toEntity(categoryCode.toString(), categoryItemCode.toString())).getCategoryId();
    }

    public List<CategoryInfoDto> findAll() {
        return categoryRepository.findAll(Sort.by("code"))
                .stream().map(CategoryInfoDto::new).collect(Collectors.toList());
    }
}