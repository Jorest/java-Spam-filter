import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SpamFilter{
	static float k =2;
	static int totalm=5576;
	
	public static void main(String argv[]) throws IOException{
		SortCorpus getS = new SortCorpus();
		getS.readCorpus();
		getS.SelectData(5576);
		getS.SelectSet(getS.training);
	   
		readfile(getS);
	    System.out.println("fin");

		
	}

	
	
	public static double SpamMessage(String mensaje, ArrayList<String> spamP, ArrayList<String> hamP,int totalPal, int totalSpam,int  totalmen){
   	 	double ps=1;
		//vamos a calcular la cantidad de spam por palabra y multiplicarlos
		String[] words =mensaje.split(" ");
		for (int i = 0; i < words.length; i++) {
            int countS=0;
			words[i] = words[i];
            Set<String> unique = new HashSet<String>(spamP);
            
            for (String key : unique) {
            
            	if (key.equals(words[i])){
            		countS= Collections.frequency(spamP, key);
                }
            }
            
            ps=(float)ps* ((float)(countS+k)/(spamP.size()+k*totalPal)); 
		}
		
		
		double ph=1;
		//vamos a calclar la cantidad de spam por palabra y multiplicarlos
		String[] words2 =mensaje.split(" ");
		for (int i = 0; i < words2.length; i++) {
            int countS=0;
			words2[i] = words2[i];
            Set<String> unique = new HashSet<String>(hamP);
            
            for (String key : unique) {
            	if (key.equals(words2[i])){
            		countS= Collections.frequency(hamP, key);
                }
            }
          ph=(float)ph*((float)(countS+k)/(hamP.size()+k*totalPal));   
		}

		
	
		double pSpa= 1*((float)(totalSpam+k)/(totalmen+k*2));
		double result=(float)((float) ps*pSpa/(ps+ph))/pSpa;
		
		return result ;
	
	}
	//crosvalidation
	public static int CrossVal(ArrayList<Mensaje> set ,SortCorpus getS){
		int aciertos=0;
		for (int i=0; i< set.size();i++){
			double pro = SpamMessage(set.get(i).getMensaje(),getS.palabrasS,getS.palabrasH,getS.palabras.size(),getS.counts, getS.training.size());

			if (pro>0.51){
				if (set.get(i).getTipo()==1){
					aciertos++;
				}
			}
			else
			{
				if (set.get(i).getTipo()==0){
					aciertos++;
				}
			}
			
		}
		return aciertos;
	}
	
	//calculamos la mejor k para el suavisado de Laplace
	public static boolean setK (SortCorpus getS){
		int maxA=0;
		for (int i=0; i< 5 ; i+=0.2){
		    int as =CrossVal(getS.crossVal , getS);
		    if (as>maxA){
		    	maxA=as;
		    }
		    else {
		    	return false ;
		    }
		 	k++;
		 	System.out.println(k);
		}
		return true;
	}
	
	public static void readfile (SortCorpus getS) throws FileNotFoundException, IOException{
		int counts =0;
		int counth =0;
		int count2 =0;
		try (BufferedReader br = new BufferedReader(new FileReader("D:/Users/New folder/Documents/workspace/SpamFillter/src/mensaje.txt")) ){
			  PrintWriter out = new PrintWriter(new FileWriter("D:/Users/New folder/Documents/workspace/SpamFillter/src/respuesta.txt"));

			for(String line; (line = br.readLine()) != null; ) {
				//sanitizamos la entrada	
	        	 String san = line.replaceAll("[^a-zA-Z0-9\\s\']+","");
				 double pro = SpamMessage(san,getS.palabrasS,getS.palabrasH,getS.palabras.size(),getS.counts, getS.training.size());
				 if (pro>0.51){
					 counts ++;
					 out.println("ham	"+san);
					 System.out.println("ham	"+san);
					 
				 }
				 else 
				 {	 counth ++;
					 out.println("spam	"+san);
					 System.out.println("spam	"+san);
				 }
			count2++;
			 }
		}

		
		System.out.println("cantidad de spam: "+counts);
		System.out.println("cantidad de ham: "+counth);
		//System.out.println("porcentaje"+count*100/count2);
	}

}
