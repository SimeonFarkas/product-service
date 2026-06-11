package se.jensen.simeon.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import se.jensen.simeon.productservice.client.ProductClient;
import se.jensen.simeon.productservice.controller.ProductController;
import se.jensen.simeon.productservice.security.JwtFilter;
import se.jensen.simeon.productservice.security.JwtUtil;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class JwtFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductClient productClient;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private JwtFilter jwtFilter;

    @Test
    void getProducts_withoutToken_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getProducts_withInvalidToken_shouldReturn401() throws Exception {
        when(jwtUtil.isTokenValid("invalidtoken")).thenReturn(false);

        mockMvc.perform(get("/api/products")
                        .header("Authorization", "Bearer invalidtoken"))
                .andExpect(status().isUnauthorized());
    }
}