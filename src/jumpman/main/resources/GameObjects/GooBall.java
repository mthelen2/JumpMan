package jumpman.main.resources.GameObjects;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import jumpman.main.logic.Game;
import jumpman.main.logic.HitPoint;
import jumpman.main.logic.MathUtils;
import jumpman.main.logic.Transform;
import jumpman.main.resources.GameObject;
import jumpman.main.resources.Sprite;

public class GooBall extends GameObject implements PlayerController 
{
	private static final double MOVEMENT_SPEED = 200.0;
	private static final double JUMP_VELOCITY = -700.0;
	private static final double GRAVITY = 980.0;
	
	private static final Map<Integer, Integer> keyMap = new HashMap<Integer, Integer>();
	static
	{
		keyMap.put(KeyEvent.VK_LEFT, -1);
		keyMap.put(KeyEvent.VK_RIGHT, 1);
		keyMap.put(KeyEvent.VK_UP, -1);
		keyMap.put(KeyEvent.VK_DOWN, 1);
	}
	
	private int dX = 0;
	private int dY = 0;
	
	private static final int DIFFERENCE_MIN = -1;
	private static final int DIFFERENCE_MAX = 1;
	
	private boolean allowVerticalMovement = false;
	private boolean allowGravity = true;
	
	private boolean tryingToJump = false;
	
	private Transform currentPlatform = null;
	
	private Transform prevTransform = null;
	public Transform getPrevTransform(){ return prevTransform; }
	
	private boolean died = false;
	public boolean isDead() { return died; }
	
	public GooBall(Transform transform, Sprite sprite)
	{
		super(transform, sprite);
		
		prevTransform = transform;
	}
	
	public GooBall(Sprite sprite)
	{
		super(sprite);
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE)
            tryingToJump = true;
        else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
        	dX = MathUtils.Clamp(DIFFERENCE_MIN, DIFFERENCE_MAX, dX + keyMap.get(key));
        else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
        {
        	if(!allowVerticalMovement)
        		return;
        	
            dY = MathUtils.Clamp(DIFFERENCE_MIN, DIFFERENCE_MAX, dY + keyMap.get(key));
        }
	}
	
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE)
        	tryingToJump = false;
        else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
        	dX = MathUtils.Clamp(DIFFERENCE_MIN, DIFFERENCE_MAX, dX - keyMap.get(key));
        else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
        {
        	if(!allowVerticalMovement)
        		return;

        	dY = MathUtils.Clamp(DIFFERENCE_MIN, DIFFERENCE_MAX, dY - keyMap.get(key));
        }
    }

	private void jump() 
	{
		//If we're not on a platform, and not on the ground
		if(currentPlatform == null && (transform.getY() + transform.getHeight()) != Game.screenHeight)
			return;
		
		transform.setVelocity(transform.getVelocityX(), JUMP_VELOCITY);
		
		allowGravity = true;
		currentPlatform = null;
	}

	@Override
	public void Update(double delta)
	{
		prevTransform = transform.clone();
		
		if(allowGravity)
			transform.setVelocity(transform.getVelocityX(), transform.getVelocityY() + GRAVITY * delta);
		
		if(tryingToJump)
			jump();
		
		double deltaX = (dX * delta * MOVEMENT_SPEED) + (transform.getVelocityX() * delta);
		double deltaY = (dY * delta * MOVEMENT_SPEED) + (transform.getVelocityY() * delta);
		
		Move(deltaX, deltaY);

		//Check to see if we fell off
		if(currentPlatform != null)
		{
			if(transform.getX() < currentPlatform.getX() ||
					transform.getX() > currentPlatform.getX() + currentPlatform.getWidth())
			{
				currentPlatform = null;
				allowGravity = true;
			}
		}
	}
	
	public void hitPlatform(HitPoint hitPoint, Transform platform)
	{
		switch(hitPoint)
		{
			case UP:
				transform.setPosition(transform.getX(), platform.getY() - transform.getHeight());
				transform.setVelocity(transform.getVelocityX(), 0);
				
				currentPlatform = platform;
				allowGravity = false;
				break;
			case DOWN:
				transform.setPosition(transform.getX(), platform.getY() + platform.getHeight());
				transform.setVelocity(transform.getVelocityX(), 0);
				break;
		}
	}

	public void death() 
	{
		//Game.restart();
	}
}
