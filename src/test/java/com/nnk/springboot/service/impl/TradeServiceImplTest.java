package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
class TradeServiceImplTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    void given_tradesExist_when_findAll_then_returnAllTrades() {
        Trade trade = new Trade("Account", "Type");
        given(tradeRepository.findAll()).willReturn(List.of(trade));

        List<Trade> result = tradeService.findAll();

        assertThat(result).hasSize(1).containsExactly(trade);
        then(tradeRepository).should().findAll();
    }

    @Test
    void given_noTrades_when_findAll_then_returnEmptyList() {
        given(tradeRepository.findAll()).willReturn(Collections.emptyList());

        List<Trade> result = tradeService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void given_validId_when_findById_then_returnTrade() {
        Trade trade = new Trade("Account", "Type");
        given(tradeRepository.findById(1)).willReturn(Optional.of(trade));

        Trade result = tradeService.findById(1);

        assertThat(result).isEqualTo(trade);
    }

    @Test
    void given_invalidId_when_findById_then_throwIllegalArgumentException() {
        given(tradeRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> tradeService.findById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void given_validTrade_when_save_then_returnSavedTrade() {
        Trade trade = new Trade("Account", "Type");
        given(tradeRepository.save(trade)).willReturn(trade);

        Trade result = tradeService.save(trade);

        assertThat(result).isEqualTo(trade);
        then(tradeRepository).should().save(trade);
    }

    @Test
    void given_existingId_when_update_then_setIdAndReturnUpdatedTrade() {
        Trade existing = new Trade("Account", "Type");
        Trade updated = new Trade("Updated Account", "New Type");
        given(tradeRepository.findById(1)).willReturn(Optional.of(existing));
        given(tradeRepository.save(updated)).willReturn(updated);

        Trade result = tradeService.update(1, updated);

        assertThat(result).isEqualTo(updated);
        assertThat(updated.getTradeId()).isEqualTo(1);
        then(tradeRepository).should().save(updated);
    }

    @Test
    void given_invalidId_when_update_then_throwIllegalArgumentException() {
        given(tradeRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> tradeService.update(99, new Trade("A", "T")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(tradeRepository).should(never()).save(any());
    }

    @Test
    void given_existingId_when_delete_then_deleteFromRepository() {
        Trade trade = new Trade("Account", "Type");
        given(tradeRepository.findById(1)).willReturn(Optional.of(trade));

        tradeService.delete(1);

        then(tradeRepository).should().delete(trade);
    }

    @Test
    void given_invalidId_when_delete_then_throwIllegalArgumentException() {
        given(tradeRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> tradeService.delete(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(tradeRepository).should(never()).delete(any());
    }
}
