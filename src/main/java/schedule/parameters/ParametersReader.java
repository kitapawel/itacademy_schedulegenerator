package schedule.parameters;

import org.apache.commons.cli.*;
import schedule.util.PropertiesReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

public class ParametersReader {

    private final PropertiesReader propertiesReader = PropertiesReader.getInstance();
    private Options options;

    public ParametersReader() {
        options = new Options();
        options.addOption("h", false, "print application help");
        options.addOption("n", true, "required number of hours");
        options.addOption("f", true, "name of the created file");
        options.addOption("s", true, "start date of the course, e.g. 31.01.2018, DD-MM-YYYY");
        options.addOption("b", true, "beginning time of a lesson, e.g. 9:00, H:MM");
        options.addOption("e", true, "end time of a lesson, e.g. 12:00, H:MM");
        options.addOption("d", true, "days of week when lessons can take place, separated by underscore sign (_)");
    }

        int requiredHours = 0;
        EnumSet<DayOfWeek> lessonDays = EnumSet.noneOf(DayOfWeek.class);
        LocalDate startDate = null;
        LocalTime beginTime = null;
        LocalTime endTime = null;
        String fileName = "schedule.xlsx";

        String timeFormat = propertiesReader.readProperty("application.timeFormat");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormat);
        String dateFormat = propertiesReader.readProperty("application.dateFormat");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

    public EnteredParameters readParameters (String[] args) throws ParseException {

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption("n")) {
                requiredHours = Integer.parseInt(commandLine.getOptionValue("n"));
            }

            if (commandLine.hasOption("f")) {
                fileName = commandLine.getOptionValue("f");
            }

            boolean showHelp = commandLine.hasOption("h");

            if (commandLine.hasOption("s")) {
                String cmdLineInput = commandLine.getOptionValue("s");
                startDate = LocalDate.parse(cmdLineInput, dateFormatter);
            }

            if (commandLine.hasOption("b")) {
                String cmdLineInput = commandLine.getOptionValue("b");
                beginTime = LocalTime.parse(cmdLineInput, timeFormatter);
            }

            if (commandLine.hasOption("e")) {
                String cmdLineInput = commandLine.getOptionValue("e");
                endTime = LocalTime.parse(cmdLineInput, timeFormatter);
            }

            if (commandLine.hasOption("d")) {
                String cmdLineInput = commandLine.getOptionValue("d");
                String[] daysOfWeek = cmdLineInput.split("_");
                for (String s : daysOfWeek){
                    String weekDay = s.toUpperCase();
                    lessonDays.add(DayOfWeek.valueOf(weekDay));
                }
            }

        return new EnteredParameters.Builder(beginTime, endTime, requiredHours, lessonDays, startDate).fileName(fileName)
                .showHelp(showHelp).build();
    }

    public void showHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -cp schedule-generator.jar schedulegenerator.ScheduleGeneratorApp -d monday_thursday -b 17:00 -e 18:30 -n 21 -s 30.05.2019 -f example1", options);
    }

}