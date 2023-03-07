package temp.telegram;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BotConstants {
    private static final String CREDENTIALS_FILE = "config/bot_credentials.txt";
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream inputStream = new FileInputStream(CREDENTIALS_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private BotConstants(){}
    public static String getBotToken() {
        return properties.getProperty("BOT_TOKEN");
    }

    public static String getBotName() {
        return properties.getProperty("BOT_NAME");
    }

}
