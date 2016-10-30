package com.hunter.cookies.utils;

import android.text.Html;

public class HtmlFormatUtils {

    public static String Html2String(String htmlStr) {
        htmlStr = htmlStr.replace("<p>", "");
        htmlStr = htmlStr.replace("</p>", "");
        return Html.fromHtml(htmlStr).toString();
    }

    public static String setupBold(String text, String normalText) {
        return Html.fromHtml("<b>" + text + "</b> " + normalText).toString();
    }

    public static String setupBold(int text, String normalText) {
        return setupBold(text + "", normalText);
    }
}
