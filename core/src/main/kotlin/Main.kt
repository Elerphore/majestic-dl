package ru.elerphore

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.elerphore.core.JNAService
import ru.elerphore.core.StateService
import ru.elerphore.utils.emptyDirectory

fun main() {
    emptyDirectory()
    run()
}

fun run() =
    runBlocking {
        launch(Dispatchers.IO) {
            JNAService.listener()
        }
        launch(Dispatchers.IO) {
            StateService.updateOverlayState()
        }
    }
