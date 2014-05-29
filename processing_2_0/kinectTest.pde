/**
* kinectTest.pde
* 
* Driver program.
*
* @author Michael Layman
*/

import hexgrinder.canvas.*;
import hexgrinder.screen.*;

import org.openni.*;
import java.nio.ShortBuffer;

// Full screen toggle
private static final boolean FULL_SCREEN_MODE = true;
// Pixel time fadeout time
private static final int MAX_LIFETIME = 20;
// Path to XML settings
private static final String SAMPLE_XML_FILE = "C:\\Program Files\\OpenNI\\Data\\SamplesConfig.xml";
// Screen refresh time
private static final float LAYER_REFRESH_TIME = 10000;
// NEAR VIEW CLIPPING LIMIT
private static final short LWR_VISION = 775;
// FAR VIEW CLIPPING LIMIT
private static final short UPR_VISION = 1485;
// Presentation display width
private static final int PRESENTATION_WIDTH = 1279;
// Presentation display height
private static final int PRESENTATION_HEIGHT = 959;

// Output to script
private OutArg<ScriptNode> scriptNode;
// Kinect context
private Context context;
// Generator for depth pixels
private DepthGenerator depthGen;
// Generator for user pixels
private UserGenerator userGen;

// Drawing canvas
private UserLayer layer;

// Resolution map.  Maps from lower res kinect data to higher res screen
private ResolutionMap[] resMap;

// CRAP?: What is this for???
private float time;

// Use full screen to see the full border
boolean sketchFullScreen() {
    return FULL_SCREEN_MODE;
}
   
void setup() {
    
    // performance mode is 640x480
    size(PRESENTATION_WIDTH,PRESENTATION_HEIGHT);
	// create resolution map
    resMap = ResolutionMapGenerator.create(640, 480, PRESENTATION_WIDTH, PRESENTATION_HEIGHT);
    // set background color of canvas
    background(0);
    // remove color band borders when sketch is at full screen
    ((javax.swing.JFrame)frame).getContentPane().setBackground(java.awt.Color.black);
    
	// container class for settings file
    scriptNode = new OutArg<ScriptNode>();
    
    try {

        // get access to kinect sensor
        context = Context.createFromXmlFile(
            SAMPLE_XML_FILE, 
            scriptNode);
        
        // depth setup...
        depthGen = DepthGenerator.create(context);
        DepthMetaData depthMD = depthGen.getMetaData();

		// get kinect's output resolution
        int mywidth = depthMD.getFullXRes();
        int myheight = depthMD.getFullYRes();
        
		// declare the drawing layer dimensions to be same as kinect's
        layer = new UserLayer(mywidth,myheight,MAX_LIFETIME);
        
        // user detection setup
        userGen = UserGenerator.create(context);
        
        // start kinect...
        context.startGeneratingAll();
        
    } catch (Exception e) {
        e.printStackTrace();
        exit();
    }
 
    time = 0;
}

void draw() {
    
    // if it is not refresh time...
    if (time < LAYER_REFRESH_TIME) {
		
	// updates scene layer colors
	_update(); 
		
        // draws the layer onto the screen
        loadPixels();
            _drawScene();   
        updatePixels();
        
		// update time in the layer
        _applyTime(layer);
    } else {
		// end of days => wipe the screen
        layer.clear();
        time = 0;
    }
    
	// update time
    time++;

	// slowly brighten the border as clear time approaches
    _drawBorder(time/LAYER_REFRESH_TIME);
}

// Draws the scene onto the screen
void _drawScene() {
    
    int [] scrn = Screen.increaseResolution(layer.toARGBint32Array(), layer.width, 1279, 959, resMap);
    
    for (int i = 0, len = pixels.length; i < len; i++) {
        pixels[i] = scrn[i];
    }

}

