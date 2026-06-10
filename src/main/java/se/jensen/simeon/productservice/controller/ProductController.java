package se.jensen.simeon.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.simeon.productservice.client.ProductClient;
import se.jensen.simeon.productservice.model.Product;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductClient productClient;

    public ProductController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productClient.getProducts();
    }
}
