package cin.mineracao;

import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.apache.commons.io.FilenameUtils.removeExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public final class Engine {
	private static final boolean OVERWRITE_INDEX = true;
	private static final String INDEX_PATH = "index %d";
	private static final String INDEXES_PATH = "indexes";
	private static final String FILES_PATH = "files";
	private static final CustomAnalyzer[] ANALYZERS = new CustomAnalyzer[4];
	
	static {
		ANALYZERS[0] = new CustomAnalyzer(false, false); 
		ANALYZERS[1] = new CustomAnalyzer(true, false);
		ANALYZERS[2] = new CustomAnalyzer(false, true);
		ANALYZERS[3] = new CustomAnalyzer(true, true);
	}
	
	private Engine() { }
	
	private static String getFileContent(File file) throws IOException, TikaException {
		Tika tika = new Tika();
        tika.setMaxStringLength(10 * 1024 * 1024);
        
        return tika.parseToString(file);
    }
	
	public static void createIndex(CustomAnalyzer analyzer) throws IOException, TikaException {
		System.out.println("Criando base " + analyzer.getIndex() + "...");
		String indexPath = String.format(INDEX_PATH, analyzer.getIndex());
		
    	Directory index = FSDirectory.open(Paths.get(INDEXES_PATH, indexPath));  
	    
    	if (!DirectoryReader.indexExists(index)) {
	    	try (IndexWriter indexWriter = new IndexWriter(index, new IndexWriterConfig(analyzer))) {						
				File dir = new File(FILES_PATH);
				File[] files = dir.listFiles();
							
				for (File file : files) {
					Document document = new Document();
			        document.add(new TextField("content", getFileContent(file), Field.Store.YES));
			        document.add(new StringField("name", removeExtension(file.getName()), Field.Store.YES));
	
			        indexWriter.addDocument(document);
				}
				System.out.println("Base " + analyzer.getIndex() + " criada.");
	    	}
	    }
    }
    
    public static String[] search(String queryString, CustomAnalyzer analyzer) throws ParseException, IOException {
    	String indexPath = String.format(INDEX_PATH, analyzer.getIndex()); 
    	
        Directory index = FSDirectory.open(Paths.get(INDEXES_PATH, indexPath));
        
        try (IndexReader reader = DirectoryReader.open(index)) {
	        IndexSearcher searcher = new IndexSearcher(reader);
	        
	        Query query = new QueryParser("content", analyzer).parse(queryString);
	        
	        int hitsPerPage = 1000;
	        
	        TopDocs docs = searcher.search(query, hitsPerPage);
	        
	        ScoreDoc[] hits = docs.scoreDocs;
	        
	        String[] results = new String[hits.length];
	        
			for (int i = 0, n = hits.length; i < n; i++) {
				Document doc = searcher.doc(hits[i].doc);
				
				results[i] = doc.get("name");
				
			}
			
			
			
			return results;
        }
    }
    
    public static int[] searchTest(String queryString, CustomAnalyzer analyzer) throws ParseException, IOException {
    	String indexPath = String.format(INDEX_PATH, analyzer.getIndex()); 
    	
        Directory index = FSDirectory.open(Paths.get(INDEXES_PATH, indexPath));
        
        int[] r = new int[300];
        
        try (IndexReader reader = DirectoryReader.open(index)) {
	        IndexSearcher searcher = new IndexSearcher(reader);
	        
	        Query query = new QueryParser("content", analyzer).parse(queryString);
	        
	        int hitsPerPage = 1000;
	        
	        TopDocs docs = searcher.search(query, hitsPerPage);
	        
	        ScoreDoc[] hits = docs.scoreDocs;
	        
	        String[] results = new String[hits.length];
	        
			for (int i = 0, n = hits.length; i < n; i++) {
				Document doc = searcher.doc(hits[i].doc);
				
				results[i] = doc.get("name");
				r[hits[i].doc] = 1;
			
			}
			
			
			
			return r;
        }
    }
    
    public static void createIndexes() throws IOException, TikaException, InterruptedException {
    	ExecutorService threadPool = Executors.newFixedThreadPool(ANALYZERS.length);
       	ExecutorCompletionService<Void> pool = new ExecutorCompletionService<>(threadPool);
    	
    	for (int i = 1, n = ANALYZERS.length; i <= n; i++) {
    		final int j = i;
    		
    		pool.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					String indexPath = String.format("index %d", j);
		    		
		    		if (OVERWRITE_INDEX) {
		    			deleteDirectory(new File(INDEXES_PATH, indexPath));
		    		}
		    		
		    		createIndex(ANALYZERS[j - 1]);
					
					return null;
				}
			});
    	}
    	
    	for (int i = 1, n = ANALYZERS.length; i <= n; i++) {
    		pool.take();
    	}
    	
    	threadPool.shutdown();
    	
    	System.out.println("Todas as bases foram criadas com sucesso.");
    }
    
    public static void main(String[] args) throws IOException, TikaException, InterruptedException {
    	createIndexes();
    }
}