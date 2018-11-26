package servidor.queue;

import java.util.concurrent.Semaphore;

import servidor.Finger;
import servidor.FrameServer;
import servidor.dataBase.Data;

public class Queue {
	protected QueueCommand queue;
	protected Data data;
	protected Finger finger;
	protected Semaphore mutex_f1;
	protected Semaphore mutex;
	protected FrameServer frame;

	public Queue(QueueCommand queue) {
		this.queue = queue;
	}

	public Queue(QueueCommand queue, Data data, FrameServer frame) {
		this.queue = queue;
		this.data = data;
		this.frame = frame;
	}

	public Queue(QueueCommand queue, Finger finger, FrameServer frame) {
		this.queue = queue;
		this.finger = finger;
		this.frame = frame;
	}

	public Queue(QueueCommand queue, Data data, Finger finger, Semaphore mutex_f1, Semaphore mutex, FrameServer frame) {
		this.data = data;
		this.queue = queue;
		this.finger = finger;
		this.mutex_f1 = mutex_f1;
		this.mutex = mutex;
		this.frame = frame;
	}

	public void run() throws InterruptedException {
		Thread f1 = new Thread(new QueueF1(queue));
		Thread f2 = new Thread(new QueueF2(queue, finger, frame));
		Thread f3 = new Thread(new QueueF3(queue, data, frame));
		Thread f4 = new Thread(new QueueF4(queue, finger, frame));
		f1.start();
		f2.start();
		f3.start();
		f4.start();
	}
}
