package ltu;


import java.util.Calendar;
import java.util.Date;

public class CalendarSpring extends CalendarImpl {

    private int y, m, d;

    @Override
    public Date getDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 05, 01);
        return cal.getTime();
    }
}
