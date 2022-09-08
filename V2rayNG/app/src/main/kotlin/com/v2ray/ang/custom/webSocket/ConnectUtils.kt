package com.v2ray.ang.custom.webSocket

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.forest.bss.sdk.countdown.scheduleTimer
import com.forest.bss.sdk.log.logger
import com.v2ray.ang.custom.webSocket.params.PublicWebSocketParams
import kotlinx.coroutines.launch
import org.java_websocket.enums.ReadyState
import org.json.JSONObject
import java.net.URI

object ConnectUtils {
    private var socket: CusWebSocket? = null
    fun instance(wsUrl: String?): CusWebSocket? {
        if (wsUrl != null) {
            if (socket == null) {
                socket = CusWebSocket(URI(wsUrl))
            }
        }
        return socket
    }

    fun CusWebSocket.preConnect() : Boolean{
        return if (this.readyState?.equals(ReadyState.NOT_YET_CONNECTED) == true) {
            this.connectBlocking()
            true
        } else if (this.readyState.equals(ReadyState.CLOSING) || this.readyState.equals(ReadyState.CLOSED)) {
            this.reconnectBlocking()
            true
        } else {
            false
        }
    }

    /**
     * 登陆连接，传公共参数，action为：connect_app
     */
    fun CusWebSocket?.loginSend(activity: FragmentActivity) {
        val mutableMap = mutableMapOf<String, String>()
        mutableMap["action"] = "login"
        activity.sendMessage(this, mutableMap)
    }

    /**
     * 连接节点后，传公共参数，action为：connect_app
     */
    fun CusWebSocket?.connectAppSend(activity: FragmentActivity, nodeId: Int) {
        val mutableMap = mutableMapOf<String, String>()
        mutableMap["action"] = "connect_app"
        mutableMap["node_id"] = nodeId.toString()

        activity.lifecycleScope.scheduleTimer(4 * 60 * 1000) { //每隔4分钟上报一次Socket心跳
            activity.sendMessage(this, mutableMap)
        }
    }

    /**
     * 断开连接，传公共参数，action为：disconnect_app
     */
    fun CusWebSocket?.disConnectAppSend(activity: FragmentActivity, nodeId: Int) {
        val mutableMap = mutableMapOf<String, String>()
        mutableMap["action"] = "disconnect_app"
        mutableMap["node_id"] = nodeId.toString()
        activity.sendMessage(this, mutableMap)
    }

    private fun FragmentActivity.sendMessage(
        socket: CusWebSocket?,
        mutableMap: MutableMap<String, String>
    ) {
        lifecycleScope.launch {

            PublicWebSocketParams.params(mutableMap).apply {

                val params = JSONObject()

                this.map {
                    params.put(it.key, it.value)
                }
                if (socket?.isOpen == false) {
                    socket.preConnect()
                }

                if (socket?.isOpen == true) {
                    try {
                        socket.send(params.toString())
                    }catch (throwable: Throwable) {
                        throwable.printStackTrace()
                    }
                }

                logger {
                    "CusWebSocket onOpen isOpen: ${socket?.isOpen} 发送数据 json: $params; socket: $socket"
                }
            }
        }
    }
}
