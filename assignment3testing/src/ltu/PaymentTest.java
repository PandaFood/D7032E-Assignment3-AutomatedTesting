package ltu;

import static ltu.CalendarFactory.getCalendar;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PaymentTest
{
    PaymentImpl payment;

    @Before
    public void initTest() throws IOException {
         payment = new PaymentImpl(getCalendar());
    }

    @Test
    public void testSilly()
    {
        assertEquals(1, 1);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentName(){
        payment.getMonthlyAmount(null, 100, 100, 100);
        payment.getMonthlyAmount("1997", 100, 100, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentIncome(){
        payment.getMonthlyAmount("19970718-0000", -1, 100, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentStudyRate(){
        payment.getMonthlyAmount("19970718-0000", 100, -1, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentCompletionRatio(){
        payment.getMonthlyAmount("19970718-0000", 100, 100, -1);
    }

    @Test
    public void testStudyPace(){
        // [201]
        assertEquals(0, payment.getMonthlyAmount("19970718-0000", 0, 25, 100));
        assertTrue(0<payment.getMonthlyAmount("19970718-0000", 0, 50, 100));

        // [202]
        assertEquals(1396+3564, payment.getMonthlyAmount("19970718-0000", 0, 75, 100));

        // [203]
        assertEquals(2816+7088, payment.getMonthlyAmount("19970718-0000", 0, 100, 100));
    }


    @Test
    public void testIncome(){
        // [301]
        assertTrue(0 < payment.getMonthlyAmount("19970718-0000", 0, 100, 100));
        assertTrue(0 < payment.getMonthlyAmount("19970718-0000", 85813, 100, 100));
        assertFalse(0 < payment.getMonthlyAmount("19970718-0000", 100000, 100, 100));

        // [302]
        assertTrue(0 < payment.getMonthlyAmount("19970718-0000", 0, 75, 100));
        assertTrue(0 < payment.getMonthlyAmount("19970718-0000", 128722, 75, 100));
        assertFalse(0 < payment.getMonthlyAmount("19970718-0000", 130000, 75, 100));
    }


    @Test
    public void testCompletionRatio(){
        // [401]
        assertTrue(0 < payment.getMonthlyAmount("19970718-0000", 0, 100, 100));
        assertTrue(0 < payment.getMonthlyAmount("19970718-0000", 0, 100, 50));
        assertFalse(0 < payment.getMonthlyAmount("19970718-0000", 0, 100, 25));

    }

}
