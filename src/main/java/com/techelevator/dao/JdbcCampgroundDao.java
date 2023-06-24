package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Campground;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCampgroundDao implements CampgroundDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcCampgroundDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Campground getCampgroundById(int id) {
        Campground campground = null;
       String sql = "select * from campground where campground_id =?;";
       try {

           SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
           if (results.next()) {
               campground = mapRowToCampground(results);
           }
       }catch(CannotGetJdbcConnectionException e){
           throw new DaoException("cannot find query",e);
       }
        return campground;

    }

    @Override
    public List<Campground> getCampgroundsByParkId(int parkId) {
        List<Campground> list = new ArrayList<>();
        String sql = "select * from campground where park_id =?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
            while (results.next()) {
                list.add(mapRowToCampground(results));
            }
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("cannot find query",e);

        }
        return list;
    }

    private Campground mapRowToCampground(SqlRowSet results) {
        Campground campground = new Campground();
        campground.setCampgroundId(results.getInt("campground_id"));
        campground.setParkId(results.getInt("park_id"));
        campground.setName(results.getString("name"));
        campground.setOpenFromMonth(results.getInt("open_from_mm"));
        campground.setOpenToMonth(results.getInt("open_to_mm"));
        campground.setDailyFee(results.getDouble("daily_fee"));
        return campground;
    }
}
