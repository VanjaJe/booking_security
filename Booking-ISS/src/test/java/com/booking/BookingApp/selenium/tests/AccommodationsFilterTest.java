package com.booking.BookingApp.selenium.tests;

import com.booking.BookingApp.selenium.pages.AccommodationsPage;
import com.booking.BookingApp.selenium.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccommodationsFilterTest extends TestBase {

    @Test
    public void testFilterValidData() {  //two results shown
        LoginPage loginPage=new LoginPage(driver);
        loginPage.login("mika@example.com","123");

        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        boolean loaded=accommodationsPage.pageLoaded();
        assertTrue(loaded);

        String[] amenities={"wifi"};

        accommodationsPage.filterByLocationTypeAndAmenities("Serbia","HOTEL",amenities);
        int resultNumber=accommodationsPage.countCards();
        assertEquals(resultNumber,2);
        boolean locationFilter=accommodationsPage.checkFilteredLocation("Serbia");
        assertTrue(locationFilter);
        boolean amenitiesFilter=accommodationsPage.checkFilteredAmenities(amenities);
        assertTrue(amenitiesFilter);

    }
    @Test
    public void testFilterValidDataOne() {  //one result shown
        LoginPage loginPage=new LoginPage(driver);
        loginPage.login("mika@example.com","123");

        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        boolean loaded=accommodationsPage.pageLoaded();
        assertTrue(loaded);

        String[] amenities={"wifi","pool"};

        accommodationsPage.filterByLocationTypeAndAmenities("Serbia","HOTEL",amenities);
        int resultNumber=accommodationsPage.countCards();
        assertEquals(resultNumber,1);
        boolean locationFilter=accommodationsPage.checkFilteredLocation("Serbia");
        assertTrue(locationFilter);
        boolean amenitiesFilter=accommodationsPage.checkFilteredAmenities(amenities);
        assertTrue(amenitiesFilter);
    }
    @Test
    public void testFilterInvalidAmenities() {  //no results shown
        LoginPage loginPage=new LoginPage(driver);
        loginPage.login("mika@example.com","123");

        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        boolean loaded=accommodationsPage.pageLoaded();
        assertTrue(loaded);

        String[] amenities={"wifi","spa","bar"};

        accommodationsPage.filterByLocationTypeAndAmenities("Serbia","HOTEL",amenities);
        int resultNumber=accommodationsPage.countCards();
        assertEquals(resultNumber,0);

    }
    @Test
    public void testFilterInvalidLocation() {  //no results shown
        LoginPage loginPage=new LoginPage(driver);
        loginPage.login("mika@example.com","123");

        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        boolean loaded=accommodationsPage.pageLoaded();
        assertTrue(loaded);

        String[] amenities={"wifi"};

        accommodationsPage.filterByLocationTypeAndAmenities("England","HOTEL",amenities);
        int resultNumber=accommodationsPage.countCards();
        assertEquals(resultNumber,0);
    }

    @Test
    public void testPricesAndDates(){ //one result
        LoginPage loginPage=new LoginPage(driver);
        loginPage.login("mika@example.com","123");

        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        boolean loaded=accommodationsPage.pageLoaded();
        assertTrue(loaded);

        accommodationsPage.filterByGuestsDatesAndPrices("5",100,53000,"2024","JAN","18","20");
        int resultNumber=accommodationsPage.countCards();
        assertEquals(resultNumber,1);
        boolean priceFilter=accommodationsPage.checkFilteredPrice(100,53000);
        assertTrue(priceFilter);
        boolean datesFilter=accommodationsPage.checkFilteredDates("2024","JAN","18","20");
        assertTrue(datesFilter);
    }
    @Test
    public void testPricesInvalidDates(){  //no results are shown
        LoginPage loginPage=new LoginPage(driver);
        loginPage.login("mika@example.com","123");

        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        boolean loaded=accommodationsPage.pageLoaded();
        assertTrue(loaded);

        accommodationsPage.filterByGuestsDatesAndPrices("5",1200,53000,"2024","JAN","27","30");
        int resultNumber=accommodationsPage.countCards();
        assertEquals(resultNumber,0);
    }
    @Test
    public void testPricesInvalidPriceRange(){  //no results are shown
        LoginPage loginPage=new LoginPage(driver);
        loginPage.login("mika@example.com","123");

        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        boolean loaded=accommodationsPage.pageLoaded();
        assertTrue(loaded);

        accommodationsPage.filterByGuestsDatesAndPrices("5",2200,53000,"2024","JAN","17","20");
        int resultNumber=accommodationsPage.countCards();
        assertEquals(resultNumber,0);
    }

}
