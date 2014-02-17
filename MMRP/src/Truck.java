import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
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
		try
		{
			//toDo: set id on insert set update statement
			if(isNew())
			{
				executeCommand("Insert into Truck (TruckName,Contractor,Longitude,Latitude,LocationName,TruckType,Capacity,Status) Values ('"+
						getTruckName() + "','" + getContractor() + "','"+ this.getLongitude()+"','"+this.getLatitude() + "','" + this.getLocationName() + "','" + this.getTruckType()+ "','"+
						this.getCapacity()+"','"+this.getStatus()+"')");
				
				ArrayList<Map<String,Object>> temp =executeQuery("Select TruckID from Truck where TruckName = '" + this.getTruckName() + "' AND Contractor = '"+this.getContractor()+
						"' AND Longitude = '" + this.getLongitude() + "' AND Latitude = '" + this.getLatitude() + "' AND LocationName = '" + this.getLocationName() + 
						"' AND TruckType = '" + this.getTruckType() + "' AND Capacity = '" +this.getCapacity() + "' AND Status = '" + this.getStatus()+"'");
				if(temp.size()>0)
				{
					this.id = (Integer)temp.get(0).get("TruckID");
					MarkClean();
					MarkOld();
				}
			}
			else
			{
				if(isDirty())
				{
					executeCommand("Update Truck Set TruckName = '" + this.getTruckName() + "' , Contractor = '"+this.getContractor()+
						"' , Longitude = '" + this.getLongitude() + "' , Latitude = '" + this.getLatitude() + "' , LocationName = '" + this.getLocationName() + 
						"' , TruckType = '" + this.getTruckType() + "' , Capacity = '" +this.getCapacity() + "' , Status = '" + this.getStatus() + "' Where TruckID = " +this.id);
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
			executeCommand("Delete from Truck Where TruckID = " + this.id);
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}

	}

	public static Truck Load(int id)
	{
		try
		{
			ArrayList<Map<String,Object>> temp =executeQuery("Select * from Truck where TruckId = " + id);
			if(temp.size()>0)
				return BuildFromDataRow(temp.get(0));
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
			ArrayList<Map<String,Object>> temp =executeQuery("Select * from Truck " +where );
			for(int i = 0; i<temp.size();i++)
				returnList.add(BuildFromDataRow(temp.get(i)));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Truck BuildFromDataRow(Map<String,Object> data) throws SQLException
	{
		Truck t = new Truck((Integer)data.get("TruckID"));
		t.setTruckName((String)data.get("TruckName"));//data;//rs.getString("TruckName"));
		t.setCapacity((Integer)data.get("Capacity"));//rs.getInt("Capacity"));
		t.setContractor((String)data.get("Contractor"));//rs.getString("Contractor"));
		t.setLocation(Double.parseDouble(data.get("Latitude").toString()),Double.parseDouble(data.get("Longitude").toString()),(String)data.get("LocationName"));//,rs.getString("LocationName"));
		t.setTruckType((String)data.get("TruckType"));//rs.getString("TruckType"));
		t.setStatus((String)data.get("Status"));//rs.getString("Status"));		
		t.MarkClean();
		return t;
		
	}
	@Override
	public String toString()
	{
		return getTruckName();
	}

}
