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
package com.excilys.ebi.utils.web.flash.spring;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.excilys.ebi.utils.web.flash.BuiltInStorages;
import com.excilys.ebi.utils.web.flash.FlashScopeHandler;
import com.excilys.ebi.utils.web.flash.Storage;

/**
 * Filter in charge of synchronizing the flash scope and the HTTP session.
 * 
 * @deprecated Use Spring 3.1 built in flash scope instead.
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
@Deprecated
public class SpringFlashFilter extends OncePerRequestFilter {

	private Storage storage = BuiltInStorages.MODE_THREADLOCAL;

	private FlashScopeHandler handler;

	public void initFilterBean() {
		handler = new FlashScopeHandler(storage);
	}

	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

		try {
			handler.movePreviousFlashFromSessionToRequest(request);
			chain.doFilter(request, response);

		} finally {
			handler.storeCurrentFlashInSession(request);
		}
	}

	public Storage getStrategy() {
		return storage;
	}

	public void setStrategy(Storage strategy) {
		this.storage = strategy;
	}
}
