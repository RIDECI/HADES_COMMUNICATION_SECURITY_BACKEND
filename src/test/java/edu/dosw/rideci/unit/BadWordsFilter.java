package edu.dosw.rideci.unit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.dosw.rideci.application.service.BadWordsFilter;

class BadWordsFilterTest {

    private final BadWordsFilter filter = new BadWordsFilter();

    @Test
    void shouldReturnFalseWhenMessageHasNoBadWords() {
        boolean result = filter.containsBadWords("hola como estas");

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenMessageContainsBadWord() {
        boolean result = filter.containsBadWords("eres un hp");

        assertTrue(result);
    }

    @Test
    void shouldDetectBadWordIgnoringCase() {
        boolean result = filter.containsBadWords("GONORREA");

        assertTrue(result);
    }

    @Test
    void shouldDetectBadWordInsideSentence() {
        boolean result = filter.containsBadWords("ese man es un malparido");

        assertTrue(result);
    }
}
