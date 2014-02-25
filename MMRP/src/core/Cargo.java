
//======================================================================================//
//							Software Engineering MMRP Project							//
//									Dr. Sam Thangiah									//
//					Dan Miller, Zach Petrusch, Chris Solomon, and Jordan Schiller		//
//======================================================================================//
//										Cargo Class										//
//======================================================================================//
// Purpose:																				//
//																						//
//======================================================================================//
package core;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Cargo extends Vehicle {

	//This is an enumeration of the different types of Cargo that can be shipped
	public static enum CargoType 
	{
			Standard("Standard"),
			Other("OTHER");
			private String type;
			//This is the constructor for new types of Cargo
			CargoType(String s)
			{
				type=s;
			}//End of CargoType constructor
			//This is the overridden toString function
			@Override public String toString()
			{
				return type;
			}//End of toString()
			
	}//End of the CargoType enumeration
	
	private CargoType type;												//The type of the Cargo
	private int numOfContainers;										//The number of containers in the Cargo
	
	//This is the default Cargo constructor
	public Cargo()
	{
		setTravelType(TravelTypes.Cargo);								//Set the type to the default Cargo type
		MarkNew();														//Mark this cargo as new
	}//End of Cargo()
	
	//This function will create a new Cargo object with the given id
	public Cargo(int id)
	{
		setTravelType(TravelTypes.Cargo);								//Set the type of the Cargo
		this.id=id;														//Set the id of the Cargo to the given value
																		//SHOULD THIS BE MARKED AS NEW OR DIRTY AS WELL?
	}//End of arguemented Cargo constructor
	
	//This function sets the number of containers
	public void setNumOfContainers(int c)
	{
		if(this.numOfContainers!=c)
		{
			numOfContainers=c;											//Set the number of containers
			MarkDirty();												//Mark the Cargo as dirty
		}//End of update numOfContainers if
	}//End of setNumOfContainers(int c)
	
	//This function returns the number of containers
	public int getNumOfContainers()
	{
		return numOfContainers;											//Return the number of containers
	}//End of getNumOfContainers()
	
	//This function sets the name of the Cargo
	public void setCargoName(String s)
	{
		if(name==null || !this.name.equals(s))
		{
			super.setVehicleName(s);									//Set the name of the Cargo (as a Vehicle)
		}//End of valid name if
		
	}//End of setCargoName(String s)
	
	//This function returns the name of the Cargo
	public String getCargoName()
	{
		return super.getVehicleName();									//Return the name (from the Vehicle parent)
	}//End of getCargoName()
	
	//This function sets the CargoType
	public void setCargoType(CargoType s)
	{
		if(type==null || !this.type.equals(s))
		{
			type = s;													//Set the CargoType
			MarkDirty();												//Mark the cargo as dirty
		}//End of valid CargoType if
	}//End of setCargoType(CargoType s)
	
	//This function sets the CargoType based on a string
	public void setCargoType(String s)
	{
		if(type==null || !this.type.toString().equals(s))
		{
			type = loadCargoType(s);									//Set the CargoType
			MarkDirty();												//Mark the Cargo as dirty
		}//End of valid Cargo Type string if
	}//End of setCargoType(String s)
	
	//This function returns the CargoType
	public String getCargoType()
	{
		return type.toString();											//Return the CargoType
	}//End of getCargoType()
	
	//This function will convert a string to a valid CargoType
	private CargoType loadCargoType(String s)
	{
		if(s.equals(CargoType.Standard.toString()))
			return CargoType.Standard;									//Return Standard
		return CargoType.Other;											//Return Other
			
	}//End of loadCargoType(String s)
	
	//This function overrides the parent's Update function and will handle changes made to the Cargo object in the database
	@Override
	public void Update() 
	{
		try
		{
		if(isNew())
		{
			//If the cargo is new insert it into the database by executing the following
			executeCommand("Insert into Ship (ShipName,Contractor,Longitude,Latitude,LocationName,ShipType,Capacity,Status,NoOfContainers) Values ('"+
					getCargoName() + "','" + getContractor() + "','"+ this.getLongitude()+"','"+this.getLatitude() + "','" + this.getLocationName() + "','" + this.getCargoType()+ "','"+
					this.getCapacity()+"','"+this.getStatus()+"','"+this.getNumOfContainers()+"')");
			//Grab this cargo from the database
			ArrayList<Map<String,Object>> temp =executeQuery("Select ShipID from Ship where ShipName = '" + this.getCargoName() + "' AND Contractor = '"+this.getContractor()+
					"' AND Longitude = '" + this.getLongitude() + "' AND Latitude = '" + this.getLatitude() + "' AND LocationName = '" + this.getLocationName() + 
					"' AND ShipType = '" + this.getCargoType() + "' AND Capacity = '" +this.getCapacity() + "' AND Status = '" + this.getStatus()+ "' AND NoOfContainers = '" + this.getNumOfContainers()+"'");
			//If this cargo exists on the database mark it as old and clean
			if(temp.size()>0)
			{
				this.id = (Integer)temp.get(0).get("ShipID");					//Set this Cargo's id
				
			}
			MarkClean();														//Mark the Cargo as clean
			MarkOld();															//Mark the Cargo as old
		}//End of isNew if
		else
		{
			if(isDirty())
			{
				//If the Cargo is not new, but is dirty then it needs to be updated by the following SQL command
				executeCommand("Update Ship Set ShipName = '" + this.getCargoName() + "' , Contractor = '"+this.getContractor()+
					"' , Longitude = '" + this.getLongitude() + "' , Latitude = '" + this.getLatitude() + "' , LocationName = '" + this.getLocationName() + 
					"' , ShipType = '" + this.getCargoType() + "' , Capacity = '" +this.getCapacity() + "' , Status = '" + this.getStatus() +
					"' , NoOfContainers ='" + this.getNumOfContainers()+"' Where ShipID = " +this.id);
				MarkClean();													//Mark the cargo as clean
			}//End of isDirty if
		}//End of isOld else
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);									//Print out the error
		}//End of catch block
		
	}//End of overridden Update()

	//This is the overridden Delete function of the parent class and will remove this Cargo from the database
	@Override
	public  void Delete() 
	{
		try
		{
			executeCommand("Delete From ship where ShipID = " + id);			//Delete this Cargo from the database
		}//End of try block
		catch(Exception ex)
		{
			System.out.println("Error "+ ex);									//Print out the error
		}//End of catch block

	}//End of overridden Delete()

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
	//This function builds objects from returned data from SQL queries against our database
	public static Cargo BuildFromDataRow(Map<String,Object> data) throws SQLException
	{
		//This code grabs each element that will be found in the database on the Cargo table and set the appropriate values for a new Cargo
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
		
	}//End of the BuildFromDataRow(Map<String, Object> data)
	
	//This function overrides the toString function and returns the name of the Cargo
	@Override
	public String toString()
	{
		return getCargoName();											//Return the cargo name
	}//End of the overridden toString()



}
