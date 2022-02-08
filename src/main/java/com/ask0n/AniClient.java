package com.ask0n;

import com.ask0n.domains.AniResponse;
import com.ask0n.domains.Anime;
import com.ask0n.domains.Pagination;
import com.ask0n.domains.filters.AnimeFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class AniClient {
    private static final Logger log = LogManager.getLogger(AniClient.class);
    private static final String ENDPOINT = "https://api.aniapi.com/v1/";
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    private static final JsonMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .enable(SerializationFeature.WRITE_ENUM_KEYS_USING_INDEX)
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .build();

    private final String token;

    @SneakyThrows
    private <T> CompletableFuture<T> fetch(String uri, TypeReference<T> clazz) {
        log.info(String.format("GET | %s%s", ENDPOINT, uri));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT + uri))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        return HTTP_CLIENT
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApplyAsync(res -> {
                    try {
                        return mapper.readValue(res.body(), clazz);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }

    public CompletableFuture<AniResponse<Anime>> getAnime(Long id) {
        return fetch("anime/" + id, new TypeReference<>() {
        });
    }

    public CompletableFuture<AniResponse<Pagination<Anime>>> getAnimeList(AnimeFilter filter) {
        return fetch("anime" + filter.toQueryString(), new TypeReference<>() {
        });
    }

    public CompletableFuture<AniResponse<List<Anime>>> getRandomAnime() {
        return fetch("random/anime/1/true", new TypeReference<>() {
        });
    }
}
