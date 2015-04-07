package de.uni_koeln.spinfo.ir.ir6;

import java.util.ArrayList;
import java.util.List;

import de.uni_koeln.spinfo.ir.ir4.Preprocessor;
import de.uni_koeln.spinfo.ir.ir5.Document;
import de.uni_koeln.spinfo.ir.ir5.InformationRetrieval;

/*
 * Erstellung eines Dummy-Goldstandards auf Grundlage unseres Shakespeare-Korpus
 */
public class GoldStandard {
	
	  public static List<Document> create(InformationRetrieval index, String query) {
		  
		  List<Document> result = new ArrayList<Document>();
		  Preprocessor p = new Preprocessor();
		  List<String> q = p.process(query);
		  
		    for (Document d : index.getWorks()) {
		        /*
		         * Für unsere Experimente mit P, R und F betrachten wir ein Dokument immer dann als relevant,
		         * wenn ein Elemente der Anfrage im Titel des Dokuments enthalten ist:
		         */
		    	if(containsAny(d.getTitle(),q)){
		    		result.add(d);
		    	}
		    }
		    return result;
		  }

	private static boolean containsAny(String title, List<String> query) {
		for (String q : query) {
		      /* Wir geben true zurück wenn ein Element der Anfrage im Titel enthalten ist: */
			if(title.toLowerCase().contains(q.toLowerCase())){
				return true;
			}
		}
		return false;
	}
}
