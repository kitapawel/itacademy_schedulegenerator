package schedule;

import org.apache.poi.ss.usermodel.Workbook;
import schedule.excel.WorkbookCreator;
import schedule.generator.Lesson;
import schedule.generator.Schedule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScheduleGeneratorApp {
    public static void main(String[] args) throws IOException {
//        PropertiesReader propertiesReader = PropertiesReader.getInstance();
//        System.out.println(propertiesReader.readProperty("excel.defaultName"));
//

        LocalTime beginTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 30);
        Lesson lesson1 = new Lesson(LocalDate.of(2020, 1, 1), beginTime, endTime);
        Lesson lesson2 = new Lesson(LocalDate.of(2020, 1, 6), beginTime, endTime);
        Lesson lesson3 = new Lesson(LocalDate.of(2020, 1, 8), beginTime, endTime);
        Lesson lesson4 = new Lesson(LocalDate.of(2020, 1, 9), beginTime, endTime);
        Lesson lesson5 = new Lesson(LocalDate.of(2020, 1, 11), beginTime, endTime);
        Lesson lesson6 = new Lesson(LocalDate.of(2020, 1, 12), beginTime, endTime);
        Schedule schedule = new Schedule(List.of(lesson1, lesson2, lesson3, lesson4,lesson5,lesson6), true);

        WorkbookCreator workbookCreator = new WorkbookCreator();
        Workbook workbook = workbookCreator.createWorkbook(schedule);
        FileOutputStream fos = new FileOutputStream("test.xlsx");
        workbook.write(fos);
        workbook.close();
    }
}