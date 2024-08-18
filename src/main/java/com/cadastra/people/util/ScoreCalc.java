package com.cadastra.people.util;

import java.util.Map;
import java.util.function.Function;

public class ScoreCalc {
    private static final Map<Function<Integer, Boolean>, String> scoreClassifications = Map.of(
            score -> score >= 0 && score <= 200, "Insuficiente",
            score -> score >= 201 && score <= 500, "Inaceitável",
            score -> score >= 501 && score <= 700, "Aceitável",
            score -> score >= 701 && score <= 1000, "Recomendável"
    );

    public static String classifyScore(int score) {
        return scoreClassifications.entrySet().stream()
                .filter(entry -> entry.getKey().apply(score))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("Classificação inválida");
    }
}
