package schedule.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import schedule.generator.Lesson;
import schedule.generator.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class WorkbookCreator {
    public Workbook createWorkbook(Schedule schedule) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        List<Lesson> lessons = schedule.getLessons();
        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            Row row = getOrCreateRow(sheet, i);

            Cell dateCell = row.createCell(0);
            dateCell.setCellValue(lesson.getDate());

            Cell beginTime = row.createCell(3);
            beginTime.setCellValue(createDateWithTime(lesson.getBeginTime()));

            Cell endTime = row.createCell(4);
            endTime.setCellValue(createDateWithTime(lesson.getEndTime()));

            Cell lessonTime = row.createCell(5);
            int index = i + 1;
            String timeFormula = "E" + index + "-D" + index + "";
            lessonTime.setCellFormula("IF(B" + index + "=\"done\",HOUR(" + timeFormula + ") + MINUTE(" + timeFormula + ")/60,\"\")");
        }

        return workbook;
    }

    private LocalDateTime createDateWithTime(LocalTime localTime) {
        return LocalDateTime.of(LocalDate.now(), localTime);
    }

    private Row getOrCreateRow(Sheet sheet, int index) {
        return Optional.ofNullable(sheet.getRow(index)).orElseGet(() -> sheet.createRow(index));

//        Row row = sheet.getRow(index);
//        if(isNull(row)) {
//            row = sheet.createRow(index);
//        }
//        return row;
    }

}