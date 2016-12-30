package com.toto.avisame_mvp.models.services;

import com.toto.avisame_mvp.models.ServiceCatalog;
import com.toto.avisame_mvp.models.User;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface UserService {

    @Headers({"Content-Type: application/json"})
    @POST("users/login")
    Observable<User> onUserLogin(@Body BoardingRequest request);

    @Headers({"Content-Type: application/json"})
    @POST("users/")
    Observable<User> onUserRegister(@Body BoardingRequest request);

    @Headers({
            "Content-Type: application/json",
            "Authorization: (token)"
    })
    @POST("users/(userId)")
    Observable<User> onUserUpdate(@Path("userId") String userId, @Body UpdateUserRequest request);

    class UserFactory {
        public static UserService create() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServiceCatalog.BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(UserService.class);
        }
    }

    class BoardingRequest {

        final String email;
        final String password;

        public BoardingRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    class UpdateUserRequest {

        final String firstName;
        final String lastName;
        final String friend;

        public UpdateUserRequest(String firstName, String lastName, String friend) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.friend = friend;
        }
    }
}
