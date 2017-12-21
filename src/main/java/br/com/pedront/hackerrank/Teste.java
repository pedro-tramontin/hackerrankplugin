package br.com.pedront.hackerrank;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 18/12/17 19:01
 */
public class Teste {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.hackerrank.com/challenges/making-anagrams/problem").get();

        final Element scriptInitialData = doc.selectFirst("script#initialData");
        // System.out.println(scriptInitialData.children().size());
        // System.out.println(scriptInitialData.childNode(0));

        String text = scriptInitialData.childNode(0).toString().trim();

        System.out.println(String.format("Text: %s", text));

        String textUnescaped = URLDecoder.decode(text, "UTF-8");

        System.out.println(textUnescaped);

        JsonParser parser = new JsonParser();
        final JsonElement json = parser.parse(textUnescaped);

        Gson gson = new Gson();
        final InitialData community1 = gson.fromJson(textUnescaped, InitialData.class);

        System.out.println(community1);

        System.out.println(json);

        final JsonObject jsonObject = json.getAsJsonObject();
        System.out.println(jsonObject.get("community"));

        final JsonObject community = jsonObject.getAsJsonObject("community");
        System.out.println(community.get("challenges"));

        final JsonObject challenges = community.getAsJsonObject("challenges");
        System.out.println(challenges.get("challenge"));

        final JsonObject challenge = challenges.getAsJsonObject("challenge");

        final Iterator<Map.Entry<String, JsonElement>> iterator = challenge.entrySet().iterator();
        if (iterator.hasNext()) {
            final String challengeKey = iterator.next().getKey();

            System.out.println(challengeKey);

            System.out.println(challenge.get(challengeKey));

            final JsonObject master = challenge.getAsJsonObject(challengeKey);
            System.out.println(master.get("detail"));

            final JsonObject detail = master.getAsJsonObject("detail");

            System.out.println(detail.get("java8_template"));
            System.out.println(detail.get("java8_template_head"));
            System.out.println(detail.get("java8_template_tail"));
        }
    }
}
