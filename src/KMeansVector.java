import java.util.ArrayList;
import java.util.Collections;


public class KMeansVector 
{
	//number of clusters
	private int kValue;
	
	//all phones to be clustered
	private ArrayList<Smartphone> phones;
	
	private ArrayList<Cluster> clusters;
	

	
	//k must be smaller than s's size
	public KMeansVector(int k, ArrayList<Smartphone> s)
	{
		kValue = k;
		clusters = new ArrayList<>();
		for (int i = 0; i < kValue; i++)
		{
			clusters.add(new Cluster());
		}
		phones = s;
	}
	
	
	public void formClusters()
	{
		Collections.shuffle(phones);
		//make a deep copy of the arraylists for the centroid  
		for(int i = 0; i < kValue; i++)
		{
			ArrayList<Double> temporary = new ArrayList<Double>();
			for (int j = 0; j < phones.get(i).attributes.size(); j++)
				temporary.add(phones.get(i).attributes.get(j));
			
			clusters.get(i).centroid= temporary;
			
		}
		
		//double RSS = Double.MAX_VALUE;
		//double deltaRSS = Double.MAX_VALUE;
		for (int loopcount = 0; loopcount < 10000; loopcount++)
		{
			for (int i = 0; i < kValue; i++)
			{
				clusters.get(i).clusterphones.clear();
			}
			//recalculate clusters
			for (int i = 0; i < phones.size(); i++)
				clusters.get(findMin(phones.get(i).attributes,clusters)).clusterphones.add(phones.get(i));
				
			//recalculate centroids
			for (int i =0 ; i < kValue; i++)
			{
				ArrayList<Double> calculating = new ArrayList<>();
				for (int j = 0; j < phones.get(0).attributes.size(); j++)
					calculating.add(0.0);
			
				//for each phone in the cluster
				//clusters.get(i) = current cluster
				//clusters.get(i).clusterphones.get(j) = current cluster's jth phone
				//clusters.get(i).clusterphones.get(j).attributes.get(k) = current cluster's jth phone's kth attribute
				
				for (int j = 0; j < clusters.get(i).clusterphones.size(); j++)
				{
					for (int k = 0; k < clusters.get(i).clusterphones.get(j).attributes.size(); k++)
						calculating.set(k, calculating.get(k) + clusters.get(i).clusterphones.get(j).attributes.get(k));
				}
				
				for (int j = 0; j < calculating.size(); j++)
					calculating.set(j, calculating.get(j)/clusters.get(i).clusterphones.size());
				clusters.get(i).centroid = calculating;
			}
			
			
			
		}
		
		
	}
	
	
	
	
	//returns index number of arraylist of clusters to which the current arraylist belongs to
	private int findMin(ArrayList<Double> current, ArrayList<Cluster> things)
	{
		int min=0;
		double smallestDistance = Double.MAX_VALUE; 
		for (int i = 0; i < things.size(); i++)
		{
			double distance = 0;
			for (int j = 0; j < things.get(i).centroid.size();j++)
			{
				distance += Math.pow(current.get(j) - things.get(i).centroid.get(j),2.0); 
			}
			distance = Math.sqrt(distance);
			if (distance < smallestDistance)
			{
				min = i;
				smallestDistance = distance;
			}
		}
		
		return min;
		
	}
	
	public ArrayList<Cluster> getClusters()
	{
		return clusters;
	}
	
	

}