// Draws a border around the viewable area.
// Border serves as a visual indicator as to when
// screen wipe is to happen.
//
// @param inc - Brightness of color. Valid values [0,1]
void _drawBorder(float inc) {
    
	strokeWeight(2);
    
	stroke(lerpColor(
        color(#030303),
        color(255,255,255,255),
        inc)); 
	
	// top
    line(
		0, 0,
		PRESENTATION_WIDTH - 1, 0);
	
	// right
    line(
		PRESENTATION_WIDTH - 1, 0, 
		PRESENTATION_WIDTH - 1, PRESENTATION_HEIGHT - 1);   
	
	// bottom
    line(
		0, PRESENTATION_HEIGHT - 1,
		PRESENTATION_WIDTH - 1, PRESENTATION_HEIGHT - 1);   
    
	// left
	line(
		0, 0,
		0,PRESENTATION_HEIGHT - 1);
    
	noStroke();
}

// Draws the contents of the layer onto the screen
void _update() {
    
	try {
		
		// kinect registers an update on any of its streams 
		context.waitAnyUpdateAll();
    
        DepthMetaData depthMD = depthGen.getMetaData();
        SceneMetaData sceneMD = userGen.getUserPixels(0);
               
        ShortBuffer sceneBuff = sceneMD.getData().createShortBuffer();
        ShortBuffer depthBuff = depthMD.getData().createShortBuffer();
        
        // made the updates
        
        UserPixel pixel;
        int pos;
        short depth;
        short user;
        
		// scan through kinect data...
        while (depthBuff.remaining() > 0) {
            
            pos = depthBuff.position();
            depth = depthBuff.get();
            user = sceneBuff.get();
            pixel = layer.get(pos);    
            
            if (depth != 0) {
                
                Color pixelColor = pixel.getColor();
                
                int col = _updatePixel(pos, user, pixel);
                
                // load alpha. alpha is not faded.
                pixelColor.alpha = (col >> 24) & 0xFF;
                // fade red. load red.
                pixelColor.red = (col >> 16) & 0xFF;
                // fade green. load green.
                pixelColor.green = (col >> 8) & 0xFF;
                // fade blue. load blue
                pixelColor.blue = (col & 0xFF);
        
                //DEBUG: pixels[pos] = col;  
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    
}

// Performs pixel update.  Returns the updated
// pixel color.
//
// @param pos Pixel index position
// @param userId User id
// @param p User pixel being updated
// @return Returns the updated color for the pixel
int _updatePixel(int pos, short userId, UserPixel p) {
    
    int col = 0;
  
    float life = p.getCurrLifeTime();
    
    // create contour
    // TODO?: HOW TO BUILD CONTOUR WHEN USER STANDING??
    if (life <= 0) {
        p.setCurrLifeTime(MAX_LIFETIME);
        p.userId = 0;
    } else {
        p.setCurrLifeTime(life - 1);
    }
    
    float fract = life / MAX_LIFETIME;
    int temp = 0;
    Color pixelColor = p.getColor();
    
    if (userId > 0) {
        // there is a user occupying the pixel...
        
        // Set pixel color of user
        // fade color
        col = PixelEffect.fade(
            UserColors.translateUserToColor(userId),
            fract); 
        
    } else {
        // pixel is void of a user...
        int c0 = color(
            pixelColor.red,
            pixelColor.green,
            pixelColor.blue,
            pixelColor.alpha);
       
        if (c0 == 0xff000000) {
            // eliminate 'flashing' empty parts of the screen
            col = 0;
        } else {
            col =  
                PixelEffect.fade(
                  color(245,245,245,50),
                  .1*fract);
        }
    }
    
    // Set new owner of pixel
    p.userId = userId;

    return col;
}

// Updates the time of all pixels on the passed in layer.
//
// @param layer UserLayer object
void _applyTime(UserLayer layer) {
    
    float currLifeTime; 
    for (UserPixel p : layer) {
      currLifeTime = p.getCurrLifeTime();
      if (currLifeTime > 0 && p.userId != 0) { 
          p.setCurrLifeTime(currLifeTime-1); 
      } else {
          // No more time left... Free the pixel for another user
          p.userId = 0;
      }
    }
}
