package simple_sound_system.main;

public interface SoundGenerator {
	
	
	/*
	 Returns Sample at a given time(measured in samples)
	*/
	float generateSample(int time);

}
