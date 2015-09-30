package gui;

import java.awt.Graphics;

import maze.MazeCell;

public class MazeSquare
{
	private MazeCell cell;
	
	public MazeSquare(MazeCell mc)
	{
		cell = mc;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(cell.getColor());
		int x = cell.getX();
		int y = cell.getY();
		
		g.drawLine(x, y, x, y);
	}
	
	public MazeCell getCell(){return cell;}
}
