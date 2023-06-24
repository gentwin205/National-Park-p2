package com.techelevator.dao;

import com.techelevator.model.Campground;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcCampgroundDaoTests extends BaseDaoTests {

    private CampgroundDao dao;

    @Before
    public void setup() {
        dao = new JdbcCampgroundDao(dataSource);
    }

    @Test
    public void getCampgroundById_Should_Return_Specific_Campground() {
        Campground campground = dao.getCampgroundById(1);

        assertEquals("Incorrect campground returned for ID 1", 1, campground.getCampgroundId());
    }

    @Test
    public void getCampgroundsByParkId_Should_Return_All_Campgrounds_For_Park() {
        List<Campground> campground = dao.getCampgroundsByParkId(1);

        assertEquals("Incorrect campground returned for ID 1", 2, campground.size());
        Campground campground_1 = mapValuesToCampground(1, 1, "Test Campground 1",
                1, 12, 35.00);
        Campground campground_2 = mapValuesToCampground(2, 1, "Test Campground 2",
                1, 12, 35.00);
        assertCampgroundsMatch(campground_1, campground.get(0));
        assertCampgroundsMatch(campground_2, campground.get(1));

    }

    private Campground mapValuesToCampground(int campground_id, int park_id, String name, int open_from_mm,
                                             int open_to_mm, double daily_fee) {
        Campground campground = new Campground();
        campground.setCampgroundId(campground_id);
        campground.setParkId(park_id);
        campground.setName(name);
        campground.setOpenFromMonth(open_from_mm);
        campground.setOpenToMonth(open_to_mm);
        campground.setDailyFee(daily_fee);
        return campground;
    }

    private void assertCampgroundsMatch(Campground expected, Campground actual){
        Assert.assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
        Assert.assertEquals(expected.getParkId(), actual.getParkId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getOpenFromMonth(), actual.getOpenFromMonth());
        Assert.assertEquals(expected.getOpenToMonth(), actual.getOpenToMonth());
    }

}
