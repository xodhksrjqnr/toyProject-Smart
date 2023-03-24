package taewan.Smart.domain.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.category.dto.CategoryInfoDto;
import taewan.Smart.domain.category.dto.CategorySaveDto;
import taewan.Smart.domain.category.repository.CategoryItemRepository;
import taewan.Smart.domain.category.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static taewan.Smart.global.error.ExceptionStatus.CATEGORY_NAME_DUPLICATE;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;

    @Transactional
    public void save(CategorySaveDto dto) {
        categoryItemRepository.findByName(dto.getMiddleClassification())
                .ifPresent(c -> {throw CATEGORY_NAME_DUPLICATE.exception();});
        categoryRepository.findByName(dto.getClassification())
                .ifPresentOrElse(
                        c -> {
                            String newCode = String.format("%02d", c.getCategoryItems().size() + 1);
                            c.addCategoryItem(dto.toItemEntity(newCode));
                            log.info("[새로 추가된 카테고리] : Category : {}, CategoryItem : {}", c.getCode(), newCode);
                        },
                        () -> {
                            String newCode = Character.toString('A' + (int)categoryRepository.count());
                            categoryRepository.save(dto.toEntity(newCode, "01"));
                            log.info("[새로 추가된 카테고리] : Category : {}, CategoryItem : {}", newCode, "01");
                        });
    }

    public List<CategoryInfoDto> findAll() {
        Map<Long, CategoryInfoDto> result = new HashMap<>();

        categoryRepository.findAll().forEach(c -> {
            if (!result.containsKey(c.getCategoryId())) {
                result.put(c.getCategoryId(), c.toInfoDto());
            }
            c.getCategoryItems().forEach(ci -> {
                result.get(c.getCategoryId())
                        .getCategoryItemInfoDtoList()
                        .add(ci.toInfoDto());
            });
        });
        return new ArrayList<>(result.values());
    }
}