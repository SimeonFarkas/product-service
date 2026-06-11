package se.jensen.simeon.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import se.jensen.simeon.productservice.client.ProductClient;
import se.jensen.simeon.productservice.controller.ProductController;
import se.jensen.simeon.productservice.model.Product;
import se.jensen.simeon.productservice.model.Rating;
import se.jensen.simeon.productservice.security.JwtUtil;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductClient productClient;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    void getProducts_shouldReturnList() throws Exception {
        List<Product> products = List.of(
                new Product(1, "Test Product", 99.99, "Description", "category", "image.jpg", new Rating(4.0, 100))
        );

        when(productClient.getProducts()).thenReturn(products);
        when(jwtUtil.isTokenValid(any())).thenReturn(true);

        mockMvc.perform(get("/api/products")
                        .header("Authorization", "Bearer faketoken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Product"));
    }
}