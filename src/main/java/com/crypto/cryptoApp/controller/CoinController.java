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

    @PutMapping()
    public ResponseEntity put ( @RequestBody Coin coin){
        try {
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(repository.update(coin),HttpStatus.OK);

        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id){
        try {
            return new ResponseEntity<>(repository.remove(id),HttpStatus.OK);

        }catch (Exception error){
            return  new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
