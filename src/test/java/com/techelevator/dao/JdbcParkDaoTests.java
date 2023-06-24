package com.techelevator.dao;

import com.techelevator.model.Park;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;
import java.util.List;

public class JdbcParkDaoTests extends BaseDaoTests {

    private ParkDao dao;

    @Before
    public void setup() {
        dao = new JdbcParkDao(dataSource);
    }

    @Test
    public void getParks_Should_Return_All_Parks() {
        List<Park> parks = dao.getParks();
        Assert.assertEquals("Incorrect size for parks" ,2, parks.size());
        LocalDate date = LocalDate.of(1970, 1, 1);
        Park park2 = mapValuesToPark(2, "Park 2", "Ohio", date, 2048, 1024, "Test description 2");
        Park park1 = mapValuesToPark(1, "Park 1", "Pennsylvania", date, 1024, 512, "Test description 1");
        assertParksMatch(park2, parks.get(0));
        assertParksMatch(park1, parks.get(1));
    }

    private Park mapValuesToPark(int park_id, String name, String location, LocalDate establish_date, int area,
                              int visitors, String description) {
        Park park = new Park();
        park.setParkId(park_id);
        park.setName(name);
        park.setLocation(location);
        park.setEstablishDate(establish_date);
        park.setArea(area);
        park.setVisitors(visitors);
        park.setDescription(description);
        return park;
    }

    private void assertParksMatch(Park expected, Park actual) {
        Assert.assertEquals(expected.getParkId(), actual.getParkId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getLocation(), actual.getLocation());
        Assert.assertEquals(expected.getEstablishDate(), actual.getEstablishDate());
        Assert.assertEquals(expected.getArea(), actual.getArea());
        Assert.assertEquals(expected.getVisitors(), actual.getVisitors());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());

    }

}
