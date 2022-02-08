package com.ask0n.domains.filters;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AnimeFilter extends Filter {
    private String title;
    @Builder.Default
    private Boolean nsfw = true;

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("nsfw", nsfw.toString());
        return params;
    }
}
