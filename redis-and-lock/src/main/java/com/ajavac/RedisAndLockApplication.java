package com.ajavac;

import com.ajavac.util.RedisLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.locks.Lock;

@SpringBootApplication
public class RedisAndLockApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = SpringApplication.run(RedisAndLockApplication.class, args);
		StringRedisTemplate stringRedisTemplate = context.getBean(StringRedisTemplate.class);

		RedisLock lock = new RedisLock(stringRedisTemplate, "test_002");
		// Lock lock = new ReentrantLock();
		final Printer outer = new Printer(lock);
		Thread t1 = new Thread(() -> outer.output("One : I am first String !!"));
		Thread t2 = new Thread(() -> outer.output("Two : I am second String !!"));
		Thread t3 = new Thread(() -> outer.output("Three : I am third String !!"));
		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();
		System.exit(0);
	}


	static class Printer {
		private Lock lock;
		public Printer(Lock lock) {
			this.lock = lock;
		}

		public void output(String name) {

			lock.lock();
			for (int i = 0; i < name.length(); i++) {
				System.out.print(name.charAt(i));
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println();
			lock.unlock();
		}
	}
}
