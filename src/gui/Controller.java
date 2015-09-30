package gui;

import heuristics.DiagonalDistance;
import heuristics.ManhattanDistance;
import heuristics.ManhattanTieBreak;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import maze.Colors;
import maze.Maze;
import maze.MazeCell;
import mazeIO.ImageFilter;
import mazeIO.ImageReadWrite;
import solver.Dijkstra;
import solver.aStar;

public class Controller
{
	private Interface inter;
	private Maze maze;
	private JFileChooser fc;
	private Controller control;
	
	private boolean solvable, checked, choosingStart, choosingEnd;
	
	private int width = 400, height = 400;
	
	public Controller()
	{			
		maze = new Maze(new Dimension(width, height), new ManhattanDistance(), new aStar());
		control = this;
		inter = new Interface(control);
		
		solvable = checked = choosingStart = choosingEnd = false;
		
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new ImageFilter());
	
		initializeListeners();
	}
	
	/**
	 * Initializes all the ActionListeners
	 */
	private void initializeListeners()
	{
		initializeFileListeners();
		initializeMazeListeners();
		
		initializeCenterListener();
		initializeCenterMouseListener();
	}
	
	/**
	 * Initializes all the ActionListeners related to the File menu 
	 */
	private void initializeFileListeners()
	{
		initializeLoad();
		initializeSave();
		initializeQuit();
	}
	
	/**
	 * Initializes all the ActionListeners related to the Maze menu
	 */
	private void initializeMazeListeners()
	{
		initializeSetStart();
		initializeSetEnd();
		
		initializeSolvable();
		initializeSolve();
		initializeReset();
		
		initializeAlgorithms();
		initializeHeuristics();
	}
	
	/**
	 * Initializes all the ActionListeners related to the Algorithms submenu
	 */
	private void initializeAlgorithms()
	{
		initializeaStar();
		initializeDjikstra();
	}
	
	/**
	 * Initializes all the ActionListeners related to the Heuristics submenu
	 */
	private void initializeHeuristics()
	{
		initializeManhattan();
		initializeManhattanTied();
		initializeDiagonal();
	}
	
	/**
	 * Set the action for the Load button
	 * 		i.e. Load the matrix and display it
	 */
	private void initializeLoad()
	{
		inter.addLoadListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				checked = false;
				
				int ret = fc.showOpenDialog(inter);
				if(ret == JFileChooser.APPROVE_OPTION)
				{
					double thresh = inter.getThreshold();
					
					File file = fc.getSelectedFile();
					ImageReadWrite.makeMaze(maze, file, thresh);
					
					width = maze.getWidth();
					height = maze.getHeight();
					inter.setWidth(width);
					inter.setHeight(height);
					
					MazeSquare[][] grid = new MazeSquare[width][height];
					for(int x = 0; x<width; x++)
						for(int y = 0; y<height; y++)
							grid[x][y] = new MazeSquare(maze.getCell(x, y));
					inter.setGrid(grid);
					
					MazeCell start = maze.getCell(0, 0);
					maze.setStart(start);
				}
				
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the Save button
	 * 		i.e. Save the maze to the specified directory
	 */
	private void initializeSave()
	{
		inter.addSaveListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int ret = fc.showSaveDialog(inter);
				if(ret == JFileChooser.APPROVE_OPTION)
				{
					File f = fc.getSelectedFile();
					ImageReadWrite.writeToFile(maze, f);
				}
				
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the quit button
	 * 		i.e. Quit the program
	 */
	private void initializeQuit()
	{
		inter.addQuitListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
	}
	
	/**
	 * Set the action for the set start button
	 * 		set the flag for the mouse listener
	 */
	private void initializeSetStart()
	{
		inter.addSetStartListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				choosingEnd = false;
				choosingStart = true;
				
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the set end button
	 * 		set the flag for the mouse listener
	 */
	private void initializeSetEnd()
	{
		inter.addSetEndListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				choosingStart = false;
				choosingEnd = true;
				
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the Solvable button
	 * 		i.e. Display a message whether the maze is solvable or not
	 * 
	 * 		If a solvable check has already been run, simply return old value
	 */
	private void initializeSolvable()
	{
		inter.addSolveableListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(checked)
					JOptionPane.showMessageDialog(inter, "Maze is solvable");
				else
				{
					String ret = "Maze is ";
					solvable = maze.solvable();
					
					if(solvable)
						ret += "solvable";
					else
						ret += "not solvable";
					
					JOptionPane.showMessageDialog(inter, ret);
				}
				
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the Solve button
	 * 		i.e. Solve the maze
	 * 
	 * 		If the maze is not solvable, display message,
	 * 		otherwise, solve the maze and display the path
	 */
	private void initializeSolve()
	{
		inter.addSolveListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!checked)
				{
					solvable = maze.solvable();
					checked = true;
				}
				
				if(solvable)
				{
					Stack<MazeCell> solution = maze.solveMaze();
					
					MazeCell mc = solution.pop();
					while(!solution.isEmpty())
					{
						mc = solution.pop();
						mc.setColor(Colors.PATH);
					}
				}// end if
				else
					JOptionPane.showMessageDialog(inter, "Maze is not solvable");
				
				inter.repaint();
			}// end method
		});
	}
	
	/**
	 * Set the action for the Reset button
	 * 		i.e. Remove the path from the maze
	 */
	private void initializeReset()
	{
		inter.addResetListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				maze.resetCells();
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the aStar button
	 * 		i.e. Set the maze's solver to use A* algorithm
	 */
	private void initializeaStar()
	{
		inter.addaStarListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				aStar algorithm = new aStar();
				maze.setSolver(algorithm);
				
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the Dijkstra button
	 * 		i.e. Set the maze's solver to use Dijkstra's Pathfdinding Algorithm
	 */
	private void initializeDjikstra()
	{
		inter.addDjikstraListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Dijkstra algorithm = new Dijkstra();
				maze.setSolver(algorithm);
				
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the Manhattan button
	 * 		Sets the maze's Heuristics to use the Manhattan Distance
	 */
	private void initializeManhattan()
	{
		inter.addManhattanListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ManhattanDistance heuristics = new ManhattanDistance();
				maze.setHeuristics(heuristics);
				
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the ManhattanTied button
	 * 		Sets the maze's Heuristics to use the Manhattan Distance with a Tie Breaker
	 */
	private void initializeManhattanTied()
	{
		inter.addManhattanListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ManhattanTieBreak heuristics = new ManhattanTieBreak();
				maze.setHeuristics(heuristics);
				
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the Diagonal button
	 * 		Sets the maze's heuristics to use the Diagonal Distance
	 */
	private void initializeDiagonal()
	{
		inter.addDiagonalListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DiagonalDistance heur = new DiagonalDistance();
				maze.setHeuristics(heur);
				
				inter.repaint();
			}
		});
	}
	
	/**
	 * Set the action for the Center Panel
	 * 		Tracks the mouse to get position
	 */
	private void initializeCenterListener()
	{
		inter.addCenterMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseDragged(MouseEvent arg0){}

			@Override
			public void mouseMoved(MouseEvent arg0)
			{
				int x = arg0.getX();
				int y = arg0.getY();
				
				inter.setPosition(x, y);
			}
		});
	}
	
	private void initializeCenterMouseListener()
	{
		inter.addCenterMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				int x = arg0.getX();
				int y = arg0.getY();
				MazeCell cell = maze.getCell(x, y);
				
				if(choosingStart)
				{
					maze.setStart(cell);
					choosingStart = false;
				}
				else if(choosingEnd)
				{
					maze.setEnd(cell);
					choosingEnd = false;
				}
				
				inter.repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0){}

			@Override
			public void mouseExited(MouseEvent arg0){}

			@Override
			public void mousePressed(MouseEvent arg0){}

			@Override
			public void mouseReleased(MouseEvent arg0){}
		});
	}
	
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	
	public Maze getMaze(){return maze;}
	
	/**
	 * Main function
	 * 
	 * @param args - command line arguments
	 */
	public static void main(String[] args)
	{
		new Controller();
	}
}
