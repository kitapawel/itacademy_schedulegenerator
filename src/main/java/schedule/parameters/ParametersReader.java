package schedule.parameters;

import org.apache.commons.cli.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumSet;

public class ParametersReader {
    public EnteredParameters readParameters(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("n", true, "required number of hours");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        int requiredHours = 0;
        if(cmd.hasOption("n")) {
            requiredHours = Integer.parseInt(cmd.getOptionValue("n"));
        }

        EnteredParameters readParameters = new EnteredParameters.Builder(LocalTime.of(10, 0), LocalTime.of(12, 0), requiredHours)
                .withLessonDays(EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY))
                .withStartDate(LocalDate.of(2020, 1, 1))
                .build();
        return readParameters;
    }

}