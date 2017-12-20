package br.com.pedront.hackerrank;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLAnchorElement;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.messages.MessageBus;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 07/12/17 15:48
 */
public class HackerRankToolWindow implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {

        final MessageBus messageBus = project.getMessageBus();

        final JPanel toolWindowPanel = new JPanel(new BorderLayout());

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(toolWindowPanel, ": Problem Description", false);
        toolWindow.getContentManager().addContent(content);

        JFXPanel fxPanel = new JFXPanel();

        toolWindowPanel.add(fxPanel, BorderLayout.PAGE_START);

        messageBus.connect().subscribe(ProblemNotifier.OPEN_PROBLEM_NOTIFIER_TOPIC, url -> Platform.runLater(() -> {
            try {
                Document doc = Jsoup.connect(url).get();

                Element descriptionDiv = doc.selectFirst(".challenge-text");

                WebView webView = new WebView();
                fxPanel.setScene(new Scene(webView));

                webView.getEngine().loadContent(descriptionDiv.html());
                webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {

                    @Override
                    public void changed(final ObservableValue<? extends Worker.State> observable,
                            final Worker.State oldValue,
                            final Worker.State newValue) {
                        if (newValue == Worker.State.SUCCEEDED) {
                            final org.w3c.dom.Document document = webView.getEngine().getDocument();
                            NodeList nodeList = document.getElementsByTagName("a");
                            for (int i = 0; i < nodeList.getLength(); i++) {
                                Node node = nodeList.item(i);
                                EventTarget eventTarget = (EventTarget) node;
                                eventTarget.addEventListener("click", new EventListener() {

                                    @Override
                                    public void handleEvent(final org.w3c.dom.events.Event evt) {
                                        EventTarget target = evt.getCurrentTarget();
                                        HTMLAnchorElement anchorElement = (HTMLAnchorElement) target;
                                        String href = anchorElement.getHref();
                                        // handle opening URL outside JavaFX WebView
                                        System.out.println(href);
                                        evt.preventDefault();
                                    }
                                }, false);
                            }
                        }
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

}
