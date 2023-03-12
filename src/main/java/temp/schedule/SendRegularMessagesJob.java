package temp.schedule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import temp.settings.StorageOfUsers;

import java.util.Calendar;

public class SendRegularMessagesJob implements Job {
    private static final Logger logger = LogManager.getLogger(SendRegularMessagesJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Start SendRegularMessagesJob");
        StorageOfUsers storageOfUsers = getStorageOfUsers();

        storageOfUsers.getUsersWithNotficationOnCurrentHour(getCurrentHour())
                .forEach(this::sendNotification);

        logger.info("Finish SendRegularMessagesJob");
    }
    private void sendNotification(Long userId) {
        // TODO need to implement service to send notification for user and use it
    }

    private int getCurrentHour() {
        return Calendar.HOUR_OF_DAY;
    }
    private StorageOfUsers getStorageOfUsers() {
        return StorageOfUsers.getInstance();
    }
}
