package jumpman.main.logic;

import java.awt.geom.Point2D;

public interface ISaveTransform 
{
	public Point2D getPosition();
	public double getX();
	public double getY();
	public Point2D getVelocity();
	public double getVelocityX();
	public double getVelocityY();
}
