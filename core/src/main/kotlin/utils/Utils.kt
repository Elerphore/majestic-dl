package ru.elerphore.utils

import org.jetbrains.kotlinx.dl.api.inference.objectdetection.DetectedObject
import ru.elerphore.core.model.Point
import java.io.File
import java.net.URISyntaxException
import java.net.URL
import javax.imageio.ImageIO

/** Converts resource string path to the file. */
@Throws(URISyntaxException::class)
fun getFileFromResource(fileName: String): File {
    val classLoader: ClassLoader = object {}.javaClass.classLoader
    val resource: URL? = classLoader.getResource(fileName)
    return if (resource == null) {
        throw IllegalArgumentException("file not found! $fileName")
    } else {
        File(resource.toURI())
    }
}

fun directoryList(): Array<out File>? {
    val directoryPath = "screenshots" // Replace with your directory path
    return File(directoryPath).listFiles()
}

fun emptyDirectory() {
    val directoryPath = "screenshots"
    val directory = File(directoryPath)

    if(!directory.exists()) {
        directory.mkdirs()
    }

    directory.listFiles().map {
        it.delete()
    }
}

private fun findCenter(minX: Float, minY: Float, maxX: Float, maxY: Float): Point {
    val centerX = (minX + maxX) / 2
    val centerY = (minY + maxY) / 2
    return Point(centerX, centerY)
}

fun getCenteredCoordinates(image: File, detectedObjects: List<DetectedObject>): List<Point> {
    val bufferedImage = ImageIO.read(image)

    return detectedObjects.map {
        val minY = it.yMin * bufferedImage.height
        val minX = it.xMin * bufferedImage.width
        val maxY = it.yMax * bufferedImage.height
        val maxX = it.xMax * bufferedImage.width

        findCenter(minX, minY, maxX, maxY)
    }
}