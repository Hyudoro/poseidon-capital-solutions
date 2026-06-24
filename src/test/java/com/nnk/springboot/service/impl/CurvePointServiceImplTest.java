package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurvePointServiceImplTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

    @Test
    void given_curvePointsExist_when_findAll_then_returnAllCurvePoints() {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        given(curvePointRepository.findAll()).willReturn(List.of(curvePoint));

        List<CurvePoint> result = curvePointService.findAll();

        assertThat(result).hasSize(1).containsExactly(curvePoint);
        then(curvePointRepository).should().findAll();
    }

    @Test
    void given_noCurvePoints_when_findAll_then_returnEmptyList() {
        given(curvePointRepository.findAll()).willReturn(Collections.emptyList());

        List<CurvePoint> result = curvePointService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void given_validId_when_findById_then_returnCurvePoint() {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        given(curvePointRepository.findById(1)).willReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.findById(1);

        assertThat(result).isEqualTo(curvePoint);
    }

    @Test
    void given_invalidId_when_findById_then_throwIllegalArgumentException() {
        given(curvePointRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> curvePointService.findById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void given_validCurvePoint_when_save_then_returnSavedCurvePoint() {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        given(curvePointRepository.save(curvePoint)).willReturn(curvePoint);

        CurvePoint result = curvePointService.save(curvePoint);

        assertThat(result).isEqualTo(curvePoint);
        then(curvePointRepository).should().save(curvePoint);
    }

    @Test
    void given_existingId_when_update_then_setIdAndReturnUpdatedCurvePoint() {
        CurvePoint existing = new CurvePoint(10, 10d, 30d);
        CurvePoint updated = new CurvePoint(20, 20d, 50d);
        given(curvePointRepository.findById(1)).willReturn(Optional.of(existing));
        given(curvePointRepository.save(updated)).willReturn(updated);

        CurvePoint result = curvePointService.update(1, updated);

        assertThat(result).isEqualTo(updated);
        assertThat(updated.getId()).isEqualTo(1);
        then(curvePointRepository).should().save(updated);
    }

    @Test
    void given_invalidId_when_update_then_throwIllegalArgumentException() {
        given(curvePointRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> curvePointService.update(99, new CurvePoint(10, 10d, 30d)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(curvePointRepository).should(never()).save(any());
    }

    @Test
    void given_existingId_when_delete_then_deleteFromRepository() {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        given(curvePointRepository.findById(1)).willReturn(Optional.of(curvePoint));

        curvePointService.delete(1);

        then(curvePointRepository).should().delete(curvePoint);
    }

    @Test
    void given_invalidId_when_delete_then_throwIllegalArgumentException() {
        given(curvePointRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> curvePointService.delete(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(curvePointRepository).should(never()).delete(any());
    }
}
