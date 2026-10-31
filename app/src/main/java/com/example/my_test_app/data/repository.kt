package com.example.my_test_app.data

import com.example.my_test_app.domain.AppInfo
import com.example.my_test_app.domain.AppRepository
import com.example.my_test_app.data.mapper.toDomain // Импорт маппера

class AppRepositoryImpl : AppRepository {

    private val appsDto = listOf(
        AppDto("ВКонтакте", "Социальная сеть для общения", "Социальные", android.R.drawable.ic_menu_myplaces),
        AppDto("Telegram", "Мессенджер с облачным хранением", "Общение", android.R.drawable.ic_menu_send),
        AppDto("YouTube", "Видео, музыка и стримы", "Развлечения", android.R.drawable.ic_media_play),
        AppDto("Яндекс Карты", "Навигация и пробки", "Навигация", android.R.drawable.ic_menu_mylocation),
        AppDto("Spotify", "Музыка и подкасты", "Музыка", android.R.drawable.ic_media_play)
    )

    private val showcaseAppsDto = listOf(
        AppDto("App1", "Описание 1", "Игры", android.R.drawable.ic_menu_myplaces),
        AppDto("App2", "Описание 2", "Игры", android.R.drawable.ic_menu_send),
        AppDto("App3", "Описание 3", "Игры", android.R.drawable.ic_media_play),
        AppDto("App4", "Описание 4", "Учеба", android.R.drawable.ic_menu_mylocation),
        AppDto("App5", "Описание 5", "Учеба", android.R.drawable.ic_media_play),
        AppDto("App6", "Описание 6", "Учеба", android.R.drawable.ic_menu_myplaces),
        AppDto("App7", "Описание 7", "Работа", android.R.drawable.ic_menu_send),
        AppDto("App8", "Описание 8", "Работа", android.R.drawable.ic_media_play),
        AppDto("App9", "Описание 9", "Работа", android.R.drawable.ic_menu_mylocation)
    )

    override fun getApps(): List<AppInfo> {
        return appsDto.map { it.toDomain() }
    }

    override fun getShowcaseApps(): List<AppInfo> {
        return showcaseAppsDto.map { it.toDomain() }
    }
}