package jumpman.main.logic;

public class MathUtils 
{
	public static final int Clamp(int min, int max, int value)
	{
		if(value < min)
			return min;
		else if(value > max)
			return max;
		else
			return value;
	}
}
