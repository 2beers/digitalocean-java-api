package digitalocean.region;

public class DropletRegion {

	private int ID;
	private String name;
	
	public DropletRegion(int id,String name){
		this.setID(id);
		this.setName(name);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
