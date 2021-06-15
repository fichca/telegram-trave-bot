package by.treval.telegramtravelbot.config;

import by.treval.telegramtravelbot.bot.TelegramBotApiListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Component
@Slf4j
public class BotInitializer {

    @Autowired
    TelegramBotApiListener telegramBotApiListener;

    @EventListener({ContextRefreshedEvent.class})
    public void Init() throws TelegramApiException {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBotApiListener);
        } catch (TelegramApiRequestException e) {
            log.error(e.toString());
        }
    }

}
