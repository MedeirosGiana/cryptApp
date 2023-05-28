package com.crypto.cryptoApp.controller;

import com.crypto.cryptoApp.entity.Coin;
import com.crypto.cryptoApp.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/coin")
public class CoinController {
    @Autowired
    private CoinRepository repository;

    @GetMapping()
    public ResponseEntity get(){
        return  new ResponseEntity<>(repository.getAll(),HttpStatus.OK);
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
