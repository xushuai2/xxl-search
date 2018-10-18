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
		//范围查询
//		do2();		
		//3.FuzzyQuery模糊查询查询
//		doFuzzy();		
		//4.WildcardQuery通配符。可以使用'*'或者‘？’
//		doWildcard();	
		//前缀查询数据
//		do5();
		//BooleanQuery逻辑查询
//		do6();
		//BooleanQuery逻辑查询
//		do7();
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
	}



	/**
	 * @throws IOException
	 */
	private static void do5() throws IOException {
		//5.前缀查询数据
        Query queryPrefix = new PrefixQuery(new Term("city", "a"));
        TopDocs hitsPrefix = is.search(queryPrefix,200);
        printSearvh(hitsPrefix);
	}



	/*
	 * *4.WildcardQuery通配符。可以使用'*'或者‘？’
	 * @throws IOException
	 */
	private static void doWildcard() throws IOException {
		Term termWildcardQuery = new Term("desc", "cul??re");
        Query queryWildcardQuery = new WildcardQuery(termWildcardQuery);
        TopDocs hitsWildcardQuery = is.search(queryWildcardQuery,200);
		
        printSearvh(hitsWildcardQuery);
	}



	/**
	 * @throws IOException
	 */
	private static void doFuzzy() throws IOException {
		//3.FuzzyQuery模糊查询查询
		Term term = new Term("desc", "city");
		FuzzyQuery queryFuzzyQuery = new FuzzyQuery(term);
		TopDocs hitsFuzzyQuery = is.search(queryFuzzyQuery,100);
		
		printSearvh(hitsFuzzyQuery);
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
	 * Occur.MUST：必须满足此条件，相当于and

		Occur.SHOULD：应该满足，但是不满足也可以，相当于or
		
		Occur.MUST_NOT：必须不满足。相当于not
	 * @throws IOException
	 */
	private static void do7() throws IOException {
		//6.BooleanQuery逻辑查询 主要作用是将查询语句进行是否必须的设置   must为必须，should为不必须
        BooleanQuery boolQuery = new BooleanQuery();
        Query query1 = new TermQuery(new Term("city", "banjing"));
        Query query2 = new TermQuery(new Term("type", "cc"));
        boolQuery.add(query1, Occur.SHOULD);
        boolQuery.add(query2, Occur.SHOULD);
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
		
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			
			Document doc=is.doc(scoreDoc.doc);
			System.out.println(doc.get("id"));
			System.out.println(doc.get("city"));
			System.out.println(doc.get("type"));
			System.out.println(doc.get("desc"));
			System.out.println("----------------------------");
		}
	}
}
