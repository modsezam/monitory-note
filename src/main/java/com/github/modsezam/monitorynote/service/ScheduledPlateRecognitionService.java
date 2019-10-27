package com.github.modsezam.monitorynote.service;

import com.github.modsezam.monitorynote.component.FtpComponent;
import com.github.modsezam.monitorynote.component.ParseToPlateRecognitionComponent;
import com.github.modsezam.monitorynote.model.PlateRecognition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ScheduledPlateRecognitionService {

    @Autowired
    private FtpComponent ftpComponent;

    @Autowired
    private ParseToPlateRecognitionComponent parseToPlateRecognitionComponent;

    @Autowired
    private PlateRecognitionService plateRecognitionService;

    @Scheduled(fixedDelay = 10_000L)
    public void findNewFilesOnFtp(){
        List<FTPFile> ftpFileList = ftpComponent.listOfFiles("/plate_recognition");

        if (!ftpFileList.isEmpty()){
            for (FTPFile ftpFile : ftpFileList) {
                Optional<PlateRecognition> plateRecognitionOptional = parseToPlateRecognitionComponent.parseFile(ftpFile);
                if (plateRecognitionOptional.isPresent()){
                    PlateRecognition plateRecognition = plateRecognitionOptional.get();
                    boolean removeFileResult = ftpComponent.deleteFile("/plate_recognition/", ftpFile.getName());
                    if (removeFileResult){
                        log.info("Delete file {} from ftp.", ftpFile.getName());
                        plateRecognitionService.addPlateNumberToDataBase(plateRecognition);
                        log.info("Add plate number {} to data base", plateRecognition.getPlateNumber());
                    } else {
                        log.error("Fail delete file {} from ftp", ftpFile.getName());
                    }
                } else {
                    log.debug("Check ftp. There are no correct files on the ftp server");
                    return;
                }
            }
        } else {
            log.debug("Check ftp. There are no files on the ftp server.");
        }
    }


}
