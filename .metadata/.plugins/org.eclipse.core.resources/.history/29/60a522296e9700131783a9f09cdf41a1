import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;




public class Cargo extends Vehicle {

	public static enum CargoType 
	{
			Standard("Standard"),
			Other("OTHER");
			private String type;
			CargoType(String s)
			{
				type=s;
			}
			@Override public String toString()
			{
				return type;
			}
			
	}
	private CargoType type;
	private int numOfContainers;
	public Cargo()
	{
		setTravelType(TravelTypes.Cargo);
		MarkNew();
	}	
	public Cargo(int id)
	{
		setTravelType(TravelTypes.Cargo);
		this.id=id;
	}	
	public void setNumOfContainers(int c)
	{
		if(this.numOfContainers!=c)
		{
			numOfContainers=c;
			MarkDirty();
		}
	}
	public int getNumOfContainers()
	{
		return numOfContainers;
	}
	public void setCargoName(String s)
	{
		if(name==null || !this.name.equals(s))
		{
			super.setVehicleName(s);
		}
		
	}
	public String getCargoName()
	{
		return super.getVehicleName();
	}
	public void setCargoType(CargoType s)
	{
		if(type==null || !this.type.equals(s))
		{
			type = s;
			MarkDirty();
		}
	}
	public void setCargoType(String s)
	{
		if(type==null || !this.type.toString().equals(s))
		{
			type = loadCargoType(s);
			MarkDirty();
		}
	}
	public String getCargoType()
	{
		return type.toString();
	}
	private CargoType loadCargoType(String s)
	{
		if(s.equals(CargoType.Standard.toString()))
			return CargoType.Standard;
		return CargoType.Other;
			
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

	public static Cargo Load(int id)
	{
		try
		{
			Connection c = getConnection();
			ResultSet rs = c.createStatement().executeQuery("Select * from ship where ShipID = " + id);
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
	public static ArrayList<Cargo> LoadAll(String where)
	{
		ArrayList<Cargo> returnList = new ArrayList<Cargo>();
		try 
		{
			Connection c = getConnection();
			ResultSet rs = c.createStatement().executeQuery("Select * from ship " +  where);
			while(rs.next())
				returnList.add(BuildFromDataRow(rs));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Cargo BuildFromDataRow(ResultSet rs) throws SQLException
	{
		Cargo c = new Cargo(rs.getInt("ShipID"));
		//b.setId();
		c.setCargoName(rs.getString("ShipName"));
		c.setCapacity(rs.getInt("Capacity"));
		c.setContractor(rs.getString("Contractor"));
		c.setLocation(rs.getDouble("Latitude"), rs.getDouble("Longitude"),rs.getString("LocationName"));
		c.setCargoType(rs.getString("ShipType"));
		c.setStatus(rs.getString("Status"));
		c.setNumOfContainers(rs.getInt("NoOfContainers"));
		c.MarkClean();
		return c;
		
	}
	@Override
	public String toString()
	{
		return getCargoName();
	}



}
