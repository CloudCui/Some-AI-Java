import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This is the class to store a configuration
 * 
 * @author Xiangyun
 *
 */
public class Grid{
	
	/**
	 * attributes of a Grid
	 */
	private ArrayList<Cell> config = new ArrayList<Cell>();
	private int n; //N
	private int m; //M
	private int movingCellPos;//stores the position of cell 0 in the arraylist
	
	private Grid parentGrid;
	private int direction;
	
	private int totalGn;//G(n), distance from origin
	private int totalHn;//H(n), distance from to goal

	
	/**
	 * This constructor is for constructing start and end configuration
	 * @param filename	name of the test.txt
	 * @param state		0 reps startConfig
	 * 					1 reps endConfig
	 */
	public Grid(String filename, int state){
		if(state == 0)
			loadStartConfig(filename);
		else if(state == 1)
			loadEndConfig(filename);
		else
			System.out.println("Loading config failed.");
	}
	
	public Grid(Grid g, int dir){
		this.n = g.getN();
		this.m = g.getM();
		this.parentGrid = g;
		this.movingCellPos = g.movingCellPos;
		this.direction = dir;
		this.totalGn = 0;
		this.totalHn = 0;
		loadConfig(g);
		reScheduleGrid(dir);
	}
	
	public void loadConfig(Grid g){
		for(Cell c : g.getConfig()){
			this.config.add(new Cell(c.getValue()));
		}
	}
	
