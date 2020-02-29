package schedule.holidays;

import java.time.LocalDate;
import java.util.List;

public interface HolidayChecker {
    List<LocalDate> getHolidays(int year);
}