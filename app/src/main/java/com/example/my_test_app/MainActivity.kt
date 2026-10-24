package com.example.my_test_app

import android.os.Bundle
import androidx.compose.runtime.remember
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// --- Главная Activity ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}

// --- Навигация ---
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "appList") {
        composable("appList") {
            // Передаем ViewModel в экран списка
            AppListScreen(navController = navController)
        }
        composable(
            route = "appDetail/{appIndex}",
            arguments = listOf(navArgument("appIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("appIndex") ?: 0
            val app = (sampleApps + showcaseApps).getOrNull(index)
            AppDetailScreen(appInfo = app, navController = navController)
        }
    }
}

// --- Экран списка ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    navController: NavController,
    viewModel: AppListViewModel = viewModel() // Инициализация ViewModel
) {
    // Подписываемся на состояние ViewModel. collectAsStateWithLifecycle безопасен для поворотов экрана.
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    // Показываем Snackbar только один раз при изменении состояния
    LaunchedEffect(uiState.isSnackbarVisible) {
        if (uiState.isSnackbarVisible) {
            snackbarHostState.showSnackbar(uiState.snackbarMessage)
            viewModel.onSnackbarShown() // Сообщаем ViewModel, что показали и можно скрыть
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Список приложений") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { viewModel.onToolbarClick() } // Клик по тулбару
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            // Экран загрузки, если понадобится
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(uiState.apps) { index, app ->
                    Box(modifier = Modifier.clickable {
                        navController.navigate("appDetail/$index")
                    }) {
                        AppListItem(app)
                    }
                }

                // Блок "Витрина" внизу
                item {
                    ShowcaseSection(
                        showcaseApps = showcaseApps,
                        onAppClick = { app ->
                            val globalIndex = (sampleApps + showcaseApps).indexOf(app)
                            navController.navigate("appDetail/$globalIndex")
                        }
                    )
                }
            }
        }
    }
}

// --- Элемент списка ---
@Composable
fun AppListItem(app: AppInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = app.iconResId),
                contentDescription = "Иконка ${app.name}",
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = app.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = app.description, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 4.dp))
                Text(text = "Категория: ${app.category}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

// --- Данные для основного списка ---
val sampleApps = listOf(
    AppInfo("ВКонтакте", "Социальная сеть для общения", "Социальные", android.R.drawable.ic_menu_myplaces),
    AppInfo("Telegram", "Мессенджер с облачным хранением", "Общение", android.R.drawable.ic_menu_send),
    AppInfo("YouTube", "Видео, музыка и стримы", "Развлечения", android.R.drawable.ic_media_play),
    AppInfo("Яндекс Карты", "Навигация и пробки", "Навигация", android.R.drawable.ic_menu_mylocation),
    AppInfo("Spotify", "Музыка и подкасты", "Музыка", android.R.drawable.ic_media_play)
)