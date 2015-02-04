/**
 * This is the class where Forward Chaining is implemented.
 * 
 * @author Xiangyun
 */
import java.util.*;

public class FC {
	
	private KB kb;
	private ArrayList<Element> entailedSymbols;
	private ArrayList<Element> fringe;
	private String solution;
	
	//constructor
	public FC(KB kb){
		this.solution = "";
		this.kb = kb;
		this.entailedSymbols = new ArrayList<Element>();
		this.fringe = new ArrayList<Element>();
	}
	
	
	
	/**
	 * To execute 
	 */
	public void run(){

		boolean queryFound = false;	//an variable to indicate the query is found
		
		//add the known facts into fringe
		for(Element e : kb.getAllElements()){
			if(e.getKnown()){
				fringe.add(e);	
			}
		}

		while(fringe.size() != 0){

			Element currElement = fringe.get(0);
			
			fringe.remove(0);
			infer(currElement);
			
			if(currElement.getSymbol().equals(kb.getQuery().getSymbol())){	//if query if found then break
				queryFound = true;
				break;
			}
		}
		
		if(!queryFound)
			solution = "NO.";
		else{
			solution = "YES: ";
			for(Element el : entailedSymbols){
				solution = solution + el.getSymbol() + "; ";
			}
		}
		
		System.out.println(solution);
		
	}
	
	/**
	 * To find any valid implication and add to the fringe;
	 * 
	 * @param e
	 */
	public void infer(Element e){
		
		if(!alreadyEntailed(e))
			entailedSymbols.add(e);
		
		//System.out.println(e.getImplication().toString());
		if(!e.getImplication().isEmpty()){	//if it has implication
			
			for(Element element : e.getImplication()){	//for each element in the implications
				
				boolean valid = true;	
				
				if(element.hasMultiplePre()){	
					for(Element pre : element.getPremise()){	
						if(!pre.getKnown())	
							valid = false;
					}
				}
				if(valid){
					element.setKnown(true);
					fringe.add(element);					
				}
			}
		}
	}
	
	/**
	 * To check if given element is entailed from KB
	 * @param e	given element
	 * @return	ture if yes
	 * 			false if no
	 */
	public boolean alreadyEntailed(Element e){
		for(Element element : entailedSymbols){
			if(e.getSymbol().equals(element.getSymbol()))
				return true;
		}
		return false;
	}
}
