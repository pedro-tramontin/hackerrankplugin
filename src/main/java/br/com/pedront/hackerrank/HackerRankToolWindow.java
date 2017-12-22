package br.com.pedront.hackerrank;

import java.awt.*;
import java.io.IOException;
import java.net.URLDecoder;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiPackage;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.messages.MessageBus;

import br.com.pedront.hackerrank.mapping.TemplateData;
import javafx.application.Platform;
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

    private static final String PACKAGE_CHOOSER_TITLE = "Choose Package";

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
                // webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                //
                // @Override
                // public void changed(final ObservableValue<? extends Worker.State> observable,
                // final Worker.State oldValue,
                // final Worker.State newValue) {
                // if (newValue == Worker.State.SUCCEEDED) {
                // final org.w3c.dom.Document document = webView.getEngine().getDocument();
                // NodeList nodeList = document.getElementsByTagName("a");
                // for (int i = 0; i < nodeList.getLength(); i++) {
                // Node node = nodeList.item(i);
                // EventTarget eventTarget = (EventTarget) node;
                // eventTarget.addEventListener("click", new EventListener() {
                //
                // @Override
                // public void handleEvent(final org.w3c.dom.events.Event evt) {
                // EventTarget target = evt.getCurrentTarget();
                // HTMLAnchorElement anchorElement = (HTMLAnchorElement) target;
                // String href = anchorElement.getHref();
                // // handle opening URL outside JavaFX WebView
                // System.out.println(href);
                // evt.preventDefault();
                // }
                // }, false);
                // }
                // }
                // }
                // });

                final Element scriptInitialData = doc.selectFirst("script#initialData");
                String text = scriptInitialData.childNode(0).toString().trim();
                String textUnescaped = URLDecoder.decode(text, "UTF-8");

                ApplicationManager.getApplication().invokeLater(() -> {

                    final PsiPackage psiPackage = choosePackage(project);

                    ApplicationManager.getApplication().runWriteAction(() -> {
                        final TemplateData templateData = TemplateData.getTemplateData(textUnescaped);

                        createClass(project, psiPackage, templateData);
                    });
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        // ApplicationManager.getApplication().runWriteAction(() -> createClass(project, "teste"));
    }

    private PsiPackage choosePackage(Project project) {
        final PackageChooserDialog packageChooserDialog = new PackageChooserDialog(PACKAGE_CHOOSER_TITLE, project);

        packageChooserDialog.show();

        return packageChooserDialog.getSelectedPackage();
    }

    private void createClass(final Project project, PsiPackage psiPackage,
            final TemplateData templateData) {

        if (psiPackage != null) {
            final PsiDirectory psiDirectory = psiPackage.getDirectories()[0];

            final PsiClass psiClass = createClass(psiDirectory, "Solution", templateData);

            final VirtualFile child = psiDirectory.getVirtualFile().findChild("Solution.java");
            if (child != null) {
                OpenFileDescriptor descriptor = new OpenFileDescriptor(project, child);
                FileEditorManager.getInstance(project).openTextEditor(descriptor, true);
            }
        }
    }

    private PsiClass createClass(PsiDirectory directory, String name, TemplateData templateData) {

        return JavaDirectoryService.getInstance()
                .createClass(directory, name, "hackerrank.java", false, templateData.getAsMap());

    }

    private void createClass(Project project, String name) {

        VirtualFile root = project.getBaseDir();

        final PsiDirectory directory = PsiManager.getInstance(project).findDirectory(root);
        final PsiDirectory firstTopDirectory = findFirstTopDirectory(directory);

        // final Module module = ModuleManager.getInstance(project).getModules()[0];
        //
        // final ContentEntry[] contentEntries = ModuleRootManager.getInstance(module).getContentEntries();
        // System.out.println(contentEntries);
        //
        // final SourceFolder[] sourceFolders = contentEntries[0].getSourceFolders();
        //
        // PsiDirectory sourceDirectory = PsiManager.getInstance(project).findDirectory(sourceFolders[0].getFile());
        //
        final PsiClass aClass = JavaDirectoryService.getInstance()
                .createClass(firstTopDirectory, name, "hackerrank.java", true);

        System.out.println(aClass);
    }

    public PsiDirectory findFirstTopDirectory(PsiDirectory directory) {
        if (directory == null) {
            return null;
        }

        final PsiPackage directoryPackage = JavaDirectoryService.getInstance().getPackage(directory);
        if (directoryPackage == null || PackageUtil.isPackageDefault(directoryPackage)) {
            // add subpackages
            final PsiDirectory[] subdirectories = directory.getSubdirectories();
            for (PsiDirectory subdirectory : subdirectories) {
                final PsiDirectory firstTopPackage = findFirstTopDirectory(subdirectory);
                if (firstTopPackage != null) {
                    return firstTopPackage;
                }
            }
        } else {
            // this is the case when a source root has package prefix assigned
            return directory;
        }

        return null;
    }

}
