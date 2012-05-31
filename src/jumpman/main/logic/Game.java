package jumpman.main.logic;


public class Game 
{
	/**
	 * Interval of the update cycle
	 */
	public static final int NUM_MILLISECONDS = 25;
	public static final double UPDATE_INTERVAL = NUM_MILLISECONDS / 1000.0;
	
	public static final int screenWidth = 1600;
	public static final int screenHeight = 600;
	
	private static final Camera camera = new Camera();
	public static final Camera getCamera(){return camera;}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		new MainForm();
	}
}
