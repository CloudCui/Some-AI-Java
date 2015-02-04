/**
 * This is the class to store info of a single cell
 * 
 * @author Xiangyun
 *
 */
public class Cell {
	
	//coordination of the cell
	private int x;
	private int y;
	private int value;
	
	public Cell(int value){
		this.x = 0;
		this.y = 0;
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setValue(int v){
		this.value = v;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}

	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
}
