package jumpman.main.logic;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import jumpman.main.resources.GameObject;

public interface ILevel 
{
    public void addObject(GameObject obj);
    public void paint(Graphics g);
    public void update();
    public void repaint();
    public void keyReleased(KeyEvent e);
    public void keyPressed(KeyEvent e);
}
