import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
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
	
	public static Connection getConnection() throws SQLException,ClassNotFoundException
	{
		Class.forName(DRIVER);
		return DriverManager.getConnection(URL,USER,PSWD);
	}
	
	abstract void Update();
	abstract void Delete();
	

}
