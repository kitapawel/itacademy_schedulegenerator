package schedule.parameters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

public class EnteredParameters {
    private Set<DayOfWeek> lessonDays;
    private LocalTime beginTime;
    private LocalTime endTime;
    private int requiredHours;
    private LocalDate startDate;
    private String fileName;
    private boolean showHelp;


    private EnteredParameters(LocalTime beginTime, LocalTime endTime, int requiredHours, String fileName, boolean showHelp, Set<DayOfWeek> lessonDays, LocalDate startDate) {
        this.lessonDays = lessonDays;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.requiredHours = requiredHours;
        this.startDate = startDate;
        this.fileName = fileName;
        this.showHelp = showHelp;
    }

    public Set<DayOfWeek> getLessonDays() {
        return lessonDays;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getRequiredHours() {
        return requiredHours;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isShowHelp() {
        return showHelp;
    }


    public static class Builder {
        private Set<DayOfWeek> lessonDays;
        private LocalTime beginTime;
        private LocalTime endTime;
        private int requiredHours;
        private LocalDate startDate;
        private String fileName;
        private boolean showHelp;

        public Builder(LocalTime beginTime, LocalTime endTime, int requiredHours, Set<DayOfWeek> lessonDays, LocalDate startDate) {
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.requiredHours = requiredHours;
            this.lessonDays = lessonDays;
            this.startDate = startDate;
        }


        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder showHelp(boolean showHelp) {
            this.showHelp = showHelp;
            return this;
        }

        public EnteredParameters build() {
            return new EnteredParameters(beginTime, endTime, requiredHours, fileName, showHelp, lessonDays, startDate);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnteredParameters that = (EnteredParameters) o;
        return requiredHours == that.requiredHours &&
                showHelp == that.showHelp &&
                Objects.equals(beginTime, that.beginTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(lessonDays, that.lessonDays) &&
                Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginTime, endTime, requiredHours, fileName, showHelp, lessonDays, startDate);
    }

    @Override
    public String toString() {
        return "LessonParameter{" +
                "beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", requiredHours=" + requiredHours +
                ", fileName='" + fileName + '\'' +
                ", showHelp=" + showHelp +
                ", lessonDays=" + lessonDays +
                ", startDate=" + startDate +
                '}';
    }
}