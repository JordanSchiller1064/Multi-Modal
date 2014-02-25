

//======================================================================================//
//							Software Engineering MMRP Project							//
//									Dr. Sam Thangiah									//
//					Dan Miller, Zach Petrusch, Chris Solomon, and Jordan Schiller		//
//======================================================================================//
//										Plane Class										//
//======================================================================================//
// Purpose:																				//
//																						//
//======================================================================================//
package core;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Plane extends Vehicle {

	//This is the enumeration of the different types of Planes
	public static enum PlaneType 
	{
			Standard("Standard"),
			Other("OTHER");
			private String type;
			PlaneType(String s)
			{
				type=s;
			}//End of PlaneType enumeration constructor
			@Override public String toString()
			{
				return type;
			}//End of toString() override
			
	}//End of PlaneType enumeration
	
	private PlaneType type;													//The type of this Plane
	
	//This is the plane default contructor
	public Plane()
	{
		setTravelType(TravelTypes.Plane);									//Set the TravelType to Plane
		MarkNew();															//Mark the Plane as new
	}//End of Plane default constructor
	
	//This is the Plane arugmented constructor that takes an id value
	public Plane(int id)
	{
		setTravelType(TravelTypes.Plane);									//Set the default PlaneType value
		this.id=id;															//Set the Plane's id
																			//SHOULD WE MARK THIS PLANE AS DIRTY OR NEW??
	}//End of the Plane(int id) constructor
	
	//This function will set the plane's name
	public void setPlaneName(String s)
	{
		if(name==null || !this.name.equals(s))
		{
			super.setVehicleName(s);										//Set the plane's name (as a Vehicle)
		}//End of valid name if
		
	}//End of setPlaneName(String s)
	
	//This function returns the name of the Plane
	public String getPlaneName()
	{
		return super.getVehicleName();										//Return the name of the Plane (from Vehicle)
	}//End of getPlaneName()
	
	//This function sets the Plane's type
	public void setPlaneType(PlaneType s)
	{
		if(type==null || !this.type.equals(s))								
		{
			type = s;														//Set the PlaneType
			MarkDirty();													//Mark this plane as dirty
		}//End of valid type if
	}//End of setPlaneType(PlaneType s)
	
	//This function sets the Plane's name using a String
	public void setPlaneType(String s)
	{
		if(type==null || !this.type.toString().equals(s))
		{
			type = loadPlaneType(s);										//Set the PlaneType
			MarkDirty();													//Mark this plane as dirty
		}//End of valid name if
	}//End of setPlaneName(String s)
	
	//This function returns the plane's type
	public String getPlaneType()
	{
		return type.toString();												//Return the PlaneType as a String
	}//End of getPlaneType()
	
	//This function converts a String to the PlaneType
	private PlaneType loadPlaneType(String s)
	{
		if(s.equals(PlaneType.Standard.toString()))
			return PlaneType.Standard;										//Return Standard
		return PlaneType.Other;												//Return Other
			
	}//End of loadPlaneType(String s)
	
	//This function overrides the parent's Update function and will handle changes made to the Plane object in the database
	@Override
	public void Update() 
	{
		try
		{
			//toDo: set id on insert set update statement
			if(isNew())
			{
				//If the plane is new insert it into the database by executing the following
				executeCommand("Insert into Plane (PlaneName,Contractor,Longitude,Latitude,LocationName,PlaneType,Capacity,Status) Values ('"+
						getPlaneName() + "','" + getContractor() + "','"+ this.getLongitude()+"','"+this.getLatitude() + "','" + this.getLocationName() + "','" + this.getPlaneType()+ "','"+
						this.getCapacity()+"','"+this.getStatus()+"')");
				//Grab this plane from the database
				ArrayList<Map<String,Object>> temp =executeQuery("Select PlaneID from Plane where PlaneName = '" + this.getPlaneName() + "' AND Contractor = '"+this.getContractor()+
						"' AND Longitude = '" + this.getLongitude() + "' AND Latitude = '" + this.getLatitude() + "' AND LocationName = '" + this.getLocationName() + 
						"' AND PlaneType = '" + this.getPlaneType() + "' AND Capacity = '" +this.getCapacity() + "' AND Status = '" + this.getStatus()+"'");
				//If this plane exists on the database mark it as old and clean
				if(temp.size()>0)
				{
					this.id = (Integer)temp.get(0).get("PlaneID");								//Set this Plane's id to the database id
					MarkClean();																//Mark the Plane as clean
					MarkOld();																	//Mark the Plane as old
				}//End of found something if
			}//End of isNew if
			else
			{
				if(isDirty())
				{
					//If the Plane is not new, but is dirty then it needs to be updated by the following SQL command
					executeCommand("Update Plane Set PlaneName = '" + this.getPlaneName() + "' , Contractor = '"+this.getContractor()+
						"' , Longitude = '" + this.getLongitude() + "' , Latitude = '" + this.getLatitude() + "' , LocationName = '" + this.getLocationName() + 
						"' , PlaneType = '" + this.getPlaneType() + "' , Capacity = '" +this.getCapacity() + "' , Status = '" + this.getStatus() + "' Where Plane = " +this.id);
					MarkClean();
				}//End of isDirty if
			}//End of isOld else
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);													//Print out the error
		}//End of catch block
		
	}//End of overridden Update

	//This is the overridden Delete function of the parent class and will remove this Plane from the database
	@Override
	public  void Delete() 
	{
		try
		{
			executeCommand("Delete from Plane Where PlaneID = " + this.id);					//Delete the plane
		}//End of the try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);												//Print out the error
		}//End of the catch block

	}//End of the overridden Delete function
	public static Plane Load(int id)
	{
		try
		{
			ArrayList<Map<String,Object>> temp= executeQuery("Select * from Plane where PlaneID = " + id);
			if(temp.size()>0)
			{
				
				Plane p = BuildFromDataRow(temp.get(0));
				p.getSchedule();
				return p;
			}
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
			ArrayList<Map<String,Object>> temp = executeQuery("Select * from Plane " +  where);
			for(int i = 0; i<temp.size();i++)
			{
				Plane p = BuildFromDataRow(temp.get(i));
				p.getSchedule();
				returnList.add(p);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	//This function builds objects from returned data from SQL queries against our database
	public static Plane BuildFromDataRow(Map<String,Object> data) throws SQLException
	{
		//This code grabs each element that will be found in the database on the Plane table and set the appropriate values for a new Plane
		Plane p = new Plane((Integer)data.get("PlaneID"));//rs.getInt("BikeID"));
		p.setPlaneName((String)data.get("PlaneName"));//rs.getString("BikeName"));
		p.setCapacity((Integer)data.get("Capacity"));//rs.getInt("Capacity"));
		p.setContractor((String)data.get("Contractor"));//rs.getString("Contractor"));
		p.setLocation(Double.parseDouble(data.get("Latitude").toString()),Double.parseDouble(data.get("Longitude").toString()),(String)data.get("LocationName"));
		p.setPlaneType((String)data.get("PlaneType"));//rs.getString("BikeType"));
		p.setStatus((String)data.get("Status"));//rs.getString("Status"));		
		p.MarkClean();
		return p;
		
	}//End of BuildDataFromRow(Map<String,Object> data)
	
	//This function overrides the toString function and returns the name of the Plane
	@Override
	public String toString()
	{
		return getPlaneName();													//Return the plane name
	}//End of overridden toString()

}
