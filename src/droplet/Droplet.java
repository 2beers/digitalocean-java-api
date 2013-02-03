package droplet;

public class Droplet {

	private int ID;
	private String name;
	private int imageID;
	private int regionID;
	private int sizeID;
	private String status;
	private String IP;
	
	
	public Droplet(int id, String name, int imageID,int regionID,int sizeID, String ip, String status){
		this.setID(id);
		this.setName(name);
		this.setImageID(imageID);
		this.setRegionID(regionID);
		this.setSizeID(sizeID);
		this.setStatus(status);
		this.setIP(ip);
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


	public int getRegionID() {
		return regionID;
	}


	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}


	public int getSizeID() {
		return sizeID;
	}


	public void setSizeID(int sizeID) {
		this.sizeID = sizeID;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getImageID() {
		return imageID;
	}


	public void setImageID(int imageID) {
		this.imageID = imageID;
	}


	public String getIP() {
		return IP;
	}


	public void setIP(String iP) {
		IP = iP;
	}
	
	
	
}
