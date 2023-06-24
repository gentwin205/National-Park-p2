package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Site;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcSiteDao implements SiteDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcSiteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Site> getSitesWithRVAccessByParkId(int parkId) {
        String sql = "SELECT * FROM site JOIN campground USING(campground_id) WHERE park_id = ? " +
                "AND max_rv_length > 0;";
        List <Site> sites = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
            while(results.next()) {
                sites.add(mapRowToSite(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Cannot connect to database", e);
        }
        return sites;
    }

    @Override
    public List<Site> getSitesWithoutReservationByParkId(int parkId) {
        String sql = "SELECT * FROM site JOIN campground USING(campground_id) JOIN reservation USING (site_id) " +
                "WHERE park_id = ? " +
                "AND max_rv_length > 0 AND to_date < '2023-06-24';";
        List <Site> sites = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
            while(results.next()) {
                sites.add(mapRowToSite(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Cannot connect to database", e);
        }
        return sites;
    }

    private Site mapRowToSite(SqlRowSet results) {
        Site site = new Site();
        site.setSiteId(results.getInt("site_id"));
        site.setCampgroundId(results.getInt("campground_id"));
        site.setSiteNumber(results.getInt("site_number"));
        site.setMaxOccupancy(results.getInt("max_occupancy"));
        site.setAccessible(results.getBoolean("accessible"));
        site.setMaxRvLength(results.getInt("max_rv_length"));
        site.setUtilities(results.getBoolean("utilities"));
        return site;
    }
}
