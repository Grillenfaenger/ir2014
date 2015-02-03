package test;

import static org.junit.Assert.assertEquals;
import ir7.Corpus;
import ir7.CorpusSplitter;
import ir7.Indexer;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LuceneTest {

	private static Corpus corpus;
	private static String dataDir;
	private static String luceneDir;
	private static String query;

	@BeforeClass
	public static void setUp() throws Exception {

		corpus = new Corpus("pg100.txt", "1[56][0-9]{2}\n", "\n");
		/*
		 * Verzeichnis mit Beispieltexten
		 */
		dataDir = "shakespeare/";
		/*
		 * Der CorpusSplitter prüft das betreffende Verzeichnis (dataDir) und
		 * zerlegt das Corpus ggf. in Einzeldateien, benannt nach docId+Titel
		 */
		CorpusSplitter.split(corpus, dataDir);
		/*
		 * Speicherort für den Lucene-Index:
		 */
		luceneDir = "index/";
		query = "king";
	}

	@Before
	public void printSkip() {
		System.out.println();
	}

	@Test
	public void testCorpusIndexing() throws IOException {
		/*
		 * Erstellt einen Lucene-Index für unser Corpus. Wir prüfen zunächst, ob
		 * mit dem Korpus alles in Ordnung ist ...
		 */
		assertEquals("Corpus sollte genau 38 Dokumente enthalten", 38, corpus
				.getWorks().size());
		/* ... und erstellen uns dann einen Indexer: */
		System.out.print("Indexing corpus ");
		Indexer indexer = new Indexer(luceneDir);
		indexer.index(corpus);
		indexer.close();
		/* Wenn alles OK ist, sollten nun genau die 38 Docs im Index sein: */
		assertEquals("Index sollte der Korpusgröße entsprechen", corpus
				.getWorks().size(), indexer.getNumDocs());
	}

	@Test
	public void testIndexer() throws Exception {
		/*
		 * Ein Lucene-Indexer, der zunächst Dateien aus einem Verzeichnis
		 * ('dataDir') einliest. Um sicherzustellen, dass tatsächlich die
		 * einzelnen Shakespeare-Texte in 'dataDir' liegen, wird in setUp() der
		 * CorpusSplitter ausgeführt.
		 */
		System.out.print("Indexing files ");
		Indexer indexer = new Indexer(luceneDir);
		indexer.index(dataDir);
		indexer.close();
		assertEquals("Index sollte genau 38 Dokumente enthalten", 38,
				indexer.getNumDocs());
	}

	@Test
	public void testSearcher() {
		/*
		 * TODO: Der Searcher ist das Gegenstück zum Indexer. Machen wir beim
		 * nächsten Mal.
		 */
		System.out.println("TODO: search for " + query);
	}
}
