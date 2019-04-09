package com.maxbupa.apiservices.utilities;


import com.maxbupa.apiservices.model.hospital.HospitalDTO;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public final class CSVConverter {

    public static List<HospitalDTO> processCsvToBean(String filePath) {
        List<HospitalDTO> hospitalList = null;
        try {
            hospitalList = new CsvToBeanBuilder(new FileReader(filePath)).withType(HospitalDTO.class).build().parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return hospitalList;
    }
}