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

    static int fullLoan = 7088;
    static int halfLoan = 3564;

    static int fullSub = 2816;
    static int halfSub = 1396;

    static int full = fullSub + fullLoan;
    static int half = halfSub + halfLoan;

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

    }

    @Test
    public void testStudyPace50(){
        assertEquals(half,payment.getMonthlyAmount("19900718-0000", 0, 50, 100));
    }

    @Test
    public void testStudyPace75(){
        // [202]
        assertEquals(half, payment.getMonthlyAmount("19900718-0000", 0, 75, 100));
    }

    @Test
    public void testStudyPace100(){
        // [203]
        assertEquals(full, payment.getMonthlyAmount("19900718-0000", 0, 100, 100));
    }


    @Test
    public void testIncomeFull(){
        // [301]
        assertEquals(full, payment.getMonthlyAmount("19900718-0000", 0, 100, 100));
        assertEquals(full, payment.getMonthlyAmount("19900718-0000", 85813, 100, 100));
        assertEquals(0, payment.getMonthlyAmount("19900718-0000", 100000, 100, 100));


    }

    @Test
    public void testIncomeHalf(){
        // [302]
        assertEquals(half, payment.getMonthlyAmount("19900718-0000", 0, 75, 100));
        assertEquals(half, payment.getMonthlyAmount("19900718-0000", 128722, 75, 100));
        assertEquals(0, payment.getMonthlyAmount("19900718-0000", 130000, 75, 100));
    }

    @Test
    public void testCompletionRatio(){
        // [401]
        assertEquals(full, payment.getMonthlyAmount("19900718-0000", 0, 100, 100));
        assertEquals(full, payment.getMonthlyAmount("19900718-0000", 0, 100, 50));
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
    public void testAgeLowLimit() {
        // [101]
        assertEquals(0, payment.getMonthlyAmount("20130718-0000", 0, 100, 100));
        assertEquals(full, payment.getMonthlyAmount("19960418-0000", 0, 100, 100));

    }
    @Test
    public void testAgeUpperLimit(){
        // [102]
        assertEquals(fullSub, payment.getMonthlyAmount("19620718-0000", 0, 100, 100));
        assertEquals(0, payment.getMonthlyAmount("19590418-0000", 0, 100, 100));
    }

    @Test
    public void testAgeLoan(){
        // [103]
        assertEquals(fullSub, payment.getMonthlyAmount("19620718-0000", 0, 100, 100) );
        assertEquals(fullSub, payment.getMonthlyAmount("19690418-0000", 0, 100, 100));
    }

    @Test
    public void testFullLoan(){
        // [501]
        assertEquals(fullLoan, payment.getMonthlyAmount("19900718-0000", 0, 100, 100) - fullSub);
    }

    @Test
    public void testFullSub(){
        // [502]
        assertEquals(fullSub, payment.getMonthlyAmount("19900718-0000", 0, 100, 100) - fullLoan);
    }

    @Test
    public void testHalfLoan(){
        // [503]
        assertEquals(halfLoan, payment.getMonthlyAmount("19900718-0000", 0, 50, 100) - halfSub);
        assertEquals(halfLoan, payment.getMonthlyAmount("19900718-0000", 0, 99, 100) - halfSub);
    }

    @Test
    public void testHalfSub(){
        // [504]
        assertEquals(halfSub, payment.getMonthlyAmount("19900718-0000", 0, 75, 100) - halfLoan);
        assertEquals(halfSub, payment.getMonthlyAmount("19900718-0000", 0, 99, 100) - halfLoan);

    }
}

