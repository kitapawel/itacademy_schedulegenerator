package schedule.holidays;

public class HolidaysCheckerFactory {
    public HolidayChecker createHolidaysChecker(String name) {
        if(name.equalsIgnoreCase("Calendarific")) {
            return new CalendarificHolidayChecker();
        }
        return new DefaultHolidayChecker();
    }
}