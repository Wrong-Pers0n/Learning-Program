package data;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;

public class Data implements Serializable{
	
	// Level stats
	public int moonLevel = 1;
	public int sunLevel = 1;
	public int eclipseLevel = 1;
	public int voidLevel = 1;
	// Unlocked things
	public boolean eclipseUnlocked = false;
	public boolean eclipseVoid = false;
	// Settings
	public boolean fileBlank = true;
	public double version = 0.5;
	
}
