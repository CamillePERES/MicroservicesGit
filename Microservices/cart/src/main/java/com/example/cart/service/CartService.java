package com.example.cart.service;

import com.example.cart.entities.Cart;
import com.example.cart.entities.CartItem;
import com.example.cart.repositories.CartItemRepository;
import com.example.cart.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

        @Autowired
        private CartRepository cartRepository;
        @Autowired
        private CartItemRepository cartItemRepository;

        public Cart addCartItemToCart(CartItem cItem){

            long idCart = cartRepository.findLastCartId();
            Optional<Cart> cartO = cartRepository.findById(idCart);

            if(cartO.isEmpty())
                return null;

            Cart cart = cartO.get();

            //recherche en base
            Optional<CartItem> itemBase = cart.getProducts().stream().filter(productCart -> productCart.getProductId().equals(cItem.getProductId())).findFirst();

            if(itemBase.isEmpty()){
                cart.addProduct(cItem);
                return cartRepository.saveAndFlush(cart);
            }
            else{
               CartItem item = itemBase.get();
               int totalQuantity = item.getQuantity() + cItem.getQuantity();
               item.setQuantity(totalQuantity);
               cartItemRepository.saveAndFlush(item);
            }

            return cartRepository.getById(idCart);
        }

}

