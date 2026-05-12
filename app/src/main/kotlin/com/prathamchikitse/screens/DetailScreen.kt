package com.prathamchikitse.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prathamchikitse.R
import com.prathamchikitse.data.EmergencyRepository
import com.prathamchikitse.model.AppLanguage
import com.prathamchikitse.model.Emergency
import com.prathamchikitse.ui.theme.*
import com.prathamchikitse.utils.TtsManager
import com.prathamchikitse.utils.LanguageManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    emergencyId: String,
    currentLanguage: AppLanguage,
    onBack: () -> Unit
) {
    val emergency = EmergencyRepository.getEmergencyById(emergencyId)
    val context = LocalContext.current

    if (emergency == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Emergency not found")
        }
        return
    }

    val cardColor = try {
        Color(android.graphics.Color.parseColor(emergency.colorHex))
    } catch (e: Exception) {
        EmergencyRed
    }

    val ttsManager = remember { TtsManager(context) }
    var isSpeaking by remember { mutableStateOf(false) }

    LaunchedEffect(ttsManager) {
        ttsManager.setOnSpeakingChanged { speaking ->
            isSpeaking = speaking
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            ttsManager.shutdown()
        }
    }

    val title = getLocalizedTitle(context, emergency.title)
    val allText = buildTtsText(emergency, context, currentLanguage)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cardColor
                )
            )
        },
        containerColor = NeutralGray
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = padding.calculateTopPadding() + 16.dp,
                bottom = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Card
            item {
                HeroCard(emergency = emergency, cardColor = cardColor, title = title)
            }

            // Audio Button
            item {
                AudioModeButton(
                    isSpeaking = isSpeaking,
                    onToggle = {
                        if (isSpeaking) {
                            ttsManager.stop()
                        } else {
                            ttsManager.speak(allText, currentLanguage)
                        }
                    }
                )
            }

            // Steps Header
            item {
                SectionHeader(
                    emoji = "📋",
                    LanguageManager.get("steps_title"),
                    color = cardColor
                )
            }

            // Steps
            itemsIndexed(emergency.steps) { index, step ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + expandVertically()
                ) {
                    StepCard(stepNumber = index + 1, stepText = step, accentColor = cardColor)
                }
            }

            // Do's Section
            item {
                SectionHeader(
                    emoji = "✅",
                    LanguageManager.get("dos_title"),
                    color = SafeGreen
                )
            }

            itemsIndexed(emergency.dos) { _, doItem ->
                DoDontCard(text = doItem, isDo = true)
            }

            // Don'ts Section
            item {
                SectionHeader(
                    emoji = "❌",
                    LanguageManager.get("donts_title"),
                    color = EmergencyRed
                )
            }

            itemsIndexed(emergency.donts) { _, dontItem ->
                DoDontCard(text = dontItem, isDo = false)
            }

            // Emergency Call Banner
            item {
                EmergencyCallBanner()
            }
        }
    }
}

@Composable
fun HeroCard(emergency: Emergency, cardColor: Color, title: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(cardColor, cardColor.copy(alpha = 0.7f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = emergency.emoji, fontSize = 64.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun AudioModeButton(isSpeaking: Boolean, onToggle: () -> Unit) {
    Button(
        onClick = onToggle,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSpeaking) Color(0xFF1565C0) else EmergencyRed
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Icon(
            imageVector = if (isSpeaking) Icons.Default.Pause else Icons.Default.VolumeUp,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = if (isSpeaking)
                stringResource(R.string.stop_audio)
            else
                stringResource(R.string.start_audio),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SectionHeader(emoji: String, title: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "$emoji $title",
            style = MaterialTheme.typography.headlineSmall,
            color = color,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun StepCard(stepNumber: Int, stepText: String, accentColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Step number circle
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(accentColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$stepNumber",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = stepText,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                lineHeight = 24.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun DoDontCard(text: String, isDo: Boolean) {
    val bgColor = if (isDo) SafeGreen.copy(alpha = 0.08f) else EmergencyRed.copy(alpha = 0.08f)
    val iconColor = if (isDo) SafeGreen else EmergencyRed
    val icon = if (isDo) "✅" else "❌"
    val borderColor = if (isDo) SafeGreen.copy(alpha = 0.3f) else EmergencyRed.copy(alpha = 0.3f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun EmergencyCallBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = EmergencyRedDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(R.string.emergency_call),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.ambulance_available),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "🚑 108",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "🚒 101  🚓 100",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }
    }
}

// Build the full TTS text for reading
fun buildTtsText(emergency: Emergency, context: Context, language: AppLanguage): String {
    val sb = StringBuilder()
    val titleResId = context.resources.getIdentifier(
        "emergency_${emergency.title}", "string", context.packageName
    )
    val title = if (titleResId != 0) context.getString(titleResId) else emergency.title

    sb.appendLine(title)
    sb.appendLine()
    sb.appendLine("Steps:")
    emergency.steps.forEachIndexed { i, step ->
        sb.appendLine("Step ${i + 1}. $step")
    }
    sb.appendLine()
    sb.appendLine("What to do:")
    emergency.dos.forEach { sb.appendLine(it) }
    sb.appendLine()
    sb.appendLine("What not to do:")
    emergency.donts.forEach { sb.appendLine(it) }

    return sb.toString()
}

// Localized title helper
fun getLocalizedTitle(context: Context, titlekey: String): String {
    val resId = context.resources.getIdentifier(
        "emergency_$titlekey", "string", context.packageName
    )
    return if (resId != 0) context.getString(resId)
    else titlekey.replace("_", " ").replaceFirstChar { it.uppercase() }
}
