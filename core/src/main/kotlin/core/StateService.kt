package ru.elerphore.core

import java.awt.*
import javax.swing.JFrame
import javax.swing.JPanel


class Panel : JPanel() {
    var state: State = State.CHILL
        set(value) {
            field = value
            repaint()
        }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        g2d.font = Font("Arial", Font.BOLD, 20)
        g2d.color = state.color

        g2d.drawString("Cheat by Elerphore", 7, 27)
        g2d.drawString("Status: ${state.status}", 7, 50)
    }

    override fun isOpaque(): Boolean {
        return false // Ensures the panel itself is transparent
    }
}

enum class State(val color: Color, val status: String) {
    CHILL(Color.RED, "Not Working"),
    DETECTING(Color.BLUE, "Detecting"),
    WORKING(Color.GREEN, "Working")
}

object StateService {

    var current_state: State = State.CHILL
        set(value) {
            field = value
            updateOverlayState()
        }

    private var frame: JFrame? = null

    private var lastOverlayComponent: Panel? = null

    init
    {
        frame = JFrame("Overlay")

        frame?.apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            type = Window.Type.POPUP // Makes the window appear as a popup
            isUndecorated = true // Removes the window borders
            background = Color(0, 0, 0, 0) // Makes the background fully transparent
            contentPane.layout = null // Disables layout manager for manual positioning
        }
    }

    fun updateOverlayState() {
        lastOverlayComponent = if(lastOverlayComponent == null) {
            val panel = Panel()

            frame?.apply {
                add(panel)

                val screenSize = Toolkit.getDefaultToolkit().screenSize
                val screenWidth = screenSize.width
                val screenHeight = screenSize.height

                val overlayWidth = 300
                val overlayHeight = 200

                val xPosition = screenWidth - overlayWidth
                val yPosition = screenHeight - overlayHeight

                setSize(overlayWidth, overlayHeight)
                setLocation(xPosition, yPosition)
                isVisible = true
                isAlwaysOnTop = true
            }

            panel
        } else {
            lastOverlayComponent
        }

        lastOverlayComponent!!.state = current_state
        lastOverlayComponent!!.setBounds(100, 100, 400, 200) // x, y, width, height



    }
}