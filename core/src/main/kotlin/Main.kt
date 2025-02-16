package ru.elerphore

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.elerphore.core.DetectionService
import ru.elerphore.core.JNAService
import ru.elerphore.core.StateService
import ru.elerphore.core.VisualisationService
import ru.elerphore.utils.emptyDirectory


fun main() {
    emptyDirectory()
    run()
}

fun run() =
    runBlocking {
        launch(Dispatchers.Default) {
            JNAService.listener()
        }
        launch(Dispatchers.IO) {
            DetectionService.check()
        }
        launch(Dispatchers.IO) {
            StateService.updateOverlayState()
        }
    }
