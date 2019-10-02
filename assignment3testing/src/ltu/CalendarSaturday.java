package ltu;

import java.util.Calendar;
import java.util.Date;

public class CalendarSaturday extends CalendarImpl {

    private int y, m, d;

    @Override
    public Date getDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 03, 01);
        return cal.getTime();
    }
}
