/*
 * Copyright 2010-2011 eBusiness Information, Groupe Excilys (www.excilys.com)
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
package com.excilys.ebi.utils.web.flash;

import java.util.HashMap;
import java.util.Map;

/**
 * Built-in storages
 * 
 * 
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public enum BuiltInStorages implements Storage {

	/**
	 * Storage based on a {@link ThreadLocal}
	 * 
	 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
	 */
	MODE_THREADLOCAL() {

		private ThreadLocal<Map<String, Object>> HOLDER = new ThreadLocal<Map<String, Object>>();

		public void clear() {
			HOLDER.set(null);
		}

		public Map<String, Object> getFlash() {
			Map<String, Object> flash = HOLDER.get();
			if (flash == null) {
				flash = new HashMap<String, Object>();
				HOLDER.set(flash);
			}

			return flash;
		}
	},

	/**
	 * Storage based on an {@link InheritableThreadLocal}
	 * 
	 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
	 */
	MODE_INHERITABLETHREADLOCAL() {

		private ThreadLocal<Map<String, Object>> HOLDER = new InheritableThreadLocal<Map<String, Object>>();

		public void clear() {
			HOLDER.set(null);
		}

		public Map<String, Object> getFlash() {
			Map<String, Object> flash = HOLDER.get();
			if (flash == null) {
				flash = new HashMap<String, Object>();
				HOLDER.set(flash);
			}

			return flash;
		}
	};

	/**
	 * @param name
	 *            the name of the strategy
	 * @return the strategy with the enum name
	 */
	public static Storage getBuiltIn(String name) {
		try {
			return BuiltInStorages.valueOf(name);

		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
