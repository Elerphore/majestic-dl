package ru.elerphore.core

import org.jetbrains.kotlinx.dl.api.inference.objectdetection.DetectedObject
import ru.elerphore.ui.visualise
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.Window
import java.io.File
import javax.swing.JFrame
import javax.swing.JPanel

object VisualisationService {
    fun show(image: File, objects: List<DetectedObject>) = visualise(image, objects)

    fun overlay() {
        // Create a JFrame for the overlay
        val frame = JFrame("Overlay")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.type = Window.Type.POPUP // Makes the window appear as a popup
        frame.isUndecorated = true // Removes the window borders
        frame.background = Color(0, 0, 0, 0) // Makes the background fully transparent
        frame.contentPane.layout = null // Disables layout manager for manual positioning

        // Create a custom panel to draw text
        val overlayPanel = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                super.paintComponent(g)
                val g2d = g as Graphics2D

                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

                g2d.font = Font("Arial", Font.BOLD, 24)
                g2d.color = Color.WHITE

//                g2d.drawString(StateService.current_state, 50, 50)
            }
        }

        overlayPanel.setBounds(100, 100, 400, 200) // x, y, width, height
        frame.add(overlayPanel)

        // Make the frame always-on-top
        frame.isAlwaysOnTop = true

        frame.setSize(400, 200)
        frame.setLocationRelativeTo(null) // Center the window on the screen
        frame.isVisible = true
    }
}