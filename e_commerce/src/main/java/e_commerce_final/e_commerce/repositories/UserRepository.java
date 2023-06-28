package e_commerce_final.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commerce_final.e_commerce.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
