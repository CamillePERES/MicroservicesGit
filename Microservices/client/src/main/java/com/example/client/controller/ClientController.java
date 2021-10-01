package com.example.client.controller;

import com.example.client.bean.CartBean;
import com.example.client.bean.CartItemBean;
import com.example.client.bean.ProductBean;
import com.example.client.proxy.MsCartProxy;
import com.example.client.proxy.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;
    private MsCartProxy msCartProxy;


    @RequestMapping("/")
    public String index(Model model){

        List<ProductBean> products = msProductProxy.list();
        model.addAttribute("products", products);
        return "index";
    }

    @RequestMapping("/productDetail/{id}")
    public String productDetail(Model model, @PathVariable Long id){
        Optional<ProductBean> product = msProductProxy.get(id);
        model.addAttribute("product",product.get());
        return "productDetail";
    }

    @PostMapping(value="/cart")
    public ResponseEntity<CartBean> createCart(@RequestBody CartBean cart){

        ResponseEntity<CartBean> cartBean = msCartProxy.createNewCart(cart);
        return cartBean;
    }

    @RequestMapping(value="/cart/{id}")
    public Optional<CartBean> getCart (@PathVariable Long id){
        Optional<CartBean> cart = msCartProxy.getCart(id);
        return cart;
    }

    @RequestMapping(value="/lastCart")
    public Optional<CartBean> getLastCart(Long lastId){
        Optional<CartBean> cart = msCartProxy.getLastCart(lastId);
        return cart;
    }

    @PostMapping(value="/cart/{id}")
    public ResponseEntity<CartItemBean> addToCart (@PathVariable Long id, @RequestBody CartItemBean cartItem){
        ResponseEntity<CartItemBean> item = msCartProxy.addProductToCart(id, cartItem);
        return item;
    }

    @PostMapping(value="/cart/addProduct")
    public ResponseEntity<CartBean> addProduct (@RequestBody CartItemBean cartItemBean){
        ResponseEntity<CartBean> cartItem = msCartProxy.addProduct(cartItemBean);
        return cartItem;
    }
}
