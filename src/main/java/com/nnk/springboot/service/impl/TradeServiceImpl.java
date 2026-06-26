package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.inter.ITradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements ITradeService {

    private static final Logger log = LoggerFactory.getLogger(TradeServiceImpl.class);

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public List<Trade> findAll() {
        log.debug("Fetching all trades");
        return tradeRepository.findAll();
    }

    @Override
    public Trade findById(Integer id) {
        log.debug("Fetching trade by id={}", id);
        return tradeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Trade not found for id={}", id);
                    return new IllegalArgumentException("Invalid Trade Id: " + id);
                });
    }

    @Override
    public Trade save(Trade trade) {
        log.info("Saving new trade");
        return tradeRepository.save(trade);
    }

    @Override
    public Trade update(Integer id, Trade trade) {
        tradeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Trade not found for update, id={}", id);
                    return new IllegalArgumentException("Invalid Trade Id: " + id);
                });
        trade.setTradeId(id);
        log.info("Updating trade id={}", id);
        return tradeRepository.save(trade);
    }

    @Override
    public void delete(Integer id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Trade not found for deletion, id={}", id);
                    return new IllegalArgumentException("Invalid Trade Id: " + id);
                });
        log.info("Deleting trade id={}", id);
        tradeRepository.delete(trade);
    }
}
