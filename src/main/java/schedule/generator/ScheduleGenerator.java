package schedule.generator;

import schedule.parameters.EnteredParameters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class ScheduleGenerator implements IScheduleGenerator{

    @Override
    public Schedule generateSchedule(EnteredParameters enteredParameters) {
        LocalTime beginTime = enteredParameters.getBeginTime();
        LocalTime endTime = enteredParameters.getEndTime();
        //Period period = Period.between(endTime,beginTime);

        int lessonLength = endTime.getHour() - beginTime.getHour();
        Set<DayOfWeek> lessonDays = enteredParameters.getLessonDays();
        int requiredHours = enteredParameters.getRequiredHours();
        int usedHours = 0;
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

        LocalTime lastLessonEndTime = endTime.minusHours(usedHours - requiredHours);
        Lesson lastLesson = lessons.get(lessons.size() - 1);
        lastLesson.setEndTime(lastLessonEndTime);


        boolean isSuccessful = usedHours == requiredHours;
        return new Schedule(lessons, isSuccessful);

    }
}