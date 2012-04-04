package com.semlab.shared.config;


import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class Property implements Serializable {
	
	private String uri;
	private boolean inverse;
	private Thing typeOf;
	private boolean optional;
	private String label;
	private String filter;
	private PropertyType type = PropertyType.Text;
	private List<Thing> typesOf;
	private boolean ignoreDBP;
	private boolean useDifferentUri;
	private String overrideUri;
	private boolean hideOnUi;

	public Property() {
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isInverse() {
		return inverse;
	}

	public void setInverse(boolean inverse) {
		this.inverse = inverse;
	}

	public Thing getTypeOf() {
		return typeOf;
	}

	public void setTypeOf(Thing typeOf) {
		this.typeOf = typeOf;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public List<Thing> getTypesOf() {
		return typesOf;
	}

	public void setTypesOf(List<Thing> typesOf) {
		this.typesOf = typesOf;
	}

	@Override
	public String toString() {
		return "Property [uri=" + uri + ", inverse=" + inverse + ", typeOf="
				+ typeOf + ", optional=" + optional + ", label=" + label
				+ ", filter=" + filter + ", type=" + type + ", typesOf="
				+ typesOf + ", ignoreDBP=" + ignoreDBP + "]";
	}

	public boolean isIgnoreDBP() {
		return ignoreDBP;
	}

	public void setIgnoreDBP(boolean ignoreDBP) {
		this.ignoreDBP = ignoreDBP;
	}

	public boolean isUseDifferentUri() {
		return useDifferentUri;
	}

	public void setUseDifferentUri(boolean useDifferentUri) {
		this.useDifferentUri = useDifferentUri;
	}
	

	public String getOverrideUri() {
		return overrideUri;
	}

	public void setOverrideUri(String overrideUri) {
		this.overrideUri = overrideUri;
	}

	public boolean isHideOnUi() {
		return hideOnUi;
	}

	public void setHideOnUi(boolean hideOnUi) {
		this.hideOnUi = hideOnUi;
	}

}
