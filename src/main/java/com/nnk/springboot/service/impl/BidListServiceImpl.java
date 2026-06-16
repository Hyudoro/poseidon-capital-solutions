package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.inter.IBidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListServiceImpl implements IBidListService {

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(Integer id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id: " + id));
    }

    @Override
    public BidList save(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList update(Integer id, BidList bidList) {
        bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id: " + id));
        bidList.setBidListId(id);
        return bidListRepository.save(bidList);
    }

    @Override
    public void delete(Integer id) {
        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id: " + id));
        bidListRepository.delete(bidList);
    }
}
