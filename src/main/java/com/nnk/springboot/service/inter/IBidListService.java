package com.nnk.springboot.service.inter;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface IBidListService {

    List<BidList> findAll();

    BidList findById(Integer id);

    BidList save(BidList bidList);

    BidList update(Integer id, BidList bidList);

    void delete(Integer id);
}
