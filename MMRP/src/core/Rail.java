

//======================================================================================//
//							Software Engineering MMRP Project							//
//									Dr. Sam Thangiah									//
//					Dan Miller, Zach Petrusch, Chris Solomon, and Jordan Schiller		//
//======================================================================================//
//										Rail Class										//
//======================================================================================//
// Purpose:																				//
//																						//
//======================================================================================//
package core;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Rail extends Vehicle {

	//This is the enumeration of the different types of Rails available
	public static enum RailType 
	{
			Standard("Standard"),
			Other("OTHER");
			private String type;
			RailType(String s)
			{
				type=s;
			}//End fo the RailType constructor
			@Override public String toString()
			{
				return type;
			}//End of the overridden toString()
			
	}//End of the RailType enumeration
	
	private RailType type;											//The type of this Rail
	
	//This is the default Rail constructor
	public Rail()
	{
		setTravelType(TravelTypes.Rail);							//Set the TravelType to Rail
		MarkNew();													//Mark this Rail as new
	}//End of default Rail constructor
	
	//This is the argumented Rail constructor that takes an id
	public Rail(int id)
	{
		setTravelType(TravelTypes.Rail);							//Set the TravelType to Rail
		this.id=id;													//Set the id
																	//SHOULD WE ALSO SET THIS TO NEW OR DIRTY??
	}//End of the argumented constructor Rail(int id)
	
	//This function sets the Rail's name
	public void setRailName(String s)
	{
		if(name==null || !this.name.equals(s))
		{
			super.setVehicleName(s);								//Set the Rail's name (as a vehicle)
		}//End of valid name if
		
	}//End of setRailName(String s)
	
	//This function returns the Rail's name
	public String getRailName()
	{
		return super.getVehicleName();								//Return the Rail's name as a vehicle
	}//End of getRailName()
	
	//This function sets the Rail's type
	public void setRailType(RailType s)
	{
		if(type==null || !this.type.equals(s))
		{
			type = s;												//Set the Rail's type
			MarkDirty();											//Mark the Rail as dirty
		}//End of valid type if
	}//End of setRailType(RailType s)
	
	//This function sets the Rail's type using a String
	public void setRailType(String s)
	{
		if(type==null || !this.type.toString().equals(s))
		{
			type = loadRailType(s);									//Set the Rail's type
			MarkDirty();											//Mark the Rail as dirty
		}//End of valid type if
	}//End of setRailType(String s)
	
	//This function returns the RailType
	public String getRailType()
	{
		return type.toString();										//Return the RailType
	}//End of getRailType()
	
	//This function will convert a string to a valid RailType
	private RailType loadRailType(String s)
	{
		if(s.equals(RailType.Standard.toString()))
			return RailType.Standard;								//Return Standard
		return RailType.Other;										//Return Other
			
	}//End of loadRailType(String s)
	
	//This function overrides the parent's Update function and will handle changes made to the Rail object in the database
	@Override
	public void Update() 
	{
		try
		{
			//toDo: set id on insert set update statement
			if(isNew())
			{
				//If the Rail is new insert it into the database by executing the following
				executeCommand("Insert into rail (RailName,Contractor,Longitude,Latitude,LocationName,RailType,Capacity,Status) Values ('"+
						getRailName() + "','" + getContractor() + "','"+ this.getLongitude()+"','"+this.getLatitude() + "','" + this.getLocationName() + "','" + this.getRailType()+ "','"+
						this.getCapacity()+"','"+this.getStatus()+"')");
				//Grab this Rail from the database
				ArrayList<Map<String,Object>> temp =executeQuery("Select RailID from rai where RailName = '" + this.getRailName() + "' AND Contractor = '"+this.getContractor()+
						"' AND Longitude = '" + this.getLongitude() + "' AND Latitude = '" + this.getLatitude() + "' AND LocationName = '" + this.getLocationName() + 
						"' AND RailType = '" + this.getRailType() + "' AND Capacity = '" +this.getCapacity() + "' AND Status = '" + this.getStatus()+"'");
				//If this rail exists on the database mark it as old and clean
				if(temp.size()>0)
				{
					this.id = (Integer)temp.get(0).get("RailID");					//Set this Rail id to the id in the database
					MarkClean();													//Mark this Rail as clean
					MarkOld();														//Mark this Rail as old
				}//End of found something if
			}//End of isNew if
			else
			{
				if(isDirty())
				{
					//If the Rail is not new, but is dirty then it needs to be updated by the following SQL command
					executeCommand("Update Rail Set RailName = '" + this.getRailName() + "' , Contractor = '"+this.getContractor()+
						"' , Longitude = '" + this.getLongitude() + "' , Latitude = '" + this.getLatitude() + "' , LocationName = '" + this.getLocationName() + 
						"' , RailType = '" + this.getRailType() + "' , Capacity = '" +this.getCapacity() + "' , Status = '" + this.getStatus() + "' Where RailID = " +this.id);
					MarkClean();													//Mark the Rail as clean
				}//End of isDirty else
			}//End of isOld else
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);										//Print out the error
		}//End of catch block
		
	}//End of the overridden Update()

	//This is the overridden Delete function of the parent class and will remove this Rail from the database
	@Override
	public  void Delete() 
	{
		try
		{
			executeCommand("Delete from Rail Where RailID = " + this.id);			//Delete the Rail
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);										//Print out the error
		}//End of catch block

	}//End of overridden Delete()

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
	//This function builds objects from returned data from SQL queries against our database
		public static Rail BuildFromDataRow(Map<String,Object> data) throws SQLException
		{
			//This code grabs each element that will be found in the database on the Rail table and set the appropriate values for a new Rail
			Rail r = new Rail((Integer)data.get("RailID"));
			//b.setId();
			r.setRailName((String)data.get("RailName"));
			r.setCapacity((Integer)data.get("Capacity"));
			r.setContractor((String)data.get("Contractor"));
			r.setLocation(Double.parseDouble(data.get("Latitude").toString()), Double.parseDouble(data.get("Longitude").toString()),(String)data.get("LocationName"));
			r.setRailType((String)data.get("RailType"));
			r.setStatus((String)data.get("Status"));		
			r.MarkClean();															//Mark the Rail as clean
			return r;
			
		}//End of BuildFromDataRow(Map<String,Object> data)
	@Override
	public String toString()
	{
		return getRailName();
	}



}
