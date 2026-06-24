package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Test
    void given_ratingsExist_when_findAll_then_returnAllRatings() {
        Rating rating = new Rating("Moodys", "SP", "Fitch", 1);
        given(ratingRepository.findAll()).willReturn(List.of(rating));

        List<Rating> result = ratingService.findAll();

        assertThat(result).hasSize(1).containsExactly(rating);
        then(ratingRepository).should().findAll();
    }

    @Test
    void given_noRatings_when_findAll_then_returnEmptyList() {
        given(ratingRepository.findAll()).willReturn(Collections.emptyList());

        List<Rating> result = ratingService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void given_validId_when_findById_then_returnRating() {
        Rating rating = new Rating("Moodys", "SP", "Fitch", 1);
        given(ratingRepository.findById(1)).willReturn(Optional.of(rating));

        Rating result = ratingService.findById(1);

        assertThat(result).isEqualTo(rating);
    }

    @Test
    void given_invalidId_when_findById_then_throwIllegalArgumentException() {
        given(ratingRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> ratingService.findById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void given_validRating_when_save_then_returnSavedRating() {
        Rating rating = new Rating("Moodys", "SP", "Fitch", 1);
        given(ratingRepository.save(rating)).willReturn(rating);

        Rating result = ratingService.save(rating);

        assertThat(result).isEqualTo(rating);
        then(ratingRepository).should().save(rating);
    }

    @Test
    void given_existingId_when_update_then_setIdAndReturnUpdatedRating() {
        Rating existing = new Rating("Moodys", "SP", "Fitch", 1);
        Rating updated = new Rating("Moodys2", "SP2", "Fitch2", 2);
        given(ratingRepository.findById(1)).willReturn(Optional.of(existing));
        given(ratingRepository.save(updated)).willReturn(updated);

        Rating result = ratingService.update(1, updated);

        assertThat(result).isEqualTo(updated);
        assertThat(updated.getId()).isEqualTo(1);
        then(ratingRepository).should().save(updated);
    }

    @Test
    void given_invalidId_when_update_then_throwIllegalArgumentException() {
        given(ratingRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> ratingService.update(99, new Rating("M", "S", "F", 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(ratingRepository).should(never()).save(any());
    }

    @Test
    void given_existingId_when_delete_then_deleteFromRepository() {
        Rating rating = new Rating("Moodys", "SP", "Fitch", 1);
        given(ratingRepository.findById(1)).willReturn(Optional.of(rating));

        ratingService.delete(1);

        then(ratingRepository).should().delete(rating);
    }

    @Test
    void given_invalidId_when_delete_then_throwIllegalArgumentException() {
        given(ratingRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> ratingService.delete(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(ratingRepository).should(never()).delete(any());
    }
}
