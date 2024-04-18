package se.tronhage.webshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import se.tronhage.webshop.controller.ProductController;
import se.tronhage.webshop.entity.Product;
import se.tronhage.webshop.enums.Category;
import se.tronhage.webshop.repository.ProductRepo;
import se.tronhage.webshop.services.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static se.tronhage.webshop.enums.Category.PUTTER;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepo productRepo;

    @MockBean
    private ProductService productService;

    @Test
    void testAddProductGet() throws Exception {
        mockMvc.perform(get("/addnewproduct"))
                .andExpect(status().isOk())
                .andExpect(view().name("addnewproduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testAddProductPost() throws Exception {
        mockMvc.perform(post("/addnewproduct")
                        .param("name", "Striker")
                        .param("category", PUTTER.toString())
                        .param("description", "A brand new product")
                        .param("price", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        verify(productService).createNewProduct("Striker", PUTTER,
                "A brand new product", 100);
    }

    @Test
    void testSearchProducts() throws Exception {
        // Mock the search results
        List<Product> mockResults = new ArrayList<>();
        mockResults.add(new Product("Striker", 100, "Description1",
                Category.valueOf("PUTTER")));

        mockResults.add(new Product("Blazer", 200, "Description2",
                Category.valueOf("DISTANCE_DRIVER")));

        when(productService.searchProducts("test")).thenReturn(mockResults);

        // Perform the GET request
        mockMvc.perform(get("/search").param("query", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("webshop"))
                .andExpect(model().attributeExists("searchResults"))
                .andExpect(model().attribute("searchResults", mockResults));
    }
}

