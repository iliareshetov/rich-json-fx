package com.github.iliareshetov;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RichJsonFX {
    private final JsonFactory jsonFactory;

    public RichJsonFX() {
        jsonFactory = new JsonFactory();
    }

    public static String jsonTokenToClassName(JsonToken jsonToken) {
        if (jsonToken == null) {
            return "";
        }
        return switch (jsonToken) {
            case FIELD_NAME -> "json-property";
            case VALUE_STRING -> "json-string";
            case START_OBJECT -> "json-start-object";
            case END_OBJECT -> "json-end-object";
            case VALUE_NUMBER_FLOAT -> "json-float";
            case VALUE_NUMBER_INT -> "json-int";
            case VALUE_TRUE -> "json-true";
            case VALUE_FALSE -> "json-false";
            case START_ARRAY -> "json-start-array";
            case END_ARRAY -> "json-end-array";
            case VALUE_EMBEDDED_OBJECT -> "json-embedded";
            case VALUE_NULL -> "json-null";
            default -> "";
        };
    }

    /**
     * Highlights json by applying styles to different tokens.
     *
     * @param json The json to be highlighted.
     * @return StyleSpans containing the highlighted tokens.
     */
    public StyleSpans<Collection<String>> highlight(String json) throws IOException {


        List<Match> matches = new ArrayList<>();

        JsonParser parser = jsonFactory.createParser(json);
        while (!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();
            int start = (int) parser.currentTokenLocation().getCharOffset();
            int end = start + parser.getTextLength();

            // add surrounding ""
            if (jsonToken == JsonToken.VALUE_STRING || jsonToken == JsonToken.FIELD_NAME) {
                end += 2;
            }

            String className = jsonTokenToClassName(jsonToken);
            if (!className.isEmpty()) {
                matches.add(new Match(className, start, end));
            }
        }

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        int lastPos = 0;
        for (Match match : matches) {
            if (match.start() > lastPos) {
                spansBuilder.add(Collections.emptyList(), match.start() - lastPos);
            }

            spansBuilder.add(Collections.singleton(match.kind()), match.end() - match.start());
            lastPos = match.end();
        }

        if (lastPos == 0) {
            spansBuilder.add(Collections.emptyList(), json.length());
        }

        return spansBuilder.create();
    }

    public record Match(String kind, int start, int end) implements Comparable<Match> {

        @Override
        public int compareTo(Match match) {
            return Integer.compare(start, match.start);
        }

    }

}