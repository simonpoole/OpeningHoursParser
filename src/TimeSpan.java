
public class TimeSpan {

	boolean twentyfourseven = false;
	int start=-1;
	VariableTime startEvent = null;
	int end=-1;
	VariableTime endEvent = null;
	boolean openEnded=false;
	int interval=0; //minutes

	public String toString() {
		StringBuffer b = new StringBuffer();
		if (twentyfourseven) {
			b.append("24/7");
		} else {
			if (startEvent != null) {
				b.append(startEvent.toString());
			} else {
				b.append(String.format("%02d",(int)start/60));
				b.append(":");
				b.append(String.format("%02d",start%60));
			}
			if (endEvent != null) {
				b.append("-");
				b.append(endEvent.toString());
			} else if (end != -1){
				b.append("-");
				b.append(String.format("%02d",(int)end/60));
				b.append(":");
				b.append(String.format("%02d",end%60));
			}
			if (openEnded) {
				b.append("+");
			}
			if (interval != 0) { // output only the full format
				b.append("/");
				b.append(String.format("%02d",(int)interval/60));
				b.append(":");
				b.append(String.format("%02d",interval%60));
			}
		}
		return b.toString();
	}

}
