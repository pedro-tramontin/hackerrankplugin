package br.com.pedront.hackerrank;

import java.awt.*;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;

/**
 * @author pnakano
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 18/12/17 19:01
 */
public class Teste {

    public void showWindow() {
        boolean result = new MyDialog(null, "Titulo").showAndGet();
        System.out.println(result);
    }

    public static void main(String[] args) {
        new Teste().showWindow();
    }

    class MyDialog extends DialogWrapper {

        MyDialog(Project project, String title) {
            super(project);

            init();
            setTitle(title);
        }

        @Nullable
        @Override
        protected JComponent createCenterPanel() {
            FlowLayout dialogContent = new FlowLayout();

            JPanel panel = new JPanel();
            panel.setLayout(dialogContent);
            panel.add(new JLabel(new ImageIcon(getClass().getResource("icon/warning.png"))));
            panel.add(new JLabel("Isso e um teste"));

            return panel;
        }

        @NotNull
        @Override
        protected Action[] createActions() {
            return new Action[] { getOKAction() };
        }
    }

}
