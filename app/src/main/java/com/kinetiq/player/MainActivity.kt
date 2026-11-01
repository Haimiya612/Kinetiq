package com.kinetiq.player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val horizon = HorizonCapabilities.detect(this)
        KinetiqLogger.info("MainActivity", "App started; Horizon OS detected=${horizon.isHorizonOs}")

        setContent {
            val context = LocalContext.current
            val settings = remember(context) { SettingsRepository(context) }
            val themeMode by settings.themeMode.collectAsStateWithLifecycle(
                initialValue = ThemeMode.SYSTEM
            )
            val coroutineScope = rememberCoroutineScope()
            KinetiqTheme(themeMode = themeMode) {
                KinetiqApp(
                    themeMode = themeMode,
                    onThemeModeChange = { mode ->
                        coroutineScope.launch { settings.setThemeMode(mode) }
                    },
                    isHorizonOs = horizon.isHorizonOs
                )
            }
        }
    }
}

private enum class Destination(val label: String, val symbol: String) {
    HOME("Home", "⌂"),
    SEARCH("Search", "⌕"),
    LIBRARY("Library", "▤"),
    SETTINGS("Settings", "⚙")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KinetiqApp(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    isHorizonOs: Boolean
) {
    var destination by rememberSaveable { mutableStateOf(Destination.HOME) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("KINETIQ", fontWeight = FontWeight.ExtraBold)
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "PLAYER",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        bottomBar = {
            BoxWithConstraints {
                if (maxWidth < 700.dp) {
                    NavigationBar {
                        Destination.entries.forEach { item ->
                            NavigationBarItem(
                                selected = destination == item,
                                onClick = { destination = item },
                                icon = { Text(item.symbol) },
                                label = { Text(item.label) }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val compact = maxWidth < 700.dp
            Row(Modifier.fillMaxSize()) {
                if (!compact) {
                    Column(
                        modifier = Modifier
                            .width(210.dp)
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "YOUR SPACE",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                        Destination.entries.forEach { item ->
                            val selected = destination == item
                            if (selected) {
                                Button(
                                    onClick = { destination = item },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("${item.symbol}   ${item.label}")
                                }
                            } else {
                                TextButton(
                                    onClick = { destination = item },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("${item.symbol}   ${item.label}")
                                }
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        Text(
                            "MOTION. MEDIA. MODULAR.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    when (destination) {
                        Destination.HOME -> HomeScreen(isHorizonOs)
                        Destination.SEARCH -> SearchScreen()
                        Destination.LIBRARY -> LibraryScreen()
                        Destination.SETTINGS -> SettingsScreen(themeMode, onThemeModeChange)
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(isHorizonOs: Boolean) {
    Text("Your media, in motion.", style = MaterialTheme.typography.headlineLarge)
    Spacer(Modifier.height(8.dp))
    Text(
        "A personal media space built for Meta Quest.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(28.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(Modifier.padding(24.dp)) {
            Text(
                "KINETIQ",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Fluid streaming, limitless extensions.",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "The foundation is in place. Playback, discovery, and modules will arrive in their planned development phases.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    Spacer(Modifier.height(20.dp))
    Text("FOUNDATION STATUS", style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(12.dp))
    StatusCard(
        title = "Quest environment",
        detail = if (isHorizonOs) "Horizon OS detected" else "Android fallback environment"
    )
    StatusCard(
        title = "Playback engine",
        detail = "Not connected yet · planned for Phase 2"
    )
    StatusCard(
        title = "Kinetiq Engine",
        detail = "Module runtime not installed · planned for Phase 3"
    )
}

@Composable
private fun StatusCard(title: String, detail: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(detail, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun SearchScreen() {
    Text("Discover", style = MaterialTheme.typography.headlineLarge)
    Spacer(Modifier.height(8.dp))
    Text(
        "Search across your enabled sources when Kinetiq Engine arrives.",
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(24.dp))
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(Modifier.padding(20.dp)) {
            Text("No sources connected", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text("Discovery is planned for Phase 4. Search is not simulated in this foundation build.")
        }
    }
}

@Composable
private fun LibraryScreen() {
    Text("Your library", style = MaterialTheme.typography.headlineLarge)
    Spacer(Modifier.height(8.dp))
    Text(
        "Your personal media collection will live here.",
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(24.dp))
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(Modifier.padding(20.dp)) {
            Text("Nothing here yet", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text("Local playback is planned for Phase 2. Library history and collections follow in Phase 5.")
        }
    }
}

@Composable
private fun SettingsScreen(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    Text("Settings", style = MaterialTheme.typography.headlineLarge)
    Spacer(Modifier.height(8.dp))
    Text(
        "Make Kinetiq feel right in your space.",
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(24.dp))
    Text("APPEARANCE", style = MaterialTheme.typography.labelLarge)
    Spacer(Modifier.height(8.dp))
    Text("Theme", style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(4.dp))
    Text(
        "Choose how Kinetiq looks. Your selection is saved on this device.",
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(12.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ThemeMode.entries.forEach { mode ->
            if (themeMode == mode) {
                Button(onClick = { onThemeModeChange(mode) }) {
                    Text(mode.label)
                }
            } else {
                OutlinedButton(onClick = { onThemeModeChange(mode) }) {
                    Text(mode.label)
                }
            }
        }
    }
    Spacer(Modifier.height(28.dp))
    Divider()
    Spacer(Modifier.height(16.dp))
    Text("ABOUT", style = MaterialTheme.typography.labelLarge)
    Spacer(Modifier.height(8.dp))
    Text("Kinetiq Player · 0.1.0 Phase 1", style = MaterialTheme.typography.bodyLarge)
    Text(
        "Motion. Media. Modular.",
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

private val LightColors = lightColorScheme(
    primary = Color(0xFF245CDB),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCE6FF),
    onPrimaryContainer = Color(0xFF102A64),
    secondary = Color(0xFF52617B),
    background = Color(0xFFF7F8FC),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFE9ECF4)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF9CB7FF),
    onPrimary = Color(0xFF102A64),
    primaryContainer = Color(0xFF203B75),
    onPrimaryContainer = Color(0xFFDCE6FF),
    secondary = Color(0xFFB8C6E3),
    background = Color(0xFF10131A),
    surface = Color(0xFF171B24),
    surfaceVariant = Color(0xFF252B37)
)

@Composable
private fun KinetiqTheme(
    themeMode: ThemeMode,
    content: @Composable () -> Unit
) {
    val dark = when (themeMode) {
        ThemeMode.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }
    MaterialTheme(
        colorScheme = if (dark) DarkColors else LightColors,
        content = content
    )
}
