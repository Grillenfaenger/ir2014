package de.uni_koeln.spinfo.ir.ir3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * Einfacher Preprocessor: splittet und gibt sortierte Types zurück.
 */

final class Preprocessor {

	/*
	 * Ein Unicode-wirksamer Ausdruck für "Nicht-Buchstabe", der auch Umlaute
	 * berücksichtigt; die einfache (ASCII) Version ist: "\\W"
	 */
	private static final String UNICODE_AWARE_DELIMITER = "[^\\p{L}]";
	private static final String ASCII_DELIMITER = "\\W";
	private String delimiter;

	Preprocessor() {
		delimiter = UNICODE_AWARE_DELIMITER;
	}

	List<String> process(String text) {
		/* Einheitliches lower-casing */
		text = text.toLowerCase();
		SortedSet<String> result = new TreeSet<String>();
		/* splitten, leere Strings filtern: */
		List<String> list = Arrays.asList(text.split(delimiter));
		for (String s : list) {
			if (s.trim().length() > 0) {
				result.add(s.trim());
			}
		}
		return new ArrayList<String>(result);
	}

}
