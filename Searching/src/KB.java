/**
 * This is a class to store Knowledge Base.
 * 
 * @author Xiangyun
 */

import java.util.*;
import java.io.*;

public class KB {
	
	private ArrayList<Element> elements;
	private Element query;
	private BufferedReader br;
	private ArrayList<String> clauses;
	//private ArrayList<Element> singles; // to store the single literals as a clause
	
	/**
	 * Constructor
	 * @param filename
	 */
	public KB(String filename){
		elements = new ArrayList<Element>();
		clauses = new ArrayList<String>();
		setKB(filename);
		
		/*testing purpose, to be removed
		for(Element e: elements){
			System.out.print(e.getSymbol() + "|");
		}
		System.out.println();
		for(String c : clauses){
			System.out.print(c + "|");
		}
		System.out.println();*/
	}
	
	/**
	 * Set up Knowledge Base
	 * Read file, read and store all clauses;
	 * Store single literal into knownFacts;
	 * Store all lhs to primises;
	 * Store all rhs to inferences;
	 * 
	 * @param filename	the file contains test data;
	 */
	public void setKB(String filename){
				
		try{
			br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();	//read first line of test file
						
			while(line != null){
								
				if(line.trim().equals(""))	//if line is empty, skip
					continue;
					
				//while(!br.readLine().trim().equals("ASK")){
				if(line.trim().equals("TELL")){
					
					line = br.readLine();	//read the line with all the clause
					String[] tempClauses = line.replaceAll(" ", "").split(";");
				
					for(String s : tempClauses){
						
						//add in the arraylist
						this.clauses.add(s);
						
						if(!s.contains("=>")){	
							Element e = new Element(s);
							
							if(!isDuplicate(e)){
								e.setKnown(true);
								elements.add(e); 
							}
							else
								setKnown(s);							
						}
						else{
							String[] temp = s.split("=>");
							String lhs = temp[0];
							String rhs = temp[1];
							Element right = new Element(rhs);
							
							String[] lhsTemp = lhs.split("&");	
							
							for(String str : lhsTemp){
								Element left = new Element(str);	
								boolean leftExist = isDuplicate(left);
								boolean rightExist = isDuplicate(right);
								
								if(!leftExist){	
									elements.add(left);
									addImp(left.getSymbol(), right);									
								}
								else	
									addImp(left.getSymbol(), right);								
								
								if(!rightExist){	
									elements.add(right);
									addPre(right.getSymbol(), left);
								}
								else	
									addPre(right.getSymbol(), left);
							}
						}
					}		
				}
				
				if(line.trim().equals("ASK")){	//read in query
					line = br.readLine();	
					query = new Element(line);	//create query element
				}
				
				line = br.readLine();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try
            {
                if (br != null)
                {
                    br.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
		}
	}
	

	
	/**
	 * add a premise to an element which has the given name
	 * @param name	name of the element to which the pre will added
	 * @param pre	the element which will be added as a premise
	 */
	public void addPre(String name, Element pre){
		boolean preExist = false;
		for(Element e1 : elements){
			
			if(e1.getSymbol().equals(name)){	//if the element looking at is the given one,
				
				for(Element e2 : elements){
					
					if(e2.getSymbol().equals(pre.getSymbol())){
						e1.addPremise(e2);
						preExist = true;
					}
				}
				if(!preExist)
					e1.addPremise(pre);
			}
		}
	}
	
	/**
	 * add a inference to an element which has the given name
	 * @param name	name of the element to which the imp will be added
	 * @param imp	the element which will be added as a implication
	 */
	public void addImp(String name, Element imp){
		boolean impExist = false;
		for(Element e1 : elements){
			
			if(e1.getSymbol().equals(name)){	//if the element looking at is the given one,
				
				for(Element e2 : elements){
					
					if(e2.getSymbol().equals(imp.getSymbol())){
						e1.addImplication(e2);
						impExist = true;
					}
				}
				if(!impExist)
					e1.addImplication(imp);
			}
		}
	}
	
	/**
	 * Set the element which has the given name to be known
	 * @param name	the name of the element which shall be set to known
	 */
	public void setKnown(String name){
		for(Element el : elements){
			if(el.getSymbol().equals(name))
				el.setKnown(true);
		}
	}
	
	/**
	 * To check if the given element is already in elements(based on the symbol).
	 * 
	 * @param e	the given element
	 * @return	true	if already exists
	 * 			false	if does not exist
	 */
	public boolean isDuplicate(Element e){
		boolean duplicate = false;
		
		for(Element element : elements){
			if(e.getSymbol().equals(element.getSymbol()))
				duplicate = true;
		}
		return duplicate;
	}
	
	/**
	 * To return all the elements
	 * @return elements	the arraylist contains all the elements
	 */
	public ArrayList<Element> getAllElements(){
		return elements;
	}

	/**
	 * To return an arraylist contains all the clauses
	 * @return
	 */
	public ArrayList<String> getAllClauses(){
		return clauses;
	}
	
	/**
	 * To return the query
	 * @return query
	 */
	public Element getQuery(){
		return query;
	}
	
	/**
	 * testing purpose toString
	 */
	public String toString(){
		
		String s = "";
		
		for(Element e : elements){
			s = s + e.getSymbol();
		}
		
		return s;
	}
}
