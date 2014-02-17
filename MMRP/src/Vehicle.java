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
	
	protected void setTravelType(TravelTypes t)
	{
		if(type==null || !type.equals(t))
		{
			type=t;
			MarkDirty();
		}
	}
	protected void setTravelType(String t)
	{
		if(type==null || !type.toString().equals(t))
		{
			type = loadType(t);
			MarkDirty();
		}
	}	
	public static TravelTypes loadType(String t)
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
	public String getTravelType()
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
		if(status == null || status!=(s))
		{
			status=s;
			MarkDirty();
		}
	}
	public void setStatus(String s)
	{
		if(status==null || !status.toString().equals(s));
		status=loadStatus(s);
		MarkDirty();
	}
	public static Status loadStatus(String val)
	{
		if(val.equals(Status.Delayed.toString()))
		{
			return Status.Delayed;
		}
		else
		{
			if(val.equals(Status.Disabled.toString()))
			{
				return Status.Disabled;
			}
			else
			{
				return Status.Running;
			}
		}
	}
	public String getStatus()
	{
		return status.toString();
	}
	
	public void setContractor(Contractors c)
	{
		if(contractor==null || !contractor.equals(c))
		{
			contractor=c;
			MarkDirty();
		}
	}
	public void setContractor(String c)
	{
		if(contractor==null || !contractor.toString().equals(c))
		{
			contractor=loadContractor(c);
			MarkDirty();
		}
	}
	public static Contractors loadContractor(String val)
	{
		if(val.equals(Contractors.DHL.toString()))
		{
			return Contractors.DHL;
		}
		else
		{
			if(val.equals(Contractors.FedEX.toString()))
			{
				return Contractors.FedEX;
			}
			else
			{
				if(val.equals(Contractors.UPS.toString()))
				{
					return Contractors.UPS;
				}
				else
					return Contractors.USPS;
			}
		}
	}
	public String getContractor()
	{
		return contractor.toString();
	}
	
	public void setLocation(double lat, double lon, String name)
	{
		if(this.latitude!=lat || this.longitude!=lon || this.locationName==null ||!this.locationName.equals(name))
		{
			latitude=lat;
			longitude=lon;
			locationName=name;
			MarkDirty();
		}
	}	
	public void setLocation(double lat, double lon)
	{
		if(this.latitude!=lat || this.longitude!=lon)
		{
			latitude=lat;
			longitude=lon;
			locationName="";
			MarkDirty();
		}
	}	
	public void setLongitude(double lon)
	{
		if(this.longitude!=lon)
		{
			longitude=lon;
			MarkDirty();
		}
	}
	public void setLatitude(double lat)
	{
		if(this.latitude!=lat)
		{
			this.latitude=lat;
			MarkDirty();
		}
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
		if(this.locationName==null || !this.locationName.equals(name))
		{
			this.locationName=name;
			MarkDirty();
		}
	}
	public String getLocationName()
	{
		return locationName;
	}
	
	protected void setVehicleName(String name)
	{
		if(this.name==null || !this.name.equals(name) )
		{
			this.name=name;
			MarkDirty();
		}
	}
	protected String getVehicleName()
	{
		return name;
	}
	public void setCapacity(int capac)
	{
		if(capacity!=capac)
		{
			capacity=capac;
			MarkDirty();
		}
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
