package me.planetguy.util;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;

public class RandomBlock {
	
	private Random random=new Random();
	private ArrayList<Material> materials=new ArrayList<Material>();
	
	public void addMaterial(Material m){
		materials.add(m);
	}
	
	public void addMaterial(Material m, int weight){
		for(int i=0; i<weight; i++){
			addMaterial(m);
		}
	}
	
	public Material next(){
		return materials.get(random.nextInt(materials.size()));
	}

}
