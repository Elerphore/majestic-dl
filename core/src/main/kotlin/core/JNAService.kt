package ru.elerphore.core

import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinUser
import java.awt.event.KeyEvent

object JNAService {
    private val user32 = User32.INSTANCE
    private const val HOTKEY_ID = 1
    private const val MODIFIERS = 0//WinUser.MOD_CONTROL or WinUser.MOD_ALT
    private const val VK_CODE = KeyEvent.VK_V

    suspend fun listener()  {
        if (!user32.RegisterHotKey(null, HOTKEY_ID, MODIFIERS, VK_CODE)) {
            println("Failed to register hotkey!")
            return
        }

        println("Global hotkey Ctrl+Alt+V registered. Press it to trigger an action.")

        while (true) {
            val msg = WinUser.MSG()
            if (user32.GetMessage(msg, null, 0, 0) != 0) {
                if (msg.message == WinUser.WM_HOTKEY) {
                    if (msg.wParam.toInt() == HOTKEY_ID) {
                        println("Hotkey Ctrl+Alt+V was pressed!")
                        StateService.current_state = State.DETECTING
                        RobotService.takeScreenShot()
                    }
                }
            }
        }
    }
}