package com.example.shoppingapplication.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes


class GraphView(context: Context,attributeSet: AttributeSet):View(context,attributeSet) {
    private val dataset= mutableListOf<DataPoint>()
    private var xMin =0
    private var xMax=0
    private var yMin=0
    private var yMax=0


    private val dataPointPaint = Paint().apply {
        color= Color.YELLOW
        strokeWidth=7f
        style=Paint.Style.STROKE
    }
    private val dataPointFillPaint =Paint().apply {
        color=Color.WHITE
    }
    private val dataPointLinePaint =Paint().apply {
        color=Color.BLUE
        strokeWidth=7f

    }
}



data class DataPoint(
    val xVal:Int,
    val yVal:Int,
)