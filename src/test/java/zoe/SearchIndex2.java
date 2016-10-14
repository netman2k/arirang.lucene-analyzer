package zoe;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.apache.lucene.search.* ;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.ko.KoreanAnalyzer; 
import org.apache.lucene.analysis.ko.morph.AnalysisOutput;
import org.apache.lucene.analysis.ko.morph.MorphAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;


public class SearchIndex2 {
	
	public static void main(String[] argv) {
		
		try {
			
            Scanner in =  new Scanner(System.in) ;
            in.useLocale(Locale.KOREAN) ;
            String source = "" ;
            String dir = "/Users/mgkaki/Solr/solr-5.4.0/server/solr/market/data";
            
            Path path = FileSystems.getDefault().getPath(dir);
            Directory directory = new NIOFSDirectory(path);
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);         
            
            KoreanAnalyzer analyzer = new KoreanAnalyzer() ;
            analyzer.setBigrammable(false) ;
            

            //while(true)
            {
           	
            	/*
                System.out.println("Input Line :") ;
                source = in.nextLine() ;
                System.out.println(source) ;

                if( source.trim().length() == 0 )
                    continue ;


                if( source.equals("exit") )
                    break ;
                    */
                    
            	
            	source = "오늘날씨" ;

                
  
                //QueryParser qp = new QueryParser("content", analyzer);
                //Query query = qp.parse(source) ;
            	Query query = new  MatchAllDocsQuery() ;
                //qp.setDefaultOperator(Operator.OR) ;
                TopDocs tops = null ;
                
                try {

                	tops = searcher.search(query, 10) ;
                }
                catch(Exception e) {
                	e.printStackTrace() ;
                	
                }
                
                int total = tops.scoreDocs.length ;
                for(int i=0 ; tops != null && i<total && i<tops.totalHits ; i++) {
                	
                	Document doc = searcher.doc(tops.scoreDocs[i].doc); 
                	
                	String id = doc.get("id") ;
                	String content = doc.get("content") ;
                	
                	// sorting 용 변수는 값을 가져오지 못함. 
                	
                	System.out.println(id + ":" + content ) ;
                }
            }
            
            reader.close();
            directory.close();

		}
        catch(Exception e) {
        	e.printStackTrace() ;
        }
	}

}
