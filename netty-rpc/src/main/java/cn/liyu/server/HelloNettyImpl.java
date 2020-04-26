package cn.liyu.server;

/**
 * @author lenovo
 */
public class HelloNettyImpl implements HelloNetty {
    @Override
    public String hello() {
        return "hello,netty";
    }
}
