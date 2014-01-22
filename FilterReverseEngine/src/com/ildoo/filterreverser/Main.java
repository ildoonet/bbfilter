package com.ildoo.filterreverser;


public class Main {
	public static void main(String[] args) {
		String originalFile, filteredFile;
		if (args == null || args.length < 2) { 
			originalFile = "/Users/Curtis/Documents/workspace_bbfilter/reverse/0.jpg";
			filteredFile = "/Users/Curtis/Documents/workspace_bbfilter/reverse/1.jpg";
		} else {
			originalFile = args[0];
			filteredFile = args[1];
		}

		try {
			System.out.println(filteredFile);
			Reverser r = new Reverser(originalFile);
			r.openFilteredFile(filteredFile);
			r.reverse();
		} catch (Exception e) {
			System.out.println(e.toString());
		} // end try
	}
}
