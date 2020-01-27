package schedule.parameters;

import org.apache.commons.cli.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

public class ParametersReader {
    public EnteredParameters readParameters(String[] args) throws ParseException {

        int requiredHours = 0;
        EnumSet<DayOfWeek> lessonDays = EnumSet.noneOf(DayOfWeek.class);
        LocalDate startDate = null;
        LocalTime beginTime = null;
        LocalTime endTime = null;
        String fileName = "schedule.xlsx";

        Options options = new Options();
        options.addOption("n", true, "required number of hours");
        options.addOption("f", true, "name of the created file");
        options.addOption("s", true, "start date of the course, e.g. 31-01-2018, DD-MM-YYYY");
        options.addOption("b", true, "beginning time of a lesson, e.g. 9:00, H:MM");
        options.addOption("e", true, "end time of a lesson, e.g. 12:00, H:MM");
        options.addOption("d", true, "days of week when lessons can take place, separated by space");

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "help", options );

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);


            if (commandLine.hasOption("n")) {
                requiredHours = Integer.parseInt(commandLine.getOptionValue("n"));
            }

            if (commandLine.hasOption("f")) {
            fileName = commandLine.getOptionValue("f");
            }

            if (commandLine.hasOption("s")) {
                String cmdLineInput = commandLine.getOptionValue("s");
                System.out.println(cmdLineInput);//testing code to see input is accepted
                String[] dateComponents = cmdLineInput.split("-");
                for (String s : dateComponents) {
                    System.out.println(s);//test code to see input code is processed
                }
                int day = Integer.parseInt(dateComponents[0]);
                int month = Integer.parseInt(dateComponents[1]);
                int year = Integer.parseInt(dateComponents[2]);

                startDate = LocalDate.of(year, month, day);
            }

            if (commandLine.hasOption("b")) {
                String cmdLineInput = commandLine.getOptionValue("b");
                String[] timeComponents = cmdLineInput.split(":");
                int hour = Integer.parseInt(timeComponents[0]);
                int minute = Integer.parseInt(timeComponents[1]);

                beginTime = LocalTime.of(hour,minute);
            }

            if (commandLine.hasOption("e")) {
                String cmdLineInput = commandLine.getOptionValue("e");
                String[] timeComponents = cmdLineInput.split(":");
                int hour = Integer.parseInt(timeComponents[0]);
                int minute = Integer.parseInt(timeComponents[1]);

                endTime = LocalTime.of(hour,minute);
            }



            if (commandLine.hasOption("d")) {
                String cmdLineInput = commandLine.getOptionValue("d");
                String[] daysOfWeek = cmdLineInput.split(" ");
                for (String s : daysOfWeek){
                    String weekDay = s.toUpperCase();
                    lessonDays.add(DayOfWeek.valueOf(weekDay));
                }
            }


        EnteredParameters readParameters = new EnteredParameters.Builder(beginTime, endTime, requiredHours)
                .withLessonDays(lessonDays)
                .withFileName(fileName)
                .withStartDate(startDate)
                .build();
        System.out.println(readParameters);
        return readParameters;
    }

}