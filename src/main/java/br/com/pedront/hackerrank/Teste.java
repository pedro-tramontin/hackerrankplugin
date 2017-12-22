package br.com.pedront.hackerrank;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 18/12/17 19:01
 */
public class Teste {

    public static void main(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.hackerrank.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Login loginService = retrofit.create(Login.class);

        String login = "";
        String password = "";

        final Call<ResponseBody> auth = loginService.auth(new RequestAuth(login, password, "false"));
        final Response<ResponseBody> response = auth.execute();

        System.out.println(response);
        System.out.println(response.body().string());
    }
}
