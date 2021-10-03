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
import java.util.List;
import java.util.Optional;

@FeignClient(name = "ms-order", url="localhost:8093")
public interface MsOrderProxy {

    @GetMapping(value="/order/{id}")
    public Optional<OrderBean> getOrder(@PathVariable Long id);

    @GetMapping(value="/order/all")
    public List<OrderBean> getAllOrder();

    @PostMapping(value="/order")
    public ResponseEntity<OrderBean> createNewOrder();




}
