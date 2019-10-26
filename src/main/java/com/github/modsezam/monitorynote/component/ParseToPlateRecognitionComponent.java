package com.github.modsezam.monitorynote.component;

import com.github.modsezam.monitorynote.model.PlateRecognition;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

@Component
public class ParseToPlateRecognitionComponent {

    public Optional<PlateRecognition> parseFile(FTPFile ftpFile) {
        if (ftpFile != null){
            LocalDateTime fileCreationDateTime = toLocalDateTime(ftpFile.getTimestamp());
            Optional<String> optionalPlateNumber = parseFileToPlateNumber(ftpFile.getName());
            if (optionalPlateNumber.isPresent()){
                String plateNumber = optionalPlateNumber.get();
                return Optional.of(new PlateRecognition(ftpFile.getName(), plateNumber, fileCreationDateTime));
            }
        }
        return Optional.empty();
    }

    private Optional<String> parseFileToPlateNumber(String ftpFileName) {

        String[] splitFileName = ftpFileName.split("_|\\.");
        if (splitFileName.length == 4){
            return Optional.of(splitFileName[2]);
        }
        return Optional.empty();
    }


    private static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }



}
