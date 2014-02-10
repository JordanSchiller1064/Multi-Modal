import java.util.ArrayList;
import java.sql.*;


public class Segment extends BaseClass {

	private int id;
	private int toID;
	private int FromID;
	private int VehicleID;
	private String mode;
	private double distance;
	private double cost;
	private int departureTime;
	private int arrivalTime;
	
	public Segment()
	{
		
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
