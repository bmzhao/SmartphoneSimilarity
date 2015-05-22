import java.util.ArrayList;
import java.util.HashMap;

//OSTypeToInt limited to windows, android, iOS
//0 for windows, 1 for android, 2 for ios

public class Smartphone implements Comparable<Smartphone>
{
	
	protected ArrayList<Double> attributes;
	protected String name;

	private static boolean mapInitialized; // is true if the following static hashmaps have been initialized
	protected static HashMap<String,Integer> OSTypeToInt;
	protected static HashMap<Integer,String> intToOSType;

	
	
	public Smartphone(ArrayList<Double> values, String n)
	{
		if (mapInitialized == false)
		{
			initialize();	
			mapInitialized = true;
		}
		name = n;
		attributes = values;
	}
	
	private void initialize()
	{
		OSTypeToInt = new HashMap<>();
		OSTypeToInt.put("Windows",1);
		OSTypeToInt.put("Android", 2);
		OSTypeToInt.put("iOS", 3);
		
		
		intToOSType = new HashMap<>();
		intToOSType.put(1,"Windows");
		intToOSType.put(2,"Android");
		intToOSType.put(3,"iOS");
	}
	
	public int compareTo(Smartphone s)
	{
		return this.name.compareTo(s.name);
	}
	
}
