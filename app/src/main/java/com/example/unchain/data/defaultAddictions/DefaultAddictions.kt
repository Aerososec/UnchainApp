package com.example.unchain.data.defaultAddictions

import com.example.unchain.domain.models.Addiction
import com.example.unchain.domain.models.personalization.Personality


object DefaultAddictions {
    val list = listOf(
        Addiction(id = 1, name = "Алкоголь"),
        Addiction(id = 2, name = "Курение"),
        Addiction(id = 3, name = "Социальные сети"),
        Addiction(id = 4, name = "Сладкое"),
        Addiction(id = 5, name = "Азартные игры")
    )

    val listPersonality = listOf(
        Personality(
            id = 1,
            name = "Кратос",
            description = "Бог Войны",
            price = 100,
            promptModifier = "_",
            themeId = 1
        ),
        Personality(
            id = 2,
            name = "Messi",
            description = "Football",
            price = 200,
            promptModifier = "_",
            themeId = 2
        ),
        Personality(
            id = 3,
            name = "Ronaldo",
            description = "Football",
            price = 300,
            promptModifier = "_",
            themeId = 3
        ),
        Personality(
            id = 4,
            name = "Shelby",
            description = "Peaky Blinders",
            price = 500,
            promptModifier = "_",
            themeId = 4
        ),
        Personality(
            id = 5,
            name = "Qwerty",
            description = "Описание",
            price = 500,
            promptModifier = "_",
            themeId = 4
        )
    )
}