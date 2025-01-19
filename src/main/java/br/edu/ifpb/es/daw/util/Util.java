package br.edu.ifpb.es.daw.util;

import jakarta.persistence.Persistence;
import java.util.Collection;

public class Util {

	public static String safeToStringLazyCollection(Collection<?> collection) {

		if (collection == null) {
			return null;
		}

		if (Persistence.getPersistenceUtil().isLoaded(collection)) {
			return collection.toString();
		}

		return "<not initialized>";
	}

}
