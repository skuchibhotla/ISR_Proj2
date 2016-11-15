import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;


public class Projecttwo {
	public static Map<String, ArrayList<String>> dataContent;
	public static List<Documents> docList;

	Projecttwo() {
		dataContent = new HashMap<String, ArrayList<String>>();
		 docList =new ArrayList<Documents>();
	}

	public int processing(String fileName) {
		BufferedReader br = null;
		String sCurrentLine;
		try
		{
		br = new BufferedReader(new FileReader("Dataset.txt"));
	/*	if (br.readLine() == null) {
		    System.out.println("No errors, and file empty");
		    br.close();
		    return -1;
		}*/
		while ((sCurrentLine = br.readLine()) != null) {
			String[] split_Name = sCurrentLine.split("\\s+", 2);

			String newsGroupName = split_Name[0];
			String recordString = null;
			if (split_Name.length > 1) {
				recordString = split_Name[1];
			}
			ArrayList<String> r = new ArrayList<String>();
			if (dataContent.containsKey(newsGroupName)) {
				r = dataContent.get(newsGroupName);
			}
			r.add(recordString); 
			dataContent.put(newsGroupName, r);	
		}
		br.close();
		}catch(Exception e){
			System.out.println("Exception occured "+e);
			return -1;
		}
		
		return 1;
	}

	public static void main(String[] args) {

		Projecttwo p2 = new Projecttwo();
		BufferedReader br = null;
		
			int i = p2.processing("dataset");
			if (i == 1) {		
	                    Set<String> newsGroupset = dataContent.keySet();
						for (String newsGrp : newsGroupset) {
							System.out.println(newsGrp);
							Documents d =new Documents(newsGrp,dataContent.get(newsGrp));
							docList.add(d);
						}
       			for(Documents d : docList){
				d.wordFrequency = p2.calculateWordFrequency(d.docContent);
				d.wordsinDocument = d.wordFrequency.keySet();
				System.out.println(d.wordsinDocument);
				d.weightIFIDF1 = p2.calculatetfidf1(d.wordFrequency);
				System.out.println(d.weightIFIDF1);
//				d.weightIFIDF2 = p2.calculate-tfidf1(d.wordFrequency);
//				d.weightIFIDF3 = p2.calculate-tfidf1(d.wordFrequency);
//				
					
				}
						
				
//			}  catch (Exception e) {
//			// TODO Auto-generated catch block
//				System.out.println("Excpetion occured "+e);
//			
//		}
			}
	}



	private int caluculateCount(ArrayList <String> newsgroupContent, String word)
	{
		int count = 0;
		for (String record : newsgroupContent)
		{
			String[] words = record.split("\\s+");
			for (String tempWord : words) {
				if (tempWord.equals(word))
					count = count + 1;
			}
			if(record.contains(word))
			{
				count = count + 1;
			}
		}
		return count;
	}
	private Map<String,Integer> calculateWordFrequency(List<String>docContent){
		 Map<String,Integer> wordFrequency = new HashMap<String, Integer>();
		 for (String record : docContent){			
			 String[] words = record.split("\\s+");
			 for (String word : words) {
				 int frequency = 0;
				 if(wordFrequency.get(word)!=null){
					 frequency = wordFrequency.get(word);
				 }
				 frequency = frequency + 1;
				 System.out.println("word is "+word+"frequncy is "+frequency);
				 wordFrequency.put(word, frequency);
			 }
			 System.out.println(wordFrequency);
		 }
		 return wordFrequency;
	 }
	private Map<String,Double> calculatetfidf1(Map<String,Integer>wordFrequency ){
		
		Map<String,Double> tfidf1 = new HashMap<String, Double>();
		for (String word : (wordFrequency.keySet())){
			double idf = calIDF(word);
			double tfidf = wordFrequency.get(word) * idf;
			tfidf1.put(word,tfidf);
		}
		return tfidf1;
	}
	private Map<String,Double> calculatetfidf2(Map<String,Integer>wordFrequency ){
		
		Map<String,Double> tfidf2 = new HashMap<String, Double>();
		
		return tfidf2;
	}
	private Map<String,Double> calculatetfidf3(Map<String,Integer>wordFrequency ){
		
		Map<String,Double> tfidf3 = new HashMap<String, Double>();
		
		return tfidf3;
	}
private double calIDF(String word){
	double d = 0;
	double N = 20;
	double n = 0;
	for(Documents doc : docList){
		if(doc.wordFrequency.containsKey(word)){
			n = n+1;
		}
	}
	d = Math.log(N/n)/Math.log(2);
	return d;
	}	
}

class Documents
{
	 String name;
	 Map<String, Integer> wordFrequency;
	 double docDet;
	 Set<String> wordsinDocument;
	 //Map<String, Double> weightIDoc;
	 Map<String, Double> weightIFIDF1;
	 Map<String, Double> weightIFIDF2;
	 Map<String, Double> weightIFIDF3;
	 ArrayList<String> docContent;
	 Documents(String name,ArrayList<String> docContent){
		 this.name = name;
		 this.docContent = docContent;
	 }
	 void calculateWordFrequency(){
		 wordFrequency = new HashMap<String, Integer>();
		 for (String record : docContent){			
			 String[] words = record.split("\\s+");
			 for (String word : words) {
				 int frequency = 0;
				 if(wordFrequency.get(word)!=null){
					 frequency = wordFrequency.get(word);
				 }
				 frequency = frequency + 1;
				 System.out.println("word is "+word+"frequncy is "+frequency);
				 wordFrequency.put(word, frequency);
			 }
			 System.out.println(wordFrequency);
		 }
		 this.wordsinDocument = wordFrequency.keySet();
		 
	 }
	 void calculateTFIDF(){
		  
	 }
}
