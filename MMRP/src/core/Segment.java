
//======================================================================================//
//							Software Engineering MMRP Project							//
//									Dr. Sam Thangiah									//
//					Dan Miller, Zach Petrusch, Chris Solomon, and Jordan Schiller		//
//======================================================================================//
//									Segment Class										//
//======================================================================================//
// Purpose:																				//
//																						//
//======================================================================================//
package core;
import java.util.ArrayList;
import java.util.Map;
import java.sql.*;


public class Segment extends BaseClass {

	private int id;												//The Segment id
	private int toID;											//The endpoint id
	private int fromID;											//The startpoint id
	private int VehicleID;										//The id of the vehicle on this segment
	private String mode;										//The mode of transport
	private double distance;									//The distance between the start and endpoints
	private double cost;										//The cost to travel along this segment 
	private int departureTime;									//The time the vehicle departs
	private int arrivalTime;									//The time the vehicle arrives
	
	//This is the default constructor for the Segment
	public Segment()
	{
		MarkNew();												//Mark this Segment as new
	}//End of the default Segment constructor
	
	//This is the argumented Segment constructor that takes an id value
	public Segment(int id)
	{
		this.id=id;												//Set the Segment id
																//SHOULD WE ALSO SET THIS TO NEW OR DIRTY??
	}//End of the arguemented Segment constructor

	//This function will return the Segment id
	public int getID()
	{
		return id;												//Return the id
	}//End of getID()
	
	//This function will set the Segment's distance
	public void setDistance(double d)
	{
		//SHOULD WE CHECK TO SEE IF D > 0??
		if(this.distance!=d)
		{
			distance=d;											//Set the distance
			MarkDirty();										//Mark this Segment as dirty
		}//End of update distance if
	}//End of setDistance(double d)
	
	//This function returns the distance along the Segment
	public double getDistance()
	{
		return this.distance;									//Return the distance
	}//End of getDistance()
	
	//This function sets the cost to move along the Segment
	public void setCost(double c)
	{
		//SHOULD WE CHECK TO SEE IF C > 0?? To avoid cycles in our searches
		if(this.cost!=c)
		{
			this.cost=c;										//Set the cost
			MarkDirty();										//Mark this Segment as dirty
		}//End of update cost if
	}//End of setCost(double c)
	
	//This function returns the cost of the Segment
	public double getCost()
	{
		return this.cost;										//Return the cost
	}//End of getCost()
	
	//This function sets the arrival time of the vehicle on this Segment
	public void setArrivalTime(int t)
	{
		//SHOULD WE CHECK TO SEE IF 0 < arrivaltime < departure time?? 
		if(this.arrivalTime!=t)
		{
			arrivalTime=t;										//Set the arrival time
			MarkDirty();										//Mark this Segment as dirty
		}//End of update if
	}//End of setArrivalTime(int t)
	
	//This function returns the arrival time
	public int getArrivalTime()
	{
		return this.arrivalTime;								//Return the arrivalTime
	}//End of getArrivalTime()
	
	//This function sets the departure time of the vehicle on this Segment
	public void setDepartureTime(int t)
	{
		if(this.departureTime!=t)
		{
			this.departureTime=t;								//Set the departureTime
			MarkDirty();										//Mark this Segment as dirty
		}//End of update if
	}//End of setDepartureTime(int t)
	
	//This function returns the departure time
	public int getDepartureTime()
	{
		return this.departureTime;								//Return departureTime
	}//End of getDepartureTime()
	
	//This function sets the starting location of this Segment
	public void setStartLocation(int id)
	{
		if(fromID!=id)
		{
			fromID=id;											//Set the fromID
			MarkDirty();										//Mark this Segment as dirty
		}//End of update if
	}//End of setStartLocation(int id)
	
	//This function sets the starting location of this Segment using a Location object
	public void setStartLocation(Location l)
	{
		if(fromID!=l.getID())
		{
			fromID=l.getID();									//Set the fromID
			MarkDirty();										//Mark the Segment as dirty
		}//End of update if
	}//End of setStartLocation(Location l)
	