	/**
	 * To construct this grid to be the initial grid
	 * @param file	file to read data from
	 */
	public void loadStartConfig(String file){
		
		BufferedReader reader;
		String line; //for storing each line
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			//read the first line of the file
			line = reader.readLine(); 
			n = Integer.parseInt(line);
			
			//read the second line of the file
			line = reader.readLine(); 
			m = Integer.parseInt(line);
			
			//read the third line of the file, which is the start config
			line = reader.readLine();
			String cells[] = line.split(";");
			int count = 0;
			for(int i = 0; i < n; i++){
				for(int j = 0; j < m; j++){
					//if it is the cell to be moved, pass true
					if(Integer.parseInt(cells[count]) == 0){
						config.add(new Cell(Integer.parseInt(cells[count])));
						movingCellPos = count;
					}
					else
						config.add(new Cell(Integer.parseInt(cells[count])));
					count++;
				}
			}//finish creating the list of cells
			
		}
		catch(FileNotFoundException fnf){
			System.out.println("fnf");
		}
		catch(Exception e){
			System.out.println("Error");
		}
		this.parentGrid = null;
	}
	
	/**
	 * To construct the goal grid
	 * @param file	file to read data from
	 */
	public void loadEndConfig(String file){
		BufferedReader reader;
		String line; //for storing each line
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			//read the first line of the file
			line = reader.readLine(); 
			n = Integer.parseInt(line);
			//read the second line of the file
			line = reader.readLine(); 
			m = Integer.parseInt(line);
			
			//read the third line and skip
			line = reader.readLine();

			//read the fourth line of the file, which is the end config
			line = reader.readLine();
			String[] cells = line.split(";");
			int count = 0;
			for(int i = 0; i < n; i++){
				for(int j = 0; j < m; j++){
					//if it is the cell to be moved, pass true
					if(Integer.parseInt(cells[count]) == 0)
						config.add(new Cell(Integer.parseInt(cells[count])));
					else
						config.add(new Cell(Integer.parseInt(cells[count])));
					count++;
				}
			}//finish creating the list of cells
			
		}
		catch(FileNotFoundException fnf){
			System.out.println("fnf");
		}
		catch(Exception e){
			System.out.println("Error");
		}
	
	}
	
	/**
	 * reSchedule the grid by the given direction
	 * change the position of the moving cell
	 * @param dir given direction
	 */
	public void reScheduleGrid(int dir){
		
		int up = 1;
		int left = 2;
		int down = 3;
		int right = 4;
		int temp;
		
		if(dir == up){
			temp = config.get(this.movingCellPos - this.m).getValue();
			config.get(this.movingCellPos - this.m).setValue(0);
			config.get(movingCellPos).setValue(temp);
			this.movingCellPos = this.movingCellPos - this.m;
		}
		else if(dir == left){
			temp = config.get(this.movingCellPos - 1).getValue();
			config.get(this.movingCellPos - 1).setValue(0);
			config.get(movingCellPos).setValue(temp);
			this.movingCellPos = this.movingCellPos - 1;
		}
		else if(dir == down){
			temp = config.get(this.movingCellPos + this.m).getValue();
			config.get(this.movingCellPos + this.m).setValue(0);
			config.get(movingCellPos).setValue(temp);
			this.movingCellPos = this.movingCellPos + this.m;
		}
		else if(dir == right){
			temp = config.get(this.movingCellPos + 1).getValue();
			config.get(this.movingCellPos + 1).setValue(0);
			config.get(movingCellPos).setValue(temp);
			this.movingCellPos = this.movingCellPos + 1;
		}
		else
			System.out.println("Not valid direction when re-shedule-grid.");
	}
	
	public int getN(){
		return n;
	}
	
	public int getM(){
		return m;
	}
	
	public ArrayList<Cell> getConfig(){
		return config;
	}
	
	public int getMovingCellPos(){
		return this.movingCellPos;
	}
	
	public int getSize(){
		return n * m;
	}
	
	public void setParent(Grid g){
		this.parentGrid = g;
	}
	
	/**
	 * Compare this grid with a given grid
	 * @param g	given grid
	 * @return	true if they are the same
	 * 			false if they are different
	 */
	public boolean equals(Grid g){
		boolean equal = true;
		for(int i = 0; i < g.getSize(); i++){
			if(config.get(i).getValue() != g.getConfig().get(i).getValue())
				equal = false;
		}
		return equal;
	}
	
	public Grid getParent(){
		return this.parentGrid;
	}
	
	/**
	 * Get the move of getting to this grid from its parent
	 * @return
	 */
	public String getDirection(){
		if(this.direction == 1)
			return "up;";
		if(this.direction == 2)
			return "left;";
		if(this.direction == 3)
			return "down;";
		else
			return "right;";
	}
	
	/**
	 * Check the move of given direction is valid, ie out of boundry
	 * @param dir
	 * @return
	 */
	public boolean checkValidMove(int dir){
		
		int up = 1;
		int left = 2;
		int down = 3;
		int right = 4;
		
		if(dir == up){
			if(movingCellPos >= m)
				return true;
			else 
				return false;
		}
		else if(dir == left){
			if(movingCellPos % m != 0)
				return true;
			else
				return false;
		}
		else if(dir == down){
			if(movingCellPos < (config.size() - m))
				return true;
			else 
				return false;
		}
		else if(dir == right){
			if((movingCellPos + 1) % m == 0)
				return false;
			else 
				return true;
		}
		else{
			System.out.println("Error occured when trying to validate next move. Returning false.");
			return false;
		}
			
	}
	
	/**
	 * for testing purpose
	 */
	public void printGrid(){
		int count = 0;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){
				System.out.print("	" + config.get(count).getValue());
				count++;
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	/**
	 * Calculate the h(n) value of this grid
	 * ie sum of h(n) of all individual grid
	 * @param endconfig	the goal grid
	 */
	public void calculateTotalHn(Grid endconfig){
		int targetPos = 0;
		
		for(int currentPos = 0; currentPos < this.config.size(); currentPos++){
			//get target pos
			for(int j = 0; j < config.size(); j++){
				if(endconfig.getConfig().get(j).getValue() == config.get(currentPos).getValue()){
					targetPos = j;
					break;
				}
			}
			
			//if 2 cells in the same row
			//then the distance is abs(1 - 2)
			if(currentPos/m == targetPos/m){
				totalHn += Math.abs(currentPos - targetPos);
			}
			//if 2 cells are not in the same roll
			//then the distance is 
			//the difference in rows
			//plus the difference of columes 
			else{
				totalHn += Math.abs(currentPos/m - targetPos/m);
				totalHn += Math.abs(currentPos%m - targetPos%m);
			}
		}
		
	}
	
	public int getTotalGn(){
		return this.totalGn;
	}
	
	public int getTotalHn(){
		return this.totalHn;
	}
	
	public int getFn(){
		return totalGn + totalHn;
	}
	
	public void incrementGn(int i){
		this.totalGn = i + 1;
	}
}
