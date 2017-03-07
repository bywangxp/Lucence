
package com.bywangxp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

//��Ҫ��apache�µ�lucene��
/**
 * ���������ļ�
 */
public class Create {

	@SuppressWarnings("resource")
	public static boolean createDocumentIndex(String dir1) {
		boolean bool = false;
		try {
			// ��Ҫ������Ŀ¼
			File dirpath = new File(dir1);
			// �������ļ������indexĿ¼��
			File indexpath = new File("c:\\index");
			// �����ִ���
			Analyzer analyzer = new SmartChineseAnalyzer();
			Directory dir = FSDirectory.open(indexpath);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
			// iwc.setUseCompoundFile(true);
			// iwc.setOpenMode(OpenMode.CREATE);
			IndexWriter indexWriter = new IndexWriter(dir, iwc);
			// IndexWriter index = new IndexWriter(indexpath, analyzer, true);

			File[] txtfiles = dirpath.listFiles();
			long starttime = new Date().getTime();
			System.out.println("�ļ�����" + txtfiles.length);
			for (int i = 0; i < txtfiles.length; i++) {
				System.out.println(txtfiles[i].getName());
				if (txtfiles[i].isFile()) {
					System.out.println("�ļ�" + i + txtfiles[i].getCanonicalPath() + "���ڽ���������...");

					/* ����TXT�ļ� */
					String pathname = txtfiles[i].getCanonicalPath(); // ����·�������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ���·��
					File filename = new File(pathname); // Ҫ��ȡ����·����input��txt�ļ�
					InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // ����һ������������reader
					BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������
					String line = "";
					Document doc = new Document();
					while (line != null) {
						line = br.readLine(); // һ�ζ���һ������
						if (line != null) {
							doc.add(new Field("content", line, Field.Store.YES, Field.Index.ANALYZED));
							String filname1 = txtfiles[i].getName().split("\\.")[0];
							System.out.println(filname1);
							doc.add(new Field("path", filname1, Field.Store.YES, Field.Index.NO));
						}
					}
					br.close();
					indexWriter.addDocument(doc);
				}
				System.out.println("û���쳣" + i);
			}
			// indexWriter.optimize();
			indexWriter.close();
			long endTime = new Date().getTime();
			System.out.println("�⻨����" + (endTime - starttime) + " ���������ĵ����ӵ���������ȥ!" + dirpath.getPath());
			bool = true;
		} catch (Exception e) {
			bool = false;
			e.printStackTrace();
		}
		return bool;
	}

	// �˷����ǳ�ȥ�ļ�Ϊ.lock��β���ļ�
	public static boolean removeLock(String filename) {
		String[] split = filename.split("\\.");

		if (split.length >= 1 && split[split.length - 1].equals("lock")) {
			return false;
		}
		return true;

	}

	/**
	 * ����
	 * 
	 * @throws InterruptedException
	 */
	public static void main(String[] a) throws InterruptedException {
		Create.createDocumentIndex("c:\\s");
		Thread.sleep(3000);
		Create.createDocumentIndex("c:\\s\\text2");
		// removeLock("324234.3dsdf/lock");
	}
}