package jumpman.main.resources.GameObjects;

import java.awt.Graphics2D;
import java.util.Random;

import jumpman.main.logic.Game;
import jumpman.main.logic.Transform;
import jumpman.main.resources.GameObject;
import jumpman.main.resources.Sprite;

public class Enemy extends GameObject
{
	private static Random randomGenerator = new Random();
	private static int MOVEMENT_SPEED = 75;
	private static int PATROL_CUSHION = 20;
	
	private int patrolDirection = randomGenerator.nextInt(1) == 1 ? -1 : 1;
	private void setPatrolDirection(int newPatrolDirection) 
	{
		this.patrolDirection = newPatrolDirection;
	}

	private int leftBoundary;
	private int rightBoundary;
	
	private GooBall player;
	private boolean died = false;
	public boolean isDead() { return died; }
	
	//For an enemy placed specifically on a position on the platform
	public Enemy(double posX, Transform platform, Sprite sprite, GooBall player) 
	{
		super(posX, ComputeHeight(platform, sprite), sprite);
		
		assignBoundaries(platform);
	}
	
	//For an enemy placed randomly on a platform
	public Enemy(Transform platform, Sprite sprite, GooBall player)
	{
		super((int)platform.getX() + randomGenerator.nextInt(sprite.getWidth()), ComputeHeight(platform, sprite), sprite);
		
		assignBoundaries(platform);
		this.player = player;
	}
	
	//For ground units
	public Enemy(int boundaryLeft, int boundaryRight, Sprite sprite, GooBall player)
	{
		super(boundaryLeft + randomGenerator.nextInt(boundaryRight - boundaryLeft), Game.screenHeight - sprite.getHeight(), sprite);
		
		assignBoundaries(boundaryLeft, boundaryRight);
		this.player = player;
	}
	
	private final void assignBoundaries(Transform platform)
	{
		this.leftBoundary = (int)platform.getX();
		this.rightBoundary = (int)platform.getX() + platform.getWidth();
		
		adjustBoundaries();
	}
	
	//In the event you do not want the defaults
	public final void assignBoundaries(int leftBoundary, int rightBoundary)
	{
		this.leftBoundary = leftBoundary + transform.getWidth();
		this.rightBoundary = rightBoundary;
		
		adjustBoundaries();
	}
	
	private final void adjustBoundaries()
	{
		this.leftBoundary += PATROL_CUSHION;
		this.rightBoundary -= PATROL_CUSHION;
	}
	
	public static int ComputeHeight(Transform platform, Sprite sprite)
	{
		return (int)platform.getY() - sprite.getHeight();
	}

	@Override
	public void Update(double delta) 
	{
		//See if we got smoked by the player
		CheckPlayerHit();
		
		double deltaX = (patrolDirection * delta * MOVEMENT_SPEED) + (transform.getVelocityX() * delta);
		
		Move(deltaX, 0);
		
		//If we go too far...
		if(transform.getX() < leftBoundary)
			setPatrolDirection(1);
		else if (transform.getX() > rightBoundary)
			setPatrolDirection(-1);
	}

	private void CheckPlayerHit() 
	{
		if(transform.getRectangle(isReversed()).intersects(player.getTransform().getRectangle()))
		{
			//If the player is falling and hits the top
			if(player.getTransform().getVelocityY() > 0 && player.getPrevTransform().getY() < transform.getY() - transform.getHeight())
				death();
			else
				player.death();
		}
	}
	
	@Override
	protected boolean isReversed()
	{
		return patrolDirection == -1;
	}

	private void death() 
	{
		died = true;
	}

	@Override
	public void draw(Graphics2D g2d)
	{
		if(patrolDirection > 0)
			super.draw(g2d);
		else
			super.drawReverse(g2d);
	}

}
