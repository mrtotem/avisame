package com.toto.avisame_mvp.models;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

interface ArrivalsService {

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

    class ArrivalsFactory {
        public static ArrivalsService create() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServiceCatalog.BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ArrivalsService.class);
        }
    }
}
