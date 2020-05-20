package simple_sound_system.utils;

import org.lwjgl.openal.AL;

import org.lwjgl.openal.ALC;

import simple_sound_system.main.SoundMaker;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

import java.util.function.Supplier;


/*
 * To be honest, I don't know what this code means.
 * I just shamelessly copied it from a tutorial
 * Hopefully nobody will ever read this. Cuz this is a mess.
 */


public class AudioThread extends Thread {
	
	public static final int BUFFER_SIZE = 512;
	static final int BUFFER_COUNT = 8;
	
	private final Supplier<short[]> bufferSupplier;
	
	private final int[] buffers = new int[BUFFER_SIZE];
	private final long device = alcOpenDevice(alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER));
	private final long context = alcCreateContext(device, new int[1]);
	private final int source;
	
	private int bufferIndex;
	
	private boolean closed;
	private boolean running;
	
	public AudioThread(Supplier<short[]> bufferSupplier) {
		this.bufferSupplier = bufferSupplier;
		alcMakeContextCurrent(context);
		AL.createCapabilities(ALC.createCapabilities(device));
		source = alGenSources();
		for(int i = 0; i < BUFFER_COUNT; i ++) {
			BufferSamples(new short[0]);
		}
		CatchInternalException();
		alSourcePlay(source);
		start();
	}
	
	@Override
	public synchronized void run() {
		while(!closed) {
			while(!running) {
				Utils.InvokeProcedure(this::wait, false);
			}
			int processedBufs = alGetSourcei(source, AL_BUFFERS_PROCESSED);
			for(int i = 0; i < processedBufs; i ++) {
				
				short[] samples = bufferSupplier.get();
				if(samples == null) {
					running = false;
					System.out.println("Oh no");
					break;
				}
				
				alDeleteBuffers(alSourceUnqueueBuffers(source));
				buffers[bufferIndex] = alGenBuffers();
				BufferSamples(samples);
			}
			if(alGetSourcei(source, AL_SOURCE_STATE) != AL_PLAYING) {
				alSourcePlay(source);
			}
			CatchInternalException();
		}
		alDeleteSources(source);
		alDeleteBuffers(buffers);
		alcDestroyContext(context);
		alcCloseDevice(device);
	}
	
	private void BufferSamples(short[] samples) {
		int buff = buffers[bufferIndex++];
		alBufferData(buff, AL_FORMAT_MONO16, samples, SoundMaker.SAMPLE_RATE);
		alSourceQueueBuffers(source, buff);
		bufferIndex %= BUFFER_COUNT;
	}
	
	public synchronized void TriggerPlayback() {
		running = true;
		notify();
	}
	
	void Close() {
		closed = true;
		TriggerPlayback();
	}
	
	private void CatchInternalException() {
		int err = alcGetError(device);
		if(err != ALC_NO_ERROR) {
			System.out.println("Error");
		}
	}
	
}
