import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Plane extends Vehicle {

	public static enum PlaneType 
	{
			Standard("Standard"),
			Other("OTHER");
			private String type;
			PlaneType(String s)
			{
				type=s;
			}
			@Override public String toString()
			{
				return type;
			}
			
	}
	private PlaneType type;
	public Plane()
	{
		setTravelType(TravelTypes.Plane);
		MarkNew();
	}	
	public Plane(int id)
	{
		setTravelType(TravelTypes.Plane);
		this.id=id;
	}	
	
	public void setPlaneName(String s)
	{
		if(name==null || !this.name.equals(s))
		{
			super.setVehicleName(s);
		}
		
	}
	public String getPlaneName()
	{
		return super.getVehicleName();
	}
	public void setPlaneType(PlaneType s)
	{
		if(type==null || !this.type.equals(s))
		{
			type = s;
			MarkDirty();
		}
	}
	public void setPlaneType(String s)
	{
		if(type==null || !this.type.toString().equals(s))
		{
			type = loadPlaneType(s);
			MarkDirty();
		}
	}
	public String getPlaneType()
	{
		return type.toString();
	}
	private PlaneType loadPlaneType(String s)
	{
		if(s.equals(PlaneType.Standard.toString()))
			return PlaneType.Standard;
		return PlaneType.Other;
			
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

	public static Plane Load(int id)
	{
		try
		{
			Connection c = getConnection();
			ResultSet rs = c.createStatement().executeQuery("Select * from Plane where PlaneID = " + id);
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
	public static ArrayList<Plane> LoadAll(String where)
	{
		ArrayList<Plane> returnList = new ArrayList<Plane>();
		try 
		{
			Connection c = getConnection();
			ResultSet rs = c.createStatement().executeQuery("Select * from Plane " +  where);
			while(rs.next())
				returnList.add(BuildFromDataRow(rs));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Plane BuildFromDataRow(ResultSet rs) throws SQLException
	{
		Plane p = new Plane(rs.getInt("PlaneID"));
		//b.setId();
		p.setPlaneName(rs.getString("PlaneName"));
		p.setCapacity(rs.getInt("Capacity"));
		p.setContractor(rs.getString("Contractor"));
		p.setLocation(rs.getDouble("Latitude"), rs.getDouble("Longitude"),rs.getString("LocationName"));
		p.setPlaneType(rs.getString("PlaneType"));
		p.setStatus(rs.getString("Status"));		
		p.MarkClean();
		return p;
		
	}
	@Override
	public String toString()
	{
		return getPlaneName();
	}


}
