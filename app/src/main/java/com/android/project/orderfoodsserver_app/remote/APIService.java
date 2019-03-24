package com.android.project.orderfoodsserver_app.remote;

import com.android.project.orderfoodsserver_app.model.MyReponse;
import com.android.project.orderfoodsserver_app.model.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by kenhoang on 09/02/2018.
 */

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAysP1NL8:APA91bELbPTt-Zl1fBWoxAqIky4qB9vHUtKV40XbDFDBv2BROXpq9PWrN6l8u0Zc3WMJ1stFJbnAodHENCGuoFmEItxSVbNqObBMGaxh0TJz0MC2-u16_vrjQe0PDgaBJvBzUZEiYIR7"
            }
    )
    @POST("fcm/send")
    Call<MyReponse> sendNotification(@Body Sender body);
}
