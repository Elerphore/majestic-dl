package ru.elerphore.core

import com.sun.jna.platform.win32.User32
import kotlinx.coroutines.delay
import java.awt.event.KeyEvent

object JNAService {
    suspend fun listener() {
        while (true) {
            delay(500)
            if (StateService.current_state !in listOf(State.DETECTING, State.WORKING, State.STOPPED)) {
                StateService.current_state = State.DETECTING
                RobotService.takeScreenShot()
                DetectionService.check()
            }
        }
    }
}
