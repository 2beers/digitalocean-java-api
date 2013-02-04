package digitalocean.images;

public class DropletImage {
	private int ID;
	private String name;
	private String distribution;
	
	
	public DropletImage(int id, String name,String distribution){
		this.setID(id);
		this.setName(name);
		this.setDistribution(distribution);
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


	public String getDistribution() {
		return distribution;
	}


	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
}
