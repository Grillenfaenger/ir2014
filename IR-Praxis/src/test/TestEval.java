package test;

import ir5.Corpus;
import ir5.Document;
import ir5.InformationRetrieval;
import ir5.InvertedIndex;
import ir5.Ranker;
import ir6.Evaluation;
import ir6.GoldStandard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestEval {

	// Basiskomponenten unseres IR-Systems:
	private static Corpus corpus;
	private static InformationRetrieval index;
	private static Ranker ranker;
	private static String query;

	// zu evaluierende Ergebnislisten (mit/ohne Ranking):
	private static List<Document> unranked;
	private static List<Document> ranked;

	// die Evaluation erfolgt gegen einen Goldstandard:
	private static List<Document> gold;
	private static Evaluation evaluation;


	@BeforeClass
	public static void setup() {
		/* Zunächst muss alles initialisiert werden */
		corpus = new Corpus("pg100.txt", "1[56][0-9]{2}\n", "\n");
		index = new InvertedIndex(corpus);
		ranker = new Ranker(query, index);
		
		query = "king";

		/* ... dann holen wir uns die Suchergebnisse ... */
		Set<Document> result = index.search(query);
		unranked = new ArrayList<Document>(result);
		System.out.println(unranked.size() + " ungewichtete Ergebnisse für "
				+ query);
		print(unranked);
		ranked = ranker.rank(result);
		System.out.println(ranked.size() + " gewichtete Ergebnisse für "
				+ query);
		print(ranked);

		/*
		 * ... die gegen einen Goldstandard evaluiert werden. Für die Übung
		 * beschränken wir uns auf eine Liste von Dokumenten für eine query:
		 */
		gold = GoldStandard.create(index, query);
		System.out.println("Der Goldstandard: " + gold.size()
				+ " relevante Dokumente für Query '" + query + "'");
		print(gold);
		/* und initialisieren die Evaluation: */
		evaluation = new Evaluation(gold);
	}

	@Before
	public void printSkip() {
		System.out.println();
	}

	@Test
	public void evalUnranked() {
		/* TODO: Wir evaluieren zunächst alle Ergebnisse gegen den Goldstandard. */
	}

		/* TODO: Und nun mit Ranking - was müsssen wir beachten? */


	/*
	 * Hilfsmethode für die Ausgabe der Ergebnislisten (inkl. Goldstandard)
	 */
	public static void print(List<Document> resultList) {
		System.out.println("-------------------------------");
		for (Document document : resultList) {
			System.out.println(document);
		}
		System.out.println("-------------------------------");
	}
}
