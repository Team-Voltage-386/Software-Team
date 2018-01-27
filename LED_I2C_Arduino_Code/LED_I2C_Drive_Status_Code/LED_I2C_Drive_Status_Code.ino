#include <Adafruit_NeoPixel.h>
#include <Wire.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif

#define PIN 12

int LEDmode;


Adafruit_NeoPixel strip = Adafruit_NeoPixel(150, PIN, NEO_GRB + NEO_KHZ800);

void setup()
{
  Serial.begin(9600);
  strip.begin();
  strip.show();
  Wire.begin(8);
}

void loop()
{
  Wire.onReceive(dataReceived);
  RunLEDs(LEDmode);
}

void dataReceived(int howMany)
{
  String LED = "";
  while (Wire.available())  
  {
    char n = (char)Wire.read();
    if (((int)n) > ((int)(' ')))
    {
      //Serial.print(n);
      LED += n;
    }

  } 

  if (LED == "DISABLED") 
  {
    LEDmode = 1;
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
    LEDmode = 0;
  }
}

void RunLEDs(int mode)
{

  if (mode == 1) 
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
    theaterChase(strip.Color(127, 127, 127), 100); // White
  }
}

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
