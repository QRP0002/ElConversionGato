package service;

import model.Conversion;
import model.ConversionRow;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ConversionClient {
    @POST("API.php")
    @FormUrlEncoded
    Call<Conversion> loadConversions(@Field("action") String action);

    @POST("API.php")
    @FormUrlEncoded
    Call<Conversion> loadUpdates(@Field("action") String action, @Field("id") String id);

    @POST("API.php")
    @FormUrlEncoded
    Call<ConversionRow> loadRowCount(@Field("action") String action);
}