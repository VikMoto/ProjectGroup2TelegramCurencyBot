package temp;

import org.quartz.SchedulerException;
import temp.schedule.ScheduleService;
import temp.schedule.impl.ScheduleServiceImpl;
import temp.telegram.TelegramBotService;

public class AppLauncher {

    public static void main(String[] args) {
        TelegramBotService botService = new TelegramBotService();

        ScheduleService scheduleService = new ScheduleServiceImpl();
        try {
            scheduleService.init();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
