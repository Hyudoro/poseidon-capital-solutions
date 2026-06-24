package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceImplTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    @Test
    void given_ruleNamesExist_when_findAll_then_returnAllRuleNames() {
        RuleName rule = new RuleName("Name", "Desc", "Json", "Template", "SQL", "Part");
        given(ruleNameRepository.findAll()).willReturn(List.of(rule));

        List<RuleName> result = ruleNameService.findAll();

        assertThat(result).hasSize(1).containsExactly(rule);
        then(ruleNameRepository).should().findAll();
    }

    @Test
    void given_noRuleNames_when_findAll_then_returnEmptyList() {
        given(ruleNameRepository.findAll()).willReturn(Collections.emptyList());

        List<RuleName> result = ruleNameService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void given_validId_when_findById_then_returnRuleName() {
        RuleName rule = new RuleName("Name", "Desc", "Json", "Template", "SQL", "Part");
        given(ruleNameRepository.findById(1)).willReturn(Optional.of(rule));

        RuleName result = ruleNameService.findById(1);

        assertThat(result).isEqualTo(rule);
    }

    @Test
    void given_invalidId_when_findById_then_throwIllegalArgumentException() {
        given(ruleNameRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> ruleNameService.findById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void given_validRuleName_when_save_then_returnSavedRuleName() {
        RuleName rule = new RuleName("Name", "Desc", "Json", "Template", "SQL", "Part");
        given(ruleNameRepository.save(rule)).willReturn(rule);

        RuleName result = ruleNameService.save(rule);

        assertThat(result).isEqualTo(rule);
        then(ruleNameRepository).should().save(rule);
    }

    @Test
    void given_existingId_when_update_then_setIdAndReturnUpdatedRuleName() {
        RuleName existing = new RuleName("Name", "Desc", "Json", "Template", "SQL", "Part");
        RuleName updated = new RuleName("New Name", "New Desc", "Json2", "Tmpl2", "SQL2", "Part2");
        given(ruleNameRepository.findById(1)).willReturn(Optional.of(existing));
        given(ruleNameRepository.save(updated)).willReturn(updated);

        RuleName result = ruleNameService.update(1, updated);

        assertThat(result).isEqualTo(updated);
        assertThat(updated.getId()).isEqualTo(1);
        then(ruleNameRepository).should().save(updated);
    }

    @Test
    void given_invalidId_when_update_then_throwIllegalArgumentException() {
        given(ruleNameRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> ruleNameService.update(99, new RuleName("N", "D", "J", "T", "S", "P")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(ruleNameRepository).should(never()).save(any());
    }

    @Test
    void given_existingId_when_delete_then_deleteFromRepository() {
        RuleName rule = new RuleName("Name", "Desc", "Json", "Template", "SQL", "Part");
        given(ruleNameRepository.findById(1)).willReturn(Optional.of(rule));

        ruleNameService.delete(1);

        then(ruleNameRepository).should().delete(rule);
    }

    @Test
    void given_invalidId_when_delete_then_throwIllegalArgumentException() {
        given(ruleNameRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> ruleNameService.delete(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(ruleNameRepository).should(never()).delete(any());
    }
}
