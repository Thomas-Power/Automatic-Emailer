/**
*This is the client data structure used to store necessary referenced values.
*
* @author  Thomas Power
* @version 1.0
* @since   2018-03-19 
*/

package emailer;

public class Client{
	public String name = "";
	public String email = "";
	
	Client(){
		name = "";
		email = "";
	}
	
	Client(String nm, String em){
		name = nm;
		email = em;
	}
	
	Client(String nm){
		name = nm;
	}
	
	public Client(Client curClient){
		if(curClient.name != null) {
			this.name = curClient.name;
		}
		if(curClient.email != null) {
			this.email = curClient.email;
		}
	}
}