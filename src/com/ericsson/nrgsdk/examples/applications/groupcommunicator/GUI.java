/*
 * **************************************************************************
 * *                                                                        *
 * * Ericsson hereby grants to the user a royalty-free, irrevocable,        *
 * * worldwide, nonexclusive, paid-up license to copy, display, perform,    *
 * * prepare and have prepared derivative works based upon the source code  *
 * * in this sample application, and distribute the sample source code and  *
 * * derivative works thereof and to grant others the foregoing rights.     *
 * *                                                                        *
 * * ERICSSON DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE,        *
 * * INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS.       *
 * * IN NO EVENT SHALL ERICSSON BE LIABLE FOR ANY SPECIAL, INDIRECT OR      *
 * * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS    *
 * * OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE  *
 * * OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE *
 * * OR PERFORMANCE OF THIS SOFTWARE.                                       *
 * *                                                                        *
 * **************************************************************************
 */

package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

    private JTabbedPane theTabs = new JTabbedPane();

    private JPanel theButtons = new JPanel();

    public GUI() {
        theButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JPanel contents = new JPanel();
        contents.setLayout(new BorderLayout());
        contents.add(theTabs, BorderLayout.CENTER);
        contents.add(theButtons, BorderLayout.SOUTH);
        contents.setBorder(new EmptyBorder(8, 8, 0, 8));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contents, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    public void showCentered() {
        pack();
        Dimension guiSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - guiSize.width) / 2,
                (screenSize.height - guiSize.height) / 2);
        setVisible(true);
    }

    public void addTab(String aTitle, Component aComp) {
        theTabs.add(aTitle, new JScrollPane(aComp));
    }

    public void addTab(String aTitle, String aText) {
        JTextArea text = new JTextArea(18, 50);
        text.setText(aText);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setBorder(new EmptyBorder(4, 8, 4, 8));
        addTab(aTitle, text);
    }

    public void addButton(Action anAction) {
        theButtons.add(new JButton(anAction));
    }
}
