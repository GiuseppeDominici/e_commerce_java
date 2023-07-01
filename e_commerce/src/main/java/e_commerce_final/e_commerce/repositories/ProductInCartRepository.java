package e_commerce_final.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commerce_final.e_commerce.entities.Product;
import e_commerce_final.e_commerce.entities.ProductInCart;
import e_commerce_final.e_commerce.entities.User;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, Integer>{
    ProductInCart findByUserAndProduct(User u, Product p);
}
