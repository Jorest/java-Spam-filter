import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class SortCorpus{

	ArrayList<String> Ham = new ArrayList<String>();
	ArrayList<String> Spam = new ArrayList<String>();
	
	ArrayList<Mensaje> rawData  = new ArrayList<Mensaje>();
	
	ArrayList<Mensaje> training  = new ArrayList<Mensaje>();
	ArrayList<Mensaje> crossVal  = new ArrayList<Mensaje>();
	ArrayList<Mensaje> test  = new ArrayList<Mensaje>();
	
	ArrayList<String> palabras = new ArrayList<String>();
	ArrayList<String> palabrasS = new ArrayList<String>();
	ArrayList<String> palabrasH = new ArrayList<String>();
	int counth;
	int counts;
	/**
	 * @throws IOException
	 */
	public void readCorpus() throws IOException{
		try (BufferedReader br = new BufferedReader(new FileReader("D:/Users/New folder/Documents/workspace/SpamFillter/src/corpus.txt")) ){
			 for(String line; (line = br.readLine()) != null; ) {
				
				 String [] geter= line.split("\t");
		         
				 if (geter[0].equals("ham")) {
		        	 String [] temp= line.split("ham+\t");
		        	 //sanitizamos el texto para tener una data mas limpia
		        	 String san = temp[1].replaceAll("[^a-zA-Z0-9\\s\']+","");
		        	 Mensaje men= new Mensaje(0,san);
		        	 rawData.add(men);
		        	 Ham.add(san);
		        	 
		        	 String[] words = san.split(" ");
		             for (int i = 0; i < words.length; i++) {
		                 words[i] = words[i];
		                 if (!palabras.contains(words[i])){
		                	 palabras.add(words[i]);
		                 }
		                 
		             }
		            
		
	                }
				 
				 if (geter[0].equals("spam")) {
		        	 String [] temp= line.split("spam+\t");
		        	 String san = temp[1].replaceAll("[^a-zA-Z0-9\\s\']+","");
		        	 Mensaje men= new Mensaje(1,san);
		        	 rawData.add(men);
		        	 Spam.add(san);
		        	 
		        	 String[] words = san.split(" ");
		             for (int i = 0; i < words.length; i++) {
		                 words[i] = words[i];
		                 if (!palabras.contains(words[i])){
		                	 palabras.add(words[i]);
		                 }
		                		                 
		             }
		            
		        	}
	            
	               	            
			 }
			    
		
		}
		
		
				
		}
	public void SelectData(int total){
		ArrayList<Mensaje> data  = rawData;
		while (training.size()<(int)(total*0.8)){
			int num= (int)Math.random()*data.size() ;
			training.add(data.get(num));
			data.remove(num);			
		}
		
		while (crossVal.size()<(int)(total*0.1)){
			int num= (int)Math.random()*data.size() ;
			crossVal.add(data.get(num));
			data.remove(num);			
		}
		while (test.size()<(int)(total*0.1)){
			int num= (int)Math.random()*data.size() ;
			test.add(data.get(num));
			data.remove(num);			
		}
		
		
	}
	
	public void SelectSet(ArrayList<Mensaje> set){
		
		for (int i=0; i< set.size(); i++){
			if (set.get(i).getTipo()==0){
				counth++;
				String mensaje=set.get(i).getMensaje();
				String[] words = mensaje.split(" ");
	        	
				for (int j = 0; j < words.length; j++) {
	                 words[j] = words[j];
	                 if (!palabras.contains(words[j])){
	                	 palabras.add(words[j]);
	                 }
	                 palabrasH.add(words[j]);
	               	                 
	             } 
			}else
				
			{
				counts++;
				String mensaje=set.get(i).getMensaje();
				String[] words = mensaje.split(" ");
	        	
				for (int j = 0; j < words.length; j++) {
	                 words[j] = words[j];
	                 if (!palabras.contains(words[j])){
	                	 palabras.add(words[j]);
	                 }
	                 palabrasS.add(words[j]);
	               	                 
	             } 
			}
			
			
		}
	}
	

	}
	
