package Components;

public class rNode implements Comparable<rNode>{
	
	private String recordName ;
	private double recordValue;
	
	
	public rNode(String recordName, double recordValue) {
		this.recordName = recordName;
		this.recordValue = recordValue;
	}
	//this to use when searching for the record by its name
	public rNode (String recordName) {
		this.recordName = recordName;
	}
	


	@Override
	public String toString() {
		return  recordName + ", recordValue=" + recordValue + "]";
	}


	public String getRecordName() {
		return recordName;
	}


	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}


	public double getRecordValue() {
		return recordValue;
	}


	public void setRecordValue(double recordValue) {
		this.recordValue = recordValue;
	}


//	@Override
//	public int compareTo(rNode o) {
//		if(recordValue>o.recordValue) {
//			return 1;
//		}
//		if(recordValue< o.recordValue) {
//			return -1;
//		}
//		return 0;
//	}

	public int compareTo(rNode o) {
        // Compare based on recordName only
        return this.recordName.compareTo(o.recordName);
    }
}
