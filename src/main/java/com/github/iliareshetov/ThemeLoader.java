package com.github.iliareshetov;

import javafx.scene.Scene;

import java.util.Objects;

/**
 * The ThemeLoader class provides static methods to load different CSS themes
 * into a JavaFX Scene, specifically designed for JSON syntax highlighting.
 * <p>
 * It supports loading a dark theme and a light theme, each defined by a
 * corresponding CSS file tailored for JSON syntax highlighting.
 */
public class ThemeLoader {

    /**
     * Loads the dark theme CSS file designed for JSON syntax highlighting and
     * applies it to the given Scene.
     *
     * @param scene The JavaFX Scene to which the dark JSON theme will be applied.
     * @throws NullPointerException if the "dark-theme.css" file is not found.
     */
    public static void loadDarkTheme(Scene scene) {
        scene.getStylesheets().add(Objects.requireNonNull(ThemeLoader.class.getResource("dark-theme.css")).toExternalForm());
    }

    /**
     * Loads the light theme CSS file designed for JSON syntax highlighting and
     * applies it to the given Scene.
     *
     * @param scene The JavaFX Scene to which the light JSON theme will be applied.
     * @throws NullPointerException if the "light-theme.css" file is not found.
     */
    public static void loadLightTheme(Scene scene) {
        scene.getStylesheets().add(Objects.requireNonNull(ThemeLoader.class.getResource("light-theme.css")).toExternalForm());
    }

}

