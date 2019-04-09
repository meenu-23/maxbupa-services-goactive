package com.maxbupa.apiservices.utilities;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelToJsonExporterMdUtility {

    private static List<List<?>> getExcelData(int sheetNumber, Workbook workbook)
    {
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

    public static List<String> readWorkbookConvertJsonWithOutDeductible(String FILE_NAME)
    {
        List<String> jsonString = new ArrayList<>();
        try {
            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = WorkbookFactory.create(excelFile);
            int sheetsCount = workbook.getNumberOfSheets();
            for(int i=0; i<=sheetsCount-1; i++)
            {
                List<List<?>> data = getExcelData(i,workbook);
                jsonString.add(excelToJsonExportWithoutDeductible(data));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private static String excelToJsonExportWithoutDeductible(List<List<?>> data)
    {
        String json = "";
        String age = "";
        List<String> sumInsured = new ArrayList<>();
        StringBuffer res = new StringBuffer();
        res.append("{");
        for(int i=0; i<=4; i++)
        {
            List<String> productDescription = new ArrayList<>();
            for(int j=0; j<=1; j++)
            {
                productDescription.add(data.get(i).get(j).toString());
            }
            res.append("\""+productDescription.get(0)+"\":\""+productDescription.get(1)+"\"");
            if(i!=4)
            {
                res.append(",");
            }
            else
            {
                res.append(","+"\"price\""+":"+"{");
            }
        }
        String product = data.get(0).get(1).toString();
        String ProductId = data.get(1).get(1).toString();
        String Variant = data.get(2).get(1).toString();
        String FamilyCombination = data.get(3).get(1).toString();
        String Zone = data.get(4).get(1).toString();
        for(int j=1; j<=data.get(5).size()-1; j++)
        {
            sumInsured.add(data.get(5).get(j).toString());
        }
        for(int i=6;i<=data.size()-1;i++)
        {
            List<String> basePremium = new ArrayList<>();
            StringBuffer ageSI = new StringBuffer();

            age = data.get(i).get(0).toString();
            ageSI.append("\""+age+"\":"+"{");

            //getting Base Premium for the age in arrayList
            for(int j=1; j<=data.get(i).size()-1; j++)
            {
                basePremium.add(data.get(i).get(j).toString());
            }
            for(int k=0; k<= sumInsured.size()-1; k++)
            {
                ageSI.append("\""+sumInsured.get(k)+"\":\""+basePremium.get(k)+"\"");
                if(k != sumInsured.size()-1)
                {
                    ageSI.append(",");
                }
                else
                {
                    ageSI.append("}");
                }
            }
            if(i != data.size()-1)
            {
                res.append(ageSI);
                res.append(",");
            }
            else
            {
                res.append(ageSI);
                res.append("}"+"}");
            }
        }
        return res.toString();
    }

    public static List<String> readWorkbookConvertJsonWithDeductible(String FILE_NAME)
    {
        List<String> jsonString = new ArrayList<>();
        try {
            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = WorkbookFactory.create(excelFile);
            int sheetsCount = workbook.getNumberOfSheets();
            for(int i=0; i<=sheetsCount-1; i++)
            {
                List<List<?>> data = getExcelData(i,workbook);
                jsonString.add(excelToJsonExportWithDeductible(data));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private static String excelToJsonExportWithDeductible(List<List<?>> data)
    {
        String age = "";
        int flagCount = 0;
        int count = 0;
        List<String> sumInsured = new ArrayList<>();
        StringBuffer res = new StringBuffer();
        res.append("{");
        for(int i=0; i<=4; i++)
        {
            List<String> productDescription = new ArrayList<>();
            for(int j=0; j<=1; j++)
            {
                productDescription.add(data.get(i).get(j).toString());
            }
            res.append("\""+productDescription.get(0)+"\":\""+productDescription.get(1)+"\"");
            if(i!=4)
            {
                res.append(",");
            }
            else
            {
                res.append(","+"\"price\""+":"+"{");
            }
        }

        //To get the deductible
        List<?> deductibles = data.get(5);
        List<String> strDeductibles = deductibles.stream()
                .map(object -> Objects.toString(object, null))
                .collect(Collectors.toList());
        Set<String> setDeductibles = convertListToSet(strDeductibles, 1, strDeductibles.size() - 1);
        List<List<Integer>> listOfIndexes = new ArrayList<>();
        for(String s : setDeductibles)
        {
            listOfIndexes.add(getIndexOfRepeatedValuesInList(strDeductibles,s));
        }

        //To get Sum Insured Range
        for(int k=0; k <= data.get(6).size()-1; k++)
        {
            sumInsured.add(data.get(6).get(k).toString());
        }

        //getting deductible
        for(String deductibleIndividual : setDeductibles)
        {
            StringBuffer deductiblesJson = new StringBuffer();
            deductiblesJson.append("\""+deductibleIndividual+"\":"+"{");
            res.append(deductiblesJson);

            for(int i=7;i<=data.size()-1;i++)
            {
                //base premium
                List<String> basePremium = new ArrayList<>();
                StringBuffer ageSI = new StringBuffer();

                age = data.get(i).get(0).toString();
                ageSI.append("\""+age+"\":"+"{");

                //getting Base Premium for the age in arrayList
                for(int j=0; j<=data.get(i).size()-1; j++)
                {
                    basePremium.add(data.get(i).get(j).toString());
                }

                //getting repeated index for the deductibles
                for(int k=0; k <= listOfIndexes.get(count).size()-1; k++)
                {
                    ageSI.append("\""+sumInsured.get(listOfIndexes.get(count).get(k))+"\":\""+basePremium.get(listOfIndexes.get(count).get(k))+"\"");
                    if(k != listOfIndexes.get(count).size()-1)
                    {
                        ageSI.append(",");
                    }
                    else
                    {
                        ageSI.append("}");
                    }
                }

                if(i != data.size()-1)
                {
                    res.append(ageSI);
                    res.append(",");
                }
                else
                {
                    res.append(ageSI);
                    res.append("}");
                }
            }

            if(count != setDeductibles.size()-1)
            {
                res.append(",");
            }
            else
            {
                res.append("}"+"}");
            }
            count = count + 1;
        }

        return res.toString();
    }

    public static List<Integer> getIndexOfRepeatedValuesInList(List<String> deductible, String individualdeductible)
    {
        List<Integer> index = new ArrayList<>();
        for(int i=0; i<=deductible.size()-1; i++)
        {
            if(deductible.get(i).toString().equals(individualdeductible))
            {
                index.add(i);
            }
        }
        return index;
    }

    public static Set<String> convertListToSet(List<String> deductibleList, int lowerIndex, int upperIndex)
    {
        List<String> modifiedList = new ArrayList<>();
        for(int i = lowerIndex; i <= upperIndex; i++ )
        {
            modifiedList.add(deductibleList.get(i));
        }
        Set<String> deductibles = new HashSet<String>(modifiedList);
        return deductibles;
    }
}