package ir6;

import ir5.Document;

import java.util.List;

/*
 * Evaluation (Precision, Recall, F-Maß) einer Query und einer Dokumentenmenge gegen einen Goldstandard.
 */
public class Evaluation {

	private List<Document> relevant;

	public Evaluation(List<Document> gold) {
		this.relevant = gold;
	}

	public void evaluate(List<Document> retrieved) {
		/*
		 * TODO: Für ein Suchergebnis sollen Precision, Recall und F-Maß
		 * errechnet werden. Grundlage sind die "true positives", die anhand des
		 * Goldstandards ermittelt werden. Wie könnte ein sinnvoller
		 * Rückgabewert der Evaluation aussehen?
		 */
	}
}
