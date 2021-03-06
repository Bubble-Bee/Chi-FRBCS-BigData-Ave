package org.apache.mahout.classifier.chi_rw.mapreduce.partial;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.classifier.chi_rw.RuleBase;
import org.apache.mahout.classifier.chi_rw.mapreduce.MapredOutput;
import org.apache.mahout.classifier.chi_rw.mapreduce.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChiReducer extends Reducer<LongWritable, MapredOutput, LongWritable, RuleBase>{

  private static final Logger log = LoggerFactory.getLogger(ChiReducer.class);
  
  public void reduce(LongWritable key, Iterable<MapredOutput> values, Context context) throws IOException, InterruptedException {
	LongWritable id = new LongWritable(1);
    RuleBase actualRuleBase;
    RuleBase finalRuleBase = new RuleBase();
    Configuration conf = context.getConfiguration();
    int j = 0;
    
    for (MapredOutput value : values){  
      actualRuleBase = value.getRuleBase();
	  for(int i = 0 ; i < actualRuleBase.size() ; i++){    	  
    	if (finalRuleBase.size() == 0){
    	  finalRuleBase = new RuleBase(actualRuleBase.getDataBase(), 
    			  actualRuleBase.getInferenceType(), 
    			  actualRuleBase.getCompatibilityType(), 
    			  actualRuleBase.getRuleWeight(), 
    			  actualRuleBase.getNames(), 
    			  actualRuleBase.getClasses(),
    			  Builder.getNumMaps(conf));
    	  finalRuleBase.add(actualRuleBase.get(i));      	  
    	}else if(!finalRuleBase.duplicated_reduce(actualRuleBase.get(i))){
          finalRuleBase.add(actualRuleBase.get(i));
        } 
      }
	  finalRuleBase.setRuleBaseSize(j, actualRuleBase.size());
	  j++;
  	}
    context.write(id, finalRuleBase);
  }

}
