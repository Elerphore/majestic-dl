package ru.elerphore.core

import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import kotlinx.coroutines.delay
import ru.elerphore.core.model.Point
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.event.InputEvent
import java.awt.image.BufferedImage
import java.io.File
import java.time.OffsetDateTime
import javax.imageio.ImageIO
import kotlin.random.Random

object RobotService {

    private val robot = Robot()
    private val user32 = User32.INSTANCE

    suspend fun mouseMove(points: List<Point>) {
        val point = WinDef.POINT()

        points.forEach {
            delay(Random.nextLong(100, 150))

            point.x = it.x.toInt()
            point.y = it.y.toInt()

            user32.SetCursorPos(it.x.toLong(), it.y.toLong())
            println("Mouse moved to (${it.x}, ${it.y})")

            delay(Random.nextLong(20, 50))
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
            delay(Random.nextLong(20, 50))
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK) // Release left mouse button

        }
    }

    fun takeScreenShot(filePath: String = OffsetDateTime.now().toEpochSecond().toString() + ".png") {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val screenRect = Rectangle(screenSize)
        val screenShot: BufferedImage = robot.createScreenCapture(screenRect)
        ImageIO.write(screenShot, "png", File("screenshots/${filePath}"))
        println("Screenshot saved successfully at $filePath")
    }
}