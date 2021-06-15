package by.treval.telegramtravelbot.bot;

import by.treval.telegramtravelbot.config.BotConfig;
import by.treval.telegramtravelbot.service.BotServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBotApiListener extends TelegramLongPollingBot {

    private final BotConfig config;
    private final BotServiceImpl botService;

    public TelegramBotApiListener(BotConfig config, BotServiceImpl botService) {
        this.config = config;
        this.botService = botService;
    }

    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()){
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();
            String answer = botService.getAnswer(text);
            sendMessage(chatId, answer);
        }
    }


    private void sendMessage(String chatId, String answer) {
        SendMessage sendMessage = SendMessage.
                builder()
                .text(answer)
                .chatId(chatId)
                .build();

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
