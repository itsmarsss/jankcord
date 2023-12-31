package jankcord.components.button.buttonlistener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// JankCord's mouse listener, implements MouseListener
public class JankMouseListener implements MouseListener {
    // All mouse events, using JankMLRunnable
    private JankMLRunnable mouseClicked;
    private JankMLRunnable mousePressed;
    private JankMLRunnable mouseEntered;
    private JankMLRunnable mouseExited;
    private JankMLRunnable mouseReleased;

    // Call corresponding runnables on event
    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClicked.runEvent(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed.runEvent(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseReleased.runEvent(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseEntered.runEvent(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseExited.runEvent(e);
    }

    // All getters
    public Runnable getMouseClicked() {
        return mouseClicked;
    }

    public Runnable getMousePressed() {
        return mousePressed;
    }

    public Runnable getMouseReleased() {
        return mouseReleased;
    }

    public Runnable getMouseEntered() {
        return mouseEntered;
    }

    public Runnable getMouseExited() {
        return mouseExited;
    }

    // All setters
    public void setMouseClicked(JankMLRunnable mouseClicked) {
        this.mouseClicked = mouseClicked;
    }

    public void setMousePressed(JankMLRunnable mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseReleased(JankMLRunnable mouseReleased) {
        this.mouseReleased = mouseReleased;
    }

    public void setMouseEntered(JankMLRunnable mouseEntered) {
        this.mouseEntered = mouseEntered;
    }

    public void setMouseExited(JankMLRunnable mouseExited) {
        this.mouseExited = mouseExited;
    }
}
