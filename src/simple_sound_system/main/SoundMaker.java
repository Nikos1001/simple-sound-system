package simple_sound_system.main;


import java.util.function.Supplier;

import processing.core.*;
import simple_sound_system.utils.AudioThread;


public class SoundMaker {
	
	public static int SAMPLE_RATE = 44100;
	SoundGenerator generator;
	int time;
	
	PApplet applet;
	
	public SoundMaker(PApplet app, SoundGenerator generator) {
		this.generator = generator;
		applet = app;
	}
	
	/*
	 Starts the Audio Thread
	*/
	public void begin() {
		AudioThread thread = new AudioThread(
				()->{
					short[] s = new short[AudioThread.BUFFER_SIZE];
					for(int i = 0; i < s.length; i ++) {
						float val = generator.generateSample(i + time);
						val = Math.max(-1, val);
						val = Math.min(1, val);
						s[i] = (short)(Short.MAX_VALUE * val);
					}
					time += AudioThread.BUFFER_SIZE;
					return s;
				}
		);
		thread.TriggerPlayback();
	}
}

