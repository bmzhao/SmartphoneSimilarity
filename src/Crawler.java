import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Crawler 
{
	private File list;
	String base;
	public Crawler(String a)
	{
		list = new File(a);
		base = "http://www.gsmarena.com/";
		
	}
	
	public void generateFile() throws FileNotFoundException, IOException
	{
		File toMake = new File("Smartphones.txt");
		if (!toMake.exists())
		{
			Scanner reader = new Scanner(list);
			ArrayList<String> urls = new ArrayList<String>();
			while (reader.hasNextLine())
			{
				String current = reader.nextLine();
				if (current.startsWith("http"))
				{
					retrieveFromBaseSite(current,urls);
				}
					
			}
			reader.close();
		
			PrintWriter out = new PrintWriter(toMake);
			for (int i = 0; i < urls.size(); i++)
			{
				out.println(urls.get(i));
			}
			out.close();	
			
		}
			
	}
	
	
	private void retrieveFromBaseSite(String baseSite, ArrayList<String> toStoreURLs) throws IOException
	{
		Document doc = Jsoup.connect(baseSite).get();
			
		String title = doc.title();
		System.out.println("title : " + title);
		
		
		while (true)
		{
			String current = doc.body().toString();
			String temp = "";
			int location=0;
			for (int i =0; i < 5; i++)
				location = current.indexOf("<li>",location)+1;
			int finalplace = current.indexOf("</ul>",location);
			while (location <= finalplace )
			{
				temp = "";
				location = current.indexOf("href=\"",location)+6;
				while (current.charAt(location)!='"')
					temp+=current.charAt(location++);
				toStoreURLs.add(base + temp);
			}
			toStoreURLs.remove(toStoreURLs.size()-1);
			int lastLocation = current.lastIndexOf("Next")-1;
			

			lastLocation = current.lastIndexOf("href",lastLocation)+6;
			String toConnect ="";
			while (current.charAt(lastLocation)!='"')
				toConnect += current.charAt(lastLocation++);
			if (toConnect.equals("#"))
				break;
			System.out.println(base + toConnect);
			
			doc = Jsoup.connect(base + toConnect).get();
		}
						
	}
}
