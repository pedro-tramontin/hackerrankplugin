package br.com.pedront.hackerrank;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 22/12/17 14:12
 */
public interface Login {

    @POST("/auth/login")
    Call<ResponseBody> auth(@Body RequestAuth requestAuth);
}
