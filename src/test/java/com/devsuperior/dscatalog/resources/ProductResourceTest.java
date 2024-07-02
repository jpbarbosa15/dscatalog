package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = ProductResource.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService service;
    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;
    private Long existingID;
    private Long nonExistingID;
    @BeforeEach
    void SetUp()throws Exception {
        existingID = 1L;
        nonExistingID = 2L;

        productDTO = new ProductDTO();
        productDTO.setName("Computer");
        productDTO.setPrice(2000.0);
        productDTO.setDescription("Descriçao teste");
        page = new PageImpl<>(List.of(productDTO));
        when(service.findAllPaged(any())).thenReturn(page);

        when(service.findById(existingID)).thenReturn(productDTO);
        when(service.findById(nonExistingID)).thenThrow(ResourceNotFoundException.class);

        when(service.update(eq(existingID),any())).thenReturn(productDTO);
        when(service.update(eq(nonExistingID),any())).thenThrow(ResourceNotFoundException.class);
    }
    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =  mockMvc.perform(put("/products/{id}",existingID)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void updateShouldReturnNotFoundProductDTOWhenIdDoesNotExist() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =  mockMvc.perform(put("/products/{id}",nonExistingID)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPage() throws Exception{
        ResultActions result =  mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception{
        ResultActions result =  mockMvc.perform(get("/products/{id}",existingID).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
//        result.andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundProductWhenIdDoesNotExist() throws Exception{
        ResultActions result =  mockMvc.perform(get("/products/{id}",nonExistingID).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

    }
}