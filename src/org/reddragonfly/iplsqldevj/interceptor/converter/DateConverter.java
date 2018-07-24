package org.reddragonfly.iplsqldevj.interceptor.converter;

import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.util.TypeConversionException;
import org.reddragonfly.iplsqldevj.DateUtilities;
import org.reddragonfly.iplsqldevj.interceptor.converter.BaseConverter;

public class DateConverter extends BaseConverter {
	
	public final String DATE_FORMAT = "yyyy-MM-dd";
	
	public Object convertFromString(Map arg0, String[] arg1, Class arg2) {
		// TODO Auto-generated method stub
		try{
			if(arg1 != null && arg1.length > 0){
				String dateString = arg1[0];
				if(dateString != null){
					return DateUtilities.getParseDateString(dateString, DATE_FORMAT);
				}
			}
			return null;
		}catch(Exception e){
			throw new TypeConversionException(e);
		}
	}
	
	public String convertToString(Map arg0, Object arg1) {
		// TODO Auto-generated method stub
		try{
			Date date = (Date)arg1;
			return DateUtilities.getFormatDate(date, DATE_FORMAT);
		}catch(Exception e){
			throw new TypeConversionException(e);
		}
	}
	
}
