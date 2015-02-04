/**
 * This is the class representing each of the single elemen.
 * 
 * @author Xiangyun
 *
 */

import java.util.*;

public class Element {
	
	private ArrayList<Element> premises;
	private ArrayList<Element> implications;
	private String symbol;
	private boolean known;
	private int value;
	
	/**
	 * Constructor
	 * @param symbol 
	 */
	public Element(String symbol){
		this.symbol = symbol;
		this.premises = new ArrayList<Element>();
		this.implications = new ArrayList<Element>();
		this.known = false;
		
	}
	
	
	/**
	 * GETs & SETs
	 */
	
	public int getValue(){
		return this.value;
	}
	
	public void setValue(int i){
		this.value = i;
	}
	
	public void flipValue(){
		if(value == 1)
			value = 0;
		else value = 1;
	}
	
	public void setKnown(boolean bool){
		this.known = bool;
	}
	
	public boolean getKnown(){
		return this.known;
	}
	
	public String getSymbol(){
		return this.symbol;
	}
	
	public void setPremise(ArrayList<Element> pri){
		this.premises = pri;
	}
	
	public void addPremise(Element e){
		this.premises.add(e);
	}
	
	public void setImplication(ArrayList<Element> imp){
		this.implications = imp;
	}
	
	public void addImplication(Element e){
		this.implications.add(e);
	}
	
	public ArrayList<Element> getPremise(){
		return this.premises;
	}
	
	public ArrayList<Element> getImplication(){
		return this.implications;
	}
	
	public boolean hasMultiplePre(){
		if(this.premises.size() > 1)
			return true;
		return false;
	}
	
	public boolean hasMultipleImp(){
		if(this.implications.size() > 1)
			return true;
		return false;
	}
	
}




