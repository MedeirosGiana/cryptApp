package com.crypto.cryptoApp.repository;

import com.crypto.cryptoApp.entity.Coin;
import org.springframework.jdbc.core.JdbcTemplate;

public class CoinRepository {
    private JdbcTemplate jdbcTemplate;

    private static String INSERT = "insert into coin(name, price, quantity, dateTime)values(?,?,?,?)";

    public CoinRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Coin insert(Coin coin){
        Object[] attr = new Object[]{
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getDateTime()
        };
        jdbcTemplate.update(INSERT,attr);
        return coin;
    }
}
