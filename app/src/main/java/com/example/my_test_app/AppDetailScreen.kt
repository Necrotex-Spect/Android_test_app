package com.example.my_test_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailScreen(appInfo: AppInfo?, navController: NavController) {
    if (appInfo == null) {
        Text("Приложение не найдено")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Поделиться */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Поделиться")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // Шапка (Иконка, название, категория, размер)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = appInfo.iconResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = appInfo.category,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = appInfo.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(text = "Разработчик: VK Education", fontSize = 12.sp)
                    Spacer(Modifier.height(8.dp))
                    Row {
                        Text(text = "12+", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(16.dp))
                        Text(text = "15 MB", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Кнопка "Установить"
            Button(
                onClick = { /* Логика установки */ },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text(text = "Установить", fontSize = 16.sp)
            }

            Spacer(Modifier.height(24.dp))

            // Скриншоты (заглушка, так как в AppInfo нет картинок)
            Text(text = "Скриншоты", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Описание
            Text(text = "Описание", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(text = appInfo.description, fontSize = 14.sp)

            Spacer(Modifier.height(24.dp))

            HorizontalDivider()

            Spacer(Modifier.height(16.dp))

            Text(text = "Разработчик", fontWeight = FontWeight.Bold)
            Text(text = "VK Education", fontSize = 16.sp)

            Spacer(Modifier.height(32.dp))
        }
    }
}