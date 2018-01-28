#include <Adafruit_NeoPixel.h>  //Import Neopixel libraries.   These can be found through library manager or here, https://learn.adafruit.com/adafruit-neopixel-uberguide/arduino-library-installation .
#include <Wire.h>  // This is the Ard/RIOduino's I2C library.  
#ifdef __AVR__ //Necessary for LEDs. Not sure why, don't delete it anyway. 
  #include <avr/power.h>
#endif

#define PIN 12 //DIO pin used for control of LEDs

int LEDmode;
int NumPixels = 150; // Number of pixels in LED strip

Adafruit_NeoPixel strip = Adafruit_NeoPixel(NumPixels, PIN, NEO_GRB + NEO_KHZ800);  //Create NeoPixel object.  Constructor accepts number of pixels, DIO pi, and LED type.  More info on that can be found in the Adafruit tutorial.


void setup()  //Arduino Init loop.
{
  Serial.begin(9600); //So we can watch serial monitor.  Use lots of print statments when debugging.
  strip.begin(); //Initialize LED strip object
  strip.show(); //Displays commands to LED's.  Because nothing has been passed to them, it essentially turns them all off.  Useful for overriding stuck LEDs. 
  Wire.begin(8);  //Start I2C object, assigned to port #8. 
}

void loop()
{
   Wire.onReceive(dataReceived); //Looks for data send over I2C, begins when any is detected, no matter the size. 
   RunLEDs(LEDmode); //If data is received, this function passes that information to the RunLEDs method.
}

void dataReceived(int howMany)   //Method started by data on the bus.  
{
  String LED = "";  //Create string object LED
  while (Wire.available())  //Continue through whole length of message
  {
    char n = (char)Wire.read(); //Convert the received bytes back into characters.
    if (((int)n) > ((int)(' '))) //Check that incoming characters are actual characters and not data commands.
    {
      //Serial.print(n); //Debug line for viewing incoming data
      LED += n;  // Add new character to previous character, thus creating the new string.
    }

  } 
  
  //Bad practice if/else chain.  Will be replaced by a switch case soon. 
  if (LED == "DISABLED") //The string that is passed.  This is written in the Java code. 
  {
    LEDmode = 1;  //Assign a number to the LEDmode.  This is passed to the Run LEDs function after this method ends. 
  } 
  else if (LED == "AUTO") 
  {
    LEDmode = 2;
  } 
  else if (LED == "TELEOP") 
  {
    LEDmode = 3;
  } 
  else 
  {
    LEDmode = 0;  //Diagnostic else statment.  If no I2C data is being passed, or if you are reading it incorrectly, this is useful.
  }
}

void RunLEDs(int mode) //This is passed the LEDmode object from the previous method.  
{
  //Once again, bad practice, this will be replaced by another switch case. 
  if (mode == 1) //If this mode is received, this function will happen.   
  {
    colorWipe(strip.Color(0, 255, 0), 20); // Green for disabled 
  } 
  else if (mode == 2) 
  { 
    colorWipe(strip.Color(0, 0, 255), 20); // Blue for autonomous
  } 
  else if (mode == 3) 
  {
    colorWipe(strip.Color(255, 0, 0), 20); // Red for teleop
  }
  else if (mode == 0)
  {
    doubleTheaterChase(strip.Color(255, 255, 0), strip.Color(0, 0, 255), 100); // Yellow and Blue and an 80ms delay are passed to the function.
  }
}


//Standard color functions from the StrandTest example in neopixel library. doubleTheaterChase is a modification of my own.   
void colorWipe(uint32_t c, uint8_t wait) 
{
  for(uint16_t i=0; i<strip.numPixels(); i++) 
  {
    strip.setPixelColor(i, c);
    strip.show();
    delay(wait);
  }
}

void rainbow(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256; j++) {
    for(i=0; i<strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel((i+j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256*5; j++) { // 5 cycles of all colors on wheel
    for(i=0; i< strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / strip.numPixels()) + j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

//Theatre-style crawling lights.
void theaterChase(uint32_t c, uint8_t wait) {
  for (int j=0; j<10; j++) {  //do 10 cycles of chasing
    for (int q=0; q < 3; q++) {
      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, c);    //turn every third pixel on
      }
      strip.show();

      delay(wait);

      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}
void doubleTheaterChase(uint32_t c, uint32_t c2, uint8_t wait) {
  for (int j=0; j<10; j++) {  //do 10 cycles of chasing
    for (int q=0; q < 3; q++) {
      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, c);    //turn every third pixel on
        strip.setPixelColor(i+1+q, c2);    //turn every fourth pixel on
        
      }
      strip.show();

      delay(wait);

      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
        strip.setPixelColor(i+1+q, 0);      //Turn every fourth pixel off
      }
    }
  }
}

//Theatre-style crawling lights with rainbow effect
void theaterChaseRainbow(uint8_t wait) {
  for (int j=0; j < 256; j++) {     // cycle all 256 colors in the wheel
    for (int q=0; q < 3; q++) {
      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, Wheel( (i+j) % 255));    //turn every third pixel on
      }
      strip.show();

      delay(wait);

      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}

uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}
