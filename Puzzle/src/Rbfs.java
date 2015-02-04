import java.util.ArrayList;

/**
 * 
 * @author Xiangyun
 *
 */
public class Rbfs {
	private ArrayList<Grid> fringe = new ArrayList<Grid>();
	private ArrayList<Grid> checkedGrids = new ArrayList<Grid>();
	private int numberOfNodes;
	private boolean solutionFound;
	private boolean noSolutionFound;
	private String file;
	private Grid startConfig;
	private Grid endConfig;
	private int gridSize;
	private String solution;
	
	public Rbfs(String file){
		this.numberOfNodes = 0;
		this.solutionFound = false;
		this.noSolutionFound = false;
		this.file = file;
		this.startConfig = new Grid(file,0);
		this.endConfig = new Grid(file,1);
		this.gridSize = startConfig.getSize();
		this.solution = "";  
	}
	
	public void search(){
		
		fringe.add(startConfig);
		numberOfNodes++;
		
		while(!solutionFound){
			
			//check if no more possible moves in the fringe, break the loop
			if(fringe.isEmpty()){
				
				noSolutionFound = true;
				break;
			}
			
			//1. set the first element in the fringe to be the current config
			Grid currentGrid = fringe.get(0);
			
			//2. check if the current config is the end config
			//	 if yes, solution is found and break the loop
			
			if(checkIsSolution(currentGrid)){
				//make end config point to current config
				endConfig = currentGrid;
				solutionFound = true;
				break;
			}
			
			fringe.remove(0);

			
			
			
		}
		
		
		
		
		
		//insert start config into fringe
		fringe.add(startConfig);
		numberOfNodes++;
		int aaa = 0;
		//check possible moves until solution is found or no more possible moves
		while(!solutionFound){
			
			if(aaa > 30)
				break;
			//check if no more possible moves in the fringe, break the loop
			if(fringe.isEmpty()){
				
				noSolutionFound = true;
				break;
			}
			
			//if there are more possible moves
			
			//1. set the first element in the fringe to be the current config
			Grid currentGrid = fringe.get(0);

			//2. check if the current config is the end config
			//	 if yes, solution is found and break the loop
			
			if(checkIsSolution(currentGrid)){
				//make end config point to current config
				endConfig = currentGrid;
				solutionFound = true;
				break;
			}
			
			fringe.remove(0);
			/*
			if(currentGrid.equals(endConfig)){
				//make end config point to current config
				endConfig = currentGrid;
				solutionFound = true;
				break;
			}
			*/
			
			//3. check if current grid can generate possible moves
			//	 in the order of up->left->down->right
			//	 if yes, append to fringe, 
			//   skip the checked ones
			
			//check if valid to move right
			if(currentGrid.checkValidMove(4)){
				System.out.println("---------------");
				System.out.println("Current");
				currentGrid.printGrid();
				
				Grid temp = new Grid(currentGrid, 4);

				System.out.println("temp");
				temp.printGrid();
				
				if(!checked(temp)){
					fringe.add(0,temp);
					numberOfNodes++;
				}
				System.out.println(numberOfNodes);
			}
			
			
			//check if valid to move down
			if(currentGrid.checkValidMove(3)){
				System.out.println("---------------");
				System.out.println("Current");
				currentGrid.printGrid();
				
				Grid temp = new Grid(currentGrid, 3);

				System.out.println("temp");
				temp.printGrid();
				
				if(!checked(temp)){
					fringe.add(0,temp);
					numberOfNodes++;
				}
				System.out.println(numberOfNodes);
			}
			
			//check if valid to move left
			if(currentGrid.checkValidMove(2)){
				System.out.println("---------------");
				System.out.println("Current");
				currentGrid.printGrid();
				
				Grid temp = new Grid(currentGrid, 2);

				System.out.println("temp");
				temp.printGrid();
				
				if(!checked(temp)){
					fringe.add(0,temp);
					numberOfNodes++;
				}
				System.out.println(numberOfNodes);
			}
			
			//check if valid to move up	
			if(currentGrid.checkValidMove(1)){

				System.out.println("---------------");
				System.out.println("Current");
				currentGrid.printGrid();
				
				Grid temp = new Grid(currentGrid, 1);

				System.out.println("temp");
				temp.printGrid();
				
				if(!checked(temp)){
					fringe.add(0,temp);
					numberOfNodes++;
				}
				System.out.println(numberOfNodes);
			}
			
			//4. mark current grid checked, put in checkedGrids
			checkedGrids.add(currentGrid);
			
			//5. remove current grid from fringe
			
			System.out.println("fringe size" + fringe.size());
			System.out.println(numberOfNodes);
			
			
			//aaa++;
		}//end of while
		
		
		//if a solution is found, then print out the solution
		//else, print no solution found
		if(solutionFound){
			Grid currentConfig = endConfig;
			while(currentConfig.getParent() != null){
				solution = currentConfig.getDirection() + solution;
				currentConfig = currentConfig.getParent();
			}
		}
		else if(noSolutionFound){
			solution = "No solution found";
		}
		
		System.out.println(file + " DFS " + numberOfNodes);
		System.out.println(solution);
		
			
		
		
	}
	
	public boolean checked(Grid g){
		boolean checked = false;
		for(Grid grid : checkedGrids){
			if(g.equals(grid))
				checked = true;
		}
		return checked;
	}
	public boolean checkIsSolution(Grid g){
		
		boolean found = true;
		
		for(int i = 0; i < gridSize; i++){
			if(g.getConfig().get(i).getValue() != endConfig.getConfig().get(i).getValue()){
				found = false;
			}
		}
		
		return found;
	}
}
