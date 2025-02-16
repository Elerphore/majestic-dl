package ru.elerphore.core

import kotlinx.coroutines.delay
import org.jetbrains.kotlinx.dl.api.inference.objectdetection.DetectedObject
import org.jetbrains.kotlinx.dl.onnx.inference.ONNXModelHub
import org.jetbrains.kotlinx.dl.onnx.inference.ONNXModels
import ru.elerphore.utils.directoryList
import ru.elerphore.utils.getCenteredCoordinates
import java.io.File

object DetectionService {
    private var lastCheckedImage: String? = null

    suspend fun check() {
        while (true) {
            delay(10)
            val lastImage = directoryList()?.sortedBy { it.name }?.lastOrNull()

            if(lastCheckedImage != lastImage?.name) {

                lastCheckedImage = lastImage?.name

                val objects = lastImage?.let { find(it) }?.filter { it.label == "orange" }

                StateService.current_state = State.WORKING

                val points = getCenteredCoordinates(lastImage!!, objects!!)
                RobotService.mouseMove(points)

                StateService.current_state = State.CHILL
            }

        }
    }

    private fun find(image: File): List<DetectedObject> {
        val modelHub = ONNXModelHub(cacheDirectory = File("cache/pretrainedModels"))
        val model = modelHub.loadPretrainedModel(ONNXModels.ObjectDetection.EfficientDetD5)

        return model.use { detectionModel ->
            val detectedObjects = detectionModel.detectObjects(imageFile = image, topK = 15)

            detectedObjects.forEach {
                println("Found ${it.label} with probability ${it.probability}")
            }

            detectedObjects
        }
    }
}
