package core;
import java.util.ArrayList;
import java.util.Map;
import java.sql.*;


public class Segment extends BaseClass {

	private int id;
	private int toID;
	private int fromID;
	private int VehicleID;
	private String mode;
	private double distance;
	private double cost;
	private int departureTime;
	private int arrivalTime;
	
	public Segment()
	{
		MarkNew();
	}
	public Segment(int id)
	{
		this.id=id;
	}

	public int getID()
	{
		return id;
	}
	
	public void setDistance(double d)
	{
		if(this.distance!=d)
		{
			distance=d;
			MarkDirty();
		}
	}
	public double getDistance()
	{
		return this.distance;
	}
	
	public void setCost(double c)
	{
		if(this.cost!=c)
		{
			this.cost=c;
			MarkDirty();
		}
	}
	
	public double getCost()
	{
		return this.cost;
	}
	
	public void setArrivalTime(int t)
	{
		if(this.arrivalTime!=t)
		{
			arrivalTime=t;
			MarkDirty();
		}
	}
	
	public int getArrivalTime()
	{
		return this.arrivalTime;
	}
	
	public void setDepartureTime(int t)
	{
		if(this.departureTime!=t)
		{
			this.departureTime=t;
			MarkDirty();
		}
	}
	
	public int getDepartureTime()
	{
		return this.departureTime;
	}
	
	public void setStartLocation(int id)
	{
		if(fromID!=id)
		{
			fromID=id;
			MarkDirty();
		}
	}
	public void setStartLocation(Location l)
	{
		if(fromID!=l.getID())
		{
			fromID=l.getID();
			MarkDirty();
		}
	}
	public int getStartLocationID()
	{
		return fromID;
	}
	public Location getStartLocation()
	{
		return Location.Load(fromID);
	}
	
	public void setEndLocation(int id)
	{
		if(toID!=id)
		{
			toID=id;
			MarkDirty();
		}
	}
	public void setEndLocation(Location l)
	{
		if(toID!=l.getID())
		{
			toID=l.getID();
			MarkDirty();
		}
	}
	public int getEndLocationID()
	{
		return toID;
	}
	public Location getEndLocation()
	{
		return Location.Load(toID);
	}
	
