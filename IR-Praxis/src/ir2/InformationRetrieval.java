package ir2;

import java.util.Set;

public interface InformationRetrieval {

	/*
	 * Information-Retrieval in der einfachsten Form: unabhängig von der
	 * konkreten Implementation (z.B. LinearSearch, TermDocumentMatrix) gibt
	 * 'search' die Ids aller Werke zurück, in denen der String "query" vorkommt.
	 * Als Ids nehmen wir die Indexposition der Werke.
	 */
	Set<Integer> search(String query);

}
