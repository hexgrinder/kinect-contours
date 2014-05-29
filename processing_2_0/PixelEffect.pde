/*
PixelEffect.pde

Static helper class that contains pixel effects.

@author Michael Layman
*/
public static class PixelEffect {

	// Fades a pixel color to black.
	//
    // ARGB -> Processing uses a 32-bit vector to describe the pixel color.
    // To access the individual components, use the following shifts:
    // B -  0 bit-shift
    // G -  8 bit-shift
    // R - 16 bit-shift
    // A - 24 bit-shift
	//
	// @param col - Initial color value
	// @param fract - Fractional brightness of color. Valid values [0,1].
	// @returns Returns the faded color
    public static int fade(int col, float fract) {
    
        // translate color into number
        
        int rtn = 0;
        
        // load alpha. alpha is not faded.
        rtn = ((col >> 24) & 0xFF) << 24;
        // fade red. load red.
        rtn = ((Math.round(fract * ((col >> 16) & 0xFF)) & 0xFF) << 16) | rtn;
        // fade green. load green.
        rtn = ((Math.round(fract * ((col >> 8) & 0xFF)) & 0xFF) << 8) | rtn;
        // fade blue. load green
        rtn = (Math.round(fract * (col & 0xFF)) & 0xFF) | rtn;
        
        return rtn;
  }
    
} // PixelEffect
