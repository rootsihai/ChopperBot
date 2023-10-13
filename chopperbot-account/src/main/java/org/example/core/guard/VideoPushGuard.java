package org.example.core.guard;

import org.example.core.exchange.Exchange;
import org.example.mapper.AccountMapper;
import org.example.plugin.GuardPlugin;
import org.example.core.pojo.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author welsir
 * @Date 2023/9/4 22:10
 */
public class VideoPushGuard extends GuardPlugin {

    private Exchange exchange;
    private BlockingQueue<Object> receiveVideo;

    @Resource
    private AccountMapper accountMapper;


    public VideoPushGuard(String module, String pluginName, List<String> needPlugins, boolean isAutoStart) {
        super(module, pluginName, needPlugins, isAutoStart);
    }

    @Override
    public boolean init() {
        //两件事情    一注册队列;二启动队列监听
        exchange = new Exchange();
        List<Account> accountList = accountMapper.selectList(null);
        for (Account account : accountList) {
            List<AccountType> accountTypes = accountMapper.selectTypeByUid(account.getId());
            for (AccountType accountType : accountTypes) {
                exchange.bind(new VideoQueue(PlatformType.getPlatform(account.getPlatformId()) + "-" + account.getId(), account.isCompleteMatch(),account.getCookies()), accountType.getType());
            }
        }
        receiveVideo = new ArrayBlockingQueue<>(1024);
        return true;
    }

    @Override
    public void start() {
        try {
            Object videoMsg = receiveVideo.poll(5, TimeUnit.SECONDS);
            if (videoMsg instanceof Video) {
                Video video = (Video) videoMsg;
                for (VideoType videoType : video.getVideoType()) {
                    exchange.publish(videoType.toString(), video.getMessage());
                }
            }
            exchange.startListening();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVideo(Object msg) {
        receiveVideo.offer(msg);
    }
}