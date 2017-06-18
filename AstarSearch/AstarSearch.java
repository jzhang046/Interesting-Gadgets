import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;

public class AstarSearch {
	
	public static void main(String args[]) {
		FileResource flightDistanceData = new FileResource("flightDistances.csv");
		CSVParser parser = flightDistanceData.getCSVParser();
		
		HashMap<String, City> cityList = new HashMap<String, City>();
		String Host = "Vancouver, Canada";
		
		for (CSVRecord record: parser) {
			String city1 = record.get("Departure");
			String city2 = record.get("Destination");
			float distance = Float.parseFloat(record.get("Distance/Miles"));
			
			//Put cities into the list, if they are not in the city. 
			if (!cityList.containsKey(city1)) {
				cityList.put(city1, new City(city1));
			}
			if (!cityList.containsKey(city2)) {
				cityList.put(city2, new City(city2));
			}
			
			cityList.get(city1).createFlightTo(cityList.get(city2), distance);
			cityList.get(city2).createFlightTo(cityList.get(city1), distance);
		}
		
		for (String cityName : cityList.keySet()) {
			if (!cityName.equals(Host)) {
				printShortestDistance(Host, cityName, cityList);
			}
		}
		
	}
	
	private static void printShortestDistance(String host, String destination, HashMap<String, City> cityList) {		
		astSearch Search = new astSearch(host, destination, cityList);
		float shortestDistance = Search.gotoDest();
		System.out.println("====================");
		System.out.println("Destination: " + destination);
		System.out.println("Total distance: " + shortestDistance + " miles. ");
		System.out.println("\nFlight Path: ");
		Search.printPath();
		System.out.println("");
	}
}

class astSearch {
	private City origin, dest;
	private ArrayList<String> closeList;
	private HashMap<String, City> cityList;
	
	private ArrayList<String> path;
	
	public astSearch(String Departure, String Destination, HashMap<String, City> ctList) {
		this.cityList = ctList;
		
		this.origin = this.cityList.get(Departure);
		this.dest = this.cityList.get(Destination);
				
		this.closeList = new ArrayList<String>();
	}
	
	public float gotoDest() {
		PriorityQ pq = new PriorityQ(this.origin);
		Node currentNode = pq.getCurrentNode();
		while(!currentNode.getName().equals(this.dest.getName())) {
			
			this.closeList.add(currentNode.getName());
			
			pq.expanAndSort(this.closeList); 
			
			currentNode = pq.getCurrentNode();
			//Update the node at highest priority. 
		}
		
		//Now the cuurentNode must be the destination. 
		this.path = currentNode.getPath();
		return pq.getCost(currentNode);
	}
	
	public void printPath() {		
		for (String node : this.path) {
			System.out.print(node + " => ");
		}
		
		System.out.println(this.dest.getName());
	}
}

class PriorityQ {
	//Use a hash map that can sort later. 
	
	private HashMap<Node, Float> pqList;
	private Node vipNode;
	
	public PriorityQ(City origin) {
		this.pqList = new HashMap<Node, Float>();
		this.pqList.put(new Node(origin), new Float(0));
		this.vipNode = this.getHPNode();
	}

	private Node getHPNode() {
		Node nd = null;
		float cost = -1;
		for (Node tmpNode : this.pqList.keySet()) {
			if (nd == null) {
				nd = tmpNode;
				cost = pqList.get(tmpNode);
			}
			else {
				float currentCost = pqList.get(tmpNode);
				if (currentCost < cost) {
					cost = currentCost;
					nd = tmpNode;
				}
			}
		}
		
		return nd;
	}
	
	public Node getCurrentNode() {
		//Make sure vipNode will always update after every operation. 
		return this.vipNode;
	}
	
	public void expanAndSort(ArrayList<String> closedList) {
		//Under development. 
		//Add previous node to all children nodes within this method.
		
		for (City availChild : this.vipNode.getFlights().keySet()) {
			//avaukChild shouldn't be null here, for all the cities are connected. 
			//Thus no need to consider error handling for null availChild. 
			if (!closedList.contains(availChild.getName())) {
				this.pqList.put(new Node(availChild, this.vipNode.inheritPath()), 
						(this.pqList.get(this.vipNode)+this.vipNode.getDistanceTo(availChild)));
				//The above statement is in 2 lines. 
			}
		}
		
		this.pqList.remove(this.vipNode);
		this.vipNode = this.getHPNode();
	}
	
	public float getCost(Node nd) {
		return this.pqList.get(nd);
	}
	
}

class City {
	private String name;
	private HashMap<City, Float> distanceTo;
	
	public City(String nameOfCity) {
		this.name = nameOfCity;
		distanceTo = new HashMap<City, Float>();
	}
	
	public City(City ct) {
		this.name = ct.getName();
		this.distanceTo = ct.getFlights();
	}
	
	public String getName() {
		return this.name;
	}
	
	public HashMap<City, Float> getFlights() {
		return this.distanceTo;
	}
	
	public float getDistanceTo(City destCity) {
		return this.distanceTo.get(destCity);
	}
	
	public void createFlightTo(City anotherCity, float distance) {
		if (!this.distanceTo.containsKey(anotherCity)) {
			this.distanceTo.put(anotherCity,  distance);
		}
	}
}

class Node extends City {
	private ArrayList<String> pathFromOrigin;
	
	public Node(City ctOrigin) {
		super(ctOrigin);
		//Java would automatically call super constructor if not defined by the developer. 
		//Always define this call, unless there is a proper super constructor without inputs. 
		this.pathFromOrigin = new ArrayList<String>();
	}

	public Node(City childCity, ArrayList<String> inheritedPath) {
		super(childCity);
		
		this.pathFromOrigin = inheritedPath;
	}
	
	public ArrayList<String> inheritPath() {
		//Should create a new ArrayList here. 
		//Or otherwise all the subsequent nodes will write into same ArrayList. 
		
		//ArrayList<String> rtn = (ArrayList<String>) this.getPath().clone();
		//Unchecked warning from Eclipse compiler. 
		
		//Some people said that not to use .clone() method, 
		//instead, directly use a constructor, 
		//which works here. 
		ArrayList<String> rtn = new ArrayList<String>(this.getPath());
		rtn.add(this.getName());
		return rtn;
	}
	
	public ArrayList<String> getPath() {
		return this.pathFromOrigin;
	}
}
