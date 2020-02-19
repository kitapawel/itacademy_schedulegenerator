package schedule;

import org.apache.commons.cli.ParseException;
import org.apache.poi.ss.usermodel.Workbook;
import schedule.excel.WorkbookCreator;
import schedule.generator.IScheduleGenerator;
import schedule.generator.Lesson;
import schedule.generator.Schedule;
import schedule.generator.ScheduleGenerator;
import schedule.holidays.CalendarificHolidayChecker;
import schedule.holidays.DefaultHolidayChecker;
import schedule.parameters.EnteredParameters;
import schedule.parameters.ParametersReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScheduleGeneratorApp {
    public static void main(String[] args) throws IOException, ParseException {
//        PropertiesReader propertiesReader = PropertiesReader.getInstance();
//        System.out.println(propertiesReader.readProperty("excel.defaultName"));

        ParametersReader parametersReader = new ParametersReader();

        if (args.length == 0){
            System.out.println("Welcome to the Schedule Generator application. It appears you have not provided any arguments.");
            System.out.println("To learn more about the available parameters run the application with the following command:");
            System.out.println("java ScheduleGeneratorApp -h");
        }
        else {
            EnteredParameters enteredParameters = parametersReader.readParameters(args);
            IScheduleGenerator scheduleGenerator = new ScheduleGenerator(new CalendarificHolidayChecker());
            Schedule schedule = scheduleGenerator.generateSchedule(enteredParameters);

            WorkbookCreator workbookCreator = new WorkbookCreator();
            Workbook workbook = workbookCreator.createWorkbook(schedule);
            FileOutputStream fos = new FileOutputStream(enteredParameters.getFileName());
            workbook.write(fos);
            workbook.close();
        }

    }
}