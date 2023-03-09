package temp.telegram.noncommand;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface GeneralBotCommand {
    SendMessage getMessage();
}
