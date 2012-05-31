package jumpman.main.resources;

import java.awt.Graphics2D;

import jumpman.main.logic.Game;
import jumpman.main.logic.Level;
import jumpman.main.logic.Transform;

public abstract class GameObject
{
	protected final Transform transform;
	public Transform getTransform()
	{
		return transform;
	}
	
	protected final Sprite sprite;
	
	public GameObject(Transform transform, Sprite sprite)
	{
		this.sprite = sprite;
		this.transform = transform;
	}
	
	public GameObject(Sprite sprite)
	{
		this.sprite = sprite;
		this.transform = new Transform(sprite.getWidth(), sprite.getHeight());
	}
	
	public GameObject(double posX, double posY, Sprite sprite)
	{
		this.sprite = sprite;
		this.transform = new Transform(posX, posY, 0, 0, sprite.getWidth(), sprite.getHeight());
	}
	
	public final void Move(double delta)
	{
		double increaseX = transform.getVelocityX() * delta;
		transform.setPosition(transform.getX() + increaseX,
				transform.getY() + transform.getVelocityY() * delta);
	}
	
	public final void Move(double x, double y)
	{
		/**
		 * If we run into a boundary
		 */
		if(transform.getX() + x > Level.getLength())
		{
			transform.setPosition(Level.getLength() - transform.getWidth(), transform.getY());
			transform.stopVelocityX();
			x = 0;
		}
		else if(transform.getX() + x < 0)
		{
			transform.setPosition(0, transform.getY());
			transform.stopVelocityX();
			x = 0;
		}
		
		if(transform.getY() + y > Game.screenHeight - transform.getHeight())
		{
			transform.setPosition(transform.getX(), Game.screenHeight - transform.getHeight());
			transform.stopVelocityY();
			y = 0;
		}
		else if(transform.getY() + y < 0)
		{
			transform.setPosition(transform.getX(), 0);
			transform.stopVelocityY();
			y = 0;
		}
		
		transform.setPosition(transform.getX() + x, transform.getY() + y);
	}
	
	public abstract void Update(double delta);
	
	public final void Draw(Graphics2D g2d)
	{
		g2d.drawImage(sprite.getImage(), 
				(int)transform.getX() - Game.getCamera().center(), 
				(int)transform.getY(), null); 
	}
}
