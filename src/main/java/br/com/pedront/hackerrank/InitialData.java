package br.com.pedront.hackerrank;

import java.util.Map;

import com.google.gson.JsonElement;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 21/12/17 17:56
 */
public class InitialData {

    public Community community;

    private class Community {
        public Challenges challenges;
    }

    private class Challenges {
        public Map<String, JsonElement> challenge;
    }
}

// final JsonObject jsonInitialdata = json.getAsJsonObject();
// final JsonObject community = jsonInitialdata
// .getAsJsonObject("community");System.out.println(community.get("challenges"));
//
// final JsonObject challenges = community
// .getAsJsonObject("challenges");System.out.println(challenges.get("challenge"));
//
// final JsonObject challenge = challenges.getAsJsonObject("challenge");
//
// final Iterator<Map.Entry<String, JsonElement>> iterator = challenge.entrySet().iterator();if(iterator.hasNext())
// {
// final String challengeKey = iterator.next().getKey();
//
// System.out.println(challengeKey);
//
// System.out.println(challenge.get(challengeKey));
//
// final JsonObject master = challenge.getAsJsonObject(challengeKey);
// System.out.println(master.get("detail"));
//
// final JsonObject detail = master.getAsJsonObject("detail");
//
// System.out.println(detail.get("java8_template"));
// System.out.println(detail.get("java8_template_head"));
// System.out.println(detail.get("java8_template_tail"));
// }