package br.com.pedront.hackerrank;

import com.intellij.util.messages.Topic;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 08/12/17 15:41
 */
public interface ProblemNotifier {
    Topic<ProblemNotifier> OPEN_PROBLEM_NOTIFIER_TOPIC = Topic.create("ProblemNotifier",
            ProblemNotifier.class);

    void open(String url);
}
