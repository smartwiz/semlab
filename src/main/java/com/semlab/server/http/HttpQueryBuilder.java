package com.semlab.server.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.semlab.server.resources.Concept;
import com.semlab.server.resources.FType;

public class HttpQueryBuilder {
	
	public static String buildHttpGetDBpediaLookupAddress(Concept concept){
		StringBuilder addressBuilder = new StringBuilder("http://lookup.dbpedia.org/api/search.asmx/KeywordSearch?QueryClass=");
		String conceptClass = FType.getTypeForFBType(concept.getType()).getUri();
		if (conceptClass != null){
			int indexOfSlash = conceptClass.lastIndexOf("/");
			conceptClass = conceptClass.substring(indexOfSlash + 1);
		} else {
			conceptClass = "";
		}
		addressBuilder.append(conceptClass+"&QueryString=");
		String[] names = concept.getName().split(",");
		try {
			addressBuilder.append(URLEncoder.encode(names[0],"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addressBuilder.toString();
	}
	
	public static String buildHttpGetMeteoAddress(Concept concept){
		StringBuilder addressBuilder = new StringBuilder("http://api.yr.no/weatherapi/locationforecast/1.8/?lat=");
		if(concept.hasProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat")){
			if(concept.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat") instanceof List){
				List<Double> lats = (List<Double>) concept.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
				addressBuilder.append(lats.get(0));
			} else {
				addressBuilder.append(concept.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat"));
			}
		}
		if(concept.hasProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long")){
			if(concept.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long") instanceof List){
				List<Double> longs = (List<Double>) concept.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");
				addressBuilder.append(longs.get(0));
			} else {
				addressBuilder.append(";lon="+concept.getProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long"));
			}
		}
		return addressBuilder.toString();
	}

}
