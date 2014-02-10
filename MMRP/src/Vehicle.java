import java.util.ArrayList;


public abstract class Vehicle extends BaseClass {

	public static enum TravelTypes 
	{
			Truck ("TRUCK"),
			Rail("RAIL"),
			Cargo("CARGO"),
			Plane("PLANE"),
			Bike("BIKE");
			private String type;
			TravelTypes(String s)
			{
				type=s;
			}
			@Override public String toString()
			{
				return type;
			}
			
	}
	public static enum Contractors 
	{
			UPS("UPS"),
			DHL("DHL"),
			FedEX("FEDEX"),
			USPS("USPS");
			private String contractor;
			Contractors(String s)
			{
				contractor=s;
			}
			@Override public String toString()
			{
				return contractor;
			}
			
	}
	public static enum Status
	{
		Running("RUNNING"),
		Delayed("DELAYED"),
		Disabled("DISABLED");
		private String status;
		Status(String s)
		{
			status=s;
		}
		@Override public String toString()
		{
			return status;
		}
		
	}

	protected Contractors contractor;
	protected double longitude;
	protected double latitude;
	protected String name;
	protected String locationName;
	protected int capacity;
	protected Status status;
	protected TravelTypes type;
	protected int id;
	
	public void setTravelType(TravelTypes t)
	{
		type=t;
		MarkDirty();
	}
	public void setTravelType(String t)
	{
		type = loadType(t);
		MarkDirty();
	}	
	private TravelTypes loadType(String t)
	{
		if(t.equals(TravelTypes.Bike.toString()))
			return TravelTypes.Bike;
		if(t.equals(TravelTypes.Cargo.toString()))
			return TravelTypes.Cargo;
		if(t.equals(TravelTypes.Plane.toString()))
			return TravelTypes.Plane;
		if(t.equals(TravelTypes.Rail.toString()))
			return TravelTypes.Rail;
		if(t.equals(TravelTypes.Truck.toString()))
			return TravelTypes.Truck;
		return null;
	}
	private String getTravelType()
	{
		return type.toString();
	}
	
	public void setId(int id)
	{
		this.id=id;
		MarkDirty();
	}
	public int  getId()
	{
		return id;
	}
	public void setStatus(Status s)
	{
		status=s;
		MarkDirty();
	}
	public void setStatus(String s)
	{
		status=loadStatus(s);
		MarkDirty();
	}
	private Status loadStatus(String val)
	{
		if(val.equals(Status.Delayed))
		{
			return Status.Delayed;
		}
		else
		{
			if(val.equals(Status.Disabled))
			{
				return Status.Disabled;
			}
			else
			{
				return Status.Running;
			}
		}
	}
	private String getStatus()
	{
		return status.toString();
	}
	
	public void setContractor(Contractors c)
	{
		contractor=c;
		MarkDirty();
	}
	public void setContractor(String c)
	{
		contractor=loadContractor(c);
		MarkDirty();
	}
	private Contractors loadContractor(String val)
	{
		if(val.equals(Contractors.DHL))
		{
			return Contractors.DHL;
		}
		else
		{
			if(val.equals(Contractors.FedEX))
			{
				return Contractors.FedEX;
			}
			else
			{
				if(val.equals(Contractors.UPS))
				{
					return Contractors.UPS;
				}
				else
					return Contractors.USPS;
			}
		}
	}
	private String getContractor()
	{
		return contractor.toString();
	}
	
	public void setLocation(double lat, double lon, String name)
	{
		latitude=lat;
		longitude=lon;
		locationName=name;
		MarkDirty();
	}	
	public void setLocation(double lat, double lon)
	{
		latitude=lat;
		longitude=lon;
		locationName="";
		MarkDirty();
	}	
	public void setLongitude(double lon)
	{
		longitude=lon;
		MarkDirty();
	}
	public void setLatitude(double lat)
	{
		this.latitude=lat;
		MarkDirty();
	}
	public double getLongitude()
	{
		return longitude;
	}
	public double getLatitude()
	{
		return latitude;
	}
	public void setLocationName(String name)
	{
		this.locationName=name;
		MarkDirty();
	}
	public String getLocationName()
	{
		return locationName;
	}
	
	public void setVehicleName(String name)
	{
		this.name=name;
		MarkDirty();
	}
	protected String getVehicleName()
	{
		return name;
	}
	public void setCapacity(int capac)
	{
		capacity=capac;
		MarkDirty();
	}
	public int getCapacity()
	{
		return capacity;
	}
	
	abstract void Update();
	abstract void Delete();
	
	public ArrayList<Segment> getSchedule()
	{
		return Segment.LoadAll("where ModeType = '"+type.toString()+"' and VehicleID= " + id);
	}



}
