package temp.schedule;

import org.quartz.SchedulerException;

public interface ScheduleService {
    void init() throws SchedulerException;
}
