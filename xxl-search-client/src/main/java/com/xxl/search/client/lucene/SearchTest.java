package com.xxl.search.client.lucene;

import java.io.IOException;
/**
 * @author xushuai
 * @date 2018年10月17日
 * @note 
 */
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
 
public class SearchTest {
 
	private static Directory dir;
	private static IndexReader reader;
	private static IndexSearcher is;
	
 
	
	public static void main(String[] args) throws IOException {
		dir=FSDirectory.open(Paths.get("E:\\luceneDemo"));
		reader=DirectoryReader.open(dir);
		is=new IndexSearcher(reader);
		
		
		//1.精确查询
//		Query queryTerm = new TermQuery(new Term("city", "banjing"));
//		TopDocs hitsTerm=is.search(queryTerm, 200);
//		printSearvh(hitsTerm);
		
//		do2();		
		do6();
		reader.close();
	}



	/**
	 * @throws IOException
	 */
	private static void do2() throws IOException {
		//2.范围查询
		//查询前2条数据，后面两个true,表示的是是否包含头和尾
		Query  query= NumericRangeQuery.newIntRange("id", 1,2, true, true);
		TopDocs hits=is.search(query, 200);
		printSearvh(hits);		
		
		
		//3.FuzzyQuery模糊查询查询
		Term term = new Term("desc", "beautiful");
		FuzzyQuery queryFuzzyQuery = new FuzzyQuery(term);
		TopDocs hitsFuzzyQuery = is.search(queryFuzzyQuery,100);
		
		printSearvh(hitsFuzzyQuery);		
		
		
		
		//4.WildcardQuery通配符。可以使用'*'或者‘？’
		
		Term termWildcardQuery = new Term("desc", "cul??re");
        Query queryWildcardQuery = new WildcardQuery(termWildcardQuery);
        TopDocs hitsWildcardQuery = is.search(queryWildcardQuery,200);
		
        printSearvh(hitsWildcardQuery);		
		
        //5.前缀查询数据
        Query queryPrefix = new PrefixQuery(new Term("city", "a"));
        TopDocs hitsPrefix = is.search(queryPrefix,200);
        printSearvh(hitsPrefix);		
        
        
        
	}



	/**
	 * @throws IOException
	 */
	private static void do6() throws IOException {
		//6.BooleanQuery逻辑查询 主要作用是将查询语句进行是否必须的设置   must为必须，should为不必须
        BooleanQuery boolQuery = new BooleanQuery();
        Query query1 = new TermQuery(new Term("city", "banjing"));
        Query query2 = new TermQuery(new Term("type", "bb"));
        boolQuery.add(query1, Occur.MUST);
        boolQuery.add(query2, Occur.MUST);
        TopDocs hitsboolQuery= is.search(boolQuery,200);
        printSearvh(hitsboolQuery);
        
        
     /*   TextField肯定会进行语汇化，比如Lucene in Action经过语汇后会删除中间的in字符，
        这样通过TermQuery q1 = new TermQuery(new Term("title","Lucene in Action"));
        创建的查询将找不到“lucene in action”。*/
	}



	/**
	 * @param hits
	 * @throws IOException
	 */
	private static void printSearvh(TopDocs hits) throws IOException {
		System.out.println("**********************************");
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			
			Document doc=is.doc(scoreDoc.doc);
			System.out.println(doc.get("id"));
			System.out.println(doc.get("city"));
			System.out.println(doc.get("desc"));
		}
	}
}
