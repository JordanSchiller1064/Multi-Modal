import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


public class Shipment extends BaseClass {

	private int fromLocationID,toLocationID;
	private int priority;
	private int size;
	private int arrivalTime,departureTime;
	private int id;
	private ArrayList<ShipmentHistory> history;
	public Shipment()
	{
		MarkNew();
	}
	public Shipment(int id)
	{
		this.id=id;
		MarkOld();
	}
	
	public int getFromLocationID() {
		return fromLocationID;
	}
	public void setFromLocationID(int fromLocationID) {
		if(this.fromLocationID!=fromLocationID)
		{
			this.fromLocationID = fromLocationID;
			MarkDirty();
		}
	}
	public int getToLocationID() {
		return toLocationID;
	}
	public void setToLocationID(int toLocationID) {
		if(this.toLocationID != toLocationID)
		{
			this.toLocationID = toLocationID;
			MarkDirty();
		}
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		if(this.priority != priority)
		{
			this.priority = priority;
			MarkDirty();
		}
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		if(this.size!=size)
		{
			this.size = size;
			MarkDirty();
		}
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		if(this.arrivalTime!=arrivalTime)
		{
			this.arrivalTime = arrivalTime;
			MarkDirty();
		}
	}
	public int getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(int departureTime) {
		if(this.departureTime!=departureTime)
		{
			this.departureTime = departureTime;
			MarkDirty();
		}
	}
	public int getId() {
		return id;
	}

	public void setHistory(ArrayList<ShipmentHistory> hist)
	{
		history=hist;
	}
	public ArrayList<ShipmentHistory> getHistory()
	{
		return history;
	}
	public Location loadStartLocation()
	{
		return Location.Load(fromLocationID);
	}
	public Location loadEndLocation()
	{
		return Location.Load(toLocationID);
	}
	public static Shipment Load(int id)
	{
		try
		{
			ArrayList<Map<String,Object>> temp =executeQuery("Select * from Shipment where ShipmentID = " + id);
			if(temp.size()>0)
			{
				Shipment s = BuildFromDataRow(temp.get(0));
				s.setHistory(ShipmentHistory.LoadAllForShipment(id));
				return s;
			}
			return null;
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
 		return null;
	}
	
	public static ArrayList<Shipment> LoadAll(String where)
	{
		ArrayList<Shipment> returnList = new ArrayList<Shipment>();
		try 
		{
			
			ArrayList<Map<String,Object>> temp =executeQuery("Select * from Shipment " +  where);
			for(int i = 0; i<temp.size();i++)
			{
				Shipment s =BuildFromDataRow(temp.get(i));
				s.setHistory(ShipmentHistory.LoadAllForShipment(s.getId()));
				returnList.add(s);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return returnList;
	}
	public static Shipment BuildFromDataRow(Map<String,Object> data) throws SQLException
	{
		Shipment s = new Shipment((Integer)data.get("ShipmentID"));
		s.setArrivalTime((Integer)data.get("ArrivalAtDestination"));
		s.setDepartureTime((Integer)data.get("DepartureFromStart"));
		s.setFromLocationID((Integer)data.get("FromLocationID"));
		s.setPriority((Integer)data.get("Priority"));
		s.setSize((Integer)data.get("Size"));
		s.setToLocationID((Integer)data.get("ToLocationID"));
		s.MarkClean();
		return s;
		
	}
	@Override
	void Update() {
		// TODO Auto-generated method stub

	}

	@Override
	void Delete() {
		// TODO Auto-generated method stub

	}

}
