package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.inter.IBidListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListServiceImpl implements IBidListService {

    private static final Logger log = LoggerFactory.getLogger(BidListServiceImpl.class);

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public List<BidList> findAll() {
        log.debug("Fetching all bid lists");
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(Integer id) {
        log.debug("Fetching bid list by id={}", id);
        return bidListRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Bid list not found for id={}", id);
                    return new IllegalArgumentException("Invalid BidList Id: " + id);
                });
    }

    @Override
    public BidList save(BidList bidList) {
        log.info("Saving new bid list");
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList update(Integer id, BidList bidList) {
        bidListRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Bid list not found for update, id={}", id);
                    return new IllegalArgumentException("Invalid BidList Id: " + id);
                });
        bidList.setBidListId(id);
        log.info("Updating bid list id={}", id);
        return bidListRepository.save(bidList);
    }

    @Override
    public void delete(Integer id) {
        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Bid list not found for deletion, id={}", id);
                    return new IllegalArgumentException("Invalid BidList Id: " + id);
                });
        log.info("Deleting bid list id={}", id);
        bidListRepository.delete(bidList);
    }
}