	public void setVehicle(Vehicle v)
	{
		if(this.VehicleID!=v.getId() || this.mode==null || !this.mode.equals(v.getTravelType()))
		{
			this.VehicleID=v.getId();
			mode=v.getTravelType();
			MarkDirty();
		}
	}
	public void setVehicle(int id,String travelMode)
	{
		//todo:ADD check on travel mode: if not exist do nothing
		if(this.VehicleID!=id || this.mode==null || !this.mode.equals(travelMode))
		{
			this.VehicleID=id;
			mode=travelMode;
			MarkDirty();
		}
	}
	public int getVehicleID()
	{
		return this.VehicleID;
	}
	public String getTravelMode()
	{
		return this.mode;
	}
	public Vehicle getVehicle()
	{
		switch(Vehicle.loadType(mode))
		{
			case Truck:
				return Truck.Load(this.VehicleID);
			case Rail:
				return Rail.Load(this.VehicleID);
			case Cargo:
				return Cargo.Load(this.VehicleID);
			case Plane:
				return Plane.Load(this.VehicleID);
			case Bike:
				return Bike.Load(this.VehicleID);
		
		}
		return null;
	}
	public static ArrayList<Segment> LoadAll(String where)
	{
		ArrayList<Segment> returnList = new ArrayList<Segment>();
		try
		{
			ArrayList<Map<String,Object>> temp=executeQuery("Select * from Segment " + where);
			
			for(int i = 0; i<temp.size();i++)
				returnList.add(BuildFromDataRow(temp.get(i)));
			
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Segment BuildFromDataRow(Map<String,Object> data) throws SQLException
	{
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
		s.MarkClean();
		return s;
	}
	
	public static ArrayList<Segment> LoadAllAtLocation(Location l)
	{
		ArrayList<Segment> returnList = new ArrayList<Segment>();
		try
		{
			ArrayList<Map<String,Object>>temp=executeQuery("Select * from Segment where FromLocationID = '" + l.getID() +"'");
			for(int i = 0 ; i < temp.size();i++)
				returnList.add(BuildFromDataRow(temp.get(i)));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	
	public static ArrayList<Segment> LoadAllAtLocation(Location l, int startTime)
	{
		ArrayList<Segment> returnList = new ArrayList<Segment>();
		try
		{
			ArrayList<Map<String,Object>> temp = executeQuery("Select * from Segment where FromLocationID = '" + l.getID() + "' and TimeOfDeparture > " + startTime);
			for(int i = 0; i < temp.size();i++)
				returnList.add(BuildFromDataRow(temp.get(i)));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
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
	public static Segment Load(int id)
	{
		try
		{
			ArrayList<Map<String,Object>> temp= executeQuery("Select * from Segment where SegmentID = " +id);
			if(temp.size()>0)
				return BuildFromDataRow(temp.get(0));
			return null;
		}
		catch(Exception ex)
		{
			System.out.println("Error " +ex);
			return null;
		}
	}
	@Override
	public void Update() 
	{
		try
		{
			//toDo: set id on insert set update statement
			if(isNew())
			{
				executeCommand("Insert into Segment (FromLocationID,ToLocationID,VehicleID,ModeType,Distance,Cost,TimeOfDeparture,TimeOfArrival) Values ('"+
						this.getStartLocationID()+"','"+this.getEndLocationID()+"','"+this.getVehicleID()+"','"+this.getTravelMode()+"','"+this.getDistance()+"','"+
						this.getCost()+"','" + this.getDepartureTime()+"','"+this.getArrivalTime()+"')");
				
				ArrayList<Map<String,Object>> temp =executeQuery("Select SegmentID from Segment where FromLocationID ='"+ this.getStartLocationID()+"' "+
						"AND ToLocationID ='" + this.getEndLocationID() +"' "+
						"AND VehicleID='" + this.getVehicleID()+"' "+
						"And ModeType='" + this.getTravelMode()+"' "+
						"And Distance='"+this.getDistance()+"' "+
						"And Cost ='"+this.getCost()+"' "+
						"And TimeOfDeparture ='"+this.getDepartureTime()+"' "+
						"And TimeOfArrival = '"+this.getArrivalTime()+"'");
				if(temp.size()>0)
				{
					this.id = (Integer)temp.get(0).get("SegmentID");
					MarkClean();
					MarkOld();
				}
			}
			else
			{
				if(isDirty())
				{
					executeCommand("Update Segment Set FromLocationID ='"+ this.getStartLocationID()+"' "+
							"AND ToLocationID ='" + this.getEndLocationID() +"' "+
							"AND VehicleID='" + this.getVehicleID()+"' "+
							"And ModeType='" + this.getTravelMode()+"' "+
							"And Distance='"+this.getDistance()+"' "+
							"And Cost ='"+this.getCost()+"' "+
							"And TimeOfDeparture ='"+this.getDepartureTime()+"' "+
							"And TimeOfArrival = '"+this.getArrivalTime()+"' Where SegmentID="+this.id);
					MarkClean();
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		
	}

	public int estimateCapacity()
	{
		try
		{
			ArrayList<Map<String,Object>> temp = executeQuery("Select Sum(Size)as Capacity from Shipment where ShipmentID IN (Select Distinct ShipmentID where SegmentID = " + this.id);
			return (Integer)temp.get(0).get("Capacity");
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
			return -1;
		}
	}
	@Override
	public  void Delete() 
	{
		try
		{
			executeCommand("Delete from Bike Where BikeID = " + this.id);
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}

	}
	@Override
	public String toString()
	{
		return this.fromID + "   " + this.toID;
	}

}
