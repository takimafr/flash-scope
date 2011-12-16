/**
 * Copyright 2011-2012 eBusiness Information, Groupe Excilys (www.excilys.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.excilys.ebi.utils.web.flash;

import java.io.IOException;
import java.lang.reflect.Constructor;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Filter in charge of synchronizing the flash scope and the HTTP session.
 * 
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class FlashFilter implements Filter {

	public static final String INIT_PARAM = "excilys.flash.storage";

	private Storage storage;

	private FlashScopeHandler handler;

	public void init(FilterConfig filterConfig) throws ServletException {

		String storageName = filterConfig.getInitParameter(INIT_PARAM);

		if ((storageName == null) || "".equals(storageName)) {
			// Set default
			storage = BuiltInStorages.MODE_THREADLOCAL;

		} else {
			storage = BuiltInStorages.getBuiltIn(storageName);
		}

		if (storage == null) {
			// Try to load a custom storage
			try {
				Class<?> clazz = Class.forName(storageName);
				Constructor<?> customStrategy = clazz.getConstructor();
				storage = Storage.class.cast(customStrategy.newInstance());

			} catch (Exception ex) {
				throw new ExceptionInInitializerError(ex);
			}
		}

		if (storage == null) {
			throw new ExceptionInInitializerError("Impossible to find a strategy for " + storageName);
		}

		handler = new FlashScopeHandler(storage);
	}

	public void destroy() {
		// nopo
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = HttpServletRequest.class.cast(request);

		try {
			handler.movePreviousFlashFromSessionToRequest(httpRequest);
			chain.doFilter(request, response);

		} finally {
			handler.storeCurrentFlashInSession(httpRequest);
		}
	}
}
