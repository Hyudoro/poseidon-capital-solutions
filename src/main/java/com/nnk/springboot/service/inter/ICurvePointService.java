package com.nnk.springboot.service.inter;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface ICurvePointService {

    List<CurvePoint> findAll();

    CurvePoint findById(Integer id);

    CurvePoint save(CurvePoint curvePoint);

    CurvePoint update(Integer id, CurvePoint curvePoint);

    void delete(Integer id);
}
