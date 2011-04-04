/*
 * Copyright 2010-2011 Excilys (www.excilys.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.excilys.utils.web.flash;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores the current FlashScope in a ThreadLocal so that it can be accessed
 * without relying on the underliying HTTP storage.
 * 
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
// NotThreadSafe
public class FlashScopeHandler {

	public static final class Binder {

		private final String name;

		private Binder(String name) {
			this.name = name;
		}

		/**
		 * @param value The value to be set for the associated attribute name
		 */
		public void to(Object value) {
			setFlashAttribute(name, value);
		}
	}
	
	/**
	 * Call this method to set flash attributes. The fluid API should be used
	 * the following way : {@link FlashScopeHandler}.bind(name).to(value);
	 * 
	 * @param name
	 *            the flash attribute name
	 * @return a {@link Binder} on which you can call the
	 *         {@link Binder#to(Object)} method to set the attribute value.
	 */
	public static Binder bind(String name) {
		return new Binder(name);
	}

	// TODO handle InheritableThreadLocal
	private static final ThreadLocal<Map<String, Object>> HOLDER = new ThreadLocal<Map<String, Object>>();

	static Map<String, Object> getFlash() {

		Map<String, Object> flash = HOLDER.get();
		if (flash == null) {
			flash = new HashMap<String, Object>();
			HOLDER.set(flash);
		}

		return flash;
	}

	static void clear() {
		HOLDER.set(null);
	}

	public static void setFlashAttribute(String name, Object value) {
		getFlash().put(name, value);
	}

}
