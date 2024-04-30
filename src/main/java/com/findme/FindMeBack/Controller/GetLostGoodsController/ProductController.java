//package com.findme.FindMeBack.Controller.GetLostGoodsController;
//
//import com.findme.FindMeBack.Entity.Product;
//import com.findme.FindMeBack.Service.ProductService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//public class ProductController {
//    private final ProductService productService;
//
//    @CrossOrigin
//    @PostMapping("/product/save")
//    public Product productSave(@RequestBody Product product) {
//        return productService.save(product).get();
//    }
//    @CrossOrigin
//    @GetMapping("/product/search")
//    public Product productSearch(@RequestParam Long id){
//        Optional<Product> product = productService.findById(id);
//        return product.get();
//    }
//
//
//}
