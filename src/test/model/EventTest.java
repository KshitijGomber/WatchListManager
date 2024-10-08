package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
/**
 * This class is adapted from the EventTest class of AlarmSystem.
 */
public class EventTest {
    private Event e1, e2;

    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e1 = new Event("Sensor open at door");   // (1)
        e2 = new Event("Sensor close at door");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e1.getDescription());
        assertEquals(d, e1.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e1.toString());
    }

    @Test
    public void testHashCodeSameEvents() {
        assertEquals(e1.hashCode(),e1.hashCode());
    }

    @Test
    public void testEqualSameEvents() {
        assertTrue(e1.equals(e1));
        assertTrue(e2.equals(e2));
    }

    @Test
    public void testEqualDifferentEvents() {
        assertFalse(e2.equals(e1));
        assertFalse(e1.equals(e2));
        assertFalse(e1.equals("kwerokoe"));
    }

    @Test
    public void testEqualNullEvents() {
        assertFalse(e2.equals(null));
        assertFalse(e1.equals(null));
    }
}
