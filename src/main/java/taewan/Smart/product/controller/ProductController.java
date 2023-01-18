package taewan.Smart.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;
import taewan.Smart.product.service.ProductService;

import java.util.List;

@Slf4j
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
    public List<ProductInfoDto> searchAll() {
        return productService.findAll();
    }

    @GetMapping("/filter")
    public List<ProductInfoDto> searchAllWithFilter(@RequestParam(name = "code") Long code,
                                                    @RequestParam(name = "option") Long option,
                                                    @RequestParam(name = "gender") Integer gender) {
        return productService.findAllWithFilter(code, option, gender);
    }

    @PostMapping
    public Long upload(ProductSaveDto dto) {
        return productService.save(dto);
    }

    @PostMapping("/update")
    public Long update(ProductUpdateDto dto) {
        return productService.modify(dto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity remove(@PathVariable Long productId) {
        productService.delete(productId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
