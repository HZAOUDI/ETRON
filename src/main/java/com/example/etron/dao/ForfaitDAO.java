package com.example.etron.dao;

import com.example.etron.POJO.Forfait;
import com.example.etron.wrapper.ForfaitWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ForfaitDAO extends JpaRepository<Forfait,Integer> {

    List<ForfaitWrapper> getAllForfait();

    @Modifying
    @Transactional
    Integer updateForfaitStatus(@Param("status") String status, @Param("id") Integer id);

    ForfaitWrapper getForfaitById(@Param("id") Integer id);

}
