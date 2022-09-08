package com.v2ray.ang.custom.webSocket

import com.forest.bss.sdk.log.logger
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class CusWebSocket(serverUri: URI) : WebSocketClient(serverUri) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        logger {
            "CusWebSocket onOpen"
        }
    }

    override fun onMessage(message: String?) {
        logger {
            "CusWebSocket onMessage message：$message"
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        logger {
            "CusWebSocket onClose code: $code; reason: $reason; remote: $remote"
        }
    }

    override fun onError(ex: Exception?) {
        logger {
            "CusWebSocket onError errorMessage: ${ex?.message}"
        }
    }
}