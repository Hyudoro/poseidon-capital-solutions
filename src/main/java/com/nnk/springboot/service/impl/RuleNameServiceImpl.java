package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.inter.IRuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameServiceImpl implements IRuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName findById(Integer id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id: " + id));
    }

    @Override
    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName update(Integer id, RuleName ruleName) {
        ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id: " + id));
        ruleName.setId(id);
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public void delete(Integer id) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id: " + id));
        ruleNameRepository.delete(ruleName);
    }
}
