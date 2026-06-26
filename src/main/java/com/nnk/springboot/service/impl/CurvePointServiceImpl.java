package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.inter.ICurvePointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointServiceImpl implements ICurvePointService {

    private static final Logger log = LoggerFactory.getLogger(CurvePointServiceImpl.class);

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePoint> findAll() {
        log.debug("Fetching all curve points");
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint findById(Integer id) {
        log.debug("Fetching curve point by id={}", id);
        return curvePointRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Curve point not found for id={}", id);
                    return new IllegalArgumentException("Invalid CurvePoint Id: " + id);
                });
    }

    @Override
    public CurvePoint save(CurvePoint curvePoint) {
        log.info("Saving new curve point");
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint update(Integer id, CurvePoint curvePoint) {
        curvePointRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Curve point not found for update, id={}", id);
                    return new IllegalArgumentException("Invalid CurvePoint Id: " + id);
                });
        curvePoint.setId(id);
        log.info("Updating curve point id={}", id);
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public void delete(Integer id) {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Curve point not found for deletion, id={}", id);
                    return new IllegalArgumentException("Invalid CurvePoint Id: " + id);
                });
        log.info("Deleting curve point id={}", id);
        curvePointRepository.delete(curvePoint);
    }
}
