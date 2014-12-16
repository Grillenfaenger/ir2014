package test;

import static org.junit.Assert.assertTrue;
import ir1.Corpus;
import ir3.InvertedIndex;

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
	private Set<Integer> result;

	@Before
	public void setUp() throws Exception {
		corpus = new Corpus("pg100.txt", "1[56][0-9]{2}\n");
		index = new InvertedIndex(corpus);
		query = "brutus caesar";
	}

	@Test
	public void unrankedResults() {
		result = index.search(query);
		System.out.println(result.size() + " ungerankte Treffer für " + query);
		assertTrue("Ergebnis sollte nicht leer sein!", result.size() > 0);
		print(new ArrayList(result));
	}

	@Ignore
	@Test
	public void resultRanked() {
		/*
		 * TODO: Und jetzt mit Ranking ...
		 */
	}

	/*
	 * Hilfsmethode, um Ergebnisse übersichtlicher darzustellen.
	 */
	public void print(List resultList) {
		System.out.println("-------------------------------");
		for (Object document : resultList) {
			System.out.println(document);
		}
		System.out.println("-------------------------------");
	}
}
