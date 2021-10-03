package com.example.client.proxy;

import com.example.client.bean.ProductBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient (name = "ms-product", url = "localhost:8091")
public interface MsProductProxy {

    @GetMapping(value = "/products")
    public List<ProductBean> list();

    @GetMapping (value = "product/{id}")
    public  Optional<ProductBean> get (@PathVariable Long id);

    @PostMapping("/products/all")
    public ResponseEntity<List<ProductBean>> getProduct(@RequestBody List<Long> ids);
}
