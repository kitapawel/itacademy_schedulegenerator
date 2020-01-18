package schedule.holidays;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

class HolidaysCheckerFactoryTest {

    @Test
    public void createHolidaysChecker_createsDefaultHolidaysChecker() {
        HolidaysCheckerFactory factory = new HolidaysCheckerFactory();
        HolidayChecker holidayChecker = factory.createHolidaysChecker("Default");

        assertThat(holidayChecker, instanceOf(DefaultHolidayChecker.class));
    }

    @Test
    public void createHolidaysChecker_createsCalendarificHolidaysChecker() {
        HolidaysCheckerFactory factory = new HolidaysCheckerFactory();
        HolidayChecker holidayChecker = factory.createHolidaysChecker("Calendarific");

        assertThat(holidayChecker, instanceOf(CalendarificHolidayChecker.class));
    }
}