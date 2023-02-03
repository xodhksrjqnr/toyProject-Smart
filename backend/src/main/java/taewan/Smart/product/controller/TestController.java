package taewan.Smart.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.service.ProductService;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    private final ProductService productService;

    @Autowired
    public TestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/testdata")
    public String form() {
        return "form";
    }

    @PostMapping("/testupload")
    public ResponseEntity testup(MultipartHttpServletRequest mr) {
        tmpMethod(mr.getFiles("files"), mr.getFiles("detailInfos"));
        return new ResponseEntity(HttpStatus.OK);
    }

    private void tmpMethod(List<MultipartFile> files, List<MultipartFile> detailInfos) {
        Map<String, String> tmp = Map.of(
                "후드 티셔츠", "A01",
                "맨투맨", "A02",
                "코트", "B01",
                "패딩", "B02",
                "트레이닝 팬츠", "C01",
                "숏팬츠", "C02",
                "구두", "D01",
                "운동화", "D02"
        );
        boolean flag = false;
        int i = 0;
        for (MultipartFile f : files) {
            if (f.isEmpty())
                continue;
            String name = Normalizer.normalize(f.getOriginalFilename(), Normalizer.Form.NFC);
            String code = "";
            for (String key : tmp.keySet()) {
                if (name.contains(key)) {
                    code = tmp.get(key);
                    break;
                }
            }
            ProductSaveDto dto = new ProductSaveDto();
            dto.setCode(code + (flag ? "M" : "W"));
            dto.setName(name + (int)(Math.random() * 1000) + code.substring(0, 1));
            dto.setSize("s,m,l,xl,xxl");
            dto.setPrice((int)(Math.random() * 10 + 1) * 10000);
            List<MultipartFile> save = new ArrayList<>();
            save.add(f);
            dto.setImgFiles(save);
            dto.setDetailInfo(detailInfos.get(i++));
            productService.save(dto);
            flag = !flag;
        }
    }
}
