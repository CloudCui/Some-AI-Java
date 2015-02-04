import java.util.ArrayList;

/**
 * This is the class where Truth Table Checking is implemented.
 * 
 * @author Xiangyun
 *
 */
public class TT {

	private KB kb;
	private ArrayList<Element> elements;
	private ArrayList<String> clauses;
	private double tableSize;
	private int elementSize;
	private ArrayList<ArrayList<Element>> table;
	private String query;
	
	//constructor
	public TT(KB kb){
		this.kb = kb;
		this.table = new ArrayList<ArrayList<Element>>();
		this.elements = kb.getAllElements();
		this.clauses = kb.getAllClauses();
		this.tableSize = Math.pow(2, elements.size());
		this.elementSize = elements.size();
		this.query = kb.getQuery().getSymbol();
	}
	
	/**
	 * To execute 
	 */
	public void run(){
		System.out.println("TT");
		setElementTable();
		
		int total = 0;
		for(ArrayList<Element> a : table){
			if(checkAllTrue(a)){
				for(Element e : a){
					if(e.getSymbol().equals(query) && e.getValue() == 1)
						total++;					
				}
			}
		}
		
		/*test
		System.out.println(table.size());
		int b = 1;
		for(ArrayList<Element> a : table){
			System.out.println(b + ": ");
			for(Element e : a){
				System.out.print(e.getSymbol() +":" + e.getValue()+"|");
				
			}
			System.out.println();b++;
		}*/
		if(total != 0)
			System.out.println("Yes: " + total);
		else
			System.out.println("No.");
	}
	
	public void setElementTable(){

		for(int i = 0; i < tableSize; i++){
			ArrayList<Element> row = cloneElements();
			table.add(row);
		}
		
		//set values
		for(int i = 0; i < elementSize; i++){
			int flipCounter = (int)Math.pow(2, (elementSize - (i + 1)));
			int counter = 1;
			int tempValue = 0;
			for(int j = 0; j < tableSize; j++){
				if(counter != flipCounter){
					table.get(j).get(i).setValue(tempValue);
					counter++;
				}
				else{
					tempValue = flip(tempValue);
					table.get(j).get(i).setValue(tempValue);
					counter = 1;
				}
			}			
		}		
		
	}
	
	public int flip(int i){
		if(i == 1 )
			i = 0;
		else
			i = 1;
		return i;
	}
	
	public ArrayList<Element> cloneElements(){
		ArrayList<Element> temp = new ArrayList<Element>();
		for(Element e : elements){
			temp.add(new Element(e.getSymbol()));
		}
		return temp;
	}
	
	public boolean checkAllTrue(ArrayList<Element> arrayElements){
			
		boolean alltrue = true;
		for(String s : clauses){
						
			if(!s.contains("=>")){
				for(Element e : arrayElements){
					if(e.getSymbol().equals(s) && e.getValue() == 0){
						alltrue = false;
					}
				}
			}
			else{
				String[] temp = s.split("=>");
				String lhs = temp[0];
				String rhs = temp[1];
				
				//for those in the format of "a=>b"
				if(!lhs.contains("&")){
					int lvalue = 0;
					int rvalue = 0;
					for(Element e : arrayElements){
						if(e.getSymbol().equals(lhs)){
							lvalue = e.getValue();
						}
						if(e.getSymbol().equals(rhs)){
							rvalue = e.getValue();
						}
					}
					
					if(lvalue == 1 && rvalue == 0){
						alltrue = false;
					}
				}
				else{
					int leftVal = 1;
					int rightVal = 0;
					String[] left = lhs.split("&");
					for(String c : left){
						for(Element f : arrayElements){
							if(f.getSymbol().equals(c) && f.getValue() == 0){
								leftVal = 0;
								break;
							}
						}
					}
					for(Element g : arrayElements){
						if(g.getSymbol().equals(rhs))
							rightVal = g.getValue();
					}
					
					if(leftVal == 1 && rightVal == 0){
						alltrue = false;
					}
				}
			}
		}
		return alltrue;
	}
	
}

