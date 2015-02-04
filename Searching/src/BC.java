/**
 * This is the class where Backward Chaining is implemented.
 * 
 * @author Xiangyun
 */
import java.util.*;

public class BC {

	private KB kb;
	private ArrayList<Element> checkedList;
	private ArrayList<Element> fringe;
	private ArrayList<Element> endList;	// a list of elements which contains no premises
	private String solution;
	private Element query;
	
	//constructor
	public BC(KB kb){
		this.solution = "";
		this.kb = kb;
		this.fringe = new ArrayList<Element>();
		this.endList = new ArrayList<Element>();
		this.query = kb.getQuery();
		this.checkedList = new ArrayList<Element>();
	}
	
	public void infer(Element e){
		
		if(!inCheckedList(e)){
			if(!e.getPremise().isEmpty()){	//if it has premises
				for(Element element : e.getPremise()){	//for each element in the premises
					fringe.add(element);
				}
			}
			else
				endList.add(e);
		}
		
	}//end of infer
	
	/**
	 * To execute
	 */
	public void run(){
		
		//add the query into fringe
		for(Element e : kb.getAllElements()){
			if(e.getSymbol().equals(query.getSymbol()))
				fringe.add(e);
		}

		while(fringe.size() != 0){
			
			Element currElement = fringe.get(0);
			
			fringe.remove(0);
			infer(currElement);
			
			if(!inCheckedList(currElement))
				checkedList.add(currElement);
		}
		
		if(!goalSatisfied()){
			solution = "No.";
		}
		else{
			solution = "Yes: ";
			for(Element e : checkedList){
				solution = solution + e.getSymbol() + "; ";
			}
		}
		System.out.println(solution);
	}
	
	/**
	 * Return the boolean variable of whether solution is found
	 * @return true	if solution is found
	 * 		   false if solution is not found
	 */
	public boolean goalSatisfied(){
		
		boolean satisfied = true;
		
		//if all elements in endlist are knownfacts, then satisfied
		for(Element e : endList){
			if(!e.getKnown())
				satisfied = false;
		}
		
		return satisfied;
	}
	
	/**
	 * To check if given element is in the checkedList or not
	 * @param el	given element
	 * @return	true if already in the checkedList
	 * 			false if not in the checkedList
	 */
	public boolean inCheckedList(Element el){
		boolean in = false;
		for(Element e : checkedList){
			if(e.getSymbol().equals(el.getSymbol()))
				in = true;
		}
		return in;
	}
}
