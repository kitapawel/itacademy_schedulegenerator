package schedule.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import schedule.generator.Lesson;
import schedule.generator.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.MINUTES;

public class WorkbookCreator {
    public Workbook createWorkbook(Schedule schedule) {
        Workbook workbook = new XSSFWorkbook();

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd.mm.yyyy"));
        CellStyle timeStyle = workbook.createCellStyle();
        timeStyle.setDataFormat(workbook.createDataFormat().getFormat("HH:MM"));

        Sheet sheet = workbook.createSheet();

        List<Lesson> lessons = schedule.getLessons();

        Row hoursDoneRow = getOrCreateRow(sheet, 0);
        Cell hoursDoneCaption = hoursDoneRow.createCell(8);
        hoursDoneCaption.setCellValue("Hours done:");
        Cell hoursDoneValue = hoursDoneRow.createCell(9);
        hoursDoneValue.setCellFormula("SUM(F1:F57)");

        Row hoursPlannedRow = getOrCreateRow(sheet, 1);
        Cell hoursPlannedCaption = hoursPlannedRow.createCell(8);
        hoursPlannedCaption.setCellValue("Hours planned:");
        Cell hoursPlannedValue = hoursPlannedRow.createCell(9);
        hoursPlannedValue.setCellValue(getRequiredHours(lessons));

        Row lessonsDoneRow = getOrCreateRow(sheet, 3);
        Cell lessonsDoneCaption = lessonsDoneRow.createCell(8);
        lessonsDoneCaption.setCellValue("Lessons done:");
        Cell lessonsDoneValue = lessonsDoneRow.createCell(9);
        lessonsDoneValue.setCellFormula("COUNTIF(B1:B57,\"done\")");

        Row lessonsPlannedRow = getOrCreateRow(sheet, 4);
        Cell lessonsPlannedCaption = lessonsPlannedRow.createCell(8);
        lessonsPlannedCaption.setCellValue("Lessons planned:");
        Cell lessonsPlannedValue = lessonsPlannedRow.createCell(9);
        lessonsPlannedValue.setCellValue(lessons.size());

        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            Row row = getOrCreateRow(sheet, i);

            Cell dateCell = row.createCell(0);
            dateCell.setCellValue(lesson.getDate());
            dateCell.setCellStyle(dateStyle);

            Cell beginTime = row.createCell(3);
            beginTime.setCellValue(createDateWithTime(lesson.getBeginTime()));
            beginTime.setCellStyle(timeStyle);

            Cell endTime = row.createCell(4);
            endTime.setCellValue(createDateWithTime(lesson.getEndTime()));
            endTime.setCellStyle(timeStyle);

            Cell lessonTime = row.createCell(5);
            int index = i + 1;
            String timeFormula = "E" + index + "-D" + index + "";
            lessonTime.setCellFormula("IF(B" + index + "=\"done\",HOUR(" + timeFormula + ") + MINUTE(" + timeFormula + ")/60,\"\")");
        }

        Row statusRow = getOrCreateRow(sheet, sheet.getLastRowNum() + 1);
        Cell statusCaption = statusRow.createCell(8);
        statusCaption.setCellValue("STATUS:");
        Cell statusValue = statusRow.createCell(9);
        statusValue.setCellFormula("IF(J1=J2,\"COMPLETED\",\"IN PROGRESS\")");

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

    private long getRequiredHours(Collection<Lesson> lessons) {
        long requiredMinutes = 0;
        for (Lesson lesson : lessons) {
            requiredMinutes += MINUTES.between(lesson.getBeginTime(), lesson.getEndTime());
        }
        return requiredMinutes / 60;
    }

}