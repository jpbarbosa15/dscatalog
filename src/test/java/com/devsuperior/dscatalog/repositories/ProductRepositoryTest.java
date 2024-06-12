package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;


    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        long productId = 1;

        repository.deleteById(productId);

        Optional<Product> result = repository.findById(productId);

        assertFalse(result.isPresent());
    }
}