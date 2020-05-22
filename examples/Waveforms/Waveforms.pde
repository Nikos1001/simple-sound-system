import simple_sound_system.main.*;

class AudioGenerator implements SoundGenerator {
  
  float generateSample(int time) {
    float val = 0;
    float seconds = (float)time / 44100;
    switch(floor(seconds % 4)) {
      case 0:
        val = sqr(time, 220);
        break;
      case 1:
        val = saw(time, 220);
        break;
      case 2:
        val = sine(time, 220);
        break;
      case 3:
        val = tri(time, 220);
        break;
    }
    return val;
  }
  
  float sqr(int time, float freq) {
    return (freq * (float)time / 44100) % 1 > 0.5 ? 1 : 0;
  }
  
  float saw(int time, float freq) {
    return (freq * (float)time / 44100) % 1;
  }
  
  float sine(int time, float freq) {
    return sin(freq * TAU * (float)time / 44100);
  }
  
  float tri(int time, float freq) {
    float saw = saw(time, freq);
    float val = saw * 2;
    if(saw > 0.5) {
      val = 2 - 2 * saw;
    }
    return val;
  }
  
}

void setup() {
  SoundMaker soundMaker = new SoundMaker(this, new AudioGenerator());
  
  soundMaker.begin();
}

void draw() {
  
}
