package ir3;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * Die Klasse Intersection bietet nur eine statische Methode 'of', die die
 * Schnittmenge zweier Sets ermittelt (eben die 'intersection of').
 */

public class Intersection {

	/*
	 * Implementierung der Listen-Intersection, die die Sortierung der Listen
	 * ausnutzt, fast Zeile-für-Zeile umgesetzt wie in Manning et al. 2008, S.
	 * 11, beschrieben.
	 */
	public static SortedSet<Integer> of(SortedSet<Integer> pl1,
			SortedSet<Integer> pl2) {

		Iterator<Integer> it1 = pl1.iterator();
		Iterator<Integer> it2 = pl2.iterator();
		// ein einfaches 'next()' reicht leider nicht - s. Kommentar unten
		Integer p1 = nextOrNull(it1);
		Integer p2 = nextOrNull(it2);

		SortedSet<Integer> answer = new TreeSet<>();
		while (p1 != null && p2 != null) {
			if (p1 == p2) {
				answer.add(p1);
				p1 = nextOrNull(it1);
				p2 = nextOrNull(it2);
			} else if (p1 < p2) {
				p1 = nextOrNull(it1);
			} else {
				p2 = nextOrNull(it2);
			}
		}
		return answer;
	}

	/*
	 * Ein wenig müssen wir uns verbiegen um nah am Pseudocode zu bleiben: Wir
	 * müssen u.a. NoSuchElementExceptions vermeiden (wenn der Iterator keine
	 * Elemente mehr hat).
	 */
	private static Integer nextOrNull(Iterator<Integer> it1) {
		return it1.hasNext() ? it1.next() : null;
	}

	// Eine alternative Umsetzung mithilfe der Java-API:
	public static SortedSet<Integer> ofApi(SortedSet<Integer> pl1,
			SortedSet<Integer> pl2) {
		// Wir brauchen ein neues set, da sonst das Teilergebnis verändert wird:
		SortedSet<Integer> answer = new TreeSet<Integer>(pl1);
		answer.retainAll(pl2);
		return answer;
	}

}
