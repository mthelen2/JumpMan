package jumpman.main.logic;

public class Camera 
{    
    private int center = 0;
    public int getCenter(){return center;}
    public void setCenter(int center){this.center = center;}
    
    private static int LEFT_BOUND = Game.screenWidth / 4;
    private static int RIGHT_BOUND = (int)(Game.screenWidth * .75);
    
    private static double MOVEMENT_SPEED = 200.0;
	
	public int center(){return center;}
	
	public void update(double delta, double playerX)
	{
		//If the player is at the beginning of the level, do not perform scrolling
		if(playerX < RIGHT_BOUND && center == 0)
			return;
		
		int absPlayerPos = (int)playerX - center;
		if(absPlayerPos < LEFT_BOUND)
			center -= MOVEMENT_SPEED * delta;
		else if(absPlayerPos > RIGHT_BOUND)
			center += MOVEMENT_SPEED * delta;
		
/*		System.out.println("playerPos: " + playerX);
		System.out.println("center: " + center);
		System.out.println("absPlayerPos: " + absPlayerPos);*/
	}
}
