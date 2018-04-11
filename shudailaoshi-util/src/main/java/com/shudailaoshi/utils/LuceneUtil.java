package com.shudailaoshi.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.lionsoul.jcseg.analyzer.v5x.JcsegAnalyzer5X;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;

/**
 * 
 * @author Liaoyifan
 *
 */
@SuppressWarnings("resource")
public class LuceneUtil {

	private static final Logger log = LogManager.getLogger(LuceneUtil.class);

	private static final String INDEXPATH = "D:\\luceneIndex";

	private LuceneUtil() {
	}

	/**
	 * 创建索引
	 * 
	 * @param lists
	 *            创建索引的vo集合
	 */
	public static void create(List<?> lists) {
		long start = System.currentTimeMillis();
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(FSDirectory.open(Paths.get(INDEXPATH)),
					new IndexWriterConfig(new JcsegAnalyzer5X(JcsegTaskConfig.COMPLEX_MODE)));
			Field[] fields = lists.iterator().next().getClass().getDeclaredFields();
			for (Object object : lists) {
				Document doc = new Document();
				for (Field field : fields) {
					String name = field.getName();
					doc.add(new TextField(name, String.valueOf(ReflectionUtil.getFieldValue(object, name)), Store.YES));
				}
				writer.addDocument(doc);
			}
			log.info("创建索引耗时：" + (System.currentTimeMillis() - start) + "ms\n");
		} catch (IOException e) {
			log.info(e.getMessage(), e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 查询索引
	 * 
	 * @param keyword
	 *            关键字
	 * @param fields
	 *            查询字段名
	 * @param clazz
	 *            字段vo Class
	 * @return
	 */
	public static List<Object> search(String keyword, List<String> fieldNames, Class<?> clazz) {
		long start = System.currentTimeMillis();
		IndexReader reader = null;
		try {
			// 创建查询
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEXPATH)));
			IndexSearcher searcher = new IndexSearcher(reader);
			BooleanQuery.Builder builder = new BooleanQuery.Builder();
			for (String fieldName : fieldNames) {
				TermQuery query = new TermQuery(new Term(fieldName, keyword));
				builder.add(query, BooleanClause.Occur.SHOULD);
			}
			BooleanQuery booleanQuery = builder.build();
			// 获取查询结果
			TopDocs tops = searcher.search(booleanQuery, 100);
			// 高亮
			Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter("<font color='red'>", "</font>"),
					new QueryScorer(booleanQuery));
			highlighter.setTextFragmenter(new SimpleFragmenter(100));
			Analyzer analyzer = new JcsegAnalyzer5X(JcsegTaskConfig.COMPLEX_MODE);
			// 获取结果文档
			ScoreDoc[] scores = tops.scoreDocs;
			List<Object> results = new ArrayList<Object>();
			Field[] clazzFields = clazz.getDeclaredFields();
			for (ScoreDoc score : scores) {
				Document doc = searcher.doc(score.doc);
				Object obj = clazz.newInstance();
				for (Field field : clazzFields) {
					String fieldName = field.getName();
					Class<?> typeClass = field.getType();
					if (fieldNames.contains(fieldName)) {
						ReflectionUtil.setFieldValue(obj, fieldName,
								highlighter.getBestFragment(analyzer, fieldName, doc.get(fieldName)));
					} else if (typeClass.equals(String.class)) {
						ReflectionUtil.setFieldValue(obj, fieldName, doc.get(fieldName));
					} else if (typeClass.equals(Long.class)) {
						ReflectionUtil.setFieldValue(obj, fieldName, Long.parseLong(doc.get(fieldName)));
					} else if (typeClass.equals(Integer.class)) {
						ReflectionUtil.setFieldValue(obj, fieldName, Integer.parseInt(doc.get(fieldName)));
					}
				}
				results.add(obj);
			}
			log.info("搜索耗时：" + (System.currentTimeMillis() - start) + "ms，总数：" + tops.totalHits + "，结果数："
					+ scores.length + "\n");
			return results;
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 删除索引文件
	 */
	public static void deleteAll() {
		try {
			FileUtils.forceDelete(new File(INDEXPATH));
		} catch (IOException e) {
			log.info(e.getMessage(), e);
		}
	}

	/**
	 * 获取Jcseg分词结果
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> getAnalyzerResults(String content) {
		TokenStream stream = null;
		try {
			Analyzer analyzer = new JcsegAnalyzer5X(JcsegTaskConfig.COMPLEX_MODE);
			stream = analyzer.tokenStream("content", new StringReader(content));
			CharTermAttribute attr = stream.addAttribute(CharTermAttribute.class);
			stream.reset();
			List<String> results = new ArrayList<String>();
			while (stream.incrementToken()) {
				results.add(attr.toString());
			}
			return results;
		} catch (IOException e) {
			log.info(e.getMessage(), e);
			return null;
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
	}

}