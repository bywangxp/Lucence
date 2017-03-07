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
		String index = "c:\\index";// ����������·��
		// IndexReader
		// reader=IndexReader.open(FSDirectory.open(Paths.get(index));
		// v3.6.0��д��
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		IndexSearcher searcher = new IndexSearcher(reader);// ��������
		ScoreDoc[] hits = null;
		String queryString = ""; // ��������������
		Query query = null;
		Analyzer luceneAnalyzer = new SmartChineseAnalyzer();
		// QueryParser qp=new
		// QueryParser(Version.LUCENE_3_6_0,"body",analyzer);//���ڽ����û�����Ĺ���
		// v3.6.0
		QueryParser qp = new QueryParser(Version.LUCENE_CURRENT, "content", luceneAnalyzer);// ���ڽ����û�����Ĺ���
		query = qp.parse(queryString);
		System.out.println("�ִ����:" + query.toString());
		if (searcher != null) {
			TopDocs results = searcher.search(query, null, 10);// ֻȡ����ǰʮ���������
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
				System.out.println("����ؼ���Ϊ��" + queryString + "��" + "�ҵ�" + hits.length + "�����!");
			}
			// searcher.close();
			reader.close();
		}
	}

}