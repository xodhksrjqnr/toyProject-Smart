package taewan.Smart.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/testdata")
    public String form() {
        return "form";
    }
}
