package com.xiaou.studyroom.service.impl;

import com.xiaou.studyroom.entity.Seat;
import com.xiaou.studyroom.mapper.ReservationMapper;
import com.xiaou.studyroom.mapper.SeatMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    @Mock
    private SeatMapper seatMapper;

    @Mock
    private ReservationMapper reservationMapper;

    private SeatServiceImpl seatService;

    @BeforeEach
    void setUp() {
        seatService = new SeatServiceImpl();
        ReflectionTestUtils.setField(seatService, "baseMapper", seatMapper);
        ReflectionTestUtils.setField(seatService, "reservationMapper", reservationMapper);
    }

    @Test
    void shouldResetSeatToIdleWhenThereIsNoCurrentCheckedInReservation() {
        Seat seat = new Seat();
        seat.setId(1L);
        seat.setRoomId(1L);
        seat.setSeatStatus(2);

        when(seatMapper.selectList(any())).thenReturn(List.of(seat));
        when(reservationMapper.selectCount(any())).thenReturn(0L);

        List<Seat> result = seatService.getSeatsWithStatus(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSeatStatus()).isEqualTo(1);
        verify(reservationMapper).selectCount(any());
    }

    @Test
    void shouldKeepMaintenanceSeatStatusUntouched() {
        Seat seat = new Seat();
        seat.setId(2L);
        seat.setRoomId(1L);
        seat.setSeatStatus(3);

        when(seatMapper.selectList(any())).thenReturn(List.of(seat));

        List<Seat> result = seatService.getSeatsWithStatus(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSeatStatus()).isEqualTo(3);
        verify(reservationMapper, never()).selectCount(any());
    }
}
