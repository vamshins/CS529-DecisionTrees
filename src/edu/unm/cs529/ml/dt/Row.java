package edu.unm.cs529.ml.dt;

import java.util.HashMap;
import java.util.Map;


// Stores each row of dataset in a structured form.

class Row {
	private Map<Integer, String> row;
	private int result;

	public Row(String str, int value) {
		if (str.length() != Constants.NoOfAtt) {
			System.out.println("invalid record");
		} else {
			row = new HashMap<Integer, String>();
			for (int i = 0; i < str.length(); i++) {
				row.put(i, "" + str.charAt(i));
			}
			this.result = value; // set 1 -> + and 0 -> -
		}
	}

	public Map<Integer, String> getRecord() {
		return row;
	}
	
	public String getValue(int key) {
		return row.get(key);
	}

	public void setRecord(Map<Integer, String> record) {
		this.row = record;
	}

	public int getSet() {
		return result;
	}

	public void setSet(int value) {
		this.result = value;
	}
	
	public void deleteValue(int attId) {
		row.remove(attId);
	}

	public String toString() {
		return "Record [record=" + row + ", set=" + result + "]";
	}
}