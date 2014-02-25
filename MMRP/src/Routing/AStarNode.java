package Routing;

public class AStarNode 
{

	private int locationID;
	private AStarNode previous;
	private double cost;
	private int segmentID;
	
	public AStarNode(int location, AStarNode previous, int segment, double cost)
	{
		locationID=location;
		this.previous=previous;
		if(this.previous==null)
		{
			this.cost=cost;
		}
		else
		{
			this.cost = previous.getCost()+cost;
		}
		segmentID=segment;
	}
	public double getCost()
	{
		return cost;
	}
	public int getLocationID()
	{
		return locationID;
	}
	public AStarNode getPrevious()
	{
		return previous;
	}
	public int getSegmentID()
	{
		return segmentID;
	}
	
}
