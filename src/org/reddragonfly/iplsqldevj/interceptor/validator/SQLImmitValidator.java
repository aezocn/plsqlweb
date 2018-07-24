package org.reddragonfly.iplsqldevj.interceptor.validator;

import com.opensymphony.xwork2.validator.ValidationException;

public class SQLImmitValidator extends BaseValidator{
	
	private boolean effect;
	
	public void validate(Object object) throws ValidationException {
		// TODO Auto-generated method stub
		String fieldName = getFieldName();
		String fieldValue = getFieldValue(fieldName, object).toString();
		if(effect){
			if(fieldValue != null && fieldValue.indexOf("'") != -1){
				addFieldError(fieldName, object);
			}
		}
	}

	public boolean getEffect() {
		return effect;
	}

	public void setEffect(boolean effect) {
		this.effect = effect;
	}
	
}
