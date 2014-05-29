/*
UserColors.pde

Static class containing colors for up to 6 users.

*/
public static class UserColors {

    // Crayon box
    private static int[] _colorMapping = {
        0xffff0000,    // Red
        0xff00ff00,    // Green
        0xffD3E029,    // Yellowish
        0xffFFBC00,    // Orangish
        0xff7CF5EC,    // Bluish-White
        0xffC771D3     // Purplish
    };
    
	// Returns the int color of for a given user.
	// Uses modulus math to assign a color to the passed in user.
	// For example:
	//		If user = 4...
	//			4 modulus 6 is 4, therefore the 4th color in the _colorMapping array will be returned.
	//
	// @param user User id
	// @returns Returns a int representing the color
    public static int translateUserToColor(short user) {
        
        // No user => no color
        if (user <= 0) { 
            return 0; 
        }
        
        // align user to color map
        user--;
        
        // Returns color off map
        return _colorMapping[user % _colorMapping.length];  
    }
    

} // UserColors