	//This function returns the starting location id
	public int getStartLocationID()
	{
		return fromID;											//Return the fromID
	}//End of getStartLocationID()
	
	//This function returns the Location object associated with the fromID
	public Location getStartLocation()
	{
		return Location.Load(fromID);							//Return the Location of this Segments start
	}//End of getStartLocation()
	
	//This function sets the end location of the Segment
	public void setEndLocation(int id)
	{
		if(toID!=id)
		{
			toID=id;											//Set the end point id
			MarkDirty();										//Mark the Segment as dirty
		}//End of update if
	}//End fo setEndLocation(int id)
	
	//This function sets the endpoint of Segment using a Location object
	public void setEndLocation(Location l)
	{
		if(toID!=l.getID())
		{
			toID=l.getID();										//Set the toID
			MarkDirty();										//Mark the Segment as dirty
		}//End of update if
	}//End of setEndLocation(Location l)
	
	//This function returns the end location of the Segment
	public int getEndLocationID()
	{
		return toID;											//Return toID
	}//End of getEndLocationID()
	
	//This function returns the end location of the Segment as a Location object
	public Location getEndLocation()
	{
		return Location.Load(toID);								//Return the end point Location
	}//End of getEndLocation()
	
	//This function sets the Segment's Vehicle
	public void setVehicle(Vehicle v)
	{
		if(this.VehicleID!=v.getId() || this.mode==null || !this.mode.equals(v.getTravelType()))
		{
			this.VehicleID=v.getId();							//Set the vehicle id
			mode=v.getTravelType();								//Set the travel type
			MarkDirty();										//Mark the Segment as dirty
		}//End of valid vehicle if
	}//End of setVehicle(Vehicle v)
	
	//This function sets the Segment's Vehicle using an id and travelmode
	public void setVehicle(int id,String travelMode)
	{
		//todo:ADD check on travel mode: if not exist do nothing
		if(this.VehicleID!=id || this.mode==null || !this.mode.equals(travelMode))
		{
			this.VehicleID=id;									//Set the vehicle id
			mode=travelMode;									//Set the travel type
			MarkDirty();										//Mark the Segment as dirty
		}//End of valid vehicle if
	}//End of setVehicle(int id, String travelMode)
	
	//This function returns the vehicle id
	public int getVehicleID()
	{
		return this.VehicleID;									//Return the vehicle id
	}//End of getVehicleID()
	
	//This function returns the Travel Mode on this Segment
	public String getTravelMode()
	{
		return this.mode;										//Return the travel type
	}//End of getTravelMode()
	
	//This function returns the Vehicle object on this Segment
	public Vehicle getVehicle()
	{
		//Load the Vehicle from the database based on the type of the Vehicle
		switch(Vehicle.loadType(mode))
		{
			case Truck:
				return Truck.Load(this.VehicleID);		//Return Truck
			case Rail:
				return Rail.Load(this.VehicleID);		//Return Rail 
			case Cargo:
				return Cargo.Load(this.VehicleID);		//Return Cargo
			case Plane:
				return Plane.Load(this.VehicleID);		//Return Plane
			case Bike:
				return Bike.Load(this.VehicleID);		//Return Bike
		
		}//End of switch
		return null;
	}//End of getVehicle()
	
	//This function returns all the Segments specified in the given where clause
	public static ArrayList<Segment> LoadAll(String where)
	{
		ArrayList<Segment> returnList = new ArrayList<Segment>();
		try
		{
			//Create and populate an ArrayList of Segments
			ArrayList<Map<String,Object>> temp=executeQuery("Select * from Segment " + where);
			
			//For each of the entries in our list create a Segment object
			for(int i = 0; i<temp.size();i++)
				returnList.add(BuildFromDataRow(temp.get(i)));
			
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}//End of catch block
		return returnList;
	}//End of LoadAll(String where)
	
