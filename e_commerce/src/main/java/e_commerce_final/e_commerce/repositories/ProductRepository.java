package e_commerce_final.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commerce_final.e_commerce.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    Product findByCodP(String codP);
}
