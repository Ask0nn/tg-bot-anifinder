package com.ask0n;

import com.ask0n.domains.AniResponse;
import com.ask0n.domains.Anime;
import com.ask0n.domains.Pagination;
import com.ask0n.domains.filters.AnimeFilter;
import com.ask0n.enums.Commands;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

import static com.ask0n.Constants.*;

public class AnimeBot extends TelegramLongPollingBot {
    private static final Logger log = LogManager.getLogger(AniClient.class);
    private final AniClient aniClient = AniClient.builder().token(TOKEN).build();

    @Override
    public String getBotUsername() {
        return Constants.USERNAME;
    }

    @Override
    public String getBotToken() {
        return Constants.API_TOKEN;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                log.info(String.format("Received new message: [from: %s, message: %s]",
                        update.getMessage().getFrom().getUserName(),
                        update.getMessage().getText()));
                if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().startsWith(PREFIX)) {
                    handleCommand(update);
                } else {
                    execute(handleText(update));
                }
                return;
            }

            if (update.hasCallbackQuery()) {
                log.info(String.format("Received callback query: [from: %s, data: %s]",
                        update.getCallbackQuery().getFrom().getUserName(),
                        update.getCallbackQuery().getData()));
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(update.getCallbackQuery().getFrom().getId().toString());
                deleteMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                execute(deleteMessage);
                execute(handleCallback(update));
            }
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            execute(MessageService.notFoundCommandMessage(update.getMessage().getChatId().toString()));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    public SendPhoto handleCallback(Update update) throws Exception {
        final String chatId = update.getCallbackQuery().getFrom().getId().toString();
        final String id = update.getCallbackQuery().getData();
        final AniResponse<Anime> response = aniClient.getAnime(Long.valueOf(id)).get();
        if (response.getStatusCode() != 200) {
            log.info("Not found - " + update.getMessage().getFrom().getUserName());
            return MessageService.notFoundMessage(chatId);
        }
        log.info(String.format("Found: [title: %s for: %s]",
                response.getData().getTitles().get("en"),
                update.getCallbackQuery().getFrom().getUserName()));
        return MessageService.animeMessage(chatId, response.getData());
    }

    public SendPhoto handleText(Update update) throws Exception {
        final String chatId = update.getMessage().getChatId().toString();
        final String name = update.getMessage().getText();
        final AniResponse<Pagination<Anime>> response = aniClient
                .getAnimeList(AnimeFilter.builder().title(name).build())
                .get();
        if (response.getStatusCode() != 200) {
            log.info("Not found - " + update.getMessage().getFrom().getUserName());
            return MessageService.notFoundMessage(chatId);
        }
        if (response.getData().getCount() > 100) {
            log.info("Found over 100 results - " + update.getMessage().getFrom().getUserName());
            return MessageService.tryAgainMessage(chatId);
        }
        if (response.getData().getCount() == 1) {
            log.info(String.format("Found: [title: %s for: %s]",
                    response.getData().getDocuments().get(0).getTitles().get("en"),
                    update.getMessage().getFrom().getUserName()));
            return MessageService.animeMessage(chatId, response.getData().getDocuments().get(0));
        }
        return MessageService.keyboardMessage(chatId, response.getData().getDocuments());
    }

    public void handleCommand(Update update) throws Exception {
        final String chatId = update.getMessage().getChatId().toString();
        final String[] splitMessage = update.getMessage().getText().split("\\s+", 2);
        final String command = splitMessage[0];
        switch (Commands.fromString(command)) {
            case START:
                execute(MessageService.message(chatId, START_IMAGE, START));
                break;
            case HELP:
                execute(MessageService.message(chatId, HELP_IMAGE, HELP));
                break;
            case RANDOM:
                final AniResponse<List<Anime>> response = aniClient.getRandomAnime().get();
                final Anime anime = response.getData().get(0);
                log.info(String.format("Found: [title: %s for: %s]",
                        anime.getTitles().get("en"),
                        update.getMessage().getFrom().getUserName()));
                execute(MessageService.animeMessage(chatId, anime));
                break;
        }
    }
}
