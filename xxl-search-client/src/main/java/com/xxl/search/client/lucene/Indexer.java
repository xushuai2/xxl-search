package com.xxl.search.client.lucene;

/**
 * @author xushuai
 * @date 2018年10月17日
 * @note 
 */
//显示向文档里写索引
//********Indexer    Start************************

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

	private Integer ids[]={1,2,3};
	private String citys[]={"aingdao","banjing","changhai"};
	private String types[]={"aa","bb","cc"};
	private String descs[]={
			"Qingdao is b beautiful city.",
			"Nanjing is c city of culture.",
			"Shanghai is d bustling city."
	};
	
	private Directory dir;
	
	/**
	 *实例化indexerWriter
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter()throws Exception{
		Analyzer analyzer=new StandardAnalyzer();
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}
	
	/**
	 * 获取indexDir
	 * @param indexDir
	 * @throws Exception
	 */
	/*			Field.Store.YES或者NO（存储域选项） 
	        YES:表示会把这个域中的内容完全存储到文件中，方便进行还原[对于主键，标题可以是这种方式存储] 
	        NO:表示把这个域的内容不存储到文件中，但是可以被索引，此时内容无法完全还原（doc.get()）[对于内容而言，没有必要进行存储，可以设置为No]

	Field.Store.YES：将文件的全名存储到索引中， 
	                   Store.YES 保存 可以查询 可以打印内容 
	                    Field storeYes = new Field("storeyes","storeyes",Store.YES,Index.TOKENIZED); 
	                    Store.NO 不保存 可以查询 不可打印内容 由于不保存内容所以节省空间 
	                    Field storeNo = new Field("storeno","storeno",Store.NO,Index.TOKENIZED); 
	                    Store.COMPRESS 压缩保存 可以查询 可以打印内容 可以节省生成索引文件的空间， 
	                    Field storeCompress = new Field("storecompress","storecompress",Store.COMPRESS,Index.TOKENIZED); 
	                    至此，对于理解Store.YES，Store.NO 就是不存储就不能直接获取此字段的内容，存储了就可以。但是两者都可以用于检索。*/
	private void index(String indexDir)throws Exception{
		
		dir=FSDirectory.open(Paths.get(indexDir));
		
		IndexWriter writer=getWriter();
		
		for(int i=0;i<ids.length;i++){

			Document doc=new Document();
			//YES:表示会把这个域中的内容完全存储到文件中,方便进行还原[对于主键
			doc.add(new IntField("id", ids[i], Field.Store.YES));
			doc.add(new StringField("city",citys[i],Field.Store.YES));
			doc.add(new StringField("type",types[i],Field.Store.YES));
			doc.add(new TextField("desc", descs[i], Field.Store.YES));
			
			writer.addDocument(doc); 
		}
		
		writer.close();
	}
	
	
	public static void main(String[] args) throws Exception {
		
		new Indexer().index("E:\\luceneDemo");
		
		System.out.println("写索引成功！");
	}
	
}
//********Indexer    End************************

