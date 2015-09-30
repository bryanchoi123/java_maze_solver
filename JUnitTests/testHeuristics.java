import static org.junit.Assert.*;

import java.awt.Point;

import heuristics.*;

import maze.MazeCell;

import org.junit.Before;
import org.junit.Test;


public class testHeuristics
{
	private Heuristics heur;
	private MazeCell testParent, testCell, end;

	@Before
	public void initialize()
	{
		end = new MazeCell(new Point(10, 10));
		
		testParent = new MazeCell(new Point(2, 3));
		testCell = new MazeCell(new Point(3, 4));
		
		testCell.setParent(testParent);
	}
	
	@Test
	public void testDiagonalDistanceDiag()
	{
		heur = new DiagonalDistance();
		assertEquals(0, heur.moveCost(testParent));
		assertEquals(141, heur.moveCost(testCell));
	}
	
	@Test
	public void testDiagonalDistanceStriaght()
	{
		heur = new DiagonalDistance();
		testParent = new MazeCell(new Point(3, 5));
		testCell.setParent(testParent);
		assertEquals(100, heur.moveCost(testCell));
	}
	
	@Test
	public void testDiagonalDistanceEnd()
	{
		heur = new DiagonalDistance();
		assertEquals(7, heur.distToEnd(testCell, end));
	}
	
	@Test
	public void testManhattanDiag()
	{
		heur = new ManhattanDistance();
		assertEquals(141, heur.moveCost(testCell));
	}
	
	@Test
	public void testManhattanStraight()
	{
		heur = new ManhattanDistance();
		testParent = new MazeCell(new Point(3, 5));
		testCell.setParent(testParent);
		assertEquals(100, heur.moveCost(testCell));
	}
	
	@Test
	public void testManhattenEnd()
	{
		heur = new ManhattanDistance();
		assertEquals(13, heur.distToEnd(testCell, end));
	}
	
	@Test
	public void testManhattenNoParent()
	{
		heur = new ManhattanDistance();
		assertEquals(0, heur.moveCost(testParent));
	}
	
	@Test
	public void testManhattenTieBreak()
	{
		heur = new ManhattanTieBreak();
		assertEquals(13, heur.distToEnd(testCell, end));
	}

}
