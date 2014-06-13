package qaframework.lib.arrayfunctions;

public class Array {
	
	static public boolean in_array(String random_key, String[] array) throws Exception{
		boolean result = false;
		for(int i=0;i<array.length;i++){
			if(random_key.equals(array[i])){
				result = true;
			}
		}
		
		if(!result){
			throw new Exception(random_key + " is not found in the given array");
		}
		return result;
	}
	
	static public boolean contains_in_array(String random_key, String[] array) throws Exception{
		boolean result = false;
		for(int i=0;i<array.length;i++){
			if(array[i].contains(random_key)){
				result = true;
			}
		}
		
		if(!result){
			throw new Exception(random_key + " is not found in the given array");
		}
		return result;
	}
}
