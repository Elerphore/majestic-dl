package ru.elerphore.ui

import org.jetbrains.kotlinx.dl.api.inference.objectdetection.DetectedObject
import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.abs

fun visualise(
    imageFile: File,
    detectedObjects: List<DetectedObject>
) {
    val frame = JFrame("Detected Objects")
    frame.contentPane.add(Panel(imageFile, detectedObjects))
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.isResizable = false
}

class Panel(
    image: File,
    private val detectedObjects: List<DetectedObject>
) : JPanel() {
    private var bufferedImage = ImageIO.read(image)

    override fun paint(graphics: Graphics) {
        super.paint(graphics)
        graphics.drawImage(bufferedImage, 0, 0, null)

        detectedObjects.forEach {
            val top = it.yMin * bufferedImage.height
            val left = it.xMin * bufferedImage.width
            val bottom = it.yMax * bufferedImage.height
            val right = it.xMax * bufferedImage.width
            if (abs(top - bottom) > 300 || abs(right - left) > 300) return@forEach

            when(it.label) {
                "orange" -> graphics.color = Color.RED
                else -> graphics.color = Color.RED
            }

            graphics.font = Font("Courier New", 1, 17)
            graphics.drawString(" ${it.label} : ${it.probability}", left.toInt(), bottom.toInt() - 8)

            graphics as Graphics2D
            val stroke1: Stroke = BasicStroke(6f)
            graphics.setColor(Color.RED)
            graphics.stroke = stroke1
            graphics.drawRect(left.toInt(), bottom.toInt(), (right - left).toInt(), (top - bottom).toInt())
        }
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(bufferedImage.width, bufferedImage.height)
    }

    override fun getMinimumSize(): Dimension {
        return Dimension(bufferedImage.width, bufferedImage.height)
    }
}