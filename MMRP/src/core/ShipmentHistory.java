package core;
import java.util.ArrayList;
import java.util.Map;


public class ShipmentHistory extends BaseClass {

	private int id;
	private int segmentID;
	private int shipmentID;
	private int nodeNumber;
	
	public ShipmentHistory()
	{
		MarkNew();
	}
	public ShipmentHistory(int id)
	{
		this.id=id;
		MarkOld();
	}
	
	public void setSegmentID(int id)
	{
		if(this.segmentID!=id)
		{
			segmentID=id;
			MarkDirty();
		}
	}
	public int getSegmentID()
	{
		return segmentID;
	}
	public Segment getSegment()
	{
		return Segment.Load(this.segmentID);
	}
	
	public void setShipmentID(int id)
	{
		if(this.shipmentID!=id)
		{
			shipmentID=id;
			MarkDirty();
		}
	}
	public int getShipmentID()
	{
		return this.shipmentID;
	}
	public Shipment getShipment()
	{
		return Shipment.Load(this.shipmentID);
	}
	public void setNodeNumber(int i)
	{
		if(this.nodeNumber!=i)
		{
			nodeNumber=i;
			MarkDirty();
		}
	}
	public int getNodeNumber()
	{
		return nodeNumber;
	}
	
	public static ArrayList<ShipmentHistory> LoadAllForShipment(int id)
	{
		ArrayList<ShipmentHistory> returnList = new ArrayList<ShipmentHistory>();
		try
		{
			ArrayList<Map<String,Object>> temp = executeQuery("Select * from ShipmentHistory where ShipmentID = " + id + " order by NodeNumber");
			for(int i = 0;i<temp.size();i++)
			{
				returnList.add(BuildFromDataRow(temp.get(0)));
			}
			return returnList;
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		return null;
	}
	public static ShipmentHistory BuildFromDataRow(Map<String,Object> data)
	{
		ShipmentHistory sh = new ShipmentHistory((Integer)data.get("ShipmentHistoryID"));
		sh.setSegmentID((Integer)data.get("SegmentID"));
		sh.setShipmentID((Integer)data.get("ShipmentID"));
		sh.setNodeNumber((Integer)data.get("NodeNumber"));
		sh.MarkClean();
		return sh;
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
