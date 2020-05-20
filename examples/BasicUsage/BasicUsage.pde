import simple_sound_system.main.*; // Import the library

// To use the Simple Sound System, you need to create a class
// that implements the SoundGenerator interface.
class AudioGenerator implements SoundGenerator {
  
  // This function generates the samples.
  // Time is measured in samples(1/44100s of a second)
  // The function should return a float between 0 and 1
  float generateSample(int time) {
    float val = 0;
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
