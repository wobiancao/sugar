package com.sugar.sugarlibrary.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author wobiancao
 * @date 2019-06-14
 * desc : Subscriber 你也可以处理你的 这里只需要onNext
 */
public abstract class SugarHandleSubscriber <T> implements Observer<T>  {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
