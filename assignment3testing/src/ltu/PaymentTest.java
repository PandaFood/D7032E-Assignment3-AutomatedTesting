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
    public void testGetMonthlyAmount(){

    }

}
