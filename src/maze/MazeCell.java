package maze;
import java.awt.Color;
import java.awt.Point;
import java.security.InvalidParameterException;

public class MazeCell implements Comparable<MazeCell>
{	
	private Color color;
	private boolean walkable, visited;
	private MazeCell parent;
	
	private int movementCost, distanceToEnd;
	private final Point position;
	
	
	/**
	 * Constructor
	 * 
	 * @param pos - position of maze cell
	 */
	public MazeCell(Point pos)
	{
		if(pos == null)
			throw new InvalidParameterException("Pos cannot be null");
		
		position = pos;
		
		walkable = true;
		visited = false;
		parent = null;
		color = Colors.WALK;
		
		movementCost = distanceToEnd = 0;
	}
	
/*      Methods       */
	
	/**
	 * @param o, the object to compare this cell to
	 * 
	 * @return a positive number if o is smaller or a
	 * negative number if o is bigger. 0 if equal
	 */
	@Override
	public int compareTo(MazeCell o)
	{
		return getTotalCost() - o.getTotalCost();
	}
	
	public String toString()
	{
		return "(" + position.x + ", " + position.y + ")";
	}
	
/*      Getters and Setters        */
	
	/**
	 * @return the total cost of current cell
	 */
	public int getTotalCost(){return movementCost + distanceToEnd;}
	
	/**
	 * @return the movement cost
	 */
	public int getMovementCost(){return movementCost;}
	
	/**
	 * @param movementCost, the value to set the movement cost to
	 */
	public void setMovementCost(int movementCost){this.movementCost = movementCost;}
	
	/**
	 * @return the heuristic distance to the end
	 */
	public int getDistanceToEnd(){return distanceToEnd;}
	
	/**
	 * @param distanceToEnd, the value to set the distance to
	 */
	public void setDistanceToEnd(int distanceToEnd){this.distanceToEnd = distanceToEnd;}
	
	/**
	 * @return the position of the cell
	 */
	public Point getPosition(){return position;}
	
	/**
	 * @return the row of the cell	
	 */
	public int getX(){return position.x;}
	
	/**
	 * @return the column of the cell
	 */
	public int getY(){return position.y;}
	
	/**
	 * @return the color of the cell
	 */
	public Color getColor(){return color;}
	
	/**
	 * @param color, the color to set cell to
	 */
	public void setColor(Color color){this.color = color;}
	
	/**
	 * @return whether cell is a wall or not
	 */
	public boolean isWalkable(){return walkable;}

	/**
	 * @param isWall, the value to set isWall to
	 * 
	 */
	public void setWalkable(boolean walkable){this.walkable = walkable;}

	/**
	 * @return visited - whether or not the cell is visited (for Dijkstra's)
	 */
	public boolean isVisited(){return visited;}
	
	/**
	 * @param visited - the value to set visited to
	 */
	public void setVisited(boolean visited){this.visited = visited;}
	
	/**
	 * @return the parent cell
	 */
	public MazeCell getParent(){return parent;}

	/**
	 * @param parent, the cell to set the parent to
	 */
	public void setParent(MazeCell parent){this.parent = parent;}
}
