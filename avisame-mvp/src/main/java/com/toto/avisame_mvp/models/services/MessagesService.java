package com.toto.avisame_mvp.models.services;

import com.toto.avisame_mvp.models.ArrivalsResponse;
import com.toto.avisame_mvp.models.Message;
import com.toto.avisame_mvp.models.ServiceCatalog;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface MessagesService {

    // ARRIVALS
    @Headers({
            "Content-Type: application/json",
            "Authorization: (token)"
    })
    @GET("/arrivals/(email)")
    Observable<ArrivalsResponse> getArrivalsMessages(@Header("token") String userToken, @Path("email") String email);

    @Headers({
            "Content-Type: application/json",
            "Authorization: (token)"
    })
    @POST("/users/(userId)/arrivals")
    Observable<Message> onArrivedMessage(@Header("token") String userToken, @Path("userId") String id);

    // ALERTS
    @Headers({
            "Content-Type: application/json",
            "Authorization: (token)"
    })
    @GET("/alerts/(email)")
    Observable<ArrivalsResponse> getAlertsMessages(@Header("token") String userToken, @Path("email") String email);

    @Headers({
            "Content-Type: application/json",
            "Authorization: (token)"
    })
    @POST("/users/(userId)/alerts")
    Observable<Message> onAlertMessage(@Header("token") String userToken, @Path("userId") String id);

    @Headers({
            "Content-Type: application/json",
            "Authorization: (token)"
    })
    @POST("/users/(userId)/alerts/(alertId)")
    Observable<Message> onUpdateAlertMessage(@Header("token") String userToken, @Path("userId") String id, @Path("alertId") String alertId);

    // DANGERS
    @GET("/dangers/(email)")
    Observable<ArrivalsResponse> getDangersMessages(@Header("token") String userToken, @Path("email") String email);

    @Headers({
            "Content-Type: application/json",
            "Authorization: (token)"
    })
    @POST("/users/(userId)/dangers")
    Observable<Message> onDangerMessage(@Header("token") String userToken, @Path("userId") String id);

    @Headers({
            "Content-Type: application/json",
            "Authorization: (token)"
    })
    @POST("/users/(userId)/alerts/(dangerId)")
    Observable<Message> onUpdateDangerMessage(@Header("token") String userToken, @Path("userId") String id, @Path("dangerId") String dangerId);

    class MessagesFactory {
        public static MessagesService create() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServiceCatalog.BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(MessagesService.class);
        }
    }
}
