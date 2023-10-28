package beans;

import java.util.ArrayList;

public class Machine {
	
	private long id;
	private ArrayList<BlueRay> availableBlueRays;
	private int nbBlueRayInMachine;
	
	public Machine() {
		this.availableBlueRays = new ArrayList<BlueRay>();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public ArrayList<BlueRay> getAvailableBlueRays() {
		return availableBlueRays;
	}
	
	public void setAvailableBlueRays(ArrayList<BlueRay> availableBlueRays) {
		this.availableBlueRays = availableBlueRays;
	}
	
	public void addBlueRay(BlueRay blueRay) {
		this.availableBlueRays.add(blueRay);
	}
	
	public void removeBlueRay(BlueRay blueRay) {
		this.availableBlueRays.remove(blueRay);
	}

	public int getNbBlueRayInMachine() {
		return nbBlueRayInMachine;
	}

	public void setNbBlueRayInMachine(int nbBlueRayInMachine) {
		this.nbBlueRayInMachine = nbBlueRayInMachine;
	}
	
	
}
