package com.ask0n;

import com.ask0n.domains.Anime;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.ask0n.Constants.*;

public class MessageService {
    public static SendPhoto message(String chatId, InputFile image, String text) {
        return SendPhoto.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .photo(image)
                .caption(text)
                .build();
    }

    public static SendMessage message(String chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .text(text)
                .build();
    }

    public static SendPhoto keyboardMessage(String chatId, List<Anime> animeList) {
        final SendPhoto message = new SendPhoto();

        message.setChatId(chatId);
        message.setCaption(CHOICE);
        message.setPhoto(CHOICE_IMAGE);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (Anime anime : animeList) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(anime.getTitles().get("en"));
            button.setCallbackData(String.valueOf(anime.getId()));
            buttons.add(button);
            keyboard.add(buttons);
        }

        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }

    public static SendPhoto animeMessage(String chatId, Anime anime) {
        return SendPhoto.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .photo(anime.getCoverImage())
                .caption(anime.getCaption())
                .build();
    }

    public static SendPhoto notFoundMessage(String chatId) {
        return SendPhoto.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .photo(NOT_FOUND_IMAGE)
                .caption(NOT_FOUND)
                .build();
    }

    public static SendPhoto tryAgainMessage(String chatId) {
        return SendPhoto.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .photo(TRY_AGAIN_IMAGE)
                .caption(TRY_AGAIN)
                .build();
    }

    public static SendPhoto errorMessage(String chatId) {
        return SendPhoto.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .photo(ERROR_IMAGE)
                .caption(ERROR)
                .build();
    }

    public static SendPhoto notFoundCommandMessage(String chatId) {
        return SendPhoto.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .photo(NOT_FOUND_COMMAND_IMAGE)
                .caption(NOT_FOUND_COMMAND)
                .build();
    }
}
