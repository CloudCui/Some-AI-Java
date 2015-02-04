import java.util.ArrayList;



/**
 * This is the class which the program uses to execute the greedy best-first search
 * 
 * @author Xiangyun
 *
 */

public class As {


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
		
		public As(String file){
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
			
			//insert start config into fringe
			fringe.add(startConfig);
			numberOfNodes++;
			
			//check possible moves until solution is found or no more possible moves
			while(!solutionFound){
				
				//check if no more possible moves in the fringe, break the loop
				if(fringe.isEmpty()){
					
					noSolutionFound = true;
					break;
				}
				
				//if there are more possible moves
				
				//0.sort the fringe, with the one has least g(n) comes first
				sort();
				
				//1. set the first element in the fringe to be the current config
				Grid currentGrid = fringe.get(0);

				//2. remove the first grid from fringe
				fringe.remove(0);
				
				//3. check if the current config is the end config
				//	 if yes, solution is found and break the loop
				if(currentGrid.equals(endConfig)){
					//make end config point to current config
					endConfig = currentGrid;
					solutionFound = true;
					break;
				}
				
				//4. check if current grid can generate possible moves
				//	 if yes, append to fringe
				//	 in the order of up->left->down->right 
				//	 so check in the order of up->left->down->right,
				//   skip the checked ones
				
				//check if valid to move up	
				if(currentGrid.checkValidMove(1)){

		
					
					Grid temp = new Grid(currentGrid, 1);
					temp.calculateTotalHn(endConfig);
					temp.incrementGn(currentGrid.getTotalGn());
					
					if(!checked(temp)){
						fringe.add(temp);
						numberOfNodes++;
					}
					//System.out.println(numberOfNodes);
				}
				//check if valid to move left
				if(currentGrid.checkValidMove(2)){
					
					Grid temp = new Grid(currentGrid, 2);
					temp.calculateTotalHn(endConfig);
					temp.incrementGn(currentGrid.getTotalGn());
					if(!checked(temp)){
						fringe.add(temp);
						numberOfNodes++;
					}
					//System.out.println(numberOfNodes);
				}
				//check if valid to move down
				if(currentGrid.checkValidMove(3)){
						Grid temp = new Grid(currentGrid, 3);
					temp.calculateTotalHn(endConfig);
					temp.incrementGn(currentGrid.getTotalGn());
					if(!checked(temp)){
						fringe.add(temp);
						numberOfNodes++;
					}
					//System.out.println(numberOfNodes);
				}
				
				//check if valid to move right
				if(currentGrid.checkValidMove(4)){
					
					Grid temp = new Grid(currentGrid, 4);
					temp.calculateTotalHn(endConfig);
					temp.incrementGn(currentGrid.getTotalGn());
					
					if(!checked(temp)){
						fringe.add(temp);
						numberOfNodes++;
					}
					//System.out.println(numberOfNodes);
				}
				
				//5. mark current grid checked, put in checkedGrids
				checkedGrids.add(currentGrid);
				
				
				//System.out.println("fringe size" + fringe.size());
				//System.out.println(numberOfNodes);
				
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
			
			System.out.println(file + " AS " + numberOfNodes);
			System.out.println(solution);
			
		}
		
		/**
		 * Check if the given grid is checked
		 * @param g
		 * @return	true if checked
		 * 			false if not checked
		 */
		public boolean checked(Grid g){
			boolean checked = false;
			for(Grid grid : checkedGrids){
				if(g.equals(grid))
					checked = true;
			}
			return checked;
		}
		
		/**
		 * Check if the given grid is the goal grid
		 * @param g given grid
		 * @return	true if is goal grid
		 * 			false if not the goal grid
		 */
		public boolean checkIsSolution(Grid g){
			
			boolean found = true;
			
			for(int i = 0; i < gridSize; i++){
				if(g.getConfig().get(i).getValue() != endConfig.getConfig().get(i).getValue()){
					found = false;
				}
			}
			
			return found;
		}
		
		/**
		 * Sort the fringe
		 * in the order of small f(n) to large f(n)
		 */
		public void sort(){
			
			for(int i = 0; i < fringe.size(); i++){
				for(int j = 1; j < fringe.size() - i; j++){
					
					if((fringe.get(i).getTotalGn() + fringe.get(i).getTotalHn()) > 
						(fringe.get(i + j).getTotalGn() + fringe.get(i + j).getTotalHn())){
						Grid temp = fringe.get(i);
						fringe.set(i, fringe.get(i + j));
						fringe.set(i + j, temp);
					}
				}
			}
			/*for(Grid g : fringe){
				System.out.print(g.getTotalGn() + g.getTotalHn());
			}
			System.out.println("");*/
		}
	}














