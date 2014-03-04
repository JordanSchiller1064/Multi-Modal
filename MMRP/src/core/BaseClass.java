
//======================================================================================//
//							Software Engineering MMRP Project							//
//									Dr. Sam Thangiah									//
//					Dan Miller, Zach Petrusch, Chris Solomon, and Jordan Schiller		//
//======================================================================================//
//										Base Class										//
//======================================================================================//
// Purpose:																				//
//																						//
//======================================================================================//
package core;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseClass {
	private Boolean dirty;																	//The dirty/clean flag for DB management
	private Boolean newObject;																//The new/old flag for DB management
	private static final String DRIVER = "com.mysql.jdbc.Driver";							//Location of the driver
	private static final String URL="jdbc:mysql://Excalibur.sru.edu:3306/Multi-Modal";		//URL to connect to the database
	private static final String USER = "thangiah";											//Default user name
	private static final String PSWD="thangiah12345";										//Default password
	
	//This function returns if the dirty flag is set or not.
	public boolean isDirty()
	{
		if(dirty==null)
			return false;
		return dirty;
	}//End of isDirty()
	
	//This function returns if the newObject flag is set or not.
	public boolean isNew()
	{
		if(newObject==null)
			return false;
		return newObject;
	}//End of isNew()
	
	//This function sets the dirty flag to true
	public void MarkDirty()
	{
		dirty=true;
	}//End of MarkDirty()
	
	//This function sets the dirty flag to false
	public void MarkClean()
	{
		dirty=false;
	}//End of MarkClean()
	
	//This function sets the newObject flag to true
	public void MarkNew()
	{
		newObject=true;
	}//End of MarkNew()
	
	//This function sets the newObject flag to false
	public void MarkOld()
	{
			newObject=false;
	}//End of MarkOld
	
	//This function will create a connection to the database
	private static Connection getConnection() throws SQLException,ClassNotFoundException
	{
		Class.forName(DRIVER);
		return DriverManager.getConnection(URL,USER,PSWD);
	}//End of getConnection()
	
	//This function will execute a passed in string as and SQL command and return the results in
	//and ArrayList of Maps of strings and objects
	public static ArrayList<Map<String,Object>> executeQuery(String sql) throws SQLException 
	{
		Connection c=null;
		try
		{
			c = getConnection();															//Create the connection
			ResultSet rs = c.createStatement().executeQuery(sql);							//Execute the query
			ResultSetMetaData md = rs.getMetaData();										//Save the metadata from the results
			ArrayList<Map<String,Object>> data = new ArrayList<Map<String,Object>>();		//Create a new ArrayList of Maps of Strings to Objects
			while(rs.next())
			{
				Map<String,Object> temp = new HashMap<String,Object>(md.getColumnCount());	//While we still have results create a new HashMap object 
				for(int i = 1; i<=md.getColumnCount();i++)									//For each of the columns place the string and object pair
				{
					temp.put(md.getColumnName(i),rs.getObject(i));
				}//End of for loop
				data.add(temp);																//Add the temp variable to the data
				
			}//End of while loop
			return data;																	//Return the data
			
		}//End of the try block
		catch(Exception ex)
		{
			System.out.println("Error " + ex);												//Print out the error to the screen
			return null;
		}//End of the catch block
		finally
		{
			if(c!=null && !c.isClosed())													//Make sure the connection is closed
				c.close();				
		}//End of finally clause
	}//End of executeQuery(String sql)
	
	//This function will execute an SQL command and returns whether it succeeded of failed
	public static boolean executeCommand(String sql)throws SQLException
	{
		Connection c=null;
		try
		{
			c = getConnection();												//Create a connection to the database
			c.createStatement().execute(sql);									//Execute the SQL statement
			return true;														//Return that it was successful
			
		}//End of try block	
		catch(Exception ex)
		{
			System.out.println("Error " + ex);									//Print out the error
			return false;														//Return that the SQL command failed
		}//End of catch block
		finally
		{
			if(c!=null && !c.isClosed())										//Close the connection
				c.close();
		}//End of finally block
	}//End of executeCommand(String sql)
	
	//Abstract Functions
	abstract void Update();
	abstract void Delete();

}//End of BaseClass


/*setup
 * Flights:
 * 1-2
 * 1-7
 * 1-8
 * 1-10
 * 2-1
 * 2-7
 * 2-8
 * 2-10
 * 3-4
 * 3-5
 * 3-6
 * 3-7
 * 3-8
 * 3-9
 * 4-3
 * 4-5
 * 4-6
 * 4-8
 * 4-9
 * 5-3
 * 5-4
 * 5-6
 * 5-9
 * 6-3
 * 6-4
 * 6-5
 * 6-9
 * 7-1
 * 7-2
 * 7-3
 * 7-6
 * 7-8
 * 7-10
 * 8-1
 * 8-2
 * 8-3
 * 8-4
 * 8-7
 * 8-10
 * 9-3
 * 9-4
 * 9-5
 * 9-6
 * 10-1
 * 10-2
 * 10-7
 * 10-8
 */
