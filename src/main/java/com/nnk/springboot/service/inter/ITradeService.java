package com.nnk.springboot.service.inter;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface ITradeService {

    List<Trade> findAll();

    Trade findById(Integer id);

    Trade save(Trade trade);

    Trade update(Integer id, Trade trade);

    void delete(Integer id);
}
