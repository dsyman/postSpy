package it.poste.postspy.entity;

import java.util.ArrayList;
import java.util.List;

public class SrConsole {
	
	private List<Parameter> inputParameters;

	public List<Parameter> getInputParameters() {
		if(inputParameters==null) {
			inputParameters = new ArrayList<SrConsole.Parameter>();
		}
		return inputParameters;
	}

	public void setInputParameters(List<Parameter> inputParameters) {
		this.inputParameters = inputParameters;
	}

	public static class Parameter {
		protected String name;
		protected String value;
		
		public String getName() {
		    return name;
		}
		public void setName(String value) {
		    this.name = value;
		}
		public String getValue() {
		    return value;
		}
		public void setValue(String value) {
		    this.value = value;
		}
	}    

}
