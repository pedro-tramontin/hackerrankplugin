package br.com.pedront.hackerrank;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiPackage;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 07/12/17 15:19
 */
public class Problem extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        // try {
        // Document doc = Jsoup.connect("https://www.hackerrank.com/challenges/solve-me-first/problem").get();
        // Document doc = Jsoup.connect("https://www.hackerrank.com/challenges/simple-array-sum/problem").get();
        // Document doc = Jsoup.connect("https://www.hackerrank.com/challenges/compare-the-triplets/problem")
        // .get();
        //
        // Element descriptionDiv = doc.selectFirst(".challenge-text");
        //
        //
        // final OpenProblemNotifier openProblemNotifier = messageBus
        // .syncPublisher(OpenProblemNotifier.OPEN_PROBLEM_NOTIFIER_TOPIC);
        //
        // openProblemNotifier.open(descriptionDiv.html());

        // } catch (IOException e1) {
        // e1.printStackTrace();
        // }

        final Project project = event.getProject();
        if (project == null) {
            new SimpleMessageDialog(null, "Warning", "Open a project to use this plugin!").show();

            return;
        }

        new OpenURLWindow(
                project.getMessageBus().syncPublisher(
                        ProblemNotifier.OPEN_PROBLEM_NOTIFIER_TOPIC)::open)
                                .show();

        createClass(project, "teste");
    }

    class SimpleMessageDialog extends DialogWrapper {

        private String message;

        SimpleMessageDialog(Project project, String title, String message) {
            super(project);

            this.message = message;

            init();
            setTitle(title);
        }

        @Nullable
        @Override
        protected JComponent createCenterPanel() {
            return new JLabel(message);
        }

        @NotNull
        @Override
        protected Action[] createActions() {
            return new Action[] { getOKAction() };
        }
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
