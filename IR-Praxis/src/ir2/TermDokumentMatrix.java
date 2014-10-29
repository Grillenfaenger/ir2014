package ir2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTable.PrintMode;

import ir1.Corpus;

public class TermDokumentMatrix implements InformationRetrieval {

	private boolean[][] matrix;
	/*
	 * positions hier als Klassenvariable, damit sie für die search-Methode
	 * genutzt werden können
	 */
	private Map<String, Integer> positions;

	public TermDokumentMatrix(Corpus corpus) {

		// Zeitmessung für Vergleich mit linearer Suche:
		long start = System.currentTimeMillis();
		System.out.println("Erstelle Matrix ...");

		List<String> works = corpus.getWorks();
		List<String> terms = getTerms(works);
		positions = getPositions(terms);// 'Zeilennummern' (s.u.)
		matrix = new boolean[terms.size()][works.size()];

		System.out.println("Größe der Matrix: " + terms.size() + " X "
				+ works.size());

		for (int spalte = 0; spalte < works.size(); spalte++) {
			String[] tokens = works.get(spalte).split("\\s+");
			for (int j = 0; j < tokens.length; j++) {
				String term = tokens[j];// das aktuelle Token
				int zeile = positions.get(term);// Zeilennummer des Tokens
				matrix[zeile][spalte] = true;
			}
		}

		System.out.println("Matrix erstellt, Dauer: "
				+ (System.currentTimeMillis() - start) + " ms.");

		printMatrix(terms);// optional
	}

	/*
	 * Legt die 'Zeilennummern' der Terme in eine Map (für schnellen Zugriff).
	 */
	private Map<String, Integer> getPositions(List<String> terms) {
		Map<String, Integer> pos = new HashMap<String, Integer>();
		for (int i = 0; i < terms.size(); i++) {
			pos.put(terms.get(i), i);
		}
		return pos;
	}

	/*
	 * Ermittelt die Terme aller Werke. Das Set wird abschließend in eine Liste
	 * umgewandelt, da der Listen-Zugriff über get(index) sowohl das Mappen der
	 * Positionen als auch das ausgeben der Matrix in der printMatrix-Methode
	 * erleichtert (s.u.)
	 */
	private List<String> getTerms(List<String> works) {
		Set<String> allTerms = new HashSet<String>();
		for (String work : works) {
			List<String> termsInCurrentWork = Arrays.asList(work.split("\\s+"));
			allTerms.addAll(termsInCurrentWork);// füge zu Gesamtliste hinzu
		}
		return new ArrayList<String>(allTerms);
	}

	/*
	 * Optionale Ausgabe der Matrix
	 */
	private void printMatrix(List<String> terms) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print((matrix[i][j]) ? "1 " : "0 ");
			}
			System.out.println(terms.get(i) + " ");
		}
	}

	/*
	 * Die eigentliche Suche.
	 */
	@Override
	public Set<Integer> search(String query) {
		Set<Integer> result = new HashSet<Integer>();

		// TODO: Matrix-Suche, Boolesche Operatoren

		return result;
	}

}
