package com.github.iliareshetov;

import org.fxmisc.richtext.model.StyleSpans;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RichJsonFXTest {

    @Test
    void testHighlightBasicJson() throws IOException {
        String json = """
                {
                    "name": "John",
                    "age": 30,
                    "isStudent": false,
                    "address": {
                        "city": "New York",
                        "zip": 10001
                    }
                }
                """;
        RichJsonFX highlighter = new RichJsonFX();

        StyleSpans<Collection<String>> styleSpans = highlighter.highlight(json);

        assertEquals(Collections.singletonList("json-start-object"), new ArrayList<>(styleSpans.getStyleSpan(0).getStyle()));

        assertEquals(Collections.singletonList("json-property"), new ArrayList<>(styleSpans.getStyleSpan(2).getStyle()));
        assertEquals(Collections.singletonList("json-string"), new ArrayList<>(styleSpans.getStyleSpan(4).getStyle()));

        assertEquals(Collections.singletonList("json-property"), new ArrayList<>(styleSpans.getStyleSpan(6).getStyle()));
        assertEquals(Collections.singletonList("json-int"), new ArrayList<>(styleSpans.getStyleSpan(8).getStyle()));

        assertEquals(Collections.singletonList("json-property"), new ArrayList<>(styleSpans.getStyleSpan(10).getStyle()));
        assertEquals(Collections.singletonList("json-false"), new ArrayList<>(styleSpans.getStyleSpan(12).getStyle()));

        assertEquals(Collections.singletonList("json-property"), new ArrayList<>(styleSpans.getStyleSpan(14).getStyle()));

        assertEquals(Collections.singletonList("json-start-object"), new ArrayList<>(styleSpans.getStyleSpan(16).getStyle()));
        assertEquals(Collections.singletonList("json-property"), new ArrayList<>(styleSpans.getStyleSpan(18).getStyle()));
        assertEquals(Collections.singletonList("json-string"), new ArrayList<>(styleSpans.getStyleSpan(20).getStyle()));

        assertEquals(Collections.singletonList("json-property"), new ArrayList<>(styleSpans.getStyleSpan(22).getStyle()));
        assertEquals(Collections.singletonList("json-int"), new ArrayList<>(styleSpans.getStyleSpan(24).getStyle()));
        assertEquals(Collections.singletonList("json-end-object"), new ArrayList<>(styleSpans.getStyleSpan(26).getStyle()));

        assertEquals(Collections.singletonList("json-end-object"), new ArrayList<>(styleSpans.getStyleSpan(28).getStyle()));

    }

    @Test
    void testHighlightEmptyJson() throws IOException {
        String json = "";
        RichJsonFX highlighter = new RichJsonFX();

        StyleSpans<Collection<String>> styleSpans = highlighter.highlight(json);

        // Assert that the result spans an empty string with empty style
        assertEquals(1, styleSpans.getSpanCount());
        assertEquals(Collections.emptyList(), new ArrayList<>(styleSpans.getStyleSpan(0).getStyle()));
    }

}
