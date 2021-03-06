import simple_sound_system.main.*;

class AudioGenerator implements SoundGenerator {
  
  // This method generates the audio.
  // Time is measured in samples(1 / 44100 of a second)
  // The method should return a float between 0 and 1
  float generateSample(int time) {
    float val = (220 * (float)time / 44100 % 1 > 0.5) ? 1 : 0; // Generates a basic square wave @ 220hz
    if(floor(time / 22050) % 2 == 1) val = 0; // Turns the audio off every half-second
    return val;
  }
  
}

void setup() {
  // The soundmaker object is a wrapper around an audio thread class
  // It takes an object that implements SoundGenerator as a parameter
  SoundMaker soundMaker = new SoundMaker(this, new AudioGenerator());
  
  
  // The SoundMaker's begin method starts the thread.
  soundMaker.begin();
}

void draw() {
  
}
