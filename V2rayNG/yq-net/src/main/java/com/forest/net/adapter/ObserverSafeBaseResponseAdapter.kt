package com.forest.net.adapter

import android.app.Activity
import com.forest.net.data.BaseResponse
import com.forest.net.ext.safeRun
import com.forest.net.ext.wasActivityAlreadyDestroyed
import com.forest.net.log.logger
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.IllegalStateException

/**
 * observer safeRun Activity or Fragment
 */
abstract class ObserverSafeBaseResponseAdapter<T>(private val likelyContext: Any? = null) : ObserverAdapter<BaseResponse<T>>() {
    override fun onError(e: Throwable) {
        e.printStackTrace()
        safeRun {
            onSafeError(e)
        }
    }

    override fun onComplete() {
        logger {
            "ObserverSafeAdapter onComplete"
        }
    }

    override fun onSubscribe(disposable: Disposable) {
        logger {
            "ObserverSafeAdapter onSubscribe disposable: $disposable"
        }
        if((likelyContext as? Activity)?.wasActivityAlreadyDestroyed() == true){
            disposable.dispose()
        }
    }

    override fun onNext(t: BaseResponse<T>) {
        val safeRan = safeRun {
            onSafeNext(t)
            logger {
                "ObserverSafeAdapter onSafeNext response: $t"
            }
        }

        if (!safeRan) {
            onError(IllegalStateException())
        }
    }

    open fun onSafeError(t: Throwable){
        t.printStackTrace()
    }
    abstract fun onSafeNext(t: BaseResponse<T>)

}
