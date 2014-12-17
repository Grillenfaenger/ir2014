package test;

import static org.junit.Assert.assertTrue;
import ir5.Corpus;
import ir3.InvertedIndex;
import ir5.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestRankedIR {

	private Corpus corpus;
	private String query;
	private InvertedIndex index;
	private Set<Document> result;

	@Before
	public void setUp() throws Exception {
		corpus = new Corpus("pg100.txt", "1[56][0-9]{2}\n", "\n");
		index = new InvertedIndex(corpus);
		query = "brutus caesar";
	}

	@Test
	public void unrankedResults() {
		result = index.search(query);
		System.out.println(result.size() + " ungerankte Treffer für " + query);
		assertTrue("Ergebnis sollte nicht leer sein!", result.size() > 0);
		print(new ArrayList<Document>(result));
	}

	@Test
	public void resultRanked() {

		
		
	}

	/*
	 * Hilfsmethode, um Ergebnisse übersichtlicher darzustellen.
	 */
	public void print(List<Document> resultList) {
		System.out.println("-------------------------------");
		for (Object document : resultList) {
			System.out.println(document);
		}
		System.out.println("-------------------------------");
	}
}
