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
		String sCurrentLine;
		
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
					d.weightIFIDF2 = p2.calculatetfidf2(d.wordFrequency);
					d.weightIFIDF3 = p2.calculatetfidf3(d.wordFrequency);
				//	System.out.println(d.weightIFIDF1);
				//	System.out.println(d.weightIFIDF2);
				//	System.out.println(d.weightIFIDF3);		
				}
				
				try
				{
				br = new BufferedReader(new FileReader("input.txt"));
				while ((sCurrentLine = br.readLine()) != null) {
					String[] queryTerms = sCurrentLine.split(",");
					ArrayList<String> list =new ArrayList<String>();
					for (String word:queryTerms){
						list.add(word);
					}
					Documents query = new Documents("Query",list);
					query.wordFrequency = p2.calculateWordFrequency(query.docContent);
					query.weightIFIDF3 = p2.calculatetfidf3(query.wordFrequency);
					
					}
				br.close();
				}catch(Exception e){
					System.out.println("thsinis fileerror"+e);
				}
			}
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
		for (String word : (wordFrequency.keySet())){
			double tfidf = 1 + Math.log(wordFrequency.get(word))/Math.log(2);
			tfidf2.put(word,tfidf);
		}
		return tfidf2;
	}
	private Map<String,Double> calculatetfidf3(Map<String,Integer>wordFrequency ){
		
		Map<String,Double> tfidf3 = new HashMap<String, Double>();
		for (String word : (wordFrequency.keySet())){
			double idf = calIDF(word);
			double tfidf = (1 + Math.log(wordFrequency.get(word))/Math.log(2)) * (idf);
			tfidf3.put(word,tfidf);
		}
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