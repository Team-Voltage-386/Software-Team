#include <Adafruit_NeoPixel.h>
#include <Wire.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif

#define PIN 12

int LEDmode;
const char* LED;

Adafruit_NeoPixel strip = Adafruit_NeoPixel(150, PIN, NEO_GRB + NEO_KHZ800);

void setup()
{
  Serial.begin(9600);
  strip.begin();
  strip.show();
  Wire.begin(8);
  Serial.println("Setup Loop Complete");
}

void loop()
{
  Serial.println("Main Loop Starting");
  Wire.onReceive(dataReceived);
  RunLEDs(LEDmode);
}

void dataReceived(int howMany)
{
  Serial.println("In dataReceived Function");
  LED = "";
  while (Wire.available() > 0)  
  {
    char n = (char)Wire.read();
    if (((int)n) > ((int)(' ')))
    {
      LED += n;
    }
    Serial.print("In dataReceived function, getting robot message:");
    Serial.println(LED);
  }
  
  if (LED == "DISABLED") 
  {
    LEDmode = 0;
  } 
  else if (LED == "AUTO") 
  {
    LEDmode = 1;
  } 
  else if (LED == "TELEOP") 
  {
    LEDmode = 2;
  } 
  else 
  {
    LEDmode = 0;
  }
  delay(5000);
}

void RunLEDs(int mode)
{
  Serial.println("In Run LEDs Function");
  Serial.print("LED Mode is:");
  Serial.println(LED);
  if (LED == "DISABLED") 
  {
    colorWipe(strip.Color(0, 255, 0), 50); // Green for disabled
    Serial.println("In Disabled Color Function");
  } 
  else if (LED == "AUTO") 
  { 
    colorWipe(strip.Color(0, 0, 255), 50); // Blue for autonomous
    Serial.println("In Auto Color Function");
    delay(5000);
  } 
  else if (LED == "TELEOP") 
  {
    colorWipe(strip.Color(255, 0, 0), 50); // Red for teleop
    Serial.println("In Teleop Color Function");
    delay(5000);
  }
  else 
  {
    theaterChase(strip.Color(127, 127, 127), 10); // White
  }
  delay(5000);
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
