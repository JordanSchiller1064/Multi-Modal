package core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

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
		try
		{
			//toDo: set id on insert set update statement
			if(isNew())
			{
				executeCommand("Insert into rail (RailName,Contractor,Longitude,Latitude,LocationName,RailType,Capacity,Status) Values ('"+
						getRailName() + "','" + getContractor() + "','"+ this.getLongitude()+"','"+this.getLatitude() + "','" + this.getLocationName() + "','" + this.getRailType()+ "','"+
						this.getCapacity()+"','"+this.getStatus()+"')");
				
				ArrayList<Map<String,Object>> temp =executeQuery("Select RailID from rai where RailName = '" + this.getRailName() + "' AND Contractor = '"+this.getContractor()+
						"' AND Longitude = '" + this.getLongitude() + "' AND Latitude = '" + this.getLatitude() + "' AND LocationName = '" + this.getLocationName() + 
						"' AND RailType = '" + this.getRailType() + "' AND Capacity = '" +this.getCapacity() + "' AND Status = '" + this.getStatus()+"'");
				if(temp.size()>0)
				{
					this.id = (Integer)temp.get(0).get("RailID");
					MarkClean();
					MarkOld();
				}
			}
			else
			{
				if(isDirty())
				{
					executeCommand("Update Rail Set RailName = '" + this.getRailName() + "' , Contractor = '"+this.getContractor()+
						"' , Longitude = '" + this.getLongitude() + "' , Latitude = '" + this.getLatitude() + "' , LocationName = '" + this.getLocationName() + 
						"' , RailType = '" + this.getRailType() + "' , Capacity = '" +this.getCapacity() + "' , Status = '" + this.getStatus() + "' Where RailID = " +this.id);
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
			executeCommand("Delete from Rail Where RailID = " + this.id);
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}

	}

	public static Rail Load(int id)
	{
		try
		{
			ArrayList<Map<String,Object>> temp = executeQuery("Select * from Rail where RailID = " + id);
			if(temp.size()>0)
			{
				Rail r = BuildFromDataRow(temp.get(0));
				r.getSchedule();
				return r;
			}
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
			ArrayList<Map<String,Object>> temp = executeQuery("Select * from Rail " +  where);
			for(int i = 0; i<temp.size();i++)
			{
				Rail r = BuildFromDataRow(temp.get(i));
				r.getSchedule();
				returnList.add(r);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Rail BuildFromDataRow(Map<String,Object> data) throws SQLException
	{
		Rail r = new Rail((Integer)data.get("RailID"));
		//b.setId();
		r.setRailName((String)data.get("RailName"));
		r.setCapacity((Integer)data.get("Capacity"));
		r.setContractor((String)data.get("Contractor"));
		r.setLocation(Double.parseDouble(data.get("Latitude").toString()), Double.parseDouble(data.get("Longitude").toString()),(String)data.get("LocationName"));
		r.setRailType((String)data.get("RailType"));
		r.setStatus((String)data.get("Status"));		
		r.MarkClean();
		return r;
		
	}
	@Override
	public String toString()
	{
		return getRailName();
	}



}
