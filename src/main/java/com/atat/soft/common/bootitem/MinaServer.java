package com.atat.soft.common.bootitem;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2017/8/8.
 */
public class MinaServer {

//    private Logger logger = LoggerFactory.getLogger(getClass());

//    private static int PORT = 3005;

    public void execute(int PORT){
        IoAcceptor acceptor = null;
        try {
            // 创建一个非阻塞的server端的Socket
            acceptor = new NioSocketAcceptor();

            /*
            // 设置日志过滤器
			acceptor.getFilterChain().addLast("logger",new LoggingFilter());
            */

            // 设置读取数据的缓冲区大小
            acceptor.getSessionConfig().setReadBufferSize(1024);

            // 读写通道10秒内无操作进入空闲状态
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 8888);

            // 绑定逻辑处理器
            acceptor.setHandler(new MinaServerHandler());

            // 绑定端口
            acceptor.bind(new InetSocketAddress(PORT));

            System.out.println("服务端启动成功...     端口号为：" + PORT);
        } catch (Exception e) {
            System.out.println("服务端启动异常...."+e);
            e.printStackTrace();
        }
    }
}
