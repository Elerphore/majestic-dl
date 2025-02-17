package ru.elerphore.service

import org.jetbrains.kotlinx.dl.api.inference.objectdetection.DetectedObject
import org.jetbrains.kotlinx.dl.onnx.inference.ONNXModelHub
import org.jetbrains.kotlinx.dl.onnx.inference.ONNXModels
import java.io.File

object DetectionService {
    private var lastCheckedImage: String? = null
    val modelHub = ONNXModelHub(cacheDirectory = File("cache/pretrainedModels"))

    private fun find(image: File): List<DetectedObject> {
        val model = modelHub.loadPretrainedModel(ONNXModels.ObjectDetection.EfficientDetD5)

        return model.use { detectionModel ->
            val detectedObjects = detectionModel.detectObjects(imageFile = image, topK = 8)

            detectedObjects.forEach {
                println("Found ${it.label} with probability ${it.probability}")
            }

            detectedObjects.filter { it.label == "orange" }
        }
    }
}
