import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Miner 
{
	
	//given a website, generate the relevant html file of attribute table
	public static String getHTML(String s) throws Exception
	{
		  int count = 0;
		  int maxTries = 100;
			File answer;
			Document doc;
		  while (true)
		  {
		  	try
		  	{
		  		doc = Jsoup.connect(s).get();
		 			
					String title = doc.title().replace(" - Full phone specifications", "") + ".html";
					answer = new File(title);
					if (new File(title).exists())
						return title;
					PrintWriter p = new PrintWriter(answer);
							
					Elements table= doc.select("table");
					for (Element x: table)
						p.println(x.toString());
					p.close();
					return title;
				
		  	}
		  	catch (FileNotFoundException h)
		  	{
		  		System.out.println("File Not Found. Skipping");
		  		return null;
		  	}
		  	catch (Exception e)
		  	{
		  		System.out.println("Caught Exception. Continuing...");
		  		if (++count == maxTries) throw e;
		  	}
		  }
	
		 
			// need http protocol
			
	
		
	}
	
	//given the file made by getHTML, produce the corresponding CSV of attributes
	//returns null if invalid html documentation
	public static Smartphone toPhone(String x) throws IOException
	{
		File toRead = new File(x);
		if (!toRead.exists()){
				System.out.println("FILE " + x + " DID NOT EXIST!!\n\n\n");
				File error = new File("Error.txt");
				PrintWriter pw = new PrintWriter(new FileWriter(error, true));
				pw.println("FILE " + x + " DID NOT EXIST!!\n\n\n");
				pw.close();
				return null;
		}
		String name = x.replace(".html", "");
		String html = "";
		String current = "";
		Scanner reader = new Scanner(toRead);
		while (reader.hasNextLine())
			html += reader.nextLine() + "\n";
		reader.close();
		
		ArrayList<Double> attributes = new ArrayList<>();
		//store each attribute within the html text into the arraylist
		
		//announcement year////////////////////////////////////////////////////
		int location = html.indexOf("Announced");
		if (location == -1)
			return null;
		location = html.indexOf("\"nfo\">",location)+6;
		//make sure you're reading a digit
		while (!Character.isDigit(html.charAt(location)))
			location++;
		
		while (Character.isDigit(html.charAt(location)))
			current+=html.charAt(location++);
		double announce = Integer.parseInt(current);
		if (announce < 2010)
			return null;
		attributes.add(announce);		
		
		
		
		
		//volume integer is volume in mm^3 rounded
		//volume field////////////////////////////////////////////////////
		location = html.indexOf("Dimensions");
		if (location == -1)
			return null;
		location = html.indexOf("\"nfo\">",location)+6;
		
		//make sure you're reading a digit
		while (!Character.isDigit(html.charAt(location)))
			location++;
		
		current = "";
		//get length
		while (Character.isDigit(html.charAt(location)) || html.charAt(location) == '.')
			current+=html.charAt(location++);
		double length = Double.parseDouble(current);
		
		//move to next number
		current = "";
		while (!Character.isDigit(html.charAt(location)))
			location++;
		
		while (Character.isDigit(html.charAt(location)) || html.charAt(location) == '.')
			current+=html.charAt(location++);
		double width = Double.parseDouble(current);
		
		//move to next number
		current = "";
		while (!Character.isDigit(html.charAt(location)))
			location++;
		
		while (Character.isDigit(html.charAt(location)) || html.charAt(location) == '.')
			current+=html.charAt(location++);
		double height = Double.parseDouble(current);
		
		double volume = length * width * height;
		

		
		attributes.add(volume);
		
		
		
		
		
		//weight is weight in grams
		//weight field ////////////////////////////////////////////
		location = html.indexOf("Weight",location);
		if (location == -1)
			return null;
		location = html.indexOf("\"nfo\">",location)+6;
		
		//make sure you're reading a digit
		while (!Character.isDigit(html.charAt(location)))
			location++;
				
		
		current = "";
		while (Character.isDigit(html.charAt(location)) || html.charAt(location) == '.')
			current+=html.charAt(location++);
		double weight = Double.parseDouble(current);
		if ((int)weight <= 15)
			return null;
		attributes.add(weight);
		
		
		
		
		
		
		
		
		
		//screen size inches  ////////////////////////////////////////////////////
		location = html.indexOf("Size",location);
		if (location == -1)
			return null;
		
		location = html.indexOf("\"nfo\">",location)+6;
		//make sure you're reading a digit
		while (!Character.isDigit(html.charAt(location)))
			location++;
				
		
		current = "";
		while (Character.isDigit(html.charAt(location)) || html.charAt(location) == '.')
			current+=html.charAt(location++);
		double size = Double.parseDouble(current);
		attributes.add(size);
		
		
		
		
		
		
		
		
		
		
		
		//total resolution///////////////////////////////////
		location = html.indexOf("Resolution",location);
		if (location == -1)
			return null;
		location = html.indexOf("\"nfo\">",location)+6;
		
		//make sure you're reading a digit
		while (!Character.isDigit(html.charAt(location)))
			location++;
				
		
		current = "";
		while (Character.isDigit(html.charAt(location)))
			current+=html.charAt(location++);
		int pixel1 = Integer.parseInt(current);
		
		//move to next number
		current = "";
		while (!Character.isDigit(html.charAt(location)))
			location++;
		
		while (Character.isDigit(html.charAt(location)))
			current+=html.charAt(location++);
		int pixel2= Integer.parseInt(current);
		double resolution = pixel1*pixel2;
		attributes.add(resolution);
		
		
		
		
		
		
		
		
		
		
		//OS type///////////////////////////////////////////////////
		location = html.indexOf("Platform",location);
		if (location == -1)
			return null;
		
		location = html.indexOf("\"nfo\">",location)+6;
		
		current = "";
		while (Character.isLetter(html.charAt(location)))
			current+=html.charAt(location++);
		int OSType =0;
		if (current.startsWith("And"))
			OSType = Smartphone.OSTypeToInt.get("Android");
		else if (current.startsWith("Microsoft Windows Phone"))
			OSType = Smartphone.OSTypeToInt.get("Windows");
		else if (current.startsWith("iOS"))
			OSType = Smartphone.OSTypeToInt.get("iOS");
		else
			return null;
		
		attributes.add((double)OSType);
		
		
		
		
		
		
		//Number of CPU Cores/////////////////////////////////////////////////
		location = html.indexOf("CPU",location);
		int coreCount;
		if (location == -1)
			return null;
		
		location = html.indexOf("\"nfo\">",location)+6;
		//make sure you're reading a digit
		if (Character.isDigit(html.charAt(location)))
			coreCount = 1;
		else
		{
			current = "";
			while (!Character.isDigit(html.charAt(location)))
				current+=html.charAt(location++);
			if (current.contains("Quad"))
				coreCount = 4;
			else if (current.contains("Dual"))
				coreCount = 2;
			else if (current.contains("Oct"))
				coreCount = 8;
			else
				return null;
		}
		attributes.add((double)coreCount);

		
		
		
		
		//ClockRate of CPU/////////////////////////////////////
		while (!Character.isDigit(html.charAt(location)))
			current+=html.charAt(location++);
		current = "";
		while (Character.isDigit(html.charAt(location)) || html.charAt(location) == '.')
			current+=html.charAt(location++);
		double clock = Double.parseDouble(current.trim());
		String hertz = html.substring(location, location+4);
		if (hertz.contains("MHz"))
			clock = clock/1000.00;
		
		attributes.add(clock);
		
		
		//ram ////////////////////////////////////////////////////
		location = html.indexOf("Internal",location);
		if (location == -1)
			return null;
		location = html.indexOf(",",location)+1;
		//make sure you're reading a digit
		while (!Character.isDigit(html.charAt(location)))
			location++;
		
		current = "";
		while (Character.isDigit(html.charAt(location)))
			current+=html.charAt(location++);
		
		String examine = html.substring(location, location+4);
		double ram = Double.parseDouble(current);
		if (examine.contains("MB"))
			ram = ram/1000.000;
		attributes.add(ram);
		
	
		//Primary Camera MP////////////////////////////////////////////////////
		location = html.indexOf("Primary",location);
		if (location == -1)
			return null;
		location = html.indexOf("\"nfo\">",location)+6;
		//make sure you're reading a digit
		while (!Character.isDigit(html.charAt(location)))
			location++;
		
		current = "";
		while (Character.isDigit(html.charAt(location)))
			current+=html.charAt(location++);
		double MP = Integer.parseInt(current);
		if (MP>60)
			return null;
		attributes.add(MP);

		
		
		//Battery Capacity////////////////////////////////////////////
		location = html.indexOf(">Battery",location);
		if (location == -1)
			return null;
		location = html.indexOf("\"nfo\">",location)+6;
		//make sure you're reading a digit
		while (!Character.isDigit(html.charAt(location)))
			location++;
		
		current = "";
		while (Character.isDigit(html.charAt(location)))
			current+=html.charAt(location++);
		double battery = Integer.parseInt(current);
		attributes.add(battery);
		if (battery<100)
			return null;
		
		return new Smartphone(attributes,name);
		
	}
	
}

