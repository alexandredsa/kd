package me.alexandredsa.kd.api;

import me.alexandredsa.kd.dto.APIResponse;
import me.alexandredsa.kd.dto.LocalizacaoDTO;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by alexa on 23/02/2016.
 */
public interface LocationAPIService {
    @POST("/v2/56cd0a73280000f201219e70")
    Call<APIResponse> sendLocation(@Body LocalizacaoDTO localizacaoDTO);
}
