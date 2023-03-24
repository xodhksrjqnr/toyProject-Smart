package taewan.Smart.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;
import taewan.Smart.domain.product.service.ProductService;

import javax.validation.Valid;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ProductInfoDto searchOne(@PathVariable Long productId) {
        return productService.findOne(productId);
    }

    @GetMapping("/filter")
    public Page<ProductInfoDto> searchAllByFilter(@RequestParam(name = "code", defaultValue = "") String code,
                                                    @RequestParam(name = "search", defaultValue = "") String search,
                                                    Pageable pageable) {
        return productService.findAllByFilter(pageable, code, search);
    }

    @PostMapping
    public Long upload(@Valid @ModelAttribute ProductSaveDto dto) {
        return productService.save(dto);
    }

    @PostMapping("/update")
    public Long modify(@Valid @ModelAttribute ProductUpdateDto dto) {
        return productService.update(dto);
    }

    @PostMapping("/{productId}/delete")
    public void remove(@PathVariable Long productId) {
        productService.delete(productId);
    }
}
