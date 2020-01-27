package schedule.parameters;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import schedule.generator.IScheduleGenerator;
import schedule.generator.Lesson;
import schedule.generator.Schedule;
import schedule.generator.ScheduleGenerator;
import schedule.holidays.CalendarificHolidayChecker;
import schedule.holidays.DefaultHolidayChecker;
import schedule.holidays.HolidayChecker;
import schedule.parameters.EnteredParameters;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ParametersReaderTest {
    @Test
    public  void readParameters_containsNReturnsEnteredParametersWithRequiredNumberOfHours() throws ParseException {
        //given
        ParametersReader parametersReader = new ParametersReader();
        String[] args = {"-n", "42"};
        //when
        EnteredParameters result = parametersReader.readParameters(args);
        //then
        assertThat(result.getRequiredHours(), equalTo(42));
    }

    @Test
    public  void readParameters_containsNReturnsEnteredParametersWithFileName() throws ParseException {
        //given
        ParametersReader parametersReader = new ParametersReader();
        String[] args = {"-f", "exampleschedule.xlsx"};
        //when
        EnteredParameters result = parametersReader.readParameters(args);
        //then
        assertThat(result.getFileName(), equalTo("exampleschedule.xlsx"));
    }

    @Test
    public  void readParameters_containsNReturnsEnteredParametersWithStartDate() throws ParseException {
        //given
        ParametersReader parametersReader = new ParametersReader();
        String[] args = {"-s", "30-05-2019"};
        //when
        EnteredParameters result = parametersReader.readParameters(args);
        //then
        assertThat(result.getStartDate(), equalTo(LocalDate.of(2019,05,30)));
    }

    @Test
    public  void readParameters_containsNReturnsEnteredParametersWithLessonBeginTime() throws ParseException {
        //given
        ParametersReader parametersReader = new ParametersReader();
        String[] args = {"-b", "9:00"};
        //when
        EnteredParameters result = parametersReader.readParameters(args);
        //then
        assertThat(result.getBeginTime(), equalTo(LocalTime.of(9,00)));
    }

    @Test
    public  void readParameters_containsNReturnsEnteredParametersWithLessonEndTime() throws ParseException {
        //given
        ParametersReader parametersReader = new ParametersReader();
        String[] args = {"-e", "12:00"};
        //when
        EnteredParameters result = parametersReader.readParameters(args);
        //then
        assertThat(result.getEndTime(), equalTo(LocalTime.of(12,00)));
    }

//    @Test
//    public  void readParameters_containsNReturnsEnteredParametersWithDaysOfWeek() throws ParseException {
//        //given
//        ParametersReader parametersReader = new ParametersReader();
//        String[] args = {"-d", "MONDAY"};
//        //when
//        EnteredParameters result = parametersReader.readParameters(args);
//        result.getLessonDays();
//        //then
//        assertThat(result.getLessonDays(), equalTo(DayOfWeek.MONDAY));
//    }
}
