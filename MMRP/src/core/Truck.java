//======================================================================================//
//							Software Engineering MMRP Project							//
//									Dr. Sam Thangiah									//
//					Dan Miller, Zach Petrusch, Chris Solomon, and Jordan Schiller		//
//======================================================================================//
//										Truck Class										//
//======================================================================================//
// Purpose:																				//
//																						//
//======================================================================================//

package core;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
public class Truck extends Vehicle {

	//This is the enumeration of the different available Truck types
	public static enum TruckTypes 
	{
			Semi("SEMI"),
			Other("OTHER");
			private String type;
			TruckTypes(String s)
			{
				type=s;
			}//End of the TruckTypes enumeration constructor
			@Override public String toString()
			{
				return type;
			}//End of the overridden toString()
			
	}//End of TruckTypes enumeration
	
	private TruckTypes type;													//The type of this Truck
	
	//The default Truck constructor
	public Truck()
	{
		setTravelType(TravelTypes.Truck);										//Set the TravelType to Truck
		MarkNew();																//Mark the Truck as new
	}//End of the default Truck constructor
	
	//This is the argumented Truck constructor
	public Truck(int id)
	{
		setTravelType(TravelTypes.Truck);										//Set the TravelType to Truck
		this.id=id;																//Set the Truck's id
																				//SHOULD WE ALSO SET THE TRUCK TO NEW OR DIRTY??
	}//End of the argumented Truck constructor
	
	//This function sets the truck's name
	public void setTruckName(String s)
	{
			super.setVehicleName(s);											//Set the Truck's name (as a Vehicle)
	}//End of settruckName(String s)
	
	//This function returns the Truck's name
	public String getTruckName()
	{
		return super.getVehicleName();											//Returns the Truck's name from the Vehicle base
	}//End of getTruckName()
	
	//This function sets the truck type
	public void setTruckType(TruckTypes s)
	{
		if(this.type==null || !this.type.equals(s))
		{
			type = s;															//Set the truck type
			MarkDirty();														//Make this Truck as dirty
		}//End of the valid type if
	}//End of setTruckType(TruckTypes s)
	
	//This function sets the truck type using a String
	public void setTruckType(String s)
	{
		if(this.type==null || !this.type.toString().equals(s))
		{
			type = loadTruckType(s);											//Set the truck type
			MarkDirty();														//Make this Truck dirty
		}//End of the valid if
	}//End of the setTruckType(String s)
	
	//This function returns the truck type
	public String getTruckType()
	{
		return type.toString();													//Return the truck type
	}//End of getTruckType()
	
	//This function loads a TruckType using a String
	private TruckTypes loadTruckType(String s)
	{
		if(s.equals(TruckTypes.Semi.toString()))
			return TruckTypes.Semi;												//Return Semi
		return TruckTypes.Other;												//Return Other
			
	}//End of loadTruckType(String s)
	
	//This function overrides the parent's Update function and will handle changes made to the Truck object in the database
	@Override
	public void Update() 
	{
		try
		{
			//toDo: set id on insert set update statement
			if(isNew())
			{
				//If the Truck is new insert it into the database by executing the following
				executeCommand("Insert into Truck (TruckName,Contractor,Longitude,Latitude,LocationName,TruckType,Capacity,Status) Values ('"+
						getTruckName() + "','" + getContractor() + "','"+ this.getLongitude()+"','"+this.getLatitude() + "','" + this.getLocationName() + "','" + this.getTruckType()+ "','"+
						this.getCapacity()+"','"+this.getStatus()+"')");
				//Grab this Truck from the database
				ArrayList<Map<String,Object>> temp =executeQuery("Select TruckID from Truck where TruckName = '" + this.getTruckName() + "' AND Contractor = '"+this.getContractor()+
						"' AND Longitude = '" + this.getLongitude() + "' AND Latitude = '" + this.getLatitude() + "' AND LocationName = '" + this.getLocationName() + 
						"' AND TruckType = '" + this.getTruckType() + "' AND Capacity = '" +this.getCapacity() + "' AND Status = '" + this.getStatus()+"'");
				//If this Truck exists on the database mark it as old and clean
				if(temp.size()>0)
				{
					this.id = (Integer)temp.get(0).get("TruckID");									//Set the Truck id
					MarkClean();																	//Mark the Truck as clean
					MarkOld();																		//Mark the Truck as old
				}//End of found something if
			}//End of isNew if
			else
			{
				if(isDirty())
				{
					//If the Truck is not new, but is dirty then it needs to be updated by the following SQL command
					executeCommand("Update Truck Set TruckName = '" + this.getTruckName() + "' , Contractor = '"+this.getContractor()+
						"' , Longitude = '" + this.getLongitude() + "' , Latitude = '" + this.getLatitude() + "' , LocationName = '" + this.getLocationName() + 
						"' , TruckType = '" + this.getTruckType() + "' , Capacity = '" +this.getCapacity() + "' , Status = '" + this.getStatus() + "' Where TruckID = " +this.id);
					MarkClean();																	//Mark the Truck as clean
				}//End of isDirty if
			}//End of isOld else
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);														//Print the error
		}//End of catch block
		
	}//End of overridden Update()

	//This is the overridden Delete function of the parent class and will remove this Truck from the database
	@Override
	public  void Delete() 
	{
		try
		{
			executeCommand("Delete from Truck Where TruckID = " + this.id);							//Delete this Truck
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);														//Print the error
		}//End of catch block

	}//End of the overridden Delete()


	public static Truck Load(int id)
	{
		try
		{
			ArrayList<Map<String,Object>> temp =executeQuery("Select * from Truck where TruckId = " + id);
			if(temp.size()>0)
			{
				Truck t = BuildFromDataRow(temp.get(0));
				t.getSchedule();
				return t;
			}
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
			{
				Truck t = BuildFromDataRow(temp.get(i));
				t.getSchedule();
				returnList.add(t);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	//This function builds objects from returned data from SQL queries against our database
		public static Truck BuildFromDataRow(Map<String,Object> data) throws SQLException
		{
			//This code grabs each element that will be found in the database on the Truck table and set the appropriate values for a new Truck
			Truck t = new Truck((Integer)data.get("TruckID"));
			t.setTruckName((String)data.get("TruckName"));//data;//rs.getString("TruckName"));
			t.setCapacity((Integer)data.get("Capacity"));//rs.getInt("Capacity"));
			t.setContractor((String)data.get("Contractor"));//rs.getString("Contractor"));
			t.setLocation(Double.parseDouble(data.get("Latitude").toString()),Double.parseDouble(data.get("Longitude").toString()),(String)data.get("LocationName"));//,rs.getString("LocationName"));
			t.setTruckType((String)data.get("TruckType"));//rs.getString("TruckType"));
			t.setStatus((String)data.get("Status"));//rs.getString("Status"));		
			t.MarkClean();																//Mark the Truck as clean
			return t;
			
		}//End of BuildFromDataRow(Map<String,Object> data)
		
		//This function overrides the toString function and returns the name of the Truck
		@Override
		public String toString()
		{
			return getTruckName();														//Return the name of the Truck
		}//End of overridden toString()
}
