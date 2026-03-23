package com.example.unchain.data.defaultAddictions

import com.example.unchain.domain.models.Addiction
import com.example.unchain.domain.models.AddictionWithProgress

object DefaultAddictions {
    val list = listOf(
        Addiction(id = 1, name = "Алкоголь"),
        Addiction(id = 2, name = "Курение"),
        Addiction(id = 3, name = "Социальные сети"),
        Addiction(id = 4, name = "Сладкое"),
        Addiction(id = 5, name = "Азартные игры")
    )
}