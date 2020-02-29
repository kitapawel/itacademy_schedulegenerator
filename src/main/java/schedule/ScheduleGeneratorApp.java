package schedule;

import org.apache.commons.cli.ParseException;
import org.apache.poi.ss.usermodel.Workbook;
import schedule.excel.WorkbookCreator;
import schedule.generator.Schedule;
import schedule.holidays.HolidayChecker;
import schedule.holidays.HolidaysCheckerFactory;
import schedule.parameters.EnteredParameters;
import schedule.parameters.ParametersReader;
import schedule.util.PropertiesReader;

import java.io.FileOutputStream;
import java.io.IOException;

public class ScheduleGeneratorApp {
    public static void main(String[] args) throws IOException {

        ParametersReader parametersReader = new ParametersReader();
        EnteredParameters enteredParameters = null;

        try {
            enteredParameters = parametersReader.readParameters(args);
        } catch (ParseException e) {
            parametersReader.showHelp();
            return;
        }

//        IScheduleGenerator scheduleGenerator = new ScheduleGenerator(new CalendarificHolidayChecker());
//        Schedule schedule = scheduleGenerator.generateSchedule(enteredParameters);
//
//        WorkbookCreator workbookCreator = new WorkbookCreator();
//        Workbook workbook = workbookCreator.createWorkbook(schedule);
//        FileOutputStream fos = new FileOutputStream(enteredParameters.getFileName());
//        workbook.write(fos);
//        workbook.close();

        HolidaysCheckerFactory holidaysCheckerFactory = new HolidaysCheckerFactory();
        PropertiesReader propertiesReader = PropertiesReader.getInstance();
        HolidayChecker holidayChecker = holidaysCheckerFactory.createHolidaysChecker(propertiesReader.readProperty("holiday.checker.type"));
        LessonGenerator lessonGenerator = new LessonGenerator(holidayChecker);

        Schedule schedule = lessonGenerator.generateSchedule(lessonParameters);

        WorkbookCreator excelGenerator = new WorkbookCreator();

        Workbook excel = excelGenerator.createWorkbook(schedule);

        FileOutputStream fos = new FileOutputStream(enteredParameters.getFileName());
        excel.write(fos);
        fos.close();
        excel.close();


    }
}
