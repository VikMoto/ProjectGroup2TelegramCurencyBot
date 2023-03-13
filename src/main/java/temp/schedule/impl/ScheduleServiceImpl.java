package temp.schedule.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import temp.schedule.ScheduleService;
import temp.schedule.SendRegularMessagesJob;

public class ScheduleServiceImpl implements ScheduleService {
    private final String NOTIFICATION_JOB = "testJob";
    private final String CRONTRIGGER = "crontrigger";
    private final String CRONTRIGGERGROUP = "crontriggergroup1";
//    private final String CRON_SCHEDULE = "0 0 8,9,10,11,12,13,14,15,16,17,18 ? * * *";
    private final String CRON_SCHEDULE = "0 0 17 13 3 ? 2023";

    private static final Logger logger = LogManager.getLogger(ScheduleServiceImpl.class);

    @Override
    public void init() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(SendRegularMessagesJob.class)
                .withIdentity(NOTIFICATION_JOB)
                .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(CRONTRIGGER , CRONTRIGGERGROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(CRON_SCHEDULE))
                .build();
        System.out.println("cronTrigger.getCronExpression() = " + cronTrigger.getCronExpression());

        SchedulerFactory schFactory = new StdSchedulerFactory();
        Scheduler sch = schFactory.getScheduler();
        sch.start();
        sch.scheduleJob(job, cronTrigger);

        logger.info("Schedule have been initialize");
    }
}
