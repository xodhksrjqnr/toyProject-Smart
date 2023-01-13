package taewan.Smart.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;
import taewan.Smart.product.service.ProductService;

@Controller
@RequestMapping("product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public String searchOne(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.findOne(productId));
        return "product/view";
    }

    @GetMapping
    public String searchAll(Model model) {
        model.addAttribute("productList", productService.findAll());
        return "product/list_view";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new ProductSaveDto());
        return "product/write";
    }

    @PostMapping
    public String upload(ProductSaveDto dto) {
        Long saved = productService.save(dto);
        return "redirect:/product/" + saved;
    }

    @GetMapping("/update/{productId}")
    public String updateForm(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.findOne(productId));
        return "product/update";
    }

    @PostMapping("/update")
    public String update(ProductUpdateDto dto) {
        Long updatedId = productService.modify(dto);
        return "redirect:/product/" + updatedId;
    }

    @PostMapping("/delete/{productId}")
    public String remove(@PathVariable Long productId) {
        productService.delete(productId);
        return "redirect:/product";
    }
}
