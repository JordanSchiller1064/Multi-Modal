import java.sql.*;
import java.util.ArrayList;
public class Truck extends Vehicle {

	public static enum TruckTypes 
	{
			Semi("SEMI"),
			Other("OTHER");
			private String type;
			TruckTypes(String s)
			{
				type=s;
			}
			@Override public String toString()
			{
				return type;
			}
			
	}
	private TruckTypes type;
	public Truck()
	{
		setTravelType(TravelTypes.Truck);
		MarkNew();
	}	
	public Truck(int id)
	{
		setTravelType(TravelTypes.Truck);
		this.id=id;
	}	
	
	public void setTruckName(String s)
	{
			super.setVehicleName(s);
	}
	public String getTruckName()
	{
		return super.getVehicleName();
	}
	public void setTruckType(TruckTypes s)
	{
		if(this.type==null || !this.type.equals(s))
		{
			type = s;
			MarkDirty();
		}
	}
	public void setTruckType(String s)
	{
		if(this.type==null || !this.type.toString().equals(s))
		{
			type = loadTruckType(s);
			MarkDirty();
		}
	}
	public String getTruckType()
	{
		return type.toString();
	}
	private TruckTypes loadTruckType(String s)
	{
		if(s.equals(TruckTypes.Semi.toString()))
			return TruckTypes.Semi;
		return TruckTypes.Other;
			
	}
	@Override
	public void Update() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public  void Delete() 
	{
		// TODO Auto-generated method stub

	}

	public static Truck Load(int id)
	{
		try
		{
			Connection c = getConnection();
			ResultSet rs = c.createStatement().executeQuery("Select * from Truck where TruckId = " + id);
			if(rs.next())
				return BuildFromDataRow(rs);
			return null;
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
 		return null;
	}
	public static ArrayList<Truck> LoadAll(String where)
	{
		ArrayList<Truck> returnList = new ArrayList<Truck>();
		try 
		{
			Connection c = getConnection();
			ResultSet rs = c.createStatement().executeQuery("Select * from Truck " +where );
			while(rs.next())
				returnList.add(BuildFromDataRow(rs));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Truck BuildFromDataRow(ResultSet rs) throws SQLException
	{
		Truck t = new Truck(rs.getInt("TruckID"));
		//t.setId();
		t.setTruckName(rs.getString("TruckName"));
		t.setCapacity(rs.getInt("Capacity"));
		t.setContractor(rs.getString("Contractor"));
		t.setLocation(rs.getDouble("Latitude"), rs.getDouble("Longitude"),rs.getString("LocationName"));
		t.setTruckType(rs.getString("TruckType"));
		t.setStatus(rs.getString("Status"));		
		t.MarkClean();
		return t;
		
	}
	@Override
	public String toString()
	{
		return getTruckName();
	}

}
