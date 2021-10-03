package com.example.client.proxy;

import com.example.client.bean.CartBean;
import com.example.client.bean.OrderBean;
import com.example.client.bean.OrderItemBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Optional;

@FeignClient(name = "ms-order", url="localhost:8093")
public interface MsOrderProxy {

    @PostMapping(value="/order")
    public ResponseEntity<OrderBean> createNewOrder(@RequestBody OrderBean orderData);

    @GetMapping(value="/order/{id}")
    public Optional<OrderBean> getOrder(@PathVariable Long id);

    @PostMapping(value = "/order/{id}")
    public ResponseEntity<OrderItemBean> addProductFromCartToOrder(@PathVariable Long id, @RequestBody OrderItemBean orderItem);


}