	//This function builds objects from returned data from SQL queries against our database
	public static Segment BuildFromDataRow(Map<String,Object> data) throws SQLException
	{
		//This code grabs each element that will be found in the database on the Segment table and set the appropriate values for a new Segment
		Segment s = new Segment((Integer)data.get("SegmentID"));//rs.getInt("SegmentID"));
		//s.id=;
		s.setStartLocation((Integer)data.get("FromLocationID"));//rs.getInt("FromLocationID"));
		//s.fromID=rs.getInt("FromLocationID");
		s.setEndLocation((Integer)data.get("ToLocationID"));//rs.getInt("ToLocationID"));
		//s.toID=rs.getInt("ToLocationID");
		//s.VehicleID=rs.getInt("VehicleID");
		s.setVehicle((Integer)data.get("VehicleID"),(String)data.get("ModeType"));//rs.getString("ModeType"));
		//s.mode=rs.getString("ModeType");
		//s.distance=rs.getDouble("Distance");
		s.setDistance(Double.parseDouble(data.get("Distance").toString()));//rs.getDouble("Distance"));
		s.setCost(Double.parseDouble(data.get("Cost").toString()));//;rs.getDouble("Cost"));
		//s.cost=rs.getDouble("Cost");
		s.setDepartureTime((Integer)data.get("TimeOfDeparture"));//rs.getInt("TimeOfDeparture"));
		//s.departureTime=rs.getInt("TimeOfDeparture");
		//s.arrivalTime=rs.getInt("TimeOfArrival");
		s.setArrivalTime((Integer)data.get("TimeOfArrival"));//rs.getInt("TimeOfArrival"));
		s.MarkClean();													//Mark the Segment as clean
		return s;
	}//End of BuildFromDataRow(Map<String,Object> data)
	
