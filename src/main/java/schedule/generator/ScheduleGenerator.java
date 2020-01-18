package schedule.generator;

import schedule.holidays.HolidayChecker;
import schedule.parameters.EnteredParameters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ScheduleGenerator implements IScheduleGenerator {

    private HolidayChecker holidayChecker;

    public ScheduleGenerator(HolidayChecker holidayChecker) {
        this.holidayChecker = holidayChecker;
    }

    @Override
    public Schedule generateSchedule(EnteredParameters enteredParameters) {
        LocalTime beginTime = enteredParameters.getBeginTime();
        LocalTime endTime = enteredParameters.getEndTime();
        long lessonLength = MINUTES.between(beginTime, endTime);
        Set<DayOfWeek> lessonDays = enteredParameters.getLessonDays();
        int requiredHours = enteredParameters.getRequiredHours() * 60;
        long usedHours = 0;
        LocalDate currentDate = enteredParameters.getStartDate();
        List<Lesson> lessons = new ArrayList<>();
        do {
            while (!lessonDays.contains(currentDate.getDayOfWeek())) {
                currentDate = currentDate.plusDays(1);
            }
            Lesson lesson = new Lesson(currentDate, beginTime, endTime);
            lessons.add(lesson);
            usedHours = usedHours + lessonLength;
            currentDate = currentDate.plusDays(1);
        } while (usedHours < requiredHours);

        LocalTime lastLessonEndTime = endTime.minusMinutes(usedHours - requiredHours);
        Lesson lastLesson = lessons.get(lessons.size() - 1);
        lastLesson.setEndTime(lastLessonEndTime);

        boolean isSuccessful = usedHours == requiredHours;
        return new Schedule(lessons, isSuccessful);
    }

}