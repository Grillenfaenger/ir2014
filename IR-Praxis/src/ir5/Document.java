package ir5;

import ir4.Preprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Document {
	private String text;
	private String title;
	private Map<String, Integer> tf;
	private List<String> tokens;
	private static Preprocessor PREPROCESSOR = new Preprocessor();

	public Document(String text, String title) {
		this.text = text;
		this.title = title;
		this.tokens = PREPROCESSOR.tokenize(text);
		this.tf = computeTf();
	}

	private Map<String, Integer> computeTf() {
		Map<String, Integer> termMap = new HashMap<String, Integer>();
		/* Wir zählen die Häufigkeiten der Tokens: */
		for (String token : tokens) {
			Integer tf = termMap.get(token);
			/*
			 * Wenn der Term noch nicht vorkam, beginnen wir zu zählen (d.h. wir
			 * setzen 1)
			 */
			if (tf == null) {
				tf = 1;
			} else {// sonst zählen wir einfach bei jedem Vorkommen hoch
				tf = tf + 1;
			}
			termMap.put(token, tf);
		}
		return termMap;
	}

	public Set<String> getTerms() {
		return tf.keySet();
	}

	/*
	 * Mit dem Überschreiben der toString()-Methode sorgen wir dafür, dass bei
	 * Ausgabe des Document-Objekts mit sysout nur der Titel ausgegeben wird
	 */
	@Override
	public String toString() {
		return title;
	}

	/*
	 * Gibt einen Vektor mit tfIdf-Werten zu diesem Document zurück.
	 */
	public List<Double> computeVector(InvertedIndex index) {
		Set<String> terms = index.getTerms();
		/*
		 * Ein Vektor für dieses Dokument ist eine Liste (Länge = Anzahl Terme)
		 */
		List<Double> docVector = new ArrayList<Double>(terms.size());
		Double tfIdfValue;
		/* ...und der Vektor enthält für jeden Term im Vokabular... */
		for (String t : terms) {
			/*
			 * ...den tfIdf-Wert des Terms (Berechnung in einer eigene Klasse):
			 */
			tfIdfValue = TermWeighting.tfIdf(t, this, index);// TODO: errechnen!
			docVector.add(tfIdfValue);
		}
		return docVector;
	}

}
