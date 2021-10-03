package com.example.orders.controllers;

import com.example.orders.bean.CartBean;
import com.example.orders.bean.CartItemBean;
import com.example.orders.bean.ProductBean;
import com.example.orders.entities.Orders;
import com.example.orders.entities.OrderItem;
import com.example.orders.proxy.MsCartProxy;
import com.example.orders.proxy.MsProductProxy;
import com.example.orders.repositories.OrderItemRepository;
import com.example.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MsCartProxy msCartProxy;

    @Autowired
    MsProductProxy msProductProxy;

    @PostMapping(value="/order")
    @Transactional
    public ResponseEntity<Orders> createNewOrder(){

        //Order order = orderRepository.save();
        Optional<CartBean> cart = msCartProxy.getLastCart();

        if (cart.isPresent()){
            CartBean cartBean = cart.get();
            //get les id des produits
            List<Long> ids = cartBean.getProducts().stream().map(CartItemBean::getProductId).collect(Collectors.toList());
            List<ProductBean> prods = msProductProxy.getProduct(ids).getBody();

            //on cree une map qui lie un produit (ProductBean) avec son id (Long)
            //on fait ca car plus facile pour retrouver le produit par son id dans une liste
            Map<Long, ProductBean> mapProduct = prods.stream().collect(Collectors.toMap(ProductBean::getId, Function.identity()));

            Orders ord = new Orders();
            ord.setId(cartBean.getId());

            //remplacer l'idProduit par le produit afin d'avoir acces au prix tout en ayant sa quantite
            List<OrderItem> items = cartBean.getProducts().stream().map(cartItemBean -> {
                ProductBean prodBean = mapProduct.get(cartItemBean.getProductId()) ;
                OrderItem oi = new OrderItem();
                oi.setId(cartItemBean.getId());
                oi.setQuantity(cartItemBean.getQuantity());
                oi.setPrice(prodBean.getPrice());
                oi.setTotal(cartItemBean.getQuantity()*prodBean.getPrice());
                oi.setProductId(prodBean.getId());
                return oi;
            }).collect(Collectors.toList());

            ord.setItems(items);
            Double totalOrder = ord.getItems().stream().mapToDouble(item -> item.getTotal()).sum();
            ord.setTotal(totalOrder);
            orderRepository.save(ord);

            msCartProxy.newCart();

            return ResponseEntity.ok(ord);
        }

        return null;
    }

    @GetMapping(value="/order/{id}")
    public Optional<Orders> getOrder(@PathVariable Long id){

        Optional<Orders> order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");

        return order;
    }

    @GetMapping(value="/order/all")
    public List<Orders> getAllOrder(){

       List<Orders> orders = orderRepository.findAll();

        return orders;
    }


}
