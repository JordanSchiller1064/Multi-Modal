import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Bike extends Vehicle {
	//enum
	public static enum BikeType 
	{
			Standard("Standard"),
			Other("OTHER");
			private String type;
			BikeType(String s)
			{
				type=s;
			}
			@Override public String toString()
			{
				return type;
			}
			
	}
	private BikeType type;
	public Bike()
	{
		setTravelType(TravelTypes.Bike);
		MarkNew();
	}	
	public Bike(int id)
	{
		setTravelType(TravelTypes.Bike);
		this.id=id;
	}	
	
	public void setBikeName(String s)
	{
		if(name==null || !this.name.equals(s))
		{
			super.setVehicleName(s);
		}
		
	}
	public String getBikeName()
	{
		return super.getVehicleName();
	}
	public void setBikeType(BikeType s)
	{
		if(type==null || !this.type.equals(s))
		{
			type = s;
			MarkDirty();
		}
	}
	public void setBikeType(String s)
	{
		if(type==null || !this.type.toString().equals(s))
		{
			type = loadBikeType(s);
			MarkDirty();
		}
	}
	public String getBikeType()
	{
		return type.toString();
	}
	private BikeType loadBikeType(String s)
	{
		if(s.equals(BikeType.Standard.toString()))
			return BikeType.Standard;
		return BikeType.Other;
			
	}

	@Override
	public void Update() 
	{
		try
		{
			//toDo: set id on insert set update statement
			if(isNew())
			{
				executeCommand("Insert into Bike (BikeName,Contractor,Longitude,Latitude,LocationName,BikeType,Capacity,Status) Values ('"+
						getBikeName() + "','" + getContractor() + "','"+ this.getLongitude()+"','"+this.getLatitude() + "','" + this.getLocationName() + "','" + this.getBikeType()+ "','"+
						this.getCapacity()+"','"+this.getStatus()+"')");
				
				ArrayList<Map<String,Object>> temp =executeQuery("Select BikeID from Bike where BikeName = '" + this.getBikeName() + "' AND Contractor = '"+this.getContractor()+
						"' AND Longitude = '" + this.getLongitude() + "' AND Latitude = '" + this.getLatitude() + "' AND LocationName = '" + this.getLocationName() + 
						"' AND BikeType = '" + this.getBikeType() + "' AND Capacity = '" +this.getCapacity() + "' AND Status = '" + this.getStatus()+"'");
				if(temp.size()>0)
				{
					this.id = (Integer)temp.get(0).get("BikeID");
					MarkClean();
					MarkOld();
				}
			}
			else
			{
				if(isDirty())
				{
					executeCommand("Update Bike Set BikeName = '" + this.getBikeName() + "' , Contractor = '"+this.getContractor()+
						"' , Longitude = '" + this.getLongitude() + "' , Latitude = '" + this.getLatitude() + "' , LocationName = '" + this.getLocationName() + 
						"' , BikeType = '" + this.getBikeType() + "' , Capacity = '" +this.getCapacity() + "' , Status = '" + this.getStatus() + "' Where BikeID = " +this.id);
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
			executeCommand("Delete from Bike Where BikeID = " + this.id);
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}

	}

	public static Bike Load(int id)
	{
		try
		{
			ArrayList<Map<String,Object>> temp =executeQuery("Select * from Bike where BikeID = " + id);
			if(temp.size()>0)
			{
				Bike b = BuildFromDataRow(temp.get(0));
				b.getSchedule();
				return b;
			}
			return null;
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
 		return null;
	}
	public static ArrayList<Bike> LoadAll(String where)
	{
		ArrayList<Bike> returnList = new ArrayList<Bike>();
		try 
		{
			
			ArrayList<Map<String,Object>> temp =executeQuery("Select * from Bike " +  where);
			for(int i = 0; i<temp.size();i++)
			{
				Bike  b = BuildFromDataRow(temp.get(i));
				b.getSchedule();
				returnList.add(b);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Bike BuildFromDataRow(Map<String,Object> data) throws SQLException
	{
		Bike b = new Bike((Integer)data.get("BikeID"));//rs.getInt("BikeID"));
		b.setBikeName((String)data.get("BikeName"));//rs.getString("BikeName"));
		b.setCapacity((Integer)data.get("Capacity"));//rs.getInt("Capacity"));
		b.setContractor((String)data.get("Contractor"));//rs.getString("Contractor"));
		b.setLocation(Double.parseDouble(data.get("Latitude").toString()),Double.parseDouble(data.get("Longitude").toString()),(String)data.get("LocationName"));
		b.setBikeType((String)data.get("BikeType"));//rs.getString("BikeType"));
		b.setStatus((String)data.get("Status"));//rs.getString("Status"));		
		b.MarkClean();
		return b;
		
	}
	@Override
	public String toString()
	{
		return getBikeName();
	}


}
