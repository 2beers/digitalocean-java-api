package digitalocean.size;

public class DropletSize {
	private int ID;
	private String name;
	
	public DropletSize(int id,String name){
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
