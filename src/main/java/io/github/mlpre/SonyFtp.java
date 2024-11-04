package io.github.mlpre;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class SonyFtp {

    public static void main(String[] args) throws Exception {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.OFF);
        FtpServerFactory ftpServerFactory = new FtpServerFactory();
        BaseUser user = new BaseUser();
        user.setName("sony");
        user.setPassword("sony");
        user.setAuthorities(Collections.singletonList(new WritePermission()));
        String userHome = System.getProperty("user.home");
        Path sonyPath = Paths.get(userHome, "Pictures", "Sony");
        if (!sonyPath.toFile().exists()) {
            sonyPath.toFile().mkdirs();
        }
        user.setHomeDirectory(sonyPath.toString());
        ftpServerFactory.getUserManager().save(user);
        FtpServer ftpServer = ftpServerFactory.createServer();
        ftpServer.start();
        InetAddress inetAddress = InetAddress.getLocalHost();
        String address = inetAddress.getHostAddress();
        System.out.println("Ftp Server: ftp://" + address);
        System.out.println("Name: " + user.getName());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Directory: " + user.getHomeDirectory());
    }

}
