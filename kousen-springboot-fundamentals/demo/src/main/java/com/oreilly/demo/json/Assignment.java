package com.oreilly.demo.json;

public class Assignment {
	// What are Java Records?
	private String name;
    private String craft;

    public Assignment() {}

    public Assignment(String name, String craft) {
        this.name = name;
        this.craft = craft;
    }

    public String getCraft() {
        return craft;
    }

    public String getName() {
        return name;
    }
	
	
	@Override
	public String toString() {
		return "{ " + name + " : " + craft + " }";
		
	}

	
	
}
