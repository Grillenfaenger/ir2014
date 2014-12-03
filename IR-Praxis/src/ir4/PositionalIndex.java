package ir4;

import ir1.Corpus;
import ir2.InformationRetrieval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

/*
 * Erweiterung des invertierten Index: Zusätzlich zu den Werken werden auch
 * jeweils die Positionen des Terms im Werk gespeichert. Der Ursprüngliche
 * Index ist nach wie vor enthalten und kann auf die gewohnte Weise abgefragt werden.
 */
public class PositionalIndex implements InformationRetrieval {

	// PosIndex: Zu jedem Term alle Werke inkl. der Position des Terms
	private Map<String, SortedMap<Integer, List<Integer>>> posIndex;
	// eine Instanz des Preprocessors
	private static final Preprocessor PREPROCESSOR = new Preprocessor();

	public PositionalIndex(Corpus corpus) {
		long start = System.currentTimeMillis();
		posIndex = index(corpus);
		System.out.println("Index erstellt, Dauer: "
				+ (System.currentTimeMillis() - start) + " ms.");
	}

	/*
	 * Statt Postings-Listen jetzt Postings-Maps mit Pos-Listen für jedes Werk.
	 */
	private Map<String, SortedMap<Integer, List<Integer>>> index(Corpus corpus) {
		// Rückgabetyp = Datentyp des Index
		Map<String, SortedMap<Integer, List<Integer>>> index = new HashMap<String, SortedMap<Integer, List<Integer>>>();
		// wir indexieren wieder Werk für Werk:
		List<String> works = corpus.getWorks();
		for (int i = 0; i < works.size(); i++) {
			/*
			 * Für die Positionen müssen wir über die Tokens laufen - dafür muss
			 * der Preprocessor angepasst werden (Tokens statt Terme)
			 */
			List<String> tokens = PREPROCESSOR.tokenize(works.get(i));
			// wir nutzen den Zähler der for-Schleife (Zähler = Position)
			for (int j = 0; j < tokens.size(); j++) {
				String t = tokens.get(j);
				// wie bisher zunächst postings holen (hier eine Map):
				SortedMap<Integer, List<Integer>> postings = index.get(t);
				// bei erstem Aufruf initialisieren:
				if (postings == null) {
					postings = new TreeMap<Integer, List<Integer>>();
					index.put(t, postings);
				}
				// ebenso bei den Positionen:
				List<Integer> posList = postings.get(i);// aktuelles Werk
				if (posList == null) {
					posList = new ArrayList<Integer>();
				}
				posList.add(j);// Position des aktuellen Tokens
				/*
				 * Das Wort wird indexiert, indem die Id des aktuellen Werks (=
				 * der aktuelle Zählerwert) zusammen mit der aktualisierten
				 * Positions-Liste der postings-Map hinzugefügt wird:
				 */
				postings.put(i, posList);
			}
		}
		return index;
	}

	/*
	 * Die 'einfache' Index-Suche: Gibt Werke zurück, die (Teil-)queries
	 * enthalten. Einziger Unterschied: Zugriff auf Postings über keySet().
	 */
	@Override
	public Set<Integer> search(String query) {
		long start = System.currentTimeMillis();
		List<String> queries = PREPROCESSOR.process(query);
		List<SortedSet<Integer>> allPostings = new ArrayList<SortedSet<Integer>>();
		for (String q : queries) {
			// Einzige Veränderung: Wir nehmen das keySet der Postings-Maps
			SortedSet<Integer> postings = (SortedSet<Integer>) posIndex.get(q)
					.keySet();
			allPostings.add(postings);
		}
		Collections.sort(allPostings, new Comparator<SortedSet<Integer>>() {
			public int compare(SortedSet<Integer> o1, SortedSet<Integer> o2) {
				return Integer.valueOf(o1.size()).compareTo(o2.size());
			}
		});
		SortedSet<Integer> result = allPostings.get(0);
		for (SortedSet<Integer> set : allPostings) {
			result = Intersection.of(result, set);
		}
		System.out.println("Indexsuche: "
				+ (System.currentTimeMillis() - start) + " ms.");
		return result;
	}

	/*
	 * Suche mit Beschränkung durch 'Nähe'. Grundidee: Positional Index als
	 * erweiterte Indexstruktur - zuerst wie bisher die Werke ermitteln, in
	 * denen beide Terme vorkommen, dann die PositionalIntersection
	 * "zuschalten". Vorteil: einfach "einklinken", ohne den Rest zu verändern.
	 */
	public SortedMap<Integer, List<Integer>> proximitySearch(String query,
			int maxDistance) {
		long start = System.currentTimeMillis();
		// gleicher Preprocessor wie bei Indexierung!
		List<String> queries = PREPROCESSOR.tokenize(query);
		/*
		 * Statt Postings-Listen hier Postings-Maps der Teilqueries:
		 */
		List<SortedMap<Integer, List<Integer>>> allPostingsMaps = new ArrayList<>();
		for (String q : queries) {
			SortedMap<Integer, List<Integer>> postingsMap = posIndex.get(q);
			allPostingsMaps.add(postingsMap);
			// optionale Zwischenausgabe:
			// System.out.println(String.format("query: %s \t #hits: %s \t %s",
			// q, postingsMap.size(), postingsMap.keySet()));
		}
		// dann die Maps nach ihrer Länge sortieren:
		Collections.sort(allPostingsMaps, new Comparator<SortedMap>() {
			public int compare(SortedMap o1, SortedMap o2) {
				return Integer.valueOf(o1.size()).compareTo(o2.size());
			}
		});
		// Ergebnis ist die Schnittmenge (Intersection) der ersten Map...
		SortedMap<Integer, List<Integer>> result = allPostingsMaps.get(0);
		// ... mit allen weiteren:
		for (SortedMap<Integer, List<Integer>> postingsMap : allPostingsMaps) {
			result = Intersection.of(result, postingsMap, maxDistance);
		}
		System.out.println("Proximity-Suche: "
				+ (System.currentTimeMillis() - start) + " ms.");
		return result;
	}

	/*
	 * Ergebnisdarstellung: Ausgabe von Fundstellen und Werktitel
	 */
	public void printSnippets(String query,
			SortedMap<Integer, List<Integer>> result, int maxDistance) {

		// TODO: Ideen?

	}

}
