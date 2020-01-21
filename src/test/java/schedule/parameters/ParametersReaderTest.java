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
    void readParameters_AbleToParseEmptyParameterForRequiredHours() throws ParseException {
        //given
        LocalTime beginTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        int requiredHours = 0;
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        EnteredParameters expectedEnteredParameters = new EnteredParameters.Builder(beginTime, endTime, requiredHours)
                .withLessonDays(EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY))
                .withStartDate(startDate)
                .build();

        //when
        ParametersReader parametersReader = new ParametersReader();
        EnteredParameters testedEnteredParameters = parametersReader.readParameters(new String[] {"1"});
        System.out.println(testedEnteredParameters);

        //then
        assertThat(expectedEnteredParameters.getRequiredHours(), equalTo(testedEnteredParameters.getRequiredHours()));
    }
}
