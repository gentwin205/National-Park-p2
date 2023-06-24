package com.techelevator.dao;

import com.techelevator.model.Campground;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        assertCampgroundsMatch(dao.getCampgroundById(1), campground.get(0));
        assertCampgroundsMatch(dao.getCampgroundById(2), campground.get(1));

    }

    private void assertCampgroundsMatch(Campground expected, Campground actual){
        Assert.assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
        Assert.assertEquals(expected.getParkId(), actual.getParkId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getOpenFromMonth(), actual.getOpenFromMonth());
        Assert.assertEquals(expected.getOpenToMonth(), actual.getOpenToMonth());
    }

}