	//This function returns an ArrayList of all the Segments that begin at the given Location
	public static ArrayList<Segment> LoadAllAtLocation(Location l)
	{
		//Create an empty list of Segments
		ArrayList<Segment> returnList = new ArrayList<Segment>();
		try
		{
			//Populate the list with Segments starting at the given Location
			ArrayList<Map<String,Object>>temp=executeQuery("Select * from Segment where FromLocationID = '" + l.getID() +"'");
			//For each returned Segment create a new Segment object 
			for(int i = 0 ; i < temp.size();i++)
				returnList.add(BuildFromDataRow(temp.get(i)));
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);							//Print out the error
		}//End of catch block
		return returnList;
	}//End of LoadAllAtLocation(Location l)
	
	//This function returns an ArrayList of Segments of all the Segments that start at the given Location and leave after the given start time
	public static ArrayList<Segment> LoadAllAtLocation(Location l, int startTime)
	{
		//Create a new list of Segments
		ArrayList<Segment> returnList = new ArrayList<Segment>();
		try
		{
			//Populate a list with all the Segments that match the criteria
			ArrayList<Map<String,Object>> temp = executeQuery("Select * from Segment where FromLocationID = '" + l.getID() + "' and TimeOfDeparture > " + startTime);
			//For each returned Segment create a new Segment object on our list
			for(int i = 0; i < temp.size();i++)
				returnList.add(BuildFromDataRow(temp.get(i)));
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);											//Print out the error
		}//End of catch block
		return returnList;
	}//End of LoadAllAtLocation(Location l, int startTime)
	
	public static ArrayList<Segment> LoadAllAtLocation(int locationID)
	{
		ArrayList<Segment> returnList = new ArrayList<Segment>();
		try
		{
			ArrayList<Map<String,Object>>temp=executeQuery("Select * from Segment where FromLocationID = '" + locationID +"'");
			for(int i = 0 ; i < temp.size();i++)
				returnList.add(BuildFromDataRow(temp.get(i)));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	
	public static ArrayList<Segment> LoadAllAtLocation(int locationID, int startTime)
	{
		ArrayList<Segment> returnList = new ArrayList<Segment>();
		try
		{
			ArrayList<Map<String,Object>> temp = executeQuery("Select * from Segment where FromLocationID = '" + locationID + "' and TimeOfDeparture > " + startTime);
			for(int i = 0; i < temp.size();i++)
				returnList.add(BuildFromDataRow(temp.get(i)));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	//This function will load a Segment from the database
		public static Segment Load(int id)
		{
			try
			{
				//Search the database for Segments with the given id
				ArrayList<Map<String,Object>> temp= executeQuery("Select * from Segment where SegmentID = " +id);
				//If something was returned build the Segment from it
				if(temp.size()>0)
					return BuildFromDataRow(temp.get(0));
				return null;
			}//End of try block
			catch(Exception ex)
			{
				System.out.println("Error " +ex);
				return null;
			}//End of catch block
		}//End of Load(int id)
		
		//This function overrides the parent's Update function and will handle changes made to the Segment object in the database
		@Override
		public void Update() 
		{
			try
			{
				//toDo: set id on insert set update statement
				if(isNew())
				{
					//If the Segment is new insert it into the database by executing the following
					executeCommand("Insert into Segment (FromLocationID,ToLocationID,VehicleID,ModeType,Distance,Cost,TimeOfDeparture,TimeOfArrival) Values ('"+
							this.getStartLocationID()+"','"+this.getEndLocationID()+"','"+this.getVehicleID()+"','"+this.getTravelMode()+"','"+this.getDistance()+"','"+
							this.getCost()+"','" + this.getDepartureTime()+"','"+this.getArrivalTime()+"')");
					//Grab this Segment from the database
					ArrayList<Map<String,Object>> temp =executeQuery("Select SegmentID from Segment where FromLocationID ='"+ this.getStartLocationID()+"' "+
							"AND ToLocationID ='" + this.getEndLocationID() +"' "+
							"AND VehicleID='" + this.getVehicleID()+"' "+
							"And ModeType='" + this.getTravelMode()+"' "+
							"And Distance='"+this.getDistance()+"' "+
							"And Cost ='"+this.getCost()+"' "+
							"And TimeOfDeparture ='"+this.getDepartureTime()+"' "+
							"And TimeOfArrival = '"+this.getArrivalTime()+"'");
					//If this Segment exists on the database mark it as old and clean
					if(temp.size()>0)
					{
						this.id = (Integer)temp.get(0).get("SegmentID");				//Set the Segment id to the id from the database
						MarkClean();													//Mark the Segment as clean
						MarkOld();														//Mark the Segment as old
					}//End of found something if
				}//End of isNew if
				else
				{
					if(isDirty())
					{
						//If the Segment is not new, but is dirty then it needs to be updated by the following SQL command
						executeCommand("Update Segment Set FromLocationID ='"+ this.getStartLocationID()+"' "+
								"AND ToLocationID ='" + this.getEndLocationID() +"' "+
								"AND VehicleID='" + this.getVehicleID()+"' "+
								"And ModeType='" + this.getTravelMode()+"' "+
								"And Distance='"+this.getDistance()+"' "+
								"And Cost ='"+this.getCost()+"' "+
								"And TimeOfDeparture ='"+this.getDepartureTime()+"' "+
								"And TimeOfArrival = '"+this.getArrivalTime()+"' Where SegmentID="+this.id);
						MarkClean();													//Mark the Segment as clean
					}//End of isDirty if
				}//End of isOld else
			}//End of try block
			catch(Exception ex)
			{
				System.out.println("Error " + ex);										//Print out the error
			}//End of catch block
			
		}//End of the overridden Update()

	public int estimateCapacity()
	{
		try
		{
			ArrayList<Map<String,Object>> temp = executeQuery("Select Sum(Size)as Capacity from Shipment where ShipmentID IN (Select Distinct ShipmentID from ShipmentHistory where SegmentID = " + this.id+")");
			if(temp.get(0).get("Capacity")!=null)
			return (Integer)temp.get(0).get("Capacity");
			else
				return 0;
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
			return -1;
		}
	}
	//This is the overridden Delete function of the parent class and will remove this Segment from the database
	@Override
	public  void Delete() 
	{
		try
		{
			executeCommand("Delete from Bike Where BikeID = " + this.id);			//Delete the Segment
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);										//Print out the error
		}//End of catch block

	}//End of the overridden Delete()
		
	@Override
	public String toString()
	{
		return this.fromID + "   " + this.toID;
	}

}
