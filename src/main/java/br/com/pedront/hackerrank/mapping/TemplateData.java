package br.com.pedront.hackerrank.mapping;

import java.util.HashMap;
import java.util.Map;

import br.com.pedront.hackerrank.mapping.core.InitialData;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 22/12/17 11:06
 */
public class TemplateData {
    private String head;

    private String body;

    private String tail;

    private TemplateData(final String head, final String body, final String tail) {
        this.head = head;
        this.body = body;
        this.tail = tail;
    }

    public static TemplateData getTemplateData(String jsonString) {
        final InitialData initialData = ConvertToInitialData.convert(jsonString);

        return new TemplateData(
                initialData.community.challenges.masterChallenge.detail.java8_template_head,
                initialData.community.challenges.masterChallenge.detail.java8_template,
                initialData.community.challenges.masterChallenge.detail.java8_template_tail);
    }

    public Map<String, String> getAsMap() {
        Map<String, String> vars = new HashMap<>();
        vars.put("HEAD", head);
        vars.put("BODY", body);
        vars.put("TAIL", tail);

        return vars;
    }
}
