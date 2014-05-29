import hexgrinder.canvas.*;
import hexgrinder.screen.*;

public static class Screen {

    public static int[] increaseResolution(int[] pixelArr, int[] resMap) {
        
        // TODO: EXTERNALIZE SO CREATION TIME IS SAVED.
        // New space
        int[] rtn = new int[resMap.length];
        
        for (int i = 0; i < resMap.length; i++) {
            rtn[i] = pixelArr[resMap[i]];
        }
        
        return rtn;
    }
    
    public static int[] increaseResolution(int[] pixelArr, int origWidth, int resWidth, int resHeight, ResolutionMap[] resMap) {
        
        // TODO: EXTERNALIZE SO CREATION TIME IS SAVED.
        // New space
        int[] rtn = new int[resMap.length];
        
        int rowOffset = 0;
        int index = 0;
        
        for (int i = 0; i < resHeight; i+=2) {
            
            rowOffset = i * resWidth;
            
            for (int j = 0; j < resWidth; j++) {
                
                index = rowOffset + j;
                
                                
                rtn[index] = _averageARGB_Int32(
                    pixelArr, 
                    resMap[index].sourceIndexes);
                
                //rtn[index] = resMap[index].sourceIndexes[0];
            }
        }
        
        
        for (int i = 1; i < resHeight; i+=2) {
            
            rowOffset = i * resWidth;
            
            for (int j = 0; j < resWidth; j++) {
                
                index = rowOffset + j;
                
                
                rtn[index] = _averageARGB_Int32(
                    rtn, 
                    resMap[index].sourceIndexes);
                
                //rtn[index] = resMap[index].sourceIndexes[0];
            }
        }
        
        return rtn;
    }
    
    private static int _averageARGB_Int32(int[] srcPixelArr, int[] srcIndexes) {
        
        if (srcIndexes.length == 1) {
            return srcPixelArr[srcIndexes[0]];
        }
        
        return averageARGBint32(
            srcPixelArr[srcIndexes[0]],
            srcPixelArr[srcIndexes[1]]); 
        
    } 
    
    public static int[] increaseResolution(int[] pixelArr, int origWidth, int resWidth, int resHeight) {
        
        // New space
        int[] rtn = new int[resWidth * resHeight];
        
        // new space is constructed in two parts:
        // Part 1: Interpolate even rows.  interpolated values only use left and right. 
        // Part 2: Interpolate odd rows using interpolated values from part 1.  Above and below interpolation only.
        
        // part 1: row-wise interpolation
        int indexPrime = 0;
        
        int index = 0;
        int lhs_index = 0;
        int rhs_index = 0;
        int rowPrimeOffset = 0;
        int rowSrcOffset = 0;
        
        for (int rowPrime = 0; rowPrime < resHeight; rowPrime += 2) {
            
            rowPrimeOffset = rowPrime * resWidth;
            rowSrcOffset =  (rowPrime >> 1) * origWidth;
            
            for (int colPrime = 0; colPrime < resWidth; colPrime++) {
                
                indexPrime = rowPrimeOffset + colPrime;
                
                try {
                    if (colPrime % 2 == 0) {
                        // an even column index...
                        
                        // calc index of source image
                        index = rowSrcOffset + (colPrime >> 1);
                        rtn[indexPrime] = pixelArr[index];
                        
                    } else {
                        // odd column index...
                      
                        // calc LHS index of source image
                        lhs_index = rowSrcOffset + (colPrime >> 1);
                        
                        // calc RHS index of source image
                        rhs_index = lhs_index + 1; 
                        
                        // take the average of the neighboring colors
                        rtn[indexPrime] = averageARGBint32(
                            pixelArr[lhs_index],
                            pixelArr[rhs_index]);
                        
                    }
                
                } catch (Exception ex) {
                        String str = String.format("%d | %d", lhs_index, rhs_index);
                        Exception e = new Exception(str);
                        //throw e;
                }
            }
        }
        
        
        
        // part 2: expansion row interpolation
        // only interpolate on expansion rows (odd rows only)
        int topIndex = 0;
        int btmIndex = 0;
        for (int rowPrime = 1; rowPrime < resHeight; rowPrime += 2) {
          
            topIndex = (rowPrime - 1) * resWidth;
            btmIndex = (rowPrime + 1) * resWidth; 
            
            for (int colPrime = 0; colPrime < resWidth; colPrime++) {
              
                // take the average of the neighboring above and below colors
                rtn[(rowPrime * resWidth) + colPrime] = averageARGBint32(
                    rtn[topIndex + colPrime], 
                    rtn[btmIndex + colPrime]);
            }
        }
        
        
        return rtn;
    }
   
    /*
     private static int translateARGBtoInt(int alpha, int red, int green, int blue) {
    
        int rtn = 0;
        
        // load alpha. 
        rtn = ((alpha >> 24) & 0xFF) << 24;
        // load red.
        rtn = (((red >> 16) & 0xFF) << 16) | rtn;
        // load green.
        rtn = (((green >> 8) & 0xFF) << 8) | rtn;
        // load green
        rtn = (blue & 0xFF) | rtn;
        
        return rtn;
    }
    */
    
    private static int averageARGBint32(int c1, int c2) {
    
        int rtn = 0;
        
        // average alpha component
        rtn = ((((c1 >> 24) & 0xFF) + ((c2 >> 24) & 0xFF)) >> 1) << 24;
        // average red component
        rtn = (((((c1 >> 16) & 0xFF) + ((c2 >> 16) & 0xFF)) >> 1) << 16) | rtn;
        // average grn component
        rtn = (((((c1 >> 8) & 0xFF) + ((c2 >> 8) & 0xFF)) >> 1) << 8) | rtn;
        // average blue component
        rtn = (((c1 & 0xFF) + (c2 & 0xFF)) >> 1) | rtn;
        
        return rtn;
    }
} // Screen
