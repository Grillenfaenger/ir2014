package test;

import static org.junit.Assert.assertTrue;
import ir7.Corpus;
import ir7.CorpusSplitter;
import ir7.Indexer;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class LuceneTest {

	private Corpus corpus;
	private String luceneDir;
	private String query;
	private String dataDir;

	@Before
	public void setUp() throws Exception {
		corpus = new Corpus("pg100.txt", "1[56][0-9]{2}\n", "\n");
		luceneDir = "index/"; // Speicherort für den Lucene-Index
		dataDir = "shakespeare/"; // Verzeichnis mit zu indexierenden Dateien
		query = "brutus";
	}

	@Test
	public void testCorpusIndexing() throws IOException {
		/*
		 * Ein Lucene-Indexer für unser Corpus.
		 */
		assertTrue("Corpus sollte mehr als ein Doc enthalten", corpus
				.getWorks().size() > 0);
		System.out.println("Indexing corpus ");
		Indexer indexer = new Indexer(luceneDir);
		indexer.index(corpus);
	}

	@Test
	public void testIndexer() throws Exception {
		/*
		 * Ein Lucene-Indexer, der zunächst Dateien aus einem Verzeichnis
		 * einliest. Um sicherzustellen, dass tatsächlich die einzelnen
		 * Shakespeare-Texte vorhanden sind, wird hier die Hilfsklasse
		 * CorpusSplitter eingesetzt, die das betreffende Verzeichnis prüft und
		 * ggf. das Corpus in Einzeldateien zerlegt.
		 */
		CorpusSplitter.split(corpus, dataDir);
		System.out.println("Indexing files in dir "
				+ (new File(dataDir).getAbsolutePath()));
		Indexer indexer = new Indexer(luceneDir);
		indexer.index(dataDir);
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
