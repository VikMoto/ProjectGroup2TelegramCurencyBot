package temp.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StorageOfUsers {
    private static volatile StorageOfUsers instance;
    private ConcurrentHashMap<Long, BotUser> userSettings;

    private StorageOfUsers() {
        userSettings = new ConcurrentHashMap<>();
    }

    public static StorageOfUsers getInstance() {
        StorageOfUsers result = instance;
        if (result != null) {
            return result;
        }
        synchronized (StorageOfUsers.class) {
            if (instance == null) {
                instance = new StorageOfUsers();
            }
            return instance;
        }
    }

    public void add(BotUser botUser) {
        userSettings.put(botUser.getId(), botUser);
    }

    public BotUser get(long userId) {
        return userSettings.get(userId);
    }

    public List<Long> getUsersWithNotficationOnCurrentHour(int time) {
        List<Long> userIds = new ArrayList<>();
        for (BotUser botUser : userSettings.values()) {
            if (botUser.isScheduler() && botUser.getSchedulerTime() == time) {
                userIds.add(botUser.getId());
            }
        }
        return userIds;
    }
}
