import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;




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
		try
		{
		if(isNew())
		{
			executeCommand("Insert into Ship (ShipName,Contractor,Longitude,Latitude,LocationName,ShipType,Capacity,Status,NoOfContainers) Values ('"+
					getCargoName() + "','" + getContractor() + "','"+ this.getLongitude()+"','"+this.getLatitude() + "','" + this.getLocationName() + "','" + this.getCargoType()+ "','"+
					this.getCapacity()+"','"+this.getStatus()+"','"+this.getNumOfContainers()+"')");
			
			ArrayList<Map<String,Object>> temp =executeQuery("Select ShipID from Ship where ShipName = '" + this.getCargoName() + "' AND Contractor = '"+this.getContractor()+
					"' AND Longitude = '" + this.getLongitude() + "' AND Latitude = '" + this.getLatitude() + "' AND LocationName = '" + this.getLocationName() + 
					"' AND ShipType = '" + this.getCargoType() + "' AND Capacity = '" +this.getCapacity() + "' AND Status = '" + this.getStatus()+ "' AND NoOfContainers = '" + this.getNumOfContainers()+"'");
			if(temp.size()>0)
			{
				this.id = (Integer)temp.get(0).get("ShipID");
				
			}
			MarkClean();
			MarkOld();
		}
		else
		{
			if(isDirty())
			{
				executeCommand("Update Ship Set ShipName = '" + this.getCargoName() + "' , Contractor = '"+this.getContractor()+
					"' , Longitude = '" + this.getLongitude() + "' , Latitude = '" + this.getLatitude() + "' , LocationName = '" + this.getLocationName() + 
					"' , ShipType = '" + this.getCargoType() + "' , Capacity = '" +this.getCapacity() + "' , Status = '" + this.getStatus() +
					"' , NoOfContainers ='" + this.getNumOfContainers()+"' Where ShipID = " +this.id);
				MarkClean();
			}
		}
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		
	}

	@Override
	public  void Delete() 
	{
		try
		{
			executeCommand("Delete From ship where ShipID = " + id);
		}
		catch(Exception ex)
		{
			System.out.println("Error "+ ex);
		}

	}

	public static Cargo Load(int id)
	{
		try
		{
			ArrayList<Map<String,Object>> temp = executeQuery("Select * from ship where ShipID = " + id);
			if(temp.size()>0)
			{
				Cargo c = BuildFromDataRow(temp.get(0));
				c.getSchedule();
				return c;
			}
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
			ArrayList<Map<String,Object>> temp = executeQuery("Select * from ship " +  where);
			for(int i = 0 ; i<temp.size();i++)
			{
				Cargo c = BuildFromDataRow(temp.get(i));
				c.getSchedule();
				returnList.add(c);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Cargo BuildFromDataRow(Map<String,Object> data) throws SQLException
	{
		Cargo c = new Cargo((Integer)data.get("ShipID"));//rs.getInt("ShipID"));
		//b.setId();
		c.setCargoName((String)data.get("ShipName"));//rs.getString("ShipName"));
		c.setCapacity((Integer)data.get("Capacity"));//rs.getInt("Capacity"));
		c.setContractor((String)data.get("Contractor"));//rs.getString("Contractor"));
		c.setLocation(Double.parseDouble(data.get("Latitude").toString()), Double.parseDouble(data.get("Longitude").toString()),(String)data.get("LocationName"));//rs.getString("LocationName"));
		c.setCargoType((String)data.get("ShipType"));//rs.getString("ShipType"));
		c.setStatus((String)data.get("Status"));//rs.getString("Status"));
		c.setNumOfContainers((Integer)data.get("NoOfContainer"));//rs.getInt("NoOfContainers"));
		c.MarkClean();
		return c;
		
	}
	@Override
	public String toString()
	{
		return getCargoName();
	}



}
