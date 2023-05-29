package com.crypto.cryptoApp.controller;

import com.crypto.cryptoApp.entity.Coin;
import com.crypto.cryptoApp.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/coin")
public class CoinController {
    @Autowired
    private CoinRepository repository;

    @Bean
    public  Coin init(){
        Coin c1 = new Coin();
        c1.setName("BITCOIN");
        c1.setPrice(new BigDecimal(150));
        c1.setQuantity(new BigDecimal(0.0025));
        c1.setDateTime(new Timestamp(System.currentTimeMillis()));

        Coin c2 = new Coin();
        c2.setName("BITCOIN");
        c2.setPrice(new BigDecimal(100));
        c2.setQuantity(new BigDecimal(0.0030));
        c2.setDateTime(new Timestamp(System.currentTimeMillis()));

        Coin c3 = new Coin();
        c3.setName("ETHEREUM");
        c3.setPrice(new BigDecimal(200));
        c3.setQuantity(new BigDecimal(0.0020));
        c3.setDateTime(new Timestamp(System.currentTimeMillis()));

        repository.insert(c1);
        repository.insert(c2);
        repository.insert(c3);

        return c1;
    }


    @GetMapping()
    public ResponseEntity<List> listAll(){

        return  new ResponseEntity<>(repository.listAll(),HttpStatus.OK);

    }

    @GetMapping("/{name}")
    public ResponseEntity get(@PathVariable String name){
        try {
            return  new ResponseEntity<>(repository.getByName(name), HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody Coin coin){
        try {
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(repository.insert(coin), HttpStatus.CREATED);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
