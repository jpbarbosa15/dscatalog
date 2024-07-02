package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.test.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenUtil tokenUtil;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;
    private String username;
    private String password;
    private String bearerToken;
    private ProductDTO productDTO;

    @BeforeEach
    void SetUp()throws Exception {
        username = "maria@gmail.com";
        password = "123456";

        bearerToken = tokenUtil.obtainAccessToken(mockMvc, username, password);

        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;

        productDTO = new ProductDTO();
        productDTO.setName("Computer");
        productDTO.setPrice(2000.0);
        productDTO.setDescription("Descriçao teste");
    }

    @Test
    public void findAllShouldReturnSortedPageWhenSortByName()throws Exception{

        ResultActions result =  mockMvc.perform(get("/products?page=0&size=12&sort=name,asc")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.totalElements").value(countTotalProducts));
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception{

        String expectedName = productDTO.getName();

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =  mockMvc.perform(put("/products/{id}",existingId)
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").value(expectedName));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{


        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =  mockMvc.perform(put("/products/{id}",nonExistingId)
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        result.andExpect(status().isNotFound());

    }
}
