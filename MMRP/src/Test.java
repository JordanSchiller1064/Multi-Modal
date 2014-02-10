import java.sql.*;
import java.util.ArrayList;
public class Test extends BaseClass 
{
	public Test()
	{
	}
	
	public Test Load(){return null;}
	public void Update(){};
	public void Delete(){};
	public void printRandmonness()
	{
		try
		{
			Connection conn = getConnection();
			Statement sql = conn.createStatement();
			ResultSet rs = sql.executeQuery("Select * From Availability");
			rs.next();
			System.out.println("TESTING "+rs.getInt(1));
		}
		catch(Exception ex)
		{
			System.out.println("ERROR" + ex);
		}
	}
	public static void main(String[] args)
	{
		Truck test = Truck.Load(1);
		System.out.println(test.getVehicleName());
		
		ArrayList<Segment> sched =test.getSchedule();
		for(int i = 0 ; i<sched.size();i++)
		{
			System.out.println(sched.get(i).getStart() + "\t" + sched.get(i).getEnd());
		}
	}

}
