package schedule.generator;

import schedule.parameters.EnteredParameters;

public interface IScheduleGenerator {
    Schedule generateSchedule(EnteredParameters enteredParameters);
}
