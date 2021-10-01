package com.example.cart.repositories;

import com.example.cart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value="SELECT max(c.id) FROM CART c", nativeQuery = true)
    Long findLastCartId();
}
