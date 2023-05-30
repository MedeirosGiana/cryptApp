package com.crypto.cryptoApp.repository;

import com.crypto.cryptoApp.dto.CoinTransationDTO;
import com.crypto.cryptoApp.entity.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepository {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Coin insert(Coin coin){
        entityManager.persist(coin);
        return  coin;
    }

    public Coin update(Coin coin){
        entityManager.merge(coin);
        return coin;
    }
    public List<CoinTransationDTO> listAll(){
       String jpql = "select new com.crypto.cryptoApp.dto.CoinTransationDTO(c.name,c.quantity) from Coin c group by c.name";
       TypedQuery<CoinTransationDTO> query = entityManager.createQuery(jpql,CoinTransationDTO.class);
       return query.getResultList();
    }

    public List<Coin> getByName(String name){
    }

    public int remove(int id){
    }
}
