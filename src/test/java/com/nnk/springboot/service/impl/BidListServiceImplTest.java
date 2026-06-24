package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class BidListServiceImplTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListServiceImpl bidListService;

    @Test
    void given_bidListsExist_when_findAll_then_returnAllBidLists() {
        BidList bid = new BidList("Account", "Type", 10d);
        given(bidListRepository.findAll()).willReturn(List.of(bid));

        List<BidList> result = bidListService.findAll();

        assertThat(result).hasSize(1).containsExactly(bid);
        then(bidListRepository).should().findAll();
    }

    @Test
    void given_noBidLists_when_findAll_then_returnEmptyList() {
        given(bidListRepository.findAll()).willReturn(Collections.emptyList());

        List<BidList> result = bidListService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void given_validId_when_findById_then_returnBidList() {
        BidList bid = new BidList("Account", "Type", 10d);
        given(bidListRepository.findById(1)).willReturn(Optional.of(bid));

        BidList result = bidListService.findById(1);

        assertThat(result).isEqualTo(bid);
    }

    @Test
    void given_invalidId_when_findById_then_throwIllegalArgumentException() {
        given(bidListRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bidListService.findById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void given_validBidList_when_save_then_returnSavedBidList() {
        BidList bid = new BidList("Account", "Type", 10d);
        given(bidListRepository.save(bid)).willReturn(bid);

        BidList result = bidListService.save(bid);

        assertThat(result).isEqualTo(bid);
        then(bidListRepository).should().save(bid);
    }

    @Test
    void given_existingId_when_update_then_setIdAndReturnUpdatedBidList() {
        BidList existing = new BidList("Account", "Type", 10d);
        BidList updated = new BidList("Updated", "Type", 20d);
        given(bidListRepository.findById(1)).willReturn(Optional.of(existing));
        given(bidListRepository.save(updated)).willReturn(updated);

        BidList result = bidListService.update(1, updated);

        assertThat(result).isEqualTo(updated);
        assertThat(updated.getBidListId()).isEqualTo(1);
        then(bidListRepository).should().save(updated);
    }

    @Test
    void given_invalidId_when_update_then_throwIllegalArgumentException() {
        given(bidListRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bidListService.update(99, new BidList("A", "T", 5d)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(bidListRepository).should(never()).save(any());
    }

    @Test
    void given_existingId_when_delete_then_deleteFromRepository() {
        BidList bid = new BidList("Account", "Type", 10d);
        given(bidListRepository.findById(1)).willReturn(Optional.of(bid));

        bidListService.delete(1);

        then(bidListRepository).should().delete(bid);
    }

    @Test
    void given_invalidId_when_delete_then_throwIllegalArgumentException() {
        given(bidListRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bidListService.delete(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(bidListRepository).should(never()).delete(any());
    }
}
