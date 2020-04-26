package cn.liyu.server;

/**
 * @author lenovo
 */
public class HelloRPCImpl implements HelloRPC {
    @Override
    public String hello(String name) {
        return "hello," + name;
    }
}