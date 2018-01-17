package www.test720.com.naneducation.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by LuoPan on 2017/10/19 15:33.
 */

public class HttpUtils {
    public Observable<String> getData(final String url, final HttpParams params, int what) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        OkGo.<String>post(url).params(params).execute(new StringCallback() {

                            @Override
                            public void onStart(Request<String, ? extends Request> request) {
                                LogUtils.e(params.toString());
                            }

                            @Override
                            public void onSuccess(Response<String> response) {
                                JSONObject jsonObject = JSON.parseObject(response.body());
                                LogUtils.e(jsonObject.toString());
                                if (jsonObject.getInteger("code") == 1) {
                                    subscriber.onNext(jsonObject.toString());
                                    subscriber.onCompleted();
                                } else if (jsonObject.getInteger("code") == 0) {

                                    LogUtils.e(jsonObject.toString());
                                    subscriber.onError(new Throwable(jsonObject.getString("msg")));
                                } else if (jsonObject.getInteger("code") == 101) {

                                    subscriber.onNext(jsonObject.toString());
                                    subscriber.onCompleted();
                                } else if (jsonObject.getInteger("code") == 103) {

                                    subscriber.onNext(jsonObject.toString());
                                    subscriber.onCompleted();
                                } else if (jsonObject.getInteger("code") == 2) {
                                    subscriber.onNext(jsonObject.toString());
                                    subscriber.onCompleted();
                                }

                            }

                            @Override
                            public void onError(Response<String> response) {
                                subscriber.onError(response.getException());
                            }

                        });
                    } catch (Exception e) {
                        LogUtils.e(url, params);
                    }

                }
            }
        });

    }


}
