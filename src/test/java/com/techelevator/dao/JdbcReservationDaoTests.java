package com.techelevator.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.Reservation;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class JdbcReservationDaoTests extends BaseDaoTests {

    private ReservationDao dao;

    @Before
    public void setup() {
        dao = new JdbcReservationDao(dataSource);
    }

    @Test
    public void getReservationById_Should_Return_Specific_Reservation() {
        Reservation expectedReservation = dao.getReservationById(1);

        LocalDate fromDate = LocalDate.of(2023, 6,6);
        LocalDate toDate = LocalDate.of(2023, 6,13);
        LocalDate createDate = LocalDate.of(2023, 6,1);
        Reservation reservation = mapValuesToReservation(1,1,"Amy Adams", fromDate, toDate, createDate);
        assertEquals(reservation, expectedReservation);
    }

    @Test
    public void createReservation_Should_Return_Reservation_With_New_Id() {
        Reservation reservation = new Reservation(
            0,
            1,
            "TEST NAME",
            LocalDate.now().plusDays(1),
            LocalDate.now().plusDays(3),
            LocalDate.now());

        Reservation reservationCreated = dao.createReservation(reservation);

        assertEquals("Incorrect ID of new reservation", 13, reservationCreated.getReservationId());

    }
    private Reservation mapValuesToReservation(int reservation_id, int site_id, String name, LocalDate from_date, LocalDate to_date, LocalDate create_date) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservation_id);
        reservation.setSiteId(site_id);
        reservation.setName(name);
        reservation.setFromDate(from_date);
        reservation.setToDate(to_date);
        reservation.setCreateDate(create_date);
        return reservation;
    }
    private void assertReservationsMatch (Reservation expected, Reservation actual){
        Assert.assertEquals(expected.getReservationId(), actual.getReservationId());
        Assert.assertEquals(expected.getSiteId(), actual.getSiteId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getFromDate(), actual.getFromDate());
        Assert.assertEquals(expected.getToDate(), actual.getToDate());
        Assert.assertEquals(expected.getCreateDate(), actual.getCreateDate());
    }


}
