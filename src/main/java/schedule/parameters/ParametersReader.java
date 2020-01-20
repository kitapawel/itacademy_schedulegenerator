package schedule.parameters;

import org.apache.commons.cli.*;

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

        return null;
    }

}