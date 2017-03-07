package com.bywangxp;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class ExcuteQuery {
	public static void main(String[] args)
			throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
		String index = "c:\\index";// 搜索的索引路径
		// IndexReader
		// reader=IndexReader.open(FSDirectory.open(Paths.get(index));
		// v3.6.0的写法
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		IndexSearcher searcher = new IndexSearcher(reader);// 检索工具
		ScoreDoc[] hits = null;
		String queryString = ""; // 搜索的索引名称
		Query query = null;
		Analyzer luceneAnalyzer = new SmartChineseAnalyzer();
		// QueryParser qp=new
		// QueryParser(Version.LUCENE_3_6_0,"body",analyzer);//用于解析用户输入的工具
		// v3.6.0
		QueryParser qp = new QueryParser(Version.LUCENE_CURRENT, "content", luceneAnalyzer);// 用于解析用户输入的工具
		query = qp.parse(queryString);
		System.out.println("分词情况:" + query.toString());
		if (searcher != null) {
			TopDocs results = searcher.search(query, null, 10);// 只取排名前十的搜索结果
			hits = results.scoreDocs;
			Document document = null;
			System.out.println(hits.length);
			for (int i = 0; i < hits.length; i++) {
				document = searcher.doc(hits[i].doc);
				String body = document.get("content");
				String path = document.get("path");
				String modifiedtime = document.get("modifiField");
				System.out.println("BODY---" + body);
				System.out.println("PATH--" + path);
			}
			if (hits.length > 0) {
				System.out.println("输入关键字为：" + queryString + "，" + "找到" + hits.length + "条结果!");
			}
			// searcher.close();
			reader.close();
		}
	}

}