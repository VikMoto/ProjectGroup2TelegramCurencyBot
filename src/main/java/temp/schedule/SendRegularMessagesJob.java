package temp.schedule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import temp.settings.StorageOfUsers;
import temp.telegram.CurrencyTelegramBot;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

public class SendRegularMessagesJob implements Job {
        private static final Logger logger = LogManager.getLogger(SendRegularMessagesJob.class);
        private CurrencyTelegramBot bot = CurrencyTelegramBot.getInstance();

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            logger.info("Start SendRegularMessagesJob");
            StorageOfUsers storageOfUsers = getStorageOfUsers();

            storageOfUsers.getUsersWithNotficationOnCurrentHour(getCurrentHour())
                    .forEach(this::sendNotification);

            List<Long> usersWithNotficationOnCurrentHour = storageOfUsers.getUsersWithNotficationOnCurrentHour(getCurrentHour());
            System.out.println("usersWithNotficationOnCurrentHour = " + usersWithNotficationOnCurrentHour);


            logger.info("Finish SendRegularMessagesJob");
        }
        private void sendNotification(Long userId) {
            // TODO need to implement service to send notification for user and use it
            try {
                bot.handleGetInformation(userId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        private int getCurrentHour() {
            return LocalTime.now().getHour();
        }
        private StorageOfUsers getStorageOfUsers() {
            return StorageOfUsers.getInstance();
        }
    }
