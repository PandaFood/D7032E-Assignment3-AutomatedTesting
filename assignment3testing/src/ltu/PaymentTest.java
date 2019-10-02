package ltu;

import static ltu.CalendarFactory.getCalendar;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import java.util.concurrent.locks.*;

public class PaymentTest
{
    PaymentImpl payment;

    static int fullLoan = 2816+7088;
    static int halfLoan = 1396+3564;

    @Before
    public void initTest() throws IOException {
         payment = new PaymentImpl(getCalendar("ltu.CalendarSpring"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentAgeNull(){
        payment.getMonthlyAmount(null, 100, 100, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentAgeLength(){
        payment.getMonthlyAmount("1990", 100, 100, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentIncome() throws IllegalArgumentException{
        payment.getMonthlyAmount("19900718-0000", -1, 100, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentStudyRate(){
        payment.getMonthlyAmount("19900718-0000", 100, -1, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentCompletionRatio(){
        payment.getMonthlyAmount("19900718-0000", 100, 100, -1);
    }



    @Test
    public void testStudyPace(){
        // [201]
        assertEquals(0, payment.getMonthlyAmount("19900718-0000", 0, 25, 100));
        assertEquals(halfLoan,payment.getMonthlyAmount("19900718-0000", 0, 50, 100));

        // [202]
        assertEquals(halfLoan, payment.getMonthlyAmount("19900718-0000", 0, 75, 100));

        // [203]
        assertEquals(fullLoan, payment.getMonthlyAmount("19900718-0000", 0, 100, 100));
    }


    @Test
    public void testIncome(){
        // [301]
        assertEquals(fullLoan, payment.getMonthlyAmount("19900718-0000", 0, 100, 100));
        assertEquals(fullLoan, payment.getMonthlyAmount("19900718-0000", 85813, 100, 100));
        assertEquals(0, payment.getMonthlyAmount("19900718-0000", 100000, 100, 100));

        // [302]
        assertEquals(halfLoan, payment.getMonthlyAmount("19900718-0000", 0, 75, 100));
        assertEquals(halfLoan, payment.getMonthlyAmount("19900718-0000", 128722, 75, 100));
        assertEquals(0, payment.getMonthlyAmount("19900718-0000", 130000, 75, 100));
    }


    @Test
    public void testCompletionRatio(){
        // [401]
        assertEquals(fullLoan, payment.getMonthlyAmount("19900718-0000", 0, 100, 100));
        assertEquals(fullLoan, payment.getMonthlyAmount("19900718-0000", 0, 100, 50));
        assertEquals(0, payment.getMonthlyAmount("19900718-0000", 0, 100, 25));
    }

    @Test
    public void testGetNextPaymentDay() throws IOException {

        // [506]
        assertEquals("20160630", payment.getNextPaymentDay());

        assertEquals("20160429", new PaymentImpl(getCalendar("ltu.CalendarSaturday")).getNextPaymentDay());
        assertEquals("20160729", new PaymentImpl(getCalendar("ltu.CalendarSunday")).getNextPaymentDay());
    }


    @Test
    public void testAge(){
        assertEquals(fullLoan, payment.getMonthlyAmount("19900718-0000", 0, 100, 100));
        assertEquals(0, payment.getMonthlyAmount("20130718-0000", 0, 100, 100));
        assertEquals(0, payment.getMonthlyAmount("19200718-0000", 0, 100, 100));
    }
}

