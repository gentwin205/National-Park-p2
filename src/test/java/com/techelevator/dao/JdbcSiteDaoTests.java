package com.techelevator.dao;

import com.techelevator.model.Reservation;
import com.techelevator.model.Site;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcSiteDaoTests extends BaseDaoTests {

    private SiteDao dao;

    @Before
    public void setup() {
        dao = new JdbcSiteDao(dataSource);
    }

    @Test
    public void getSitesWithRVAccessByParkId_Should_Return_Sites_With_Positive_RV_Length() {
        List<Site> sites = dao.getSitesWithRVAccessByParkId(1);
        Assert.assertEquals(2, sites.size());
        Site site1 = mapValuesToSite(1,1,1,10,true,33, true);
        Site site2 = mapValuesToSite(2,1,2,10,true,30, true);
        Assert.assertEquals(site1, sites.get(0));
        Assert.assertEquals(site2, sites.get(1));

    }

    @Test
    public void getSitesWithoutReservationByParkId_Should_Return_Available_Parks() {
        List<Site> sites = dao.getSitesWithoutReservationByParkId(1);
        assertEquals("Incorrect count of currently available sites", 5, sites.size());

    }

    private Site mapValuesToSite(int site_id, int campground_id, int site_number, int max_occupancy, boolean accessible,
                                 int max_rv_length, boolean utilities) {
        Site site = new Site();
        site.setSiteId(site_id);
        site.setCampgroundId(campground_id);
        site.setSiteNumber(site_number);
        site.setMaxOccupancy(max_occupancy);
        site.setAccessible(accessible);
        site.setMaxRvLength(max_rv_length);
        site.setUtilities(utilities);
        return site;
    }
    private void assertSitesMatch (Site expected, Site actual){
        Assert.assertEquals(expected.getSiteId(), actual.getSiteId());
        Assert.assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
        Assert.assertEquals(expected.getSiteNumber(), actual.getSiteNumber());
        Assert.assertEquals(expected.getMaxOccupancy(), actual.getMaxOccupancy());
        Assert.assertEquals(expected.isAccessible(), actual.isAccessible());
        Assert.assertEquals(expected.isUtilities(), actual.isUtilities());
    }

}
