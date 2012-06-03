package jumpman.main.logic;


public class Game 
{
	/**
	 * Interval of the update cycle
	 */
	public static final int DELAY = 5;
	public static final double DELAY_MILLISECONDS = (double)DELAY / 1000.0;
	
	public static final int screenWidth = 1600;
	public static final int screenHeight = 600;
	
	private static final Camera camera = new Camera();
	public static final Camera getCamera(){ return camera; }
	
	private static boolean restart = false;
	public static void restart(){ restart = true; }
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException
	{
		new MainForm();
	}
}
