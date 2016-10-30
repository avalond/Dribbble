package com.hunter.cookies.api;

import android.text.TextUtils;

import com.hunter.cookies.entity.ImagesEntity;
import com.hunter.cookies.entity.ShotsEntity;
import com.hunter.cookies.entity.UserEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class SearchConverter implements Converter<ResponseBody, List<ShotsEntity>> {

    public static final class Factory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                                Retrofit retrofit) {
            return INSTANCE;
        }
    }

    private SearchConverter() {
    }

    static final SearchConverter INSTANCE = new SearchConverter();

    private static final String HOST = "https://dribbble.com";
    private static final Pattern PATTERN_PLAYER_ID = Pattern.compile("users/(\\d+?)/", Pattern.DOTALL);

    @Override
    public List<ShotsEntity> convert(ResponseBody value) throws IOException {
        final Elements shotElements = Jsoup.parse(value.string(), HOST).select("li[id^=screenshot]");
        final List<ShotsEntity> shots = new ArrayList<>(shotElements.size());
        for (Element element : shotElements) {
            final ShotsEntity shot = parseShot(element);
            if (shot != null) shots.add(shot);
        }
        return shots;
    }

    private static ShotsEntity parseShot(Element element) {
        final Element descriptionBlock = element.select("a.dribbble-over").first();
        String description = descriptionBlock.select("span.comment").text().trim();
        if (!TextUtils.isEmpty(description)) description = "<p>" + description + "</p>";

        String imgUrl = element.select("img").first().attr("src");
        if (imgUrl.contains("_teaser.")) imgUrl = imgUrl.replace("_teaser.", ".");
        ImagesEntity imagesEntity = new ImagesEntity();
        imagesEntity.setHidpi(imgUrl);

        return new ShotsEntity.Builder().setId(Integer.parseInt(element.id().replace("screenshot-", "")))
                                        .setHtmlUrl(HOST + element.select("a.dribbble-link").first().attr("href"))
                                        .setTitle(descriptionBlock.select("strong").first().text())
                                        .setDescription(description)
                                        .setImages(imagesEntity)
                                        .setAnimated(element.select("div.gif-indicator").first() != null)
                                        .setLikesCount(Integer.parseInt(
                                                element.select("li.fav").first().child(0).text().replaceAll(",", "")))
                                        .setCommentsCount(Integer.parseInt(
                                                element.select("li.cmnt").first().child(0).text().replaceAll(",", "")))
                                        .setViewsCount(Integer.parseInt(
                                                element.select("li.views").first().child(0).text().replaceAll(",", "")))
                                        .setUser(parsePlayer(element.select("h2").first()))
                                        .build();
    }

    private static UserEntity parsePlayer(Element element) {
        final Element userBlock = element.select("a.url").first();
        String avatarUrl = userBlock.select("img.photo").first().attr("src");
        if (avatarUrl.contains("/mini/")) {
            avatarUrl = avatarUrl.replace("/mini/", "/normal/");
        }
        final Matcher matchId = PATTERN_PLAYER_ID.matcher(avatarUrl);
        int id = -1;
        if (matchId.find() && matchId.groupCount() == 1) {
            id = Integer.parseInt(matchId.group(1));
        }
        final String slashUsername = userBlock.attr("href");
        final String username = TextUtils.isEmpty(slashUsername) ? null : slashUsername.substring(1);
        return new UserEntity.Builder().setId(id)
                                       .setName(userBlock.text())
                                       .setUsername(username)
                                       .setHtmlUrl(HOST + slashUsername)
                                       .setAvatarUrl(avatarUrl)
                                       .setPro(element.select("span.badge-pro").size() > 0)
                                       .build();
    }

}
