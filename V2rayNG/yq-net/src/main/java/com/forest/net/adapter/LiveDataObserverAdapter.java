package com.forest.net.adapter;

import androidx.lifecycle.MutableLiveData;

import com.forest.net.data.BaseResponse;
import com.forest.net.data.Completed;
import com.forest.net.data.Error;
import com.forest.net.data.Started;
import com.forest.net.log.LogUtils;

import io.reactivex.rxjava3.disposables.Disposable;

public class LiveDataObserverAdapter<T> extends ObserverAdapter<BaseResponse<T>> {
    /**
     * 用来处理请求成功，设置LiveData的值
     */
    private MutableLiveData<BaseResponse<T>> successData;

    /**
     * token过期监听回调
     */
    private MutableLiveData<Error> errorData;
    /**
     * 请求完成监听回调
     */
    private MutableLiveData<Completed> completeData;

    public LiveDataObserverAdapter(MutableLiveData<BaseResponse<T>> successData) {
        this.successData = successData;
    }

    public LiveDataObserverAdapter(MutableLiveData<BaseResponse<T>> successData, MutableLiveData<Error> errorData) {
        this.successData = successData;
        this.errorData = errorData;
    }

    public LiveDataObserverAdapter(MutableLiveData<BaseResponse<T>> successData, MutableLiveData<Started> startData, MutableLiveData<Completed> completeData) {
        this.successData = successData;
        this.completeData = completeData;
        if (startData != null) {
            startData.postValue(Started.INSTANCE);
        }
    }

    public LiveDataObserverAdapter(MutableLiveData<BaseResponse<T>> successData, MutableLiveData<Error> errorData, MutableLiveData<Started> startData, MutableLiveData<Completed> completeData) {
        this.successData = successData;
        this.errorData = errorData;
        this.completeData = completeData;
        if (startData != null) {
            startData.postValue(Started.INSTANCE);
        }
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        super.onSubscribe(disposable);

//        EventBus.getDefault().post(new EventLoginOutEntity(EventLoginOutEntityKt.OBSERVER_SUBSCRIBE, null));

        LogUtils.logger("LiveDataObserverAdapter onSubscribe disposable: " + disposable);
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (errorData != null) {
            errorData.setValue(new Error(-1, e.getMessage()));
        }
        if (completeData != null) {
            completeData.postValue(Completed.INSTANCE);
        }
        LogUtils.logger("LiveDataObserverAdapter onError Throwable: " + e.getMessage());
    }

    @Override
    public void onNext(BaseResponse<T> tBaseResponse) {
        super.onNext(tBaseResponse);
        LogUtils.logger("LiveDataObserverAdapter onNext baseResponse code: " + tBaseResponse.getCode() + "message: " + tBaseResponse.getMsg());
        if (successData != null) {
            successData.setValue(tBaseResponse);
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (completeData != null) {
            completeData.postValue(Completed.INSTANCE);
        }
        LogUtils.logger("LiveDataObserverAdapter onComplete");
    }
}
