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
	
	public void setStartLocation(int id)
	{
		fromID=id;
		startLocation = Location.Load(id);
		MarkDirty();
	}
	public void setStartLocation(Location l)
	{
		startLocation=l;
		fromID=startLocation.getID();
		MarkDirty();
	}
	public int getStartLocationID()
	{
		return fromID;
	}
	public Location getStartLocation()
	{
		return startLocation;
	}
	
	public void setEndLocation(int id)
	{
		toId=id;
		endLocation=Location.Load(id);
		MarkDirty();
	}
	public void setEndLocation(Location l)
	{
		endLocation=l;
		toID=endLocation.getID();
		MarkDirty();
	}
	public int getEndLocationID()
	{
		return toID;
	}
	public Location getEndLocation()
	{
		return endLocation;
	}
	
	public void setVehicle(Vehicle v)
	{
		vehicleUsed=v;
		VehicleID=vehicleUsed.getID();
		MarkDirty();
	}
	public void setVehicle(int id)
	{
		VehicleID=v;
		//vehicleUsed=Vehicle.
		MarkDirty();
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
	public static Segment BuildFromDataRow(ResultSet rs)
	{
		Segment s = new Segment();
		try 
		{
			s.id=rs.getInt("SegmentID");
			s.FromID=rs.getInt("FromLocationID");
			s.toID=rs.getInt("ToLocationID");
			s.VehicleID=rs.getInt("VehicleID");
			s.mode=rs.getString("ModeType");
			s.distance=rs.getDouble("Distance");
			s.cost=rs.getDouble("Cost");
			s.departureTime=rs.getInt("TimeOfDeparture");
			s.arrivalTime=rs.getInt("TimeOfArrival");
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return s;
	}
	public int getStart()
	{
		return this.FromID;
	}
	public int getEnd()
	{
		return this.toID;
	}
	@Override
	void Update() {
		// TODO Auto-generated method stub

	}

	@Override
	void Delete() {
		// TODO Auto-generated method stub

	}
	@Override
	public String toString()
	{
		return this.FromID + "   " + this.toID;
	}

}
