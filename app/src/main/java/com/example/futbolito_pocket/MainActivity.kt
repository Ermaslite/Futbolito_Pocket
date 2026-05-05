package com.example.futbolito_pocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.futbolito_pocket.ui.cancha.DisenoCancha
import com.example.futbolito_pocket.ui.cancha.GameViewModel
import com.example.futbolito_pocket.ui.theme.Futbolito_PocketTheme

class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Futbolito_PocketTheme {
                val context = LocalContext.current
                viewModel.initSensors(context)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BoxWithConstraints(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(Color(0xFF4CAF50))
                    ) {
                        val width = constraints.maxWidth.toFloat()
                        val height = constraints.maxHeight.toFloat()
                        
                        // Scale factors for DisenoCancha
                        val sX = width / 1000f
                        val sY = height / 1800f

                        // El diseño de la cancha
                        DisenoCancha(
                            ballOffset = androidx.compose.ui.geometry.Offset(
                                viewModel.ballPosition.x * sX,
                                viewModel.ballPosition.y * sY
                            ),
                            sX = sX,
                            sY = sY
                        )

                        // Marcador: America vs Chivas
                        ScoreBoard(
                            americaScore = viewModel.americaScore,
                            chivasScore = viewModel.chivasScore
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreBoard(americaScore: Int, chivasScore: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "AMÉRICA", color = Color.Yellow, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "$americaScore", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        }
        Text(text = "VS", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "CHIVAS", color = Color.Red, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "$chivasScore", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}
