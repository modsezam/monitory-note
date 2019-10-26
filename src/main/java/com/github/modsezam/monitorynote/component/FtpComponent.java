package com.github.modsezam.monitorynote.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
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

    public List<FTPFile> listOfFiles(String pathName) {
        List<FTPFile> ftpFileList = new ArrayList<>();
        try {
            connectToFtp(ftpClient);
            FTPFile[] ftpFiles = ftpClient.listFiles(pathName);
            for (FTPFile ftpFile : ftpFiles) {
                if (!ftpFile.isDirectory()){
                    ftpFileList.add(ftpFile);
                }
            }
        } catch (IOException e) {
            log.error("Error - connect to ftp: {}", e.getMessage());
        } finally {
            if (ftpClient != null){
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("Error - disconnect from ftp: {}", e.getMessage());
                }
            }
        }
        return ftpFileList;
    }

    public boolean deleteFile(String pathName, String fileName){
        boolean fileDeleteResult = false;
        try {
            connectToFtp(ftpClient);
            fileDeleteResult = ftpClient.deleteFile(pathName + fileName);
        } catch (IOException e) {
            log.error("Error - connect add delete file from ftp:", e);
        } finally {
            if (ftpClient != null){
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("Error - disconnect from ftp:", e);
                }
            }
        }
        return fileDeleteResult;
    }

    private void connectToFtp(FTPClient client) throws IOException {
        client.connect(ftpServer);
        client.enterLocalPassiveMode();
        client.login(ftpUsername, ftpPassword);
    }
}
