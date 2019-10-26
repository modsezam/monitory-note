package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.component.FtpComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/ftp/")
public class FtpController {


    @Autowired
    private FtpComponent ftpComponent;

    @GetMapping("/test")
    public String getIndexPage(Model model, Principal principal) throws IOException {

        List<FTPFile> ftpFiles = ftpComponent.listFiles();
        for (FTPFile ftpFile : ftpFiles) {
            System.out.println(ftpFile.getName() + " " +  ftpFile.getTimestamp().toString());
        }


        return "index";
    }

    @GetMapping("/delete")
    public String getIndexDelete(Model model, Principal principal) throws IOException {

        FTPClient client = new FTPClient();
        client.connect("localhost");
        client.enterLocalPassiveMode();
        client.login("test", "test");

        boolean b = client.deleteFile("test.jpg");
        System.out.println("file is delete " + b);

        client.disconnect();

        return "index";
    }



}
