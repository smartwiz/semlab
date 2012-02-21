package com.semlab.client.fwk;

import java.util.Collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.place.shared.PlaceTokenizer;

public class Place extends com.google.gwt.place.shared.Place {
	
	public static class Parameter<T> {
		
		String name;
		Coder<T> coder;
		
		public Parameter(java.lang.String name, Coder<T> coder) {
			this.name = name;
			this.coder = coder;
		}
	}

	public static interface Coder<T> {
		T decode(String value);
		String encode(T value);
	}
	
	public static final Coder<String> String = new Coder<String>() {
		@Override
		public String decode(String value) {
			return value;
		}
		
		@Override
		public String encode(String value) {
			return value;
		}
	};
	
	public static final Coder<Integer> Integer =  new Coder<Integer>() {
		@Override
		public Integer decode(String value) {
			try {
				return java.lang.Integer.valueOf(value);
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		public String encode(Integer value) {
			return value.toString();
		}
	};
	
	public static final Coder<Long> Long =  new Coder<Long>() {
		@Override
		public Long decode(String value) {
			try {
				return java.lang.Long.valueOf(value);
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		public String encode(Long value) {
			return value.toString();
		}
	};

	
	public static final Coder<Boolean> Boolean = new Coder<Boolean>() {
		@Override
		public Boolean decode(String value) {
			return java.lang.Boolean.parseBoolean(value);
		}
		
		@Override
		public String encode(Boolean value) {
			return value.toString();
		}
	};
	
	private final String containerId;
	private final String name;
	private String title;
	private Map<String, String> params = new HashMap<String, String>();

	public Place(String containerId, String name) {
		this.containerId = containerId;
		this.name = name;
	}
	
	public Place(String containerId, String name, String title) {
		this.containerId = containerId;
		this.name = name;
		this.title = title;
	}

	private Place(Place req, String name, String value) {
		this.containerId = req.containerId;
		this.name = req.name;
		this.title = req.title;
		if (req.params != null)
			this.params.putAll(req.params);
		if (value != null)
			this.params.put(name, value);
	}

	public String getContainerId() {
		return containerId;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<String> getParameterNames() {
		if (params != null) {
			return params.keySet();
		} else {
			return Collections.emptySet();
		}
	}

	public String get(String key) {
		return get(key, String);
	}

	public <T>T get(String key, Coder<T> coder) {
		return coder.decode(params.get(key)); 
	}
	
	public <T>T get(Parameter<T> parameter) {
		return parameter.coder.decode(params.get(parameter.name)); 
	}
	

	/**
	 * Returns a new instance of the request with the specified parameter name
	 * and value. If a parameter with the same name was previously specified,
	 * the new request contains the new value.
	 * 
	 * @param name
	 *            The new parameter name.
	 * @param value
	 *            The new parameter value.
	 * @return The new place request instance.
	 */
	public Place with(String name, String value) {
		return with(name, value, String);
	}
	
	public Place with(String name, Integer value) {
		return with(name, value, Integer);
	}
	
	<T>Place with(String name, T value, Coder<T> coder) {
		return new Place(this, name, coder.encode(value));
	}
	
	public <T>Place with(Parameter<T> param, T value) {
		return new Place(this, param.name, param.coder.encode(value));
	}
	
	public <T>Place and(Parameter<T> param, T value) {
		return with(param, value);
	}

	@Override
	public String toString() {
		return "Place [containerId=" + containerId + ", name=" + name + ", params=" + params + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((containerId == null) ? 0 : containerId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (containerId == null) {
			if (other.containerId != null)
				return false;
		} else if (!containerId.equals(other.containerId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static class Tokenizer implements PlaceTokenizer<Place> {

		private static final String ID_SEPARATOR = "@";

		private static final String PARAM_SEPARATOR = ";";

		private static final String PARAM_PATTERN = PARAM_SEPARATOR + "(?!" + PARAM_SEPARATOR + ")";

		private static final String PARAM_ESCAPE = PARAM_SEPARATOR + PARAM_SEPARATOR;

		private static final String VALUE_SEPARATOR = "=";

		private static final String VALUE_PATTERN = VALUE_SEPARATOR + "(?!" + VALUE_SEPARATOR + ")";

		private static final String VALUE_ESCAPE = VALUE_SEPARATOR + VALUE_SEPARATOR;

		private static String escape(String value) {
			return value.replaceAll(PARAM_SEPARATOR, PARAM_ESCAPE).replaceAll(VALUE_SEPARATOR, VALUE_ESCAPE);
		}

		private static String unescape(String value) {
			return value.replaceAll(PARAM_ESCAPE, PARAM_SEPARATOR).replaceAll(VALUE_ESCAPE, VALUE_SEPARATOR);
		}

		@Override
		public Place getPlace(String token) {
			Place place = null;
			int split = token.indexOf(PARAM_SEPARATOR);
			if (split == 0) {
				throw new IllegalArgumentException("Place id is missing.");
			} else if (split == -1) {
				int idsplit = token.indexOf(ID_SEPARATOR);
				if (idsplit == -1) {
					throw new IllegalArgumentException("Place id is in bad format.");
				} else {
					place = new Place(token.substring(0, idsplit), token.substring(idsplit + 1));
				}
			} else if (split >= 0) {
				String idToken = token.substring(0, split);
				int idsplit = idToken.indexOf(ID_SEPARATOR);
				if (idsplit == -1) {
					throw new IllegalArgumentException("Place id is in bad format.");
				} else {
					place = new Place(idToken.substring(0, idsplit), idToken.substring(idsplit + 1));
				}
				String paramsChunk = token.substring(split + 1);
				String[] paramTokens = paramsChunk.split(PARAM_PATTERN);
				for (String paramToken : paramTokens) {
					String[] param = paramToken.split(VALUE_PATTERN);
					if (param.length == 1)
						place = place.with(unescape(param[0]), "");
					else if (param.length == 2)
						place = place.with(unescape(param[0]), unescape(param[1]));
					else
						throw new IllegalArgumentException("Bad parameter: Parameters require a single '"
								+ VALUE_SEPARATOR + "' between the key and value.");

				}
			}

			return place;
		}

		@Override
		public String getToken(Place place) {
			StringBuilder out = new StringBuilder();
			out.append(place.getContainerId());
			out.append(ID_SEPARATOR);
			out.append(place.getName());

			Set<String> params = place.getParameterNames();
			if (params != null && params.size() > 0) {
				for (String name : params) {
					out.append(PARAM_SEPARATOR);
					out.append(escape(name)).append(VALUE_SEPARATOR).append(escape(place.get(name)));
				}
			}
			return out.toString();
		}
	}

}
