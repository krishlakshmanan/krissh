package com.krissh.convertz;

import java.util.List;

/**
 * @author Krish Lakshmanan
 * @version 1.6
 */
public interface IConvertz {

	/**
	 * This method will copy one object to another object.
	 * @param src - object to be copied to dest.
	 * @param dest - copied object from src.
	 * @return
	 */
	public Object ctrlC(Object src,Object dest);
	
	public String getClassName(Class<? extends Object> c);
	
	public <U, T> List<U> ctrlAllC(Object entityCollections, Object dtoObject);
}
