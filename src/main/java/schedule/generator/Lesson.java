package schedule.generator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Lesson {

    private LocalDate date;
    private LocalTime beginTime;
    private LocalTime endTime;

    public Lesson(LocalDate date, LocalTime beginTime, LocalTime endTime) {
        this.date = date;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(date, lesson.date) &&
                Objects.equals(beginTime, lesson.beginTime) &&
                Objects.equals(endTime, lesson.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, beginTime, endTime);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "date=" + date +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
