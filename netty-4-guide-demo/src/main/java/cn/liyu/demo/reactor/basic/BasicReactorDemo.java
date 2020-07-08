package cn.liyu.demo.reactor.basic;

import java.io.IOException;

/**
 * Basic Reactor Demo
 * 
 * @since 1.0.0 2019年11月14日
 * @author liyu
 */
public class BasicReactorDemo {

	public static void main(String[] args) throws IOException {
		new Thread(new Reactor(2333)).start();
	}

}
