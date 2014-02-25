import java.util.ArrayList;
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
	public Vehicle getVehcle()
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
			Connection c =getConnection();
			Statement stm = c.createStatement();
			ResultSet rs = stm.executeQuery("Select * from Segment " + where);
			
			while(rs.next())
			{
				returnList.add(BuildFromDataRow(rs));
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Segment BuildFromDataRow(ResultSet rs) throws SQLException
	{
		Segment s = new Segment(rs.getInt("SegmentID"));
		//s.id=;
		s.setStartLocation(rs.getInt("FromLocationID"));
		//s.fromID=rs.getInt("FromLocationID");
		s.setEndLocation(rs.getInt("ToLocationID"));
		//s.toID=rs.getInt("ToLocationID");
		//s.VehicleID=rs.getInt("VehicleID");
		s.setVehicle(rs.getInt("VehicleID"), rs.getString("ModeType"));
		//s.mode=rs.getString("ModeType");
		//s.distance=rs.getDouble("Distance");
		s.setDistance(rs.getDouble("Distance"));
		s.setCost(rs.getDouble("Cost"));
		//s.cost=rs.getDouble("Cost");
		s.setDepartureTime(rs.getInt("TimeOfDeparture"));
		//s.departureTime=rs.getInt("TimeOfDeparture");
		//s.arrivalTime=rs.getInt("TimeOfArrival");
		s.setArrivalTime(rs.getInt("TimeOfArrival"));
		s.MarkClean();
		return s;
	}
	
	public static ArrayList<Segment> LoadAllAtLocation(Location l)
	{
		ArrayList<Segment> returnList = new ArrayList<Segment>();
		try
		{
			Connection c = getConnection();
			ResultSet rs = c.createStatement().executeQuery("Select * from Segment where FromLocationID = '" + l.getID() +"'");
			while(rs.next())
				returnList.add(BuildFromDataRow(rs));
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
			Connection c = getConnection();
			ResultSet rs = c.createStatement().executeQuery("Select * from Segment where FromLocationID = '" + l.getID() + "' and TimeOfDeparture > " + startTime);
			while(rs.next())
				returnList.add(BuildFromDataRow(rs));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	
	@Override
	void Update() {
		if(isNew())
		{
		
		}
		else
		{
			if(isDirty())
			{
				
			}
		}

	}

	@Override
	void Delete() {
		// TODO Auto-generated method stub

	}
	@Override
	public String toString()
	{
		return this.fromID + "   " + this.toID;
	}

}
