package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.inter.IRuleNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameServiceImpl implements IRuleNameService {

    private static final Logger log = LoggerFactory.getLogger(RuleNameServiceImpl.class);

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Override
    public List<RuleName> findAll() {
        log.debug("Fetching all rule names");
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName findById(Integer id) {
        log.debug("Fetching rule name by id={}", id);
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rule name not found for id={}", id);
                    return new IllegalArgumentException("Invalid RuleName Id: " + id);
                });
    }

    @Override
    public RuleName save(RuleName ruleName) {
        log.info("Saving new rule name");
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName update(Integer id, RuleName ruleName) {
        ruleNameRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rule name not found for update, id={}", id);
                    return new IllegalArgumentException("Invalid RuleName Id: " + id);
                });
        ruleName.setId(id);
        log.info("Updating rule name id={}", id);
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public void delete(Integer id) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rule name not found for deletion, id={}", id);
                    return new IllegalArgumentException("Invalid RuleName Id: " + id);
                });
        log.info("Deleting rule name id={}", id);
        ruleNameRepository.delete(ruleName);
    }
}
