package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parser.JacksonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private long chatId;
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        if (update.getMessage().getText().equals("Курс валют\uD83D\uDCB5")) {
            try {
                //Выводим в консоль курс валют
                String string = JacksonParser.parseJackson("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5");
                sendMessage.setText(string);
                //Пишем второе сообщение в консоль
//                execute(sendMessage);
//                sendMessage.setText(message("Спасибо, что воспользовались нашими услугами"));
                execute(sendMessage);
            } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.getMessage().getText().equals("/start")){
            sendMessage.setText(message("Начинай :)"));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            sendMessage.setText(message("Воспользуйтесь кнопками⬇️"));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String message(String message) {
        setButtons();
        return message;
    }

    private void setButtons() {
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        replyKeyboardMarkup.setResizeKeyboard(true);

        keyboardFirstRow.add(new KeyboardButton("Курс валют\uD83D\uDCB5"));
        keyboard.add(keyboardFirstRow);
        //Обновляем клавиатуру
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public String getBotUsername() {
        return "@ShowCurrencyBot";
    }

    public String getBotToken() {
        return "782125934:AAGj0nwFDD2yhS6L9mX_W7ETMmhq_yaAQA4";
    }
}

