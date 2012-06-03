package jumpman.main.logic;

public class Runner implements Runnable
{
    public void run() 
	{
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) 
        {
            Game.update();
            Game.repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = Game.DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try 
            {
                Thread.sleep(sleep);
            } 
            catch (InterruptedException e) 
            {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }
}
