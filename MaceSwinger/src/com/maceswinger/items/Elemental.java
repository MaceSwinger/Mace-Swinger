package com.maceswinger.items;



public class Elemental {
	
	public static final Elemental[] elems = new Elemental[256];
	public static final Elemental FIRE = new Elemental(0,"Fire");
	public static final Elemental POISON = new Elemental(1,"Poison");
	public static final Elemental FROST = new Elemental(2, "Frost");
	
    private byte id;
    private String name;
    private Elemental(int i,String name) {
    	this.id = (byte)i;
		if(elems[i] != null) throw new RuntimeException("Duplicant elemental id at " + i);
        this.name=name;
        elems[id] = this;
    }

    public byte getId() {
        return id;
    }
    public String getName() {
		return name;
	}
  
	public static Elemental getElemental(int id) {
		if(id<1)return null;
        for (Elemental  l : elems) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

	

}
