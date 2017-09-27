package cin.mineracao;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class CustomAnalyzer extends Analyzer {
	private boolean stop;
	private boolean stem;
	private int index;
	
	public CustomAnalyzer (boolean stop, boolean stem) {
		this.stop = stop;
		this.stem = stem;
		
		index = 1;
		
		if (stop) {
			index += 1;
		}
		
		if (stem) {
			index += 2;
		}
	}
	
	public int getIndex() {
		return index;
	}

	@Override
    protected TokenStreamComponents createComponents(String field) {
        Tokenizer tokenizer = new StandardTokenizer();
		TokenStream filter = new LowerCaseFilter(tokenizer);
		if (stop) filter = new StopFilter(filter, EnglishAnalyzer.getDefaultStopSet());
		if (stem) filter = new PorterStemFilter(filter);
	
        return new TokenStreamComponents(tokenizer, filter);
    }
}