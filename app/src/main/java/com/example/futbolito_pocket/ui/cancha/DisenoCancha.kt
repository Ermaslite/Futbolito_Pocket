package com.example.futbolito_pocket.ui.cancha

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun DisenoCancha(ballOffset: Offset, sX: Float, sY: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // 1. Background: Vertical Grass Stripes
        val stripes = 14
        val stripeWidth = w / stripes
        for (i in 0 until stripes) {
            drawRect(
                color = if (i % 2 == 0) Color(0xFF689F38) else Color(0xFF7CB342),
                topLeft = Offset(i * stripeWidth, 0f),
                size = Size(stripeWidth, h)
            )
        }

        // --- Style Configuration ---
        val wallColor = Color.White.copy(alpha = 0.9f)
        val wallStrokeWidth = 5.dp.toPx()
        val wallStyle = Stroke(width = wallStrokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)

        // 2. OUTER BOUNDARY
        val m = 40f
        val outerFrame = Path().apply {
            moveTo(150f * sX, m * sY)
            lineTo(400f * sX, m * sY)
            moveTo(600f * sX, m * sY)
            lineTo(850f * sX, m * sY)
            quadraticTo(960f * sX, m * sY, 960f * sX, 150f * sY)
            lineTo(960f * sX, 1650f * sY)
            quadraticTo(960f * sX, 1760f * sY, 850f * sX, 1760f * sY)
            lineTo(600f * sX, 1760f * sY)
            moveTo(400f * sX, 1760f * sY)
            lineTo(150f * sX, 1760f * sY)
            quadraticTo(40f * sX, 1760f * sY, 40f * sX, 1650f * sY)
            lineTo(40f * sX, 150f * sY)
            quadraticTo(40f * sX, m * sY, 150f * sX, m * sY)
        }
        drawPath(outerFrame, color = wallColor, style = wallStyle)

        // 3. INTERNAL MAZE WALLS
        drawPath(Path().apply {
            moveTo(240f * sX, 230f * sY); quadraticTo(500f * sX, 480f * sY, 760f * sX, 230f * sY)
            moveTo(240f * sX, 1570f * sY); quadraticTo(500f * sX, 1320f * sY, 760f * sX, 1570f * sY)
            
            moveTo(100f * sX, 100f * sY); quadraticTo(100f * sX, 200f * sY, 200f * sX, 200f * sY)
            moveTo(900f * sX, 100f * sY); quadraticTo(900f * sX, 200f * sY, 800f * sX, 200f * sY)
            moveTo(100f * sX, 1700f * sY); quadraticTo(100f * sX, 1600f * sY, 200f * sX, 1600f * sY)
            moveTo(900f * sX, 1700f * sY); quadraticTo(900f * sX, 1600f * sY, 800f * sX, 1600f * sY)

            moveTo(400f * sX, m * sY); lineTo(400f * sX, 150f * sY)
            moveTo(600f * sX, m * sY); lineTo(600f * sX, 150f * sY)
            moveTo(400f * sX, 1760f * sY); lineTo(400f * sX, 1650f * sY)
            moveTo(600f * sX, 1760f * sY); lineTo(600f * sX, 1650f * sY)
            
            moveTo(465f * sX, 320f * sY); lineTo(500f * sX, 355f * sY); lineTo(535f * sX, 320f * sY)
            moveTo(465f * sX, 1480f * sY); lineTo(500f * sX, 1445f * sY); lineTo(535f * sX, 1480f * sY)

            moveTo(420f * sX, 550f * sY); lineTo(580f * sX, 550f * sY)
            moveTo(420f * sX, 1250f * sY); lineTo(580f * sX, 1250f * sY)
            
            moveTo(730f * sX, 500f * sY); lineTo(850f * sX, 500f * sY)
            moveTo(730f * sX, 1300f * sY); lineTo(850f * sX, 1300f * sY)
            
            moveTo(150f * sX, 500f * sY); lineTo(270f * sX, 500f * sY)
            moveTo(150f * sX, 1300f * sY); lineTo(270f * sX, 1300f * sY)
        }, color = wallColor, style = wallStyle)

        // Vertical Bars
        val vLines = listOf(
            Pair(Offset(140f * sX, 250f * sY), Offset(140f * sX, 550f * sY)),
            Pair(Offset(140f * sX, 1250f * sY), Offset(140f * sX, 1550f * sY)),
            Pair(Offset(860f * sX, 250f * sY), Offset(860f * sX, 550f * sY)),
            Pair(Offset(860f * sX, 1250f * sY), Offset(860f * sX, 1550f * sY)),
            Pair(Offset(140f * sX, 650f * sY), Offset(140f * sX, 1150f * sY)),
            Pair(Offset(860f * sX, 650f * sY), Offset(860f * sX, 1150f * sY)),
            Pair(Offset(260f * sX, 400f * sY), Offset(260f * sX, 750f * sY)),
            Pair(Offset(260f * sX, 1050f * sY), Offset(260f * sX, 1400f * sY)),
            Pair(Offset(740f * sX, 400f * sY), Offset(740f * sX, 750f * sY)),
            Pair(Offset(740f * sX, 1050f * sY), Offset(740f * sX, 1400f * sY))
        )
        vLines.forEach { drawLine(wallColor, it.first, it.second, wallStrokeWidth, StrokeCap.Round) }

        // Horizontal Bars
        val hLines = listOf(
            Pair(Offset(400f * sX, 650f * sY), Offset(600f * sX, 650f * sY)),
            Pair(Offset(400f * sX, 1150f * sY), Offset(600f * sX, 1150f * sY)),
            Pair(Offset(50f * sX, 900f * sY), Offset(250f * sX, 900f * sY)),
            Pair(Offset(750f * sX, 900f * sY), Offset(950f * sX, 900f * sY)),
            Pair(Offset(320f * sX, 800f * sY), Offset(450f * sX, 800f * sY)),
            Pair(Offset(320f * sX, 1000f * sY), Offset(450f * sX, 1000f * sY)),
            Pair(Offset(550f * sX, 800f * sY), Offset(680f * sX, 800f * sY)),
            Pair(Offset(550f * sX, 1000f * sY), Offset(680f * sX, 1000f * sY))
        )
        hLines.forEach { drawLine(wallColor, it.first, it.second, wallStrokeWidth, StrokeCap.Round) }

        // Center Arcs
        val cR = 100f * sX
        val cRect = Rect(w/2 - cR, h/2 - cR, w/2 + cR, h/2 + cR)
        for (i in 0 until 4) {
            drawArc(wallColor, i * 90f + 25f, 40f, false, cRect.topLeft, cRect.size, style = wallStyle)
        }

        // 4. BUMPERS
        val bR = 14f * sX 
        val dotPositions = listOf(
            Offset(90f * sX, 550f * sY), Offset(90f * sX, 1250f * sY),
            Offset(500f * sX, 1250f * sY),
            Offset(420f * sX, 230f * sY), Offset(580f * sX, 230f * sY),
            Offset(420f * sX, 1570f * sY), Offset(580f * sX, 1570f * sY),
            Offset(500f * sX, 550f * sY),
            Offset(300f * sX, 600f * sY), Offset(700f * sX, 600f * sY),
            Offset(300f * sX, 1200f * sY), Offset(700f * sX, 1200f * sY),
            Offset(500f * sX, 900f * sY),
            Offset(200f * sX, 100f * sY), Offset(800f * sX, 100f * sY),
            Offset(200f * sX, 1700f * sY), Offset(800f * sX, 1700f * sY)
        )
        
        dotPositions.forEach { pos ->
            drawCircle(wallColor, bR, pos)
            drawCircle(Color.White.copy(alpha = 0.6f), bR * 0.4f, pos - Offset(bR * 0.3f, bR * 0.3f))
        }

        // --- Goal Structure ---
        drawPath(Path().apply {
            moveTo(400f * sX, m * sY); lineTo(400f * sX, 10f * sY); lineTo(600f * sX, 10f * sY); lineTo(600f * sX, m * sY)
            moveTo(400f * sX, 1760f * sY); lineTo(400f * sX, 1790f * sY); lineTo(600f * sX, 1790f * sY); lineTo(600f * sX, 1760f * sY)
        }, color = wallColor, style = wallStyle)

        // 5. BALL (Pelotita)
        val ballRadius = 12f * sX

        drawCircle(
            color = Color(0xFFB0BEC5),
            radius = ballRadius,
            center = ballOffset
        )
        drawCircle(
            color = Color.White.copy(alpha = 0.8f),
            radius = ballRadius * 0.4f,
            center = ballOffset - Offset(ballRadius * 0.3f, ballRadius * 0.3f)
        )
        // Subtle shadow
        drawCircle(
            color = Color.Black.copy(alpha = 0.2f),
            radius = ballRadius,
            center = ballOffset + Offset(5f, 5f),
            style = Stroke(width = 2f)
        )
    }
}
