package edu.unm.cs529.ml.dt;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Entropy {
	
	/**
	 * Calculates entropy of array of rows
	 */
	public static double calculateEntropy(List<Row> records) {
		Map<String, List<Row>> recordMap = CollectionsUtil.getSortedMap(records);
		double n = records.size();
		double entropy = 0;
		Iterator<Entry<String, List<Row>>> it = recordMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, List<Row>> pairs = it.next();
			List<Row> recordClass = (List<Row>) pairs.getValue();
			double p = recordClass.size();
			if (p == 0 || p == n) {
				return 0;
			}
			entropy -= (p / n) * log2(p / n);
		}
		return entropy;
	}

	/**
	 * Calculate misclassified rate.
	 */
	public static double misclassification(List<Row> records) {
		Map<String, List<Row>> recordMap = CollectionsUtil.getSortedMap(records);
		double p = recordMap.get("+").size();
		double m = recordMap.get("-").size();
		if (p > m) {
			return m / (p + m);
		} else {
			return p / (p + m);
		}
	}
	
	/**
	 * Calculates information gain
	 */
	public static double informationGain(List<Row> rows, int attrId) {
		Map<String, List<Row>> map = CollectionsUtil.getDiscreteLists(rows, attrId);

		double n = rows.size();
		double informationGain;
		if (Constants.impurity_selection == Constants.ENTROPY_IMPURITY) {
			informationGain = calculateEntropy(rows);
		} else {
			informationGain = misclassification(rows);
		}
		Iterator<Entry<String, List<Row>>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<Row>> pairs = (Map.Entry<String, List<Row>>) it.next();
			List<Row> rowClass = (List<Row>) pairs.getValue();
			double rowSize = rowClass.size();

			double entropy;
			if (Constants.impurity_selection == Constants.ENTROPY_IMPURITY) {
				entropy = calculateEntropy(rowClass);
			} else {
				entropy = misclassification(rowClass);
			}
			informationGain -= (rowSize / n) * entropy;
		}
		return informationGain;
	}
	
	/**
	 * Log2(x)
	 */

	public static double log2(double x) {
		return Math.log(x) / Math.log(2);
	}

}
