package jankcord.components.texts;

import jankcord.texthelpers.DeletePrevCharAction;
import jankcord.texthelpers.UndoRedo;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;

// JankCord's text field, child of JTextField
public class JankTextField extends JTextField {
    // Constructor, to set size and location
    public JankTextField(int width, int height, int x, int y) {
        // Set JTextField properties
        setBorder(null);
        setLocation(x, y);
        setSize(width, height);
        setCaretColor(Color.white);
        setBackground(new Color(56, 58, 64));
        setForeground(new Color(219, 222, 225));
        setSelectionColor(new Color(9, 103, 215));
        setSelectedTextColor(new Color(255, 255, 255));
        setFont(new Font("Whitney", Font.BOLD, 28));

        // Add deletePrevCharAction to action map
        getActionMap().put(DefaultEditorKit.deletePrevCharAction, new DeletePrevCharAction());

        // Make undo-/redo-able
        UndoRedo.makeUndoable(this);
    }
}
