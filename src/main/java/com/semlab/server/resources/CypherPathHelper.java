package com.semlab.server.resources;

import java.util.ArrayList;

import com.semlab.shared.Field;
import com.semlab.shared.Path;

public class CypherPathHelper {
	
	private ArrayList<CypherPathHolder> holders;

	public CypherPathHelper(ArrayList<Path> paths) {
		holders = new ArrayList<CypherPathHolder>();
		if(paths != null){
			for (Path path : paths) {
				breakPath(path);
			}
		}
	}
	
	public CypherPathHolder breakPath(Path path){
		ArrayList<Field> fields = path.getPath();
		if(fields != null){
			ArrayList<Field> temp = new ArrayList<Field>();
			CypherPathHolder tempHolder = null;
			for (int i = 0; i < fields.size(); i++) {
				temp.add(fields.get(i));
				CypherPathHolder holder = getHolder(temp);
				if(holder == null){
					tempHolder = new CypherPathHolder(temp, "x"+(holders.size()+1));
					holders.add(tempHolder);
				}
			}
			return tempHolder;
		}
		return null;
	}
	
	public CypherPathHolder getHolder(ArrayList<Field> fields){
		for (CypherPathHolder holder : holders) {
			ArrayList<Field> holderFields = holder.getFields();
			if(holderFields.size() != fields.size()){
				continue;
			}
			boolean error = false;
			for (int i = 0; i < holderFields.size(); i++) {
				Field f = holderFields.get(i);
				if(!f.getClassUri().equalsIgnoreCase(fields.get(i).getClassUri())){
					error = true;
					break;
				}
			}
			if(!error)
				return holder;
		}
		return null;
	}
	
	public String getIdentifier(ArrayList<Field> fields){
		CypherPathHolder holder =  getHolder(fields);
		return holder.getId();
	}

	public ArrayList<CypherPathHolder> getHolders() {
		return holders;
	}
	
}
