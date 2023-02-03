package taewan.Smart.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;
import taewan.Smart.product.service.ProductService;

import javax.validation.Valid;

@RestController
@RequestMapping("products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ProductInfoDto searchOne(@PathVariable Long productId) {
        return productService.findOne(productId);
    }

    @GetMapping
    public Page<ProductInfoDto> searchAll(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @GetMapping("/filter")
    public Page<ProductInfoDto> searchAllWithFilter(@RequestParam(name = "code", defaultValue = "") String code,
                                                    @RequestParam(name = "search", defaultValue = "") String search,
                                                    Pageable pageable) {
        return productService.findAllWithFilter(pageable, code, search);
    }

    @PostMapping
    public Long upload(@Valid ProductSaveDto dto) {
        return productService.save(dto);
    }

    @PostMapping("/update")
    public Long update(ProductUpdateDto dto) {
        return productService.modify(dto);
    }

    @PostMapping("/{productId}/delete")
    public ResponseEntity remove(@PathVariable Long productId) {
        productService.delete(productId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
