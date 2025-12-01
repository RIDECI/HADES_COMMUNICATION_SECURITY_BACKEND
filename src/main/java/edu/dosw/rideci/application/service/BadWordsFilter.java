package edu.dosw.rideci.application.service;

import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class BadWordsFilter {

    private final Set<String> forbiddenWords = Set.of(
            "gonorrea", "hp", "malparido", "mierda", "pirobo", "puta", "marica", "mk"
    );

    public boolean containsBadWords(String message) {
        String normalized = message.toLowerCase();
        return forbiddenWords.stream()
                .anyMatch(normalized::contains);
    }
}
