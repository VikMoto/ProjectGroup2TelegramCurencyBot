package temp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import temp.schedule.ScheduleService;
import temp.schedule.impl.ScheduleServiceImpl;
import temp.telegram.TelegramBotService;
import java.io.IOException;

public class AppLauncher {

    private static final Logger logger = LogManager.getLogger(AppLauncher.class);
    public static void main(String[] args) throws IOException {
        TelegramBotService botService = new TelegramBotService();

        ScheduleService scheduleService = new ScheduleServiceImpl();
        try {
            scheduleService.init();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
