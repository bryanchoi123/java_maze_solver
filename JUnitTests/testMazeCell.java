import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.security.InvalidParameterException;

import maze.MazeCell;

import org.junit.Before;
import org.junit.Test;


public class testMazeCell
{
	MazeCell testCell;
	
	@Before
	public void initialize()
	{
		testCell = new MazeCell(new Point(3, 3));
	}
	
	@Test (expected = InvalidParameterException.class)
	public void testNullConstructor()
	{
		new MazeCell(null);
	}
	
	@Test
	public void testCompare()
	{
		testCell.setMovementCost(10);
		testCell.setDistanceToEnd(20);
		
		MazeCell testCell2 = new MazeCell(new Point(2, 5));
		testCell2.setMovementCost(100);
		testCell2.setDistanceToEnd(260);
		
		assertTrue(testCell.compareTo(testCell2) < 0);
	}

}
