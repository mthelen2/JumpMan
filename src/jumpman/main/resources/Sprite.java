package jumpman.main.resources;

import java.awt.Image;

import javax.swing.ImageIcon;

public enum Sprite
{
	/**
	 * Image list - note these need to be located in jumpman.main.resources
	 */
	CAVE("new_cave_bg.jpg", 1600, 600),
	GOO("robin.png", 50, 50),
	GIRDER("girder.png", 100, 16),
	BIRD("bird.gif", 50, 50);
	
	private final String resourceName;
	public final String getResourceName(){return resourceName;}
	
	private final int width;
	public final int getWidth(){return width;}
	
	private final int height;
	public final int getHeight(){return height;}
	
	private final Image image;
	public final Image getImage(){return image;}
	
	Sprite(String resourceName, int width, int height)
	{
		this.resourceName = resourceName;
		this.width = width;
		this.height = height;
		
        ImageIcon ii = new ImageIcon(getClass().getResource(resourceName));
        this.image = ii.getImage();
	}
}
