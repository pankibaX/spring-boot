package com.barvepan.restfulwebservices.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class DisplayTableUtil {

	public static void printSelect(JdbcTemplate jdbcTemplate, String selectQuery) {
		retreiveData(jdbcTemplate, selectQuery);
	}

	public static void printTable(JdbcTemplate jdbcTemplate, String... tableNames) {
		for (String tableName : tableNames) {
			String selectQuery = "select * from " + tableName;
			retreiveData(jdbcTemplate, selectQuery);
		}

	}

	private static void retreiveData(JdbcTemplate jdbcTemplate, String selectQuery) {

		List<Map<String, Object>> tableData = jdbcTemplate.queryForList(selectQuery);

		System.out.println("\nPrinting " + tableData.size() + " rows by executing \"" + selectQuery + "\"");

		if (tableData.size() > 0) {

			// column names and its width
			Set<String> columnNames = tableData.get(0).keySet();

			Map<String, Integer> widthMap = new LinkedHashMap<String, Integer>();
			for (String columnName : columnNames) {
				widthMap.put(columnName, columnName.length());
			}

			// column values and its width, updating above map if value width is greater
			// than column name
			for (int i = 0; i < tableData.size(); i++) {
				int width = 0;
				for (String columnName : columnNames) {

					if (tableData.get(i).get(columnName).toString().length() > widthMap.get(columnName)) {
						width = tableData.get(i).get(columnName).toString().length();
						widthMap.put(columnName, width);
					}
				}
			}

			printLine(widthMap, columnNames);
			printHeader(widthMap, columnNames);
			printLine(widthMap, columnNames);

			for (int i = 0; i < tableData.size(); i++) {
				printContent(tableData.get(i), widthMap, columnNames);
				printLine(widthMap, columnNames);
			}
		}

	}

	private static void printLine(Map<String, Integer> widhtMap, Set<String> columnNames) {

		StringBuffer coverLine = new StringBuffer();
		coverLine.append("+");

		for (String columnName : columnNames) {
			coverLine.append("--");
			int tempWidth = widhtMap.get(columnName);
			coverLine.append(StringUtils.repeat("-", tempWidth));
			coverLine.append("--");
			coverLine.append("+");
		}

		System.out.println(coverLine);
	}

	private static void printHeader(Map<String, Integer> widhtMap, Set<String> columnNames) {

		StringBuffer coverLine = new StringBuffer();
		coverLine.append("|");

		for (String columnName : columnNames) {
			coverLine.append("  ");
			int tempWidth = widhtMap.get(columnName);
			coverLine.append(columnName);
			coverLine.append(StringUtils.repeat(" ", tempWidth - columnName.length()));
			coverLine.append("  ");
			coverLine.append("|");
		}

		System.out.println(coverLine);
	}

	private static void printContent(Map<String, Object> contentMap, Map<String, Integer> widhtMap,
			Set<String> columnNames) {

		StringBuffer coverLine = new StringBuffer();
		coverLine.append("|");

		for (String columnName : columnNames) {
			coverLine.append("  ");
			int tempWidth = widhtMap.get(columnName);
			coverLine.append(contentMap.get(columnName));
			coverLine.append(StringUtils.repeat(" ", tempWidth - String.valueOf(contentMap.get(columnName)).length()));
			coverLine.append("  ");
			coverLine.append("|");
		}

		System.out.println(coverLine);
	}

	public static int countRowsInTable(JdbcTemplate jdbcTemplate, String tableName) {
		Integer result = jdbcTemplate.queryForObject("SELECT COUNT(0) FROM " + tableName, Integer.class);
		return (result != null ? result : 0);
	}

}
