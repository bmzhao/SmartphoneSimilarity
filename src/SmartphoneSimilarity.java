import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SmartphoneSimilarity
{
	public static void main(String[] args) throws Exception
	{
		//need Smartphone class' Hashmaps to be initialized
		Smartphone temporary = new Smartphone(null,null);
		ArrayList<Smartphone> phones = new ArrayList<>();
		
		File database = new File("Smartphones.csv");
		if (!database.exists())
		{
			File phoneSites = new File("Smartphones.txt");
			if (!phoneSites.exists())
			{
				//Generates the Smartphones.txt file
				Crawler crawl = new Crawler("Manufacturers.txt");
				crawl.generateFile();
			}
			
			
			Scanner reader = new Scanner(phoneSites);
			while (reader.hasNextLine())
			{
				String s = reader.nextLine();
				String current = "";
				if (s.startsWith("http"))
				{
					current = Miner.getHTML(s);
					if (current != null)
					{
						temporary = Miner.toPhone(current);
						if (temporary != null)
							phones.add(Miner.toPhone(current));
						
					}
						
				}
				System.out.println("Done with " + current);
			}
			reader.close();
			System.out.println("\n\nDone with internet");
			
			
		
		
		
			PrintWriter printer = new PrintWriter(database);
			printer.println("Name,release year,volume(mm^3),weight(g),screen size(in),total resolution,OSType,number of cores,core clock rate(gHz),ram,primary camera(MP),battery(mAh)");
			for (int i = 0; i < phones.size(); i++)
			{
				printer.print(phones.get(i).name + ",");
				for (int j = 0; j < phones.get(i).attributes.size(); j++)
				{
					printer.print(phones.get(i).attributes.get(j) + ",");
				}
				printer.print("\n");
			}
			printer.close();
		}
		else
		{
			phones = Parser.parse(database);
			
		}
				
		
		KMeansVector clusterer = new KMeansVector(6,phones);
		clusterer.formClusters();
		ArrayList<Cluster> phoneCategories = clusterer.getClusters();
		for (int i = 0; i < phoneCategories.size(); i++)
		{
			System.out.println("Cluster " + i);
			ArrayList<Smartphone> currentCluster = phoneCategories.get(i).clusterphones;
			Collections.sort(currentCluster);
			for (int j = 0; j < currentCluster.size(); j++)
				System.out.println(phoneCategories.get(i).clusterphones.get(j).name);
			System.out.println("\n");
		}
	}
}
