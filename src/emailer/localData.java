/**
*This class is entrusted with the storing and retrieving of referenced data.
*Data is stored by strings and referenced data values. Useful for limited local data handling.
* @author  Thomas Power
* @version 1.0
* @since   2018-03-19 
*/

package emailer;
import java.io.*;

public class localData{
	
	public static String dataFile = ".\\code\\document.txt";
	public static Client[] dataList = new Client[200];
	public static boolean initialized = false;
	
	
	public static void addClient(String ClientName, String ClientEmail){
		/**
		 * Adds a client object to system or overwrites existing client with same name.
		 */
		Client temp = new Client(ClientName, ClientEmail); 
		for(int i = 0; i < dataList.length; i++){
			if(dataList[i] != null) {
				if(temp.name == dataList[i].name){
					dataList[i] = new Client(temp);
					return;
				}
			}
			else {
				dataList[i] = new Client(temp);
				return;
			}
		}
		expandList();
		addClient(ClientName, ClientEmail);
	}
	
	public static void expandList(){
		/**
		 * Expands list size if necessary.
		 */
		Client[] temp = new Client[dataList.length + 50];
		for(int i = 0; i < dataList.length; i++){
			temp[i] = new Client(dataList[i]);
		}
		dataList = temp;
	}
	
	public static Client getClient(String ClientName){
		/**
		 * Retrieves specific client by name value.
		 */
		for(int i = 0; i <= getTotal(); i++) {
			if(dataList[i].name.equals(ClientName)){
				return dataList[i];
			}
		}
		return null;
	}
	
	
	public static Client parseClient(String curClient){
		/**
		 * Processes client from storage string.
		 */
		String name;
		String email;
		name = getValue(curClient, 1);
		email = getValue(curClient, 2);
		return new Client(name, email);
	}
	
	public static String getValue(String data, int num){
		/**
		 * Retrieves a specific value from a data string based on number reference (first value = 1)
		 */
		int check = 0;
		String result = "";
		for(int i = 0; i < data.length(); i++){
			if(data.charAt(i) == ';'){
				check++;
				if(check == num){
					for(int e = i-1; e > -1; e--){
						if(data.charAt(e) == '='){
							return result;
						}
						result = data.charAt(e) + result;
					}
				}
			}
		}
		return null;
	}
	
	public static String parseClient(Client curClient){
		/**
		 * Processes client object to storage string.
		 */
		return "name=" + curClient.name + ";email=" + curClient.email + ";";
	}
	
	
	public static int getTotal(){
		/**
		 * Retrieves total clients stored in database
		 */
		int result = 0;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));
			String line = "x";
			while(line != null){
				line = bufferedReader.readLine();
				result++;
			}
			bufferedReader.close();
			return result;
		}	catch (Exception e) {
            e.printStackTrace();
        }
		return result;
	}
	
	public static void loadData(){
		/**
		 * Converts data text file to manipulatable array.
		 */
		if(initialized == false){
			int total = getTotal();
			dataList = new Client[total + 50];
			for(int i = 0; i < dataList.length; i++) {
				dataList[i] = new Client();
			}
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));
				String line;
				line = bufferedReader.readLine();
				for(int i= 0; i < total; i++){
					dataList[i] = parseClient(line);
				}
				bufferedReader.close();
			}	catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveData(){
		/**
		 * Converts manipulatable array back to stored text file.
		 */
		if(initialized == true){
			BufferedWriter writer = null;
			try {
				File logFile = new File(dataFile);
				writer = new BufferedWriter(new FileWriter(logFile));
				for(int i = 0; i < dataList.length; i++){
					if(dataList[i] != null){
						writer.write(parseClient(dataList[i]));
						writer.write(System.getProperty("line.separator"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		}
    }
}