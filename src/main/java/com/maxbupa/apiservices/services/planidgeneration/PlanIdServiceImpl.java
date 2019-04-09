package com.maxbupa.apiservices.services.planidgeneration;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlanIdServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanIdServiceImpl.class);

    public static List<String> readPlanIds(String filePath) {
        List<String> planIdList = null;
        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(excelFile);
            int sheetsCount = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetsCount; i++) {
                List<List<?>> data = getExcelData(i, workbook);
                planIdList = excelToJson(data);
            }
        } catch (InvalidFormatException e) {
            LOGGER.error("InvalidFormatException in PlanIdServiceImpl.readPlanIds()", e);
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException in PlanIdServiceImpl.readPlanIds()", e);
        } catch (IOException e) {
            LOGGER.error("IOException in PlanIdServiceImpl.readPlanIds()", e);
        } catch (Exception e) {
            LOGGER.error("Exception in PlanIdServiceImpl.readPlanIds()", e);
        }
        return planIdList;
    }

    private static List<List<?>> getExcelData(int sheetNumber, Workbook workbook) {
        List<List<?>> excelData = new ArrayList<>();
        Sheet datatypeSheet = workbook.getSheetAt(sheetNumber);
        Iterator<Row> iterator = datatypeSheet.iterator();
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            List cellData = new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell currentCell = cellIterator.next();
                currentCell.setCellType(CellType.STRING);
                cellData.add(currentCell);
            }
            excelData.add(cellData);
        }
        return excelData;
    }

    private static List<String> excelToJson(List<List<?>> data) {
        List<String> headerList = new ArrayList<>();
        List<String> planIdList = new ArrayList<>();

        for (int i = 0; i <= data.get(0).size() - 1; i++) {
            headerList.add(data.get(0).get(i).toString());
        }

        for (int i = 1; i <= data.size() - 1; i++) {
            StringBuilder res = new StringBuilder();
            res.append("{");
            for (int j = 0; j <= headerList.size() - 1; j++) {
                res.append("\"" + headerList.get(j) + "\" :");
                res.append("\"" + data.get(i).get(j) + "\"");
                if (j != headerList.size() - 1) {
                    res.append(",");
                } else {
                    res.append("}");
                }
            }
            planIdList.add(res.toString());
        }
        return planIdList;
    }
}

