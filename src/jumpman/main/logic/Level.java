package jumpman.main.logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import jumpman.main.resources.GameObject;
import jumpman.main.resources.Sprite;
import jumpman.main.resources.GameObjects.GooBall;
import jumpman.main.resources.GameObjects.Platform;

public class Level extends JPanel implements ActionListener
{	
	private static final int length = 5000;
	public static final int getLength(){return length;}
    /**
	 * Generated by serialver
	 */
	private static final long serialVersionUID = 4796115838201421109L;
	
	/**
	 * In order to perform ghetto update
	 */
	private Timer timer;
	
	/**
	 * List of objects in the scene
	 */
	private List<GameObject> objects = new ArrayList<GameObject>();
	private List<Platform> platforms = new LinkedList<Platform>();
	
	private final static List<Point> platformPositions = 
			Arrays.asList(new Point(50,50), new Point(250, 500), new Point(600, 400), new Point(1000, 300), new Point(1500, 200), new Point(1600, 500));
	
	private final GooBall player;

    public Level() 
    {
    	//Form stuffs
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        
        timer = new Timer(Game.NUM_MILLISECONDS, this);
        timer.start();
        
        this.player = new GooBall(Sprite.GOO);
        
        //Game Object stuffs
    	objects.add(this.player);
    	
    	for(Point p : platformPositions)	
    		platforms.add(new Platform(p.x, p.y, Sprite.GIRDER, player));
    }

    public void paint(Graphics g) 
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        
        //Draw cave background
        for(int i = -1; i <= 1; i++)
        {
        	int xPos = i * Sprite.CAVE.getWidth() - Game.getCamera().center() % Sprite.CAVE.getWidth();
    		g2d.drawImage(Sprite.CAVE.getImage(), xPos, 0, null);
        }
        
        //Draw player + everything else
        for(GameObject obj : objects)
        	obj.Draw(g2d);
        
        //Draw platforms
        for(Platform obj : platforms)
        	obj.Draw(g2d);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(player == null)
			return;
		
		Game.getCamera().update(Game.UPDATE_INTERVAL, player.getTransform().getX());
		for(GameObject obj : objects)
			obj.Update(Game.UPDATE_INTERVAL);
		
		for(GameObject platform : platforms)
			platform.Update(Game.UPDATE_INTERVAL);
		
        repaint();
	}
	
    private class TAdapter extends KeyAdapter 
    {
    	@Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

    	@Override
        public void keyPressed(KeyEvent e) 
    	{
    		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    			System.exit(0);
    		
            player.keyPressed(e);
        }
    }
}