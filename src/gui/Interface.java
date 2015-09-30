package gui;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

import maze.Maze;
import maze.MazeCell;


public class Interface extends JFrame
{	
	/**
	 * Automatically generated serial ID
	 */
	private static final long serialVersionUID = -2316404590312640013L;
	private JPanel bottom, center;
	private JTextField threshold, position;
	
	private JMenuBar menuBar;
	private JMenuItem load, save, quit, setStart, setEnd, solvable, solve, reset;
	private JRadioButtonMenuItem aStar, djikstra, manhattan, manhattanTied, diagonal;
	
	private MazeSquare[][] grid;
	private int width, height;
		
	public Interface(Controller c)
	{
		super("Extreme Maze Solver");
		
		width = c.getWidth();
		height = c.getHeight();
		
		initialize();
		Maze maze = c.getMaze();
		
		for(int x = 0; x<width; x++)
			for(int y = 0; y<height; y++)
			{
				MazeCell mc = maze.getCell(x, y);
				MazeSquare square = new MazeSquare(mc);
				grid[x][y] = square;
			}
		
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		setJMenuBar(menuBar);
		
		setSize(500, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void initialize()
	{
		grid = new MazeSquare[width][height];
		
		menuBar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		
			load = new JMenuItem("Load Maze", KeyEvent.VK_L);
			save = new JMenuItem("Save Maze", KeyEvent.VK_S);
			quit = new JMenuItem("Quit", KeyEvent.VK_Q);
			
			file.add(load);
			file.add(save);
			file.add(quit);
			
		JMenu mz = new JMenu("Maze");
		mz.setMnemonic(KeyEvent.VK_M);
		menuBar.add(mz);
		
			setStart = new JMenuItem("Set Start Location", KeyEvent.VK_S);
			setEnd = new JMenuItem("Set End Location", KeyEvent.VK_E);
		
			mz.add(setStart);
			mz.add(setEnd);
			mz.addSeparator();
			
			solvable = new JMenuItem("Check if Solvable", KeyEvent.VK_O);
			solve = new JMenuItem("Solve Maze", KeyEvent.VK_S);
			reset = new JMenuItem("Reset Maze", KeyEvent.VK_R);
			
			mz.add(solvable);
			mz.add(solve);
			mz.add(reset);
			mz.addSeparator();
			
			JMenu algorithms = new JMenu("Solvers");
			algorithms.setMnemonic(KeyEvent.VK_H);
			mz.add(algorithms);
				
				ButtonGroup algorithmGroup = new ButtonGroup();
				
				aStar = new JRadioButtonMenuItem("A* Algorithm", true);
				aStar.setMnemonic(KeyEvent.VK_R);
				algorithmGroup.add(aStar);
				
				djikstra = new JRadioButtonMenuItem("Djikstra's Pathfinding");
				djikstra.setMnemonic(KeyEvent.VK_D);
				algorithmGroup.add(djikstra);
				
				algorithms.add(aStar);
				algorithms.add(djikstra);
				
			JMenu heuristics = new JMenu("Heuristics");
			heuristics.setMnemonic(KeyEvent.VK_A);
			mz.add(heuristics);
			
				ButtonGroup heuristicGroup = new ButtonGroup();
				
				manhattan = new JRadioButtonMenuItem("Manhattan", true);
				manhattan.setMnemonic(KeyEvent.VK_N);
				heuristicGroup.add(manhattan);
				
				manhattanTied = new JRadioButtonMenuItem("Manhattan with Tie Break");
				manhattanTied.setMnemonic(KeyEvent.VK_T);
				heuristicGroup.add(manhattanTied);
				
				diagonal = new JRadioButtonMenuItem("Diagonal Distance");
				diagonal.setMnemonic(KeyEvent.VK_D);
				heuristicGroup.add(diagonal);
				
				heuristics.add(manhattan);
				heuristics.add(manhattanTied);
				heuristics.add(diagonal);
				
		JLabel threshLabel = new JLabel("Grey scale threshold");
		threshLabel.setHorizontalAlignment(JLabel.CENTER);
		
		threshold = new JTextField();
		threshold.setHorizontalAlignment(JTextField.CENTER);
		threshold.setText("200");
		
		JLabel positionLabel = new JLabel("Position: ");
		positionLabel.setHorizontalAlignment(JLabel.CENTER);
		
		position = new JTextField();
		position.setHorizontalAlignment(JTextField.CENTER);
		position.setEditable(false);
		
		bottom = new JPanel(new GridLayout(2, 2));
		
		bottom.add(threshLabel);
		bottom.add(threshold);
		
		bottom.add(positionLabel);
		bottom.add(position);
		
		center = new JPanel();
		center.setLayout(new BorderLayout());
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		// double buffer center panel
		Image bufferedImage = center.createImage(width, height);
		Graphics buffer = bufferedImage.getGraphics();
		
		for(int x = 0; x<width; x++)
			for(int y = 0; y<height; y++)
				grid[x][y].draw(buffer);
		
		Graphics graph = center.getGraphics();
		graph.drawImage(bufferedImage, 0, 0, center);
	}
	
	/**
	 * Functions for each component to have different ActionListeners
	 */
	public void addLoadListener(ActionListener listen){load.addActionListener(listen);}
	public void addSaveListener(ActionListener listen){save.addActionListener(listen);}
	public void addQuitListener(ActionListener l){quit.addActionListener(l);}
	
	public void addSetStartListener(ActionListener l){setStart.addActionListener(l);}
	public void addSetEndListener(ActionListener l){setEnd.addActionListener(l);}
	public void addSolveableListener(ActionListener listen){solvable.addActionListener(listen);}
	public void addSolveListener(ActionListener listen){solve.addActionListener(listen);}
	public void addResetListener(ActionListener l){reset.addActionListener(l);}
	
	public void addaStarListener(ActionListener l){aStar.addActionListener(l);}
	public void addDjikstraListener(ActionListener l){djikstra.addActionListener(l);}
	
	public void addManhattanListener(ActionListener l){manhattan.addActionListener(l);}
	public void addManhattanTiedListener(ActionListener l){manhattanTied.addActionListener(l);}
	public void addDiagonalListener(ActionListener l){diagonal.addActionListener(l);}
	
	public void addCenterMouseMotionListener(MouseMotionListener l){center.addMouseMotionListener(l);}
	public void addCenterMouseListener(MouseListener l){center.addMouseListener(l);}
	
	/**
	 * Changes the field showing current mouse position
	 * 
	 * @param x - x-pos of mouse
	 * @param y - y-pos of mouse
	 */
	public void setPosition(int x, int y)
	{
		String pos = "(" + x + ", " + y + ")";
		position.setText(pos);
		
		position.repaint();
	}
	
	/**
	 * function to get threshold in threshold field
	 * 
	 * @return the parsed threshold
	 */
	public double getThreshold()
	{
		String data = threshold.getText();
		if(data == null)
		{
			JOptionPane.showMessageDialog(this, "Threshold required");
			return -1;
		}
		
		double thresh = 200;
		
		try{
			thresh = Double.parseDouble(data);
		}catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(this, "No threshold set. Defaulting to 200");
		}
		
		return thresh;
	}
	
	public MazeSquare[][] getGrid(){return grid;}
	public void setGrid(MazeSquare[][] newGrid){grid = newGrid;}
	
	public void setWidth(int width){this.width = width;}
	public void setHeight(int height){this.height = height;}
}
