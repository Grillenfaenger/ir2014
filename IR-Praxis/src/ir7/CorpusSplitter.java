package ir7;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;

public class CorpusSplitter {
	/*
	 * Hilfsklasse: Teilt das Shakespeare-Korpus in Einzeldateien ein. Die
	 * Dateinamen setzen sich zusammen aus docId (Index der Liste von Werken)
	 * und Titel (erste Zeile des jew. Werks).
	 */
	private static String data;

	public static void split(Corpus corpus, String targetDir) {

		data = targetDir;
		File dir = new File(data);
		if (dir.mkdirs()) {
			System.out.println("New Dir: " + dir.getAbsolutePath());
			split(corpus);
		} else {
			System.out.println("Dir '" + dir.getAbsolutePath()
					+ "' already exists! (" + dir.listFiles().length
					+ " files).");
		}
	}

	private static void split(Corpus corpus) {
		List<Document> worksAsList = corpus.getWorks();
		for (Document work : worksAsList) {
			String title = work.get("title");
			String text = work.get("text");
			int docId = worksAsList.indexOf(work) + 1;// erstes 'Werk' entfernt
			String filename = docId + "-" + title + ".txt";
			System.out.println("Creating file: " + filename);
			writeFile(text, filename);
		}
	}

	private static void writeFile(String work, String filename) {
		try {
			FileWriter fw = new FileWriter(new File(data + filename));
			fw.write(work);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
