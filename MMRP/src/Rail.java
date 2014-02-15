import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Rail extends Vehicle {

	public static enum RailType 
	{
			Standard("Standard"),
			Other("OTHER");
			private String type;
			RailType(String s)
			{
				type=s;
			}
			@Override public String toString()
			{
				return type;
			}
			
	}
	private RailType type;
	public Rail()
	{
		setTravelType(TravelTypes.Rail);
		MarkNew();
	}	
	public Rail(int id)
	{
		setTravelType(TravelTypes.Rail);
		this.id=id;
	}	
	
	public void setRailName(String s)
	{
		if(name==null || !this.name.equals(s))
		{
			super.setVehicleName(s);
		}
		
	}
	public String getRailName()
	{
		return super.getVehicleName();
	}
	public void setRailType(RailType s)
	{
		if(type==null || !this.type.equals(s))
		{
			type = s;
			MarkDirty();
		}
	}
	public void setRailType(String s)
	{
		if(type==null || !this.type.toString().equals(s))
		{
			type = loadRailType(s);
			MarkDirty();
		}
	}
	public String getRailType()
	{
		return type.toString();
	}
	private RailType loadRailType(String s)
	{
		if(s.equals(RailType.Standard.toString()))
			return RailType.Standard;
		return RailType.Other;
			
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

	public static Rail Load(int id)
	{
		try
		{
			Connection c = getConnection();
			ResultSet rs = c.createStatement().executeQuery("Select * from Rail where RailID = " + id);
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
	public static ArrayList<Rail> LoadAll(String where)
	{
		ArrayList<Rail> returnList = new ArrayList<Rail>();
		try 
		{
			Connection c = getConnection();
			ResultSet rs = c.createStatement().executeQuery("Select * from Rail " +  where);
			while(rs.next())
				returnList.add(BuildFromDataRow(rs));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Rail BuildFromDataRow(ResultSet rs) throws SQLException
	{
		Rail r = new Rail(rs.getInt("RailID"));
		//b.setId();
		r.setRailName(rs.getString("RailName"));
		r.setCapacity(rs.getInt("Capacity"));
		r.setContractor(rs.getString("Contractor"));
		r.setLocation(rs.getDouble("Latitude"), rs.getDouble("Longitude"),rs.getString("LocationName"));
		r.setRailType(rs.getString("RailType"));
		r.setStatus(rs.getString("Status"));		
		r.MarkClean();
		return r;
		
	}
	@Override
	public String toString()
	{
		return getRailName();
	}



}
