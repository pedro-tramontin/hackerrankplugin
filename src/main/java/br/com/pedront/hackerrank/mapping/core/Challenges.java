package br.com.pedront.hackerrank.mapping.core;

import java.util.Iterator;
import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 22/12/17 10:23
 */
public class Challenges {
    public JsonObject challenge;

    public MasterChallenge masterChallenge;

    public Optional<JsonElement> firstChild() {
        JsonElement masterChallenge = null;

        final Iterator<String> iterator = challenge.keySet().iterator();
        if (iterator.hasNext()) {
            masterChallenge = challenge.get(iterator.next());
        }

        return Optional.ofNullable(masterChallenge);
    }
}
