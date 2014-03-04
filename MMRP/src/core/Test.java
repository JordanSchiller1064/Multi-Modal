package core;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import java.io.*;
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

	}
	public static void main(String[] args) throws IOException
	{
		double[][] arr = new double[10][10];
		//calculates the great circle distance between two points using the haversine formula.  Originally published by Roger Sinnott - Sky & Telescope magazine
		int earthRadius = 6371;			//radius of the earth in kilometers
		

		File f = new File(".././Output/Vehicles.txt");
		File f2 = new File(".././Output/Shipments.txt");
		Writer w=null;
		Writer w2=null;
		try
		{
		
			if(!f.exists()) {
				f.createNewFile();
			}
			if(!f2.exists()) {
				f2.createNewFile();
			}
			w=  new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f)));
			w2=  new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f2)));
		}
		catch(Exception ex)
		{
			System.out.println("Error " + ex);
		}
		
		ArrayList<Shipment> shipments = Shipment.LoadAll("");
		for(int i = 0; i< shipments.size();i++)
		{
			Routing.AStarAlg alg = new Routing.AStarAlg(shipments.get(i));
			ArrayList<Segment> segs = alg.getPath();
			
			w2.write("Shipment " + shipments.get(i).getId()+"\t Earliest: " + shipments.get(i).getEarliestTime()+"\t Latest: "+ shipments.get(i).getLatestTime()+"\n");
			w2.write("\t Start:" + Location.Load(shipments.get(i).getFromLocationID()).getName()+" End: "+Location.Load(shipments.get(i).getToLocationID()).getName()+"\n");
			if(segs.size()!=0)
			{
			for(int j = 0; j<segs.size();j++)
				w2.write("\t\tSegment id " + segs.get(j).getID()+ "\n\t\t\t"+segs.get(j).getStartLocation().getName()+"\t"+segs.get(j).getEndLocation().getName()+"\n");
			w2.write("\tArrival: "+ segs.get(segs.size()-1).getArrivalTime()+"\n");
			}
			System.out.println("Done with " + i);
		}
		w2.flush();
		
		/*
		for(int i =1; i<=10;i++)
		{
			Location one = Location.Load(i);
			for(int j =1; j<=10;j++)
			{
				Location two=Location.Load(j);
				double lat2=one.getLatitude();
				double long2=one.getLongitude();
				double lat1=two.getLatitude();
				double long1=two.getLongitude();
				double d2r = Math.PI/180.0;
		
			    double dlong = (long2 - long1) * d2r;
			    double dlat = (lat2 - lat1) * d2r;
			    double a = Math.pow(Math.sin(dlat/2.0), 2) + Math.cos(lat1*d2r) * Math.cos(lat2*d2r) * Math.pow(Math.sin(dlong/2.0), 2);
			    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			    double d = 6367 * c;
				arr[i-1][j-1]= d;
				System.out.print(arr[i-1][j-1]+"\t");
				
			}
			System.out.println();
		}
		*/
		
		/*
		ArrayList<Cargo> planes = Cargo.LoadAll("");
		for(int i = 0; i<planes.size();i++)
		{
			try
			{
				int lastArrival;
				int lastLocation;
				int startLocation;
				int currentLocation;
				ArrayList<Map<String,Object>> paths = executeQuery("Select * from temptable where Type = 'SHIP'");
				int rand = (int) Math.floor(Math.random()* paths.size());
				Segment s = new Segment();
				s.setDepartureTime(0);
				double baseDistance=arr[(Integer)paths.get(rand).get("Location1")-1][(Integer)paths.get(rand).get("Location2")-1];
				//plane =.9, truck=1.1, rail=1.2, cargo=1.3
				double offSetDistance = baseDistance * 1.3;
				//plane=750,truck 112,rail=29,cargo=39
				int time = (int)Math.floor(baseDistance/39);
				s.setArrivalTime(s.getDepartureTime()+time);
				lastArrival=s.getArrivalTime();
				currentLocation=(Integer)paths.get(rand).get("Location2");
				lastLocation=(Integer)paths.get(rand).get("Location1");
				startLocation=(Integer)paths.get(rand).get("Location1");
				//plane=.75,truck=.55, rail=.35, cargo=.25
				int cost = (int)Math.floor(baseDistance*.25);
				s.setCost(cost);
				s.setDistance(offSetDistance);
				s.setEndLocation(currentLocation);
				s.setStartLocation(startLocation);
				s.setVehicle(planes.get(i));
				planes.get(i).addToSchedule(s);
				int jumps=1;
				while(jumps<2)
				{
					paths=executeQuery("Select * from temptable where Type = 'SHIP' and Location1 = '" +currentLocation+"'");
					rand=(int) Math.floor(Math.random()* paths.size());
					while((Integer)paths.get(rand).get("Location2")==lastLocation)
					{
						rand=(int) Math.floor(Math.random()* paths.size());
					}
					s = new Segment();
					s.setDepartureTime(lastArrival+10);
					baseDistance=arr[(Integer)paths.get(rand).get("Location1")-1][(Integer)paths.get(rand).get("Location2")-1];
					offSetDistance = baseDistance * 1.3;
					time = (int)Math.floor(baseDistance/39);
					s.setArrivalTime(s.getDepartureTime()+time);
					lastArrival=s.getArrivalTime();
					currentLocation=(Integer)paths.get(rand).get("Location2");
					lastLocation=(Integer)paths.get(rand).get("Location1");
					cost = (int)Math.floor(baseDistance*.25);
					s.setCost(cost);
					s.setDistance(offSetDistance);
					s.setEndLocation(currentLocation);
					s.setStartLocation(lastLocation);
					s.setVehicle(planes.get(i));
					planes.get(i).addToSchedule(s);
					jumps++;
					if(currentLocation==startLocation)
						break;
				}
				
				
			
			}
			catch(Exception ex)
			{
				System.out.println("Error " +ex);
			}
		}*/
	}

}
