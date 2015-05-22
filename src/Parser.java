import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
public class Parser //will get all attributes from the csv file that should be mined from gsmarena
{
	
	public static ArrayList<Smartphone> parse(File a) throws Exception
	{
		
		Scanner input = new Scanner(a);
		String first = input.nextLine();
		int COMMACOUNT = 0;
		for (int i = 0; i < first.length(); i++)
		{
			if (first.charAt(i) == ',')
				COMMACOUNT++;
		}
				
		ArrayList<Smartphone> alldocs = new ArrayList<>();
		while (input.hasNextLine())
		{
			String name = "";
			ArrayList<Double> data = new ArrayList<>();
			int currentPosition = 0;
			String current = input.nextLine();
			for (int i = 0; i <= COMMACOUNT; i++)
			{
				String field = "";
				while (currentPosition < current.length() && current.charAt(currentPosition) != ',')
				{
							field += current.charAt(currentPosition++);
				}
				currentPosition++;
				if (i == 0)
				{
					name = field;
					continue;
				}
				
				data.add(Double.parseDouble(field));
			}
			Smartphone temp = new Smartphone(data, name);	
			alldocs.add(temp);
		}
		input.close();
		return alldocs;
	}	
}
