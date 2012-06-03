package jumpman.main.levels;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import jumpman.main.logic.Level;
import jumpman.main.resources.Sprite;
import jumpman.main.resources.GameObjects.Enemy;
import jumpman.main.resources.GameObjects.Platform;

public class LevelBuilder 
{
	private static final String EMPTY_STRING = "";
	private enum Mode {platforms, ground_units, none};
	
	public static final Level buildLevel(String fileName)
	{
		int currentLine = 0;
		Mode currentMode = Mode.none;
		Level level = new Level();
		
		try
		{
			FileInputStream fstream = new FileInputStream("C:/Users/Mitchell Thelen/workspace/JumpMan/bin/jumpman/main/levels/" + fileName);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null)
			{
				if (strLine.equals(EMPTY_STRING))
					continue;
				
				if(strLine.equals(Mode.platforms.toString()))
				{
					currentMode = Mode.platforms;
					continue;
				}
				else if(strLine.equals(Mode.ground_units.toString()))
				{
					currentMode = Mode.ground_units;
					continue;
				}
					
				String[] points = strLine.split(" ");
				
				switch(currentMode)
				{
					case platforms:
						int x = Integer.parseInt(points[0]);
						int y = Integer.parseInt(points[1]);
						boolean addEnemy = points.length > 2 && points[2].equals("x");
						
						Platform platform = new Platform(x,y, Sprite.GIRDER, level.getPlayer());
						level.addObject(platform);
						
						if(addEnemy)
						{
							Enemy enemy = new Enemy(platform.getTransform(), Sprite.BIRD, level.getPlayer());
							level.addObject(enemy);
						}
						break;
					case ground_units:	
						int boundX = Integer.parseInt(points[0]);
						int boundY = Integer.parseInt(points[1]);
						
						Enemy enemy = new Enemy(boundX, boundY, Sprite.BIRD, level.getPlayer());
						
						level.addObject(enemy);
						break;
					default:
						throw new Exception("Did not designate a parsing mode");
				}		
				currentLine++;
			}
			
			in.close();
		}
		catch (Exception e)
		{
			System.err.println("Error occured parsing line " + currentLine + ": " + e.getMessage());
			System.exit(1);
		}
		
		return level;
	}
}
