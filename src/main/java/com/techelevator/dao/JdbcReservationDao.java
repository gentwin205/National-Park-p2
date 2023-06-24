package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Campground;
import com.techelevator.model.Reservation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

public class JdbcReservationDao implements ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override 
    public Reservation getReservationById(int id) {

        Reservation reservation = null;
        String sql = "select * from reservation where reservation_id =?;";
        try {

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                reservation = mapRowToReservation(results);
            }
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("cannot find query",e);
        }
        return reservation;

    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) " +
                "VALUES (?,?,?,?,?) RETURNING reservation_id";
        Reservation reservation1 = null;
        try {
            int reservation_id = jdbcTemplate.queryForObject(sql,int.class, reservation.getSiteId(), reservation.getName(), reservation.getFromDate(), reservation.getToDate(),
            reservation.getCreateDate());
            reservation1 = getReservationById(reservation_id);
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("cannot connect to database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }

        return reservation1;
    }

    private Reservation mapRowToReservation(SqlRowSet results) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(results.getInt("reservation_id"));
        reservation.setSiteId(results.getInt("site_id"));
        reservation.setName(results.getString("name"));
        reservation.setFromDate(results.getDate("from_date").toLocalDate());
        reservation.setToDate(results.getDate("to_date").toLocalDate());
        reservation.setCreateDate(results.getDate("create_date").toLocalDate());
        return reservation;
    }


}
