package com.amsdams.ex.simplerest.web.rest.util;


import org.springframework.http.HttpHeaders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class HeaderUtil {


	private HeaderUtil() {
	}

	public static HttpHeaders createAlert(String applicationName, String message, String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-" + applicationName + "-alert", message);
		headers.add("X-" + applicationName + "-params", param);
		return headers;
	}

	public static HttpHeaders createEntityCreationAlert(String applicationName, String entityName, String param) {
		String message = "A new " + entityName + " is created with identifier " + param;
		return createAlert(applicationName, message, param);
	}

	public static HttpHeaders createEntityUpdateAlert(String applicationName, String entityName, String param) {
		String message = "A " + entityName + " is updated with identifier " + param;
		return createAlert(applicationName, message, param);
	}

	public static HttpHeaders createEntityDeletionAlert(String applicationName, String entityName, String param) {
		String message = "A " + entityName + " is deleted with identifier " + param;
		return createAlert(applicationName, message, param);
	}

	public static HttpHeaders createFailureAlert(String applicationName, String entityName, String errorKey,
			String defaultMessage) {
		log.error("Entity processing failed, {} , {} ", defaultMessage, errorKey);

		String message = defaultMessage;

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-" + applicationName + "-error", message);
		headers.add("X-" + applicationName + "-params", entityName);
		return headers;
	}
}
