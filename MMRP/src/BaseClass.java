import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
public abstract class BaseClass {
	private Boolean dirty;
	private Boolean newObject;
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL="jdbc:mysql://Excalibur.sru.edu:3306/Multi-Modal";
	private static final String USER = "thangiah";
	private static final String PSWD="thangiah12345";
	
	
	
	public boolean isDirty()
	{
		if(dirty==null)
			return false;
		return dirty;
	}
	
	public boolean isNew()
	{
		if(newObject==null)
			return false;
		return newObject;
	}
	
	public void MarkDirty()
	{
		dirty=true;
	}
	
	public void MarkClean()
	{
		dirty=false;
	}
	
	public void MarkNew()
	{
		newObject=true;
	}
	
	public void MarkOld()
	{
		newObject=false;
	}
	
	private static Connection getConnection() throws SQLException,ClassNotFoundException
	{
		Class.forName(DRIVER);
		return DriverManager.getConnection(URL,USER,PSWD);
	}
	
	public static ArrayList<Map<String,Object>> executeQuery(String sql) throws SQLException 
	{
		Connection c=null;
		try
		{
			c = getConnection();
			ResultSet rs = c.createStatement().executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			ArrayList<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			while(rs.next())
			{
				Map<String,Object> temp = new HashMap<String,Object>(md.getColumnCount());
				for(int i = 1; i<=md.getColumnCount();i++)
				{
					temp.put(md.getColumnName(i),rs.getObject(i));
				}
				data.add(temp);
				
			}
			return data;
			
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
			return null;
		}
		finally
		{
			if(c!=null && !c.isClosed())
				c.close();
		}
	}
	
	public static boolean executeCommand(String sql)throws SQLException
	{
		Connection c=null;
		try
		{
			c = getConnection();
			c.createStatement().execute(sql);
			return true;
			
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
			return false;
		}
		finally
		{
			if(c!=null && !c.isClosed())
				c.close();
		}
	}
	
	abstract void Update();
	abstract void Delete();
	

}
