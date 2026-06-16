package com.nnk.springboot.service.inter;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface IRuleNameService {

    List<RuleName> findAll();

    RuleName findById(Integer id);

    RuleName save(RuleName ruleName);

    RuleName update(Integer id, RuleName ruleName);

    void delete(Integer id);
}
