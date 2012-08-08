/*
 * Copyright (C) 2012 Binomed (http://blog.binomed.fr)
 *
 * Licensed under the Eclipse Public License - v 1.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS ECLIPSE PUBLIC 
 * LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR DISTRIBUTION OF THE PROGRAM 
 * CONSTITUTES RECIPIENT'S ACCEPTANCE OF THIS AGREEMENT.
 */
package com.binomed.test.custom;

/**
 * @author Jef
 * 
 */
public interface IFragmentCacheColor {

	/**
	 * @param useCacheColorHint
	 *            The boolean for specifying use of cache color hint process
	 * @param useTransparentCacheColorHint
	 *            The boolean for specifying if the cache color hint have to be transparent
	 */
	void setCacheColorHint(boolean useCacheColorHint, boolean useTransparentCacheColorHint);
}
