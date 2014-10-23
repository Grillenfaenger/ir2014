package test;

import static org.junit.Assert.assertTrue;
import ir1.Corpus;
import ir2.LinearSearch;
import ir2.TermDokumentMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestBooleanIR {

	private static Corpus corpus;

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
		String[] works = corpus.getWorks();
		System.out.println("Anzahl der Werke: " + works.length);
		assertTrue("Korpus sollte mehr als 1 Werk enthalten", works.length > 1);
	}

	@Test
	public void testLinearSearch() {
		// Testen, ob lineare Suche ein Ergebnis liefert:
		String query = "Brutus";
		LinearSearch linear = new LinearSearch(corpus);
		Set<Integer> result = linear.search(query);
		assertTrue("Mindestens ein Treffer erwartet", result.size() >= 1);
		System.out.println(result);
	}

	@Test
	public void testMatrixSearch() {
		// Testen, ob Suche in Term-Dokument-Matrix ein Ergebnis liefert:
		String query = "Brutus";
		TermDokumentMatrix matrix = new TermDokumentMatrix(corpus);
		Set<Integer> result = matrix.search(query);
		assertTrue("Mindestens ein Treffer erwartet", result.size() >= 1);
	}

	/*
	 * Als kleine Ergänzung hier eine einfache Testmethode, um verschiedene
	 * Werkeinteilungen auszuprobieren: Der im Seminar genutzte delimiter
	 * "1[56][0-9]{2}\n" ist nur EINE Möglichkeit, die Werke zu unterteilen,
	 * mögliche Alternativen finden sich unten in der Methode.
	 */
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
