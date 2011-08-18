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

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Filter in charge of storing the flash scope in the ThreadLocal into the HTTP
 * session.
 * 
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class FlashScopeHandler {

	/**
	 * The key to store the Flash in HTTP Session
	 */
	private static final String FLASH_SCOPE_KEY = FlashScopeHandler.class.getPackage().getName();

	public FlashScopeHandler(Storage storage) {
		FlashScope.initialize(storage);
	}

	/**
	 * Transform the content of the previous Flash as request attributes
	 * 
	 * @param request
	 *            the request
	 */
	public void movePreviousFlashFromSessionToRequest(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		if (session != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> flash = Map.class.cast(session.getAttribute(FLASH_SCOPE_KEY));

			if (flash != null) {
				for (Entry<String, Object> attribute : flash.entrySet()) {
					request.setAttribute(attribute.getKey(), attribute.getValue());
				}
				session.setAttribute(FLASH_SCOPE_KEY, null);
			}
		}
	}

	/**
	 * Store the current Flash in the HTTP Session so that it can be retrieved
	 * in the next Request
	 * 
	 * @param request
	 *            the request
	 */
	public void storeCurrentFlashInSession(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.setAttribute(FLASH_SCOPE_KEY, FlashScope.getFlash());
			FlashScope.clear();
		}
	}
}
