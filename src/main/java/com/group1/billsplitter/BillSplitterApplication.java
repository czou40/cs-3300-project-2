package com.group1.billsplitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.util.List;
import java.util.Map;


@SpringBootApplication
public class BillSplitterApplication {

//	public List<Map<String, Object>> listUsers() {
//		return jdbcTemplate.queryForList("SELECT * FROM user;");
//	}

//	static void authImplicit() {
//		// If you don't specify credentials when constructing the client, the client library will
//		// look for credentials via the environment variable GOOGLE_APPLICATION_CREDENTIALS.
//		Storage storage = StorageOptions.getDefaultInstance().getService();
//
//		System.out.println("Buckets:");
//		Page<Bucket> buckets = storage.list();
//		for (Bucket bucket : buckets.iterateAll()) {
//			System.out.println(bucket.toString());
//		}
//	}

	public static void main(String[] args) {
		SpringApplication.run(BillSplitterApplication.class, args);
		System.out.println("printing");
//		authImplicit();
	}

}
