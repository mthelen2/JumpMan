package jumpman.main.logic;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Transform 
{
	/**
	 * Position garbage 
	 */
	private Point2D position;
	public synchronized Point2D getPosition()
	{
		return position;
	}
	
	public synchronized double getX()
	{
		return position.getX();
	}
	
	public synchronized double getY()
	{
		return position.getY();
	}
	
	public synchronized void setPosition(double x, double y)
	{
		this.position = new Point2D.Double(x,y);
	}
	
	/**
	 * Velocity garbage
	 */
	private Point2D velocity;
	public synchronized Point2D getVelocity()
	{
		return velocity;
	}
	
	public synchronized double getVelocityX()
	{
		return velocity.getX();
	}
	
	public synchronized double getVelocityY()
	{
		return velocity.getY();
	}
	
	public synchronized void setVelocity(double x, double y)
	{
		velocity = new Point2D.Double(x, y);
	}
	
	public synchronized void stopVelocity()
	{
		velocity = new Point2D.Double();
	}
	
	public synchronized void stopVelocityX()
	{
		velocity = new Point2D.Double(0, getVelocityY());
	}
	
	public synchronized void stopVelocityY()
	{
		velocity = new Point2D.Double(getVelocityX(), 0);
	}
	
	private final int width;
	public synchronized int getWidth()
	{
		return width;
	}
	
	private final int height;
	public synchronized int getHeight()
	{
		return height;
	}
	
	public synchronized Rectangle getRectangle()
	{
		return new Rectangle((int)position.getX(), (int)position.getY(), width, height);
	}
	
	public Transform(double xPos, double yPos, double xVelocity, double yVelocity, int width, int height)
	{
		this.position = new Point2D.Double(xPos, yPos);
		this.velocity = new Point2D.Double(xVelocity, yVelocity);
		
		this.width = width;
		this.height = height;
	}
	
	public Transform(int width, int height)
	{
		this.position = new Point2D.Double();
		this.velocity = new Point2D.Double();
		
		this.width = width;
		this.height = height;
	}
	
	public Transform()
	{
		this(0,0);
	}
	
	@Override
	public Transform clone()
	{
		return new Transform(getX(), getY(), getVelocityX(), getVelocityY(), getWidth(), getHeight());
	}
}
