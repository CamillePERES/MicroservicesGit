package com.example.client.controller;

import com.example.client.bean.*;
import com.example.client.models.CartClient;
import com.example.client.models.CartItemClient;
import com.example.client.proxy.MsCartProxy;
import com.example.client.proxy.MsOrderProxy;
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
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;
    @Autowired
    private MsCartProxy msCartProxy;
    @Autowired
    private MsOrderProxy msOrderProxy;

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

    @RequestMapping("/cartView")
    public String cartView (Model model){

        Optional<CartBean> cart = msCartProxy.getLastCart();

        if (cart.isPresent()){
            CartBean cartBean = cart.get();
            //get les id des produits
            List<Long> ids = cartBean.getProducts().stream().map(CartItemBean::getProductId).collect(Collectors.toList());
            List<ProductBean> prods = msProductProxy.getProduct(ids).getBody();

            //on cree une map qui lie un produit (ProductBean) avec son id (Long)
            //on fait ca car plus facile pour retrouver le produit par son id dans une liste
            Map<Long, ProductBean> mapProduct = prods.stream().collect(Collectors.toMap(ProductBean::getId, Function.identity()));

            CartClient cartClient = new CartClient();
            cartClient.setId(cartBean.getId());

            //remplacer l'idProduit par le produit afin d'avoir acces au prix tout en ayant sa quantite
            List<CartItemClient> items = cartBean.getProducts().stream().map(cartItemBean -> {
               CartItemClient cic = new CartItemClient();
               cic.setId(cartItemBean.getId());
               cic.setQuantity(cartItemBean.getQuantity());
               cic.setProduct(mapProduct.get(cartItemBean.getProductId()));
               return cic;
            }).collect(Collectors.toList());

            cartClient.setProducts(items);
            model.addAttribute("cart",cartClient);
        }
        return "cart";
    }

    @RequestMapping("/orderView")
    public String orderView (Model model, Long id){
        Optional<OrderBean> order = msOrderProxy.getOrder(id);
        model.addAttribute("order", order);
        return "order";
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
    public Optional<CartBean> getLastCart(){
        Optional<CartBean> cart = msCartProxy.getLastCart();
        return cart;
    }

    @PostMapping(value="/cart/{id}")
    public ResponseEntity<CartItemBean> addToCart (@PathVariable Long id, @RequestBody CartItemBean cartItem){
        ResponseEntity<CartItemBean> item = msCartProxy.addProductToCart(id, cartItem);
        return item;
    }

    @PostMapping(value="/cart/addProduct")
    public ResponseEntity<CartBean> addProduct (@RequestBody CartItemBean cartItemBean){
        ResponseEntity<CartBean> cart = msCartProxy.addProduct(cartItemBean);
        return cart;
    }

    @PostMapping(value="/order")
    public ResponseEntity<OrderBean> createOrder(@RequestBody OrderBean orderBean){

        ResponseEntity<OrderBean> order = msOrderProxy.createNewOrder(orderBean);
        return order;
    }

    @PostMapping(value = "/order/{id}")
    public ResponseEntity<OrderItemBean> addToOrder (@PathVariable Long id, @RequestBody OrderItemBean orderItemBean){
        ResponseEntity<OrderItemBean> orderItem = msOrderProxy.addProductFromCartToOrder(id, orderItemBean);
        return orderItem;
    }


}
