package com.ask0n.domains;

import com.ask0n.enums.AnimeFormatEnum;
import com.ask0n.enums.AnimeSeasonEnum;
import com.ask0n.enums.AnimeStatusEnum;
import lombok.Data;
import lombok.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ask0n.Constants.ANILIST_URL;
import static com.ask0n.Constants.NO_IMAGE;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Anime {
    private Long id;
    private Long anilistId;
    private Long malId;
    private AnimeFormatEnum format;
    private AnimeStatusEnum status;
    private Map<String, String> titles;
    private Map<String, String> descriptions;
    private Date startDate;
    private Date endDate;
    private AnimeSeasonEnum seasonPeriod;
    private Integer seasonYear;
    private Integer episodesCount;
    private Integer episodeDuration;
    private String trailerUrl;
    private String coverImage;
    private String coverColor;
    private String bannerImage;
    private List<String> genres;
    private Long sequel;
    private Long prequel;
    private Integer score;

    public InputFile getCoverImage() {
        return coverImage != null ? new InputFile(coverImage) : NO_IMAGE;
    }

    public String getCaption() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Название:\n");
        titles.forEach((k, v) -> builder.append("<b>").append(v).append("</b> (").append(k).append(")\n"));
        if (seasonYear != null && seasonYear > 0) builder.append("\nГод: ").append(seasonYear).append("\n\n");
        builder.append("Ссылки:\n");
        builder.append("<a href=\"").append(ANILIST_URL).append(anilistId).append("\">AniList</a>\n");
        builder.append("<a href=\"").append(trailerUrl).append("\">Трейлер</a>\n\n");
        builder.append("Оценка: ").append(score).append(" | 100\n\n");
        genres.forEach(g -> builder.append("#").append(g.replaceAll("([\\s]|-)", "_")).append(" "));
        return builder.toString();
    }
}
