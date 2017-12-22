package br.com.pedront.hackerrank.mapping;

import java.util.Optional;

import com.google.gson.Gson;

import br.com.pedront.hackerrank.mapping.core.InitialData;
import br.com.pedront.hackerrank.mapping.core.MasterChallenge;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 22/12/17 10:46
 */
public class ConvertToInitialData {
    public static InitialData convert(String jsonString) {
        Gson gson = new Gson();
        final InitialData initialData = gson.fromJson(jsonString, InitialData.class);

        final Optional<MasterChallenge> masterChallenge = initialData.community.challenges.firstChild()
                .map(jsonElement -> gson.fromJson(jsonElement, MasterChallenge.class));

        masterChallenge.ifPresent(master -> initialData.community.challenges.masterChallenge = master);

        return initialData;
    }
}
