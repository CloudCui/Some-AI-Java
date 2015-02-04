/**
 * This is the Main class
 * 
 * @author Xiangyun
 */
public class IEngine {

	public static void main(String[] args){


		if (args.length == 2){
				
			if (args[0].equals("TT")){
				TT tt = new TT(new KB(args[1]));
				tt.run();
			}
			else if (args[0].equals("FC")){
				FC fc = new FC(new KB(args[1]));
				fc.run();
			}
			else if (args[0].equals("BC")){
				BC bc = new BC(new KB(args[1]));
				bc.run();
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
