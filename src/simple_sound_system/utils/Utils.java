package simple_sound_system.utils;

public class Utils {
	
	public static void InvokeProcedure(Procedure procedure, boolean stackTrace) {
		
		try {
			procedure.Invoke();
		}
		catch(Exception e) {
			if(stackTrace) {
				e.printStackTrace();
			}
		}
		
	}
	
}
