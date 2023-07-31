#include <RGBmatrixPanel.h>

#define CLK 11
#define OE   9
#define LAT 10
#define A   A0
#define B   A1
#define C   A2
#define D   A3

RGBmatrixPanel matrix(A, B, C, D, CLK, LAT, OE, false);
String readString;



void setup() {
  Serial.begin(9600);
  matrix.begin();
}

void loop() {
  if (Serial.available()) {
    char c = Serial.read();
    readString += c;
  }

  if (readString.length() > 0 && readString[readString.length()-1] == '\n') {
    readString.trim();

    if (readString.substring(0, 1) == "1" || readString.substring(0, 1) == "0") {
      drawLEDs(readString);
    }

    if (readString == "lightup") {
      lightUpLEDs();
    }

    if (readString == "clear") {
      clearLEDs();
    }

    if (readString == "drawA") {
      drawA();
    }


    readString = "";
  }
}

void lightUpLEDs() {
  for(int y=0; y < matrix.width(); y++) {
    for(int x=0; x < matrix.height(); x++) {
      matrix.drawPixel(x, y, matrix.Color333(7, 8, 5));
      delay(30);
    }
  }
}

void drawA() {
  const uint32_t letterA[32] = { 
  0b00000000000000000000000000000000,
  0b00000000000000000000000000000000,
  0b00000000000000001111000000000000,
  0b00000000000000001111000000000000,
  0b00000000000000001111000000000000,
  0b00000000000000001111000000000000,
  0b00000000000000001111000000000000,
  0b00000000000000001111000000000000,
  0b00000000000000001111000000000000,
  0b00000000000000001111000000000000,
  0b00000000000000001111000000000000,
  0b00000000000000011111100000000000,
  0b00000000000000111111110000000000,
  0b00000000000001111111111000000000,
  0b00000000000011110000111100000000,
  0b00000000000111100000011110000000,
  0b00000000001111000000001111000000,
  0b00000000011110000000000111100000,
  0b00000000111100000000000011110000,
  0b00000001111000000000000001111000,
  0b00000011110000000000000000111100,
  0b00000111100000000000000000011110,
  0b00001111111111111111111111111111,
  0b00011111111111111111111111111111,
  0b00111111111111111111111111111111,
  0b01111111111111111111111111111111,
  0b11111100000000000000000000000000,
  0b11111000000000000000000000000000,
  0b11110000000000000000000000000000,
  0b11100000000000000000000000000000,
  0b11000000000000000000000000000000,
  0b00000000000000000000000000000000
};
// should create a 32x32 pattern that somewhat resembles the letter 'A'.

  for(int y = 0; y < 32; y++) {
    for(int x = 0; x < 32; x++) {
      if(bitRead(letterA[y], 31 - x) == 1) {
        matrix.drawPixel(x, y, matrix.Color333(7, 8, 5));
      } else {
        matrix.drawPixel(x, y, matrix.Color333(0, 0, 0));
      }
    }
  }
}

void drawLEDs(String payload) {
  // Split payload into grid
  int idx = 0;
  for (int y = 0; y < matrix.height(); y++) {
    for (int x = 0; x < matrix.width(); x++) {
      // Get the current LED state
      int ledState = payload.substring(idx, idx+1).toInt();

      // Draw pixel accordingly
      if (ledState == 1) {
        matrix.drawPixel(matrix.width() - 1 - x, y, matrix.Color333(7, 8, 5)); // Flip the x index
      } else {
        matrix.drawPixel(matrix.width() - 1 - x, y, matrix.Color333(0, 0, 0)); // Flip the x index
      }

      // Move to next index
      idx += 2; // '1,' or '0,' = 2 characters
    }
  }
}

void clearLEDs() {
  matrix.fillScreen(matrix.Color333(0, 0, 0));
}
