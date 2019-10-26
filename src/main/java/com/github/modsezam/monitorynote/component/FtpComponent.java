package com.github.modsezam.monitorynote.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class FtpComponent {

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${ftp.server}")
    private String ftpServer;

    @Autowired
    private FTPClient ftpClient;

    public List<FTPFile> listFiles() {
        FTPFile[] ftpFiles = new FTPFile[0];
        try {
            connectToFtp(ftpClient);
            ftpFiles = ftpClient.listFiles("/");
            ftpClient.disconnect();
        } catch (IOException e) {
            log.error("Error - connect to ftp: {}", e.getMessage());
            e.printStackTrace();
        }
        return Arrays.asList(ftpFiles);
    }

    public boolean removeFile(String path){
        boolean fileDeleteResult = false;
        try {
            connectToFtp(ftpClient);
            fileDeleteResult = ftpClient.deleteFile("test.jpg");
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileDeleteResult;
    }

    private void connectToFtp(FTPClient client) throws IOException {
        client.connect(ftpServer);
        client.enterLocalPassiveMode();
        client.login(ftpUsername, ftpPassword);
    }
}
