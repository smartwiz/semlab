package com.semlab.shared.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.semlab.server.resources.Concept;
import com.semlab.server.resources.FType;
import com.semlab.shared.Type;

@Component
public class Things {

	@Autowired
	private List<Thing> things;

	public Thing findThing(Concept concept) {

		for (Thing thing : things) {
			if (thing.getClassType().equalsIgnoreCase(concept.getType())) {
				return thing;
			}
		}
		return null;
	}

	public Thing findThing(String uri) {
		for (Thing thing : things) {
			if (thing.getClassType().equalsIgnoreCase(uri)) {
				return thing;
			}
		}
		return null;
	}
	
	public boolean isInTypes(List<Type> types, String uri){
		for (Type type : types) {
			if(type.getClassType().equalsIgnoreCase(uri)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isInTypesFromFBTypes(List<Type> types, String fbType){
		
		FType ftype = FType.getTypeForFBType(fbType);
		if (ftype.getUri() == null && (types.contains(new Type(FType.Musician.getUri())) | types.contains(new Type(FType.Band.getUri())))){
			return true;
		} else {
			for (Type type : types) {
				if(type.getClassType().equalsIgnoreCase(FType.getTypeForFBType(fbType).getUri())){
					return true;
				}
			}
			return false;
		}
	}
	
}
