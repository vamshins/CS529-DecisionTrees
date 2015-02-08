package edu.unm.cs529.ml.dt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CollectionsUtil {
	
	/**
	 * Load each row of input file to 'Row' data structure and returns ArrayList of it.
	 */
	public static List<Row> getListOfExamples(String file) {
		List<Row> rows = new ArrayList<Row>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			boolean firstRow = true;
			String line;
			while ((line = br.readLine()) != null) { // Read from file line by line
				if (firstRow) {
					Constants.NoOfAtt = line.substring(0, line.indexOf(" ")).length(); // Set number of Attributes to number of characters in first line of file
					firstRow = false;
				}
				Row record;
				String value = line.substring(line.indexOf(" ") + 1);
				if (value.equals("+")) {
					record = new Row(line.substring(0, line.indexOf(" ")), 1); // '+' label represents 1
				} else {
					record = new Row(line.substring(0, line.indexOf(" ")), 0); // '-' label represents 0
				}
				rows.add(record);
			}
			System.out.println();
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("training.txt : File Not found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rows;
	}

	/**
	 * returns the map with class as Key and corresponding records in sorted manner.
	 */
	public static Map<String, List<Row>> getSortedMap(List<Row> rows) {
		List<Row> positiveRec = new ArrayList<Row>();
		List<Row> negativeRec = new ArrayList<Row>();
		for (Row record : rows) {
			if (record.getSet() == 1) { // 1 represents for '+' and 0 represents for '-'
				positiveRec.add(record); // Add positive records to positive ArrayList
			} else {
				negativeRec.add(record); // Add negative records to negative ArrayList
			}
		}
		Map<String, List<Row>> map = new HashMap<String, List<Row>>();
		map.put("+", positiveRec); // assign ArrayList with positive records to '+' key
		map.put("-", negativeRec); // assign ArrayList with negative records to '-' key
		return map;
	}
	
	/**
	 * Return 'class' (+ or -) which is majority in number
	 */
	public static int getMajorityClass(List<Row> rows) {
		Map<String, List<Row>> rowMap = getSortedMap(rows);
		if (rowMap.get("+").size() > rowMap.get("-").size()) {
			return 1; // '+'
		} else {
			return 0; // '-'
		}
	}
	
	/**
	 * Returns the index of an attribute which is going to be used as splitting node
	 * Takes in the records based on which decision has to be taken using information Gain value
	 */
	public static int selectSplitAttribute(List<Row> rows) {
		double infoGain = -99999999;
		int splitAttrIndex = 0;
		boolean fl = true;
		Map<Integer, String> attributes = rows.get(0).getRecord();
		Iterator<Entry<Integer, String>> it = attributes.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, String> pairs = it.next();
			Integer integer = (Integer) pairs.getKey();
			if (fl) {
				splitAttrIndex = integer;
				fl = false;
			}
			double ig = EntropyUtil.calculateInformationGain(rows, integer);
			if (ig > infoGain) {
				infoGain = ig;
				splitAttrIndex = integer;
			}
		}
		return splitAttrIndex;
	}

	/**
	 * Remove the Attribute from dataset, after it is determined to be a Node
	 * Form a discrete dataset after an attribute is deleted from dataset.
	 */
	public static Map<String, List<Row>> removeAttributeFromDataset(List<Row> rows, int attrId) {
		Map<String, List<Row>> map = new HashMap<String, List<Row>>();
		for (Row row : rows) {
			String str = row.getValue(attrId);
			row.deleteValue(attrId);
			if (map.get(str) == null) {
				List<Row> discreteAttributesList = new ArrayList<Row>();
				discreteAttributesList.add(row);
				map.put(str, discreteAttributesList);
			} else {
				map.get(str).add(row);
			}
		}
		return map;
	}

	/**
	 * retrieve the list of subsets based on the sequences.
	 */
	public static Map<String, List<Row>> getDiscreteLists(List<Row> records, int attId) {
		Map<String, List<Row>> map = new HashMap<String, List<Row>>();
		for (Row record : records) {
			String str = record.getValue(attId);
			if (map.get(str) == null) {
				List<Row> discrete = new ArrayList<Row>();
				discrete.add(record);
				map.put(str, discrete);
			} else {
				map.get(str).add(record);
			}
		}
		return map;
	}

	/**
	 * returns chi square value of 'rows' for selected attribute
	 */
	public static double getStats(List<Row> records, int attId) {
		Map<String, List<Row>> map = getDiscreteLists(records, attId);
		Map<String, List<Row>> recordMap = getSortedMap(records);
		double p = recordMap.get("+").size();
		double n = recordMap.get("-").size();
		double statistics = 0;
		Iterator<Entry<String, List<Row>>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<Row>> pairs = (Map.Entry<String, List<Row>>) it.next();
			List<Row> recordClass = (List<Row>) pairs.getValue();
			Map<String, List<Row>> rm = getSortedMap(recordClass);
			double pi = rm.get("+").size();
			double ni = rm.get("-").size();
			double p_i = p * ((pi + ni) / (p + n));
			double n_i = n * ((pi + ni) / (p + n));
			statistics += (((pi - p_i) * (pi - p_i) / p_i) + ((ni - n_i) * (ni - n_i) / n_i)); // calculate Chi square statistics value wit given formula
		}
		return statistics;
	}
}
