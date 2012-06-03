package jumpman.main.resources.GameObjects;

import jumpman.main.logic.HitPoint;
import jumpman.main.logic.Transform;
import jumpman.main.resources.GameObject;
import jumpman.main.resources.Sprite;

public class Platform extends GameObject 
{
	private final GooBall player;
	
	public boolean isDead() { return false; }
	
	public Platform(Transform transform, Sprite sprite, GooBall player)
	{
		super(transform, sprite);
		this.player = player;
	}
	
	public Platform(double posX, double posY, Sprite sprite, GooBall player)
	{
		super(posX, posY, sprite);
		this.player = player;
	}

	@Override
	/**
	 * NOTE: THIS NEEDS TO BE REFACTORED!
	 */
	public void Update(double delta) 
	{
		Transform saveTransform = player.getPrevTransform();
		
		Move(delta);
		
		if(transform.getRectangle().intersects(player.getTransform().getRectangle()))
		{
			//If we're falling and hit the top
			if(player.getTransform().getVelocityY() > 0 && saveTransform.getY() < transform.getY() - transform.getHeight())
				player.hitPlatform(HitPoint.UP, transform);
			else
			{
				boolean hitFromLeft = saveTransform.getX() <= transform.getX() - saveTransform.getWidth();
				boolean hitFromRight = saveTransform.getX() >= transform.getX() + transform.getWidth();

				if(hitFromLeft || hitFromRight)
					player.getTransform().setPosition(saveTransform.getX(), player.getTransform().getY());
				//Otherwise we hit it from the bottom, so kill y-velocity
				else
					player.hitPlatform(HitPoint.DOWN, transform);
			}
		}
	}
}
