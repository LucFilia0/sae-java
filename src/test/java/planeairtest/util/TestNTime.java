package planeairtest.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.* ;

import planeair.exceptions.InvalidTimeException;
import planeair.util.NTime;

public class TestNTime {
    private NTime time1 ;

    private NTime time2 ;

    @Before
    public void setup() {
        try {
            time1 = new NTime(12,12) ;
            time2 = new NTime(0, 0) ;
        }

        catch (Exception e) {
            // rooooon mimimimi
        }
    }

    @Test 
    public void NTime() {
        assertThrows(InvalidTimeException.class, 
            () -> new NTime(-12, 12)) ;
        assertThrows(InvalidTimeException.class, 
            () -> new NTime(72, 12)) ;
        assertThrows(InvalidTimeException.class, 
            () -> new NTime(12, -12)) ;
        assertThrows(InvalidTimeException.class, 
            () -> new NTime(12, 72)) ;
        
    }

    @Test
    public void testGetValueInMinutes() {
        assertEquals(732, time1.getValueInMinutes()) ;
        assertEquals(0, time2.getValueInMinutes()) ;
    }

}