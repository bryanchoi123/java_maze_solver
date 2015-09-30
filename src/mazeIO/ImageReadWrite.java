package mazeIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import maze.Maze;
import maze.MazeCell;

public class ImageReadWrite
{
	
	
	/**
	 * Creates a new maze from the given file
	 * 
	 * @param maze - the maze to modify
	 * @param file - the image file to read
	 * @param threshold - color threshold to differentiate between
	 * 						walkable and unwalkable
	 */
	public static void makeMaze(Maze maze, File file, double threshold)
	{
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(file);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		maze.setSize(width, height);
		
		for(int x = 0; x<width; x++)
			for(int y = 0; y<height; y++)
			{
				MazeCell square = maze.getCell(x, y);
				Color color = new Color(image.getRGB(x, y));
				
				int colorSum = color.getRed() + color.getBlue() + color.getGreen();
				if(colorSum > threshold)
					maze.setUnwalkable(square);
				else
					maze.setWalkable(square);
			}
	}
	
	/**
	 * Writes maze to a bmp file
	 * 
	 * @param maze - the maze to write
	 * @param file - the file to write to
	 */
	public static void writeToFile(Maze maze, File file)
	{
		BufferedImage image = new BufferedImage(maze.getWidth(), maze.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x<maze.getWidth(); x++)
			for(int y = 0; y<maze.getHeight(); y++)
			{
				MazeCell mc = maze.getCell(x, y);
				Color col = mc.getColor();
				image.setRGB(x, y, col.getRGB());
			}
		
		try
		{
			ImageIO.write(image, "bmp", file);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
