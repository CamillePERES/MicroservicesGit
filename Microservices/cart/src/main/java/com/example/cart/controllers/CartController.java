package com.example.cart.controllers;

import com.example.cart.entities.Cart;
import com.example.cart.entities.CartItem;
import com.example.cart.repositories.CartItemRepository;
import com.example.cart.repositories.CartRepository;
import com.example.cart.service.CartService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartService cartService;

    @PostMapping(value="/cart")
    public ResponseEntity<Cart> createNewCart(@RequestBody Cart cartData){
        Cart cart = cartRepository.save(cartData);

        if (cart.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cart");

        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }

    @GetMapping(value="/cart/{id}")
    public Optional<Cart> getCart(@PathVariable Long id){

        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");

        return cart;
    }

    @PostMapping(value = "/cart/{id}")
    @Transactional
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long id, @RequestBody CartItem cartItem)
    {
        Cart cart;

        try {
            cart = cartRepository.getById(id);

        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart", e);

        }
        cart.addProduct(cartItem);
        cartRepository.save(cart);
        return new ResponseEntity<CartItem>(cartItem, HttpStatus.CREATED);
    }

    @GetMapping(value="/lastCart")
    public Optional<Cart> getLastCart(){

        long id = cartRepository.findLastCartId();
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get last cart");

        return cart;
    }

    @PostMapping(value="/cart/addProduct")
    @Transactional
    public ResponseEntity<Cart> addProduct(@RequestBody CartItem cartItem){
        if(cartItem == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameter is null");
        }
        Cart c = cartService.addCartItemToCart(cartItem);
        return ResponseEntity.ok(c);
    }

    @PostMapping(value="/cart/newCart")
    @Transactional
    public ResponseEntity<Cart> newCart(){
        Cart c = cartService.newCart();
        return ResponseEntity.ok(c);
    }

}

