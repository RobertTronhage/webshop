package se.tronhage.webshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import se.tronhage.webshop.controller.ProductController;
import se.tronhage.webshop.enums.Category;
import se.tronhage.webshop.repository.ProductRepo;
import se.tronhage.webshop.services.ProductService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
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
    void testAddProductFormGet() throws Exception {
        mockMvc.perform(get("/addnewproduct"))
                .andExpect(status().isOk())
                .andExpect(view().name("addnewproduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testAddProductPost() throws Exception {
        mockMvc.perform(post("/addnewproduct")
                        .param("name", "New Product")
                        .param("category", Category.PUTTER.toString())
                        .param("description", "A brand new product")
                        .param("price", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
        verify(productService).createNewProduct("New Product", PUTTER, "A brand new product", 100);
    }
}