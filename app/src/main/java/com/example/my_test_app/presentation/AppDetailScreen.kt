package com.example.my_test_app.presentation
import com.example.my_test_app.domain.InstallState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.my_test_app.domain.AppInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailScreen(
    appInfo: AppInfo?,
    navController: NavController,
    viewModel: AppDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Передаем данные в ViewModel при открытии экрана
    LaunchedEffect(appInfo) {
        viewModel.setAppDetails(appInfo)
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
        when (val currentState = state) {
            is AppDetailsState.Loading -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is AppDetailsState.Error -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("Ошибка загрузки")
                }
            }
            is AppDetailsState.Content -> {
                val app = currentState.appDetails
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = app.iconUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(128.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(text = app.category, color = MaterialTheme.colorScheme.secondary, fontSize = 12.sp)
                            Spacer(Modifier.height(4.dp))
                            Text(text = app.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
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

                    // Кнопка установки с логикой отмены
                    InstallButton(
                        installState = currentState.installState,
                        onClick = { viewModel.onInstallClick() },
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    )

                    Spacer(Modifier.height(24.dp))

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

                    Text(text = "Описание", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text(text = app.description, fontSize = 14.sp)

                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun InstallButton(
    installState: InstallState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = when (installState) {
        is InstallState.NotInstalled -> "Установить"
        is InstallState.Installing -> "Отменить (${installState.progress}%)"
        is InstallState.Installed -> "Установлено"
    }

    Button(
        onClick = onClick,
        enabled = installState !is InstallState.Installed,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}