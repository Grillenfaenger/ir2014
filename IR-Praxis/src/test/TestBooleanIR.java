package test;

import static org.junit.Assert.assertTrue;
import ir1.Corpus;
import ir2.LinearSearch;
import ir2.TermDokumentMatrix;
import ir3.InvertedIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBooleanIR {

	private static Corpus corpus;
	private static String query;

	@BeforeClass
	public static void setUp() throws Exception {
		// Korpus einlesen und in Werke unterteilen:
		String filename = "pg100.txt";
		String delimiter = "1[56][0-9]{2}\n";
		corpus = new Corpus(filename, delimiter);
	}

	@Test
	public void testCorpus() throws Exception {
		// Testen, ob Korpus korrekt angelegt wurde:
		List<String> works = corpus.getWorks();
		System.out.println("Anzahl der Werke: " + works.size());
		assertTrue("Korpus sollte mehr als 1 Werk enthalten", works.size() > 1);
	}

	@Test
	public void testLinearSearch() {
		// Testen, ob lineare Suche ein Ergebnis liefert:

		System.out.println();
		System.out.println("Lineare Suche:");
		System.out.println("-------------------");
		LinearSearch linear = new LinearSearch(corpus);

		query = "Brutus";
		Set<Integer> result = linear.search(query);
		System.out.println("Ergebnis für " + query + ": " + result);
		assertTrue("Mindestens ein Treffer erwartet", result.size() >= 1);

		query = "Brutus Caesar";
		Set<Integer> result2 = linear.search(query);
		System.out.println("Ergebnis für " + query + ": " + result2);
		assertTrue("Ergebnis-Set sollte größer sein als bei einzelnem Term",
				result2.size() >= result.size());
	}

	@Test
	public void testMatrixSearch() {
		// Testen, ob Suche in Term-Dokument-Matrix ein Ergebnis liefert:

		System.out.println();
		System.out.println("Term-Dokument-Matrix:");
		System.out.println("-------------------");
		TermDokumentMatrix matrix = new TermDokumentMatrix(corpus);

		query = "Brutus";
		Set<Integer> result = matrix.search(query);
		System.out.println("Ergebnis für " + query + ": " + result);
		assertTrue("Mindestens ein Treffer erwartet", result.size() >= 1);

		query = "Caesar";
		Set<Integer> result1 = matrix.search(query);
		System.out.println("Ergebnis für " + query + ": " + result1);
		assertTrue("Mindestens ein Treffer erwartet", result1.size() >= 1);

		query = "Brutus Caesar";
		Set<Integer> result2 = matrix.search(query);
		System.out.println("Ergebnis für " + query + ": " + result2);
		assertTrue("Ergebnis-Set sollte größer sein als bei einzelnem Term",
				result2.size() >= result.size());

		query = "Brutus Caesar";
		Set<Integer> result3 = matrix.booleanSearch(query);
		System.out.println("Ergebnis für " + query + ": " + result3);
		assertTrue("Ergebnis-Set sollte kleiner sein als bei einzelnem Term",
				result2.size() >= result.size());
}

	@Test
	public void testIndexSearch() {
		// Testen, ob Suche in invertiertem Index ein Ergebnis liefert:

		System.out.println();
		System.out.println("Invertierter Index:");
		System.out.println("-------------------");
		InvertedIndex index = new InvertedIndex(corpus);

		query = "Brutus";
		Set<Integer> result = index.search(query);
		assertTrue("Mindestens ein Treffer erwartet", result.size() >= 1);
		System.out.println("Ergebnis für " + query + ": " + result);

		query = "Brutus Caesar";
		Set<Integer> result2 = index.search(query);
		assertTrue("Ergebnis-Set sollte größer sein als bei einzelnem Term",
				result2.size() >= result.size());
		System.out.println("Ergebnis für " + query + ": " + result2);
	}

	/*
	 * Als kleine Ergänzung hier eine einfache Testmethode, um verschiedene
	 * Werkeinteilungen auszuprobieren: Der im Seminar genutzte delimiter
	 * "1[56][0-9]{2}\n" ist nur EINE Möglichkeit, die Werke zu unterteilen,
	 * mögliche Alternativen finden sich unten in der Methode.
	 */
	@Ignore
	@Test
	public void testRegEx() {

		String delimiter = "1[56][0-9]{2}\n";
		// String delimiter = "by William Shakespeare";
		// String delimiter = "THE END\n";

		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile(delimiter).matcher(corpus.getText());
		while (m.find()) {
			allMatches.add(m.group());
		}
		System.out.println("# of matches: " + allMatches.size());
		System.out.println(allMatches.toString());
	}

}
