package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductServiceIT {
    @Autowired
    private ProductService service;
    @Autowired
    private ProductRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;
    @BeforeEach
    void SetUp()throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }
//    @Test
//    public void deleteShouldDeleteResourceWhenIdExists(){
//        service.delete(existingId);
//
//        assertEquals(countTotalProducts -1,repository.count());
//    }
//
//    @Test
//    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
//        assertThrows(ResourceNotFoundException.class,() -> service.delete(nonExistingId));
//    }
//
//    @Test
//    public void findAllPagedShouldReturnPageWhenPage0Size10(){
//        PageRequest pageRequest = PageRequest.of(0,10);
//
//        Page<ProductDTO> result =  service.findAllPaged(pageRequest);
//
//        assertFalse(result.isEmpty());
//        assertEquals(0,result.getNumber());
//        assertEquals(10,result.getSize());
//        assertEquals(countTotalProducts,result.getTotalElements());
//    }
//
//    @Test
//    public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist(){
//        PageRequest pageRequest = PageRequest.of(50,10);
//
//        Page<ProductDTO> result =  service.findAllPaged(pageRequest);
//
//        assertTrue(result.isEmpty());
//    }
}
