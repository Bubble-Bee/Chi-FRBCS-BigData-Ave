package org.apache.mahout.classifier.chi_rw;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;


public class Fuzzy implements Writable{
	  double x0, x1, x3, y;
	  String name;
	  int label;

	  /**
	   * Default constructor
	   */
	  public Fuzzy() {
	  }

	  /**
	   * If fuzzyfies a crisp value
	   * @param X double The crips value
	   * @return double the degree of membership
	   */
	  public double Fuzzify(double X) {
	    if ( (X <= x0) || (X >= x3)) /* If X is not in the range of D, the */
	        {
	      return (0.0); /* membership degree is 0 */
	    }

	    if (X < x1) {
	      return ( (X - x0) * (y / (x1 - x0)));
	    }

	    if (X > x1) {
	      return ( (x3 - X) * (y / (x3 - x1)));
	    }

	    return (y);

	  }

	  /**
	   * It makes a copy for the object
	   * @return Fuzzy a copy for the object
	   */
	  public Fuzzy clone(){
	    Fuzzy d = new Fuzzy();
	    d.x0 = this.x0;
	    d.x1 = this.x1;
	    d.x3 = this.x3;
	    d.y = this.y;
	    d.name = this.name;
	    d.label = this.label;
	    return d;
	  }

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		x0 = in.readDouble();
		x1 = in.readDouble();
		x3 = in.readDouble();
		y = in.readDouble();
		
		name = in.readUTF();
		
		label = in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeDouble(x0);
		out.writeDouble(x1);
		out.writeDouble(x3);
		out.writeDouble(y);
		
		out.writeUTF(name);
		
		out.writeInt(label);
	}
}

