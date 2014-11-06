package ir3;

import ir1.Corpus;
import ir2.InformationRetrieval;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class InvertedIndex implements InformationRetrieval {

	// der invertierte Index für die spätere Suche
	private Map<String, SortedSet<Integer>> index = new HashMap<String, SortedSet<Integer>>();
	// eine Instanz des Preprocessors für Indexierung und Query-Verarbeitung
	private static final Preprocessor PREPROCESSOR = new Preprocessor();

	public InvertedIndex(Corpus corpus) {
		long start = System.currentTimeMillis();
		index = index(corpus);
		System.out.println("Index erstellt, Dauer: "
				+ (System.currentTimeMillis() - start) + " ms.");
	}

	private Map<String, SortedSet<Integer>> index(Corpus corpus) {
		
		// TODO: Indexierung implementieren!
		
		return null;
	}

	@Override
	public Set<Integer> search(String query) {

		long start = System.currentTimeMillis();
		Set<Integer> result = new HashSet<Integer>();

		// TODO Suche implementieren!

		System.out.println("Suchdauer: " + (System.currentTimeMillis() - start)
				+ " ms.");
		return result;
	}

}
