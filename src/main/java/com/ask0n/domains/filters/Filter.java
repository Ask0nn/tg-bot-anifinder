package com.ask0n.domains.filters;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@SuperBuilder
@NoArgsConstructor
public abstract class Filter {
    protected abstract Map<String, String> getParams();

    public String toQueryString() {
        Map<String, String> params = getParams();

        return params.isEmpty() ? ""
                : "?" + params.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> !entry.getValue().isBlank())
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }
}
