package com.example.cart.service;

import com.example.cart.entities.Cart;
import com.example.cart.entities.CartItem;
import com.example.cart.repositories.CartItemRepository;
import com.example.cart.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CartService {

        @Autowired
        private CartRepository cartRepository;
        private CartItemRepository cartItemRepository;

        public Cart addCartItemToCart(CartItem cItem){

            long idCart = cartRepository.findLastCartId();
            Cart cart = cartRepository.getById(idCart);

            //recherche en base
            Optional<CartItem> itemBase = cart.getProducts().stream().filter(productCart -> productCart.getProductId().equals(cItem.getProductId())).findFirst();

            if(itemBase.isEmpty()){
                cart.addProduct(cItem);
                return cartRepository.save(cart);
            }
            else{
               CartItem item = itemBase.get();
               int totalQuantity = item.getQuantity() + cItem.getQuantity();
               item.setQuantity(totalQuantity);
               cartItemRepository.save(item);
            }

            return cartRepository.getById(idCart);
        }

}

