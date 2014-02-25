package Routing;
import java.util.ArrayList;

import core.*;
public class AStarAlg {
	
	private ArrayList<AStarNode> leafs;
	Shipment shpmnt;
	
	final double DISTTOGO=.2;
	final double DISTTRAVLED=.2;
	double costRatio;
	double timeRatio;
	
	
	public AStarAlg(Shipment s)
	{
		shpmnt=s;
		leafs=new ArrayList<AStarNode>();
		leafs.add(new AStarNode(shpmnt.getFromLocationID(),null,-1,0));
		if(shpmnt.getPriority()==0)
		{
			costRatio=0.0;
			timeRatio=.6;
		}
		else if(shpmnt.getPriority()==1)
		{
			costRatio=.3;
			timeRatio=.3;
		}
		else
		{
			costRatio=.6;
			timeRatio=.0;
		}
	}
	
	public ArrayList<Segment> getPath()
	{
		ArrayList<Segment> returnPath = new ArrayList<Segment>();
		
		while(leafs.size()>0)
		{
			AStarNode currentLeaf = leafs.get(0);
			for(int i =1; i<leafs.size();i++)
				currentLeaf = (currentLeaf.getCost()>leafs.get(i).getCost())?leafs.get(i):currentLeaf;
				
			leafs.remove(currentLeaf);
			if(currentLeaf.getLocationID()==shpmnt.getToLocationID())
			{
				while(currentLeaf!=null)
				{
					if(currentLeaf.getSegmentID()!=-1)
						returnPath.add(0,Segment.Load(currentLeaf.getSegmentID()));
					currentLeaf=currentLeaf.getPrevious();
				}
				return returnPath;
			}
			else
			{
				int startTime = (currentLeaf.getSegmentID()==-1)?shpmnt.getDepartureTime():Segment.Load(currentLeaf.getSegmentID()).getArrivalTime();
				ArrayList<Segment> possibleDestinations = Segment.LoadAllAtLocation(currentLeaf.getLocationID(), startTime);
				
				//Check for previously visitedLocations
				removePreviousVisited(possibleDestinations,currentLeaf);
				//Check if shipment will fit on vehicle
				removeSegmentsWithFullVehicle(possibleDestinations);
				//Check if lowerCost leaf to location already exists if not add/replace new leaf
				checkCurrentLeafs(possibleDestinations,currentLeaf);
				
			}
		}
		return returnPath;
	}
	
	
	
	
	private void removePreviousVisited(ArrayList<Segment> possible, AStarNode current)
	{
		ArrayList<Segment> toRemove = new ArrayList<Segment>();
		
		for(int i = 0; i< possible.size();i++)
		{
			Segment test = possible.get(i);
			AStarNode compareNode=current.getPrevious();
			while(compareNode!=null)
			{
				if(test.getEndLocationID()==compareNode.getLocationID())
				{
					toRemove.add(test);
					break;
				}
				compareNode=compareNode.getPrevious();
			}
		}
		//remove previously visited
		for(int i = 0; i<toRemove.size();i++)
		{
			possible.remove(toRemove.get(i));
		}
	}

	private void removeSegmentsWithFullVehicle(ArrayList<Segment> possible)
	{
		ArrayList<Segment> toRemove=new ArrayList<Segment>();
		for(int i = 0; i<possible.size();i++)
		{
			Segment test = possible.get(i);
			if(test.getVehicle().getCapacity()<test.estimateCapacity()+shpmnt.getSize())
				toRemove.add(test);
		}
		
		//remove segments where vehicle is full
		for(int i = 0; i<toRemove.size();i++)
		{
			possible.remove(toRemove.get(i));
		}
	}
	private void checkCurrentLeafs(ArrayList<Segment> possible,AStarNode currentLeaf)
	{
		for(int i =0;i<possible.size();i++)
		{
			Segment test = possible.get(i);
			
			double cost=currentLeaf.getCost();
			double distanceToDestination = getDistance(test.getEndLocationID(),shpmnt.getToLocationID());
			double distanceTravel = test.getDistance();
			double time = test.getDepartureTime()-test.getArrivalTime();
			double segmentCost = test.getCost();
			
			cost+= (distanceToDestination*this.DISTTOGO)+(distanceTravel*this.DISTTRAVLED)+(time*this.timeRatio)+(segmentCost * this.costRatio);
			
			AStarNode temp = new AStarNode(test.getEndLocationID(),currentLeaf,test.getID(),cost);
			AStarNode replace=null;
			for(int j = 0; j<leafs.size();j++)
			{
				if(temp.getLocationID()==leafs.get(j).getLocationID())
				{
					if(temp.getCost()<leafs.get(j).getCost())
					{
						replace=leafs.get(j);
						leafs.add(temp);
						
					}
					break;
				}
			}
			if(replace!=null)
				leafs.remove(replace);
			
		}
	}
	public double getDistance(int one , int two)
	{
		Location s = Location.Load(one);
		Location e = Location.Load(two);
		double distance;
		distance = Math.sqrt(Math.pow(s.getLatitude()-e.getLatitude(),2) + Math.pow(s.getLongitude() - e.getLongitude(), 2));
		return distance;
	}
}
