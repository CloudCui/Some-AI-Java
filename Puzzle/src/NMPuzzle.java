/**
 * This is the main class
 * The program is executed by .bat file using arguments
 * 
 * @author Xiangyun
 *
 */

public class NMPuzzle {
	public static void main(String args[]){
		if (args.length == 2){
				
			if (args[1].equals("BFS")){
				Bfs bfs = new Bfs(args[0]);
				bfs.search();
			}
			else if (args[1].equals("DFS")){
				Dfs dfs = new Dfs(args[0]);
				dfs.search();
			}
			else if (args[1].equals("GBFS")){
				Gbfs gbfs = new Gbfs(args[0]);
				gbfs.search();
			}
			else if (args[1].equals("AS")){
				As as = new As(args[0]);
				as.search();
			}			
			else if (args[1].equals("UCS")){
				Ucs ucs = new Ucs(args[0]);
				ucs.search();
			}
			else{
				System.out.println("Method not found");
			}
		}
		else{
			System.out.println("Wrong Command Line Arguements");
		}
	}
}

