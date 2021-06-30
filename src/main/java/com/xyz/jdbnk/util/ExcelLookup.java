package com.xyz.jdbnk.util;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelLookup {
    private AmazonS3 s3client;

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static List read_excel(String val) throws EncryptedDocumentException, InvalidFormatException, IOException {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials("AKIA444KKQ73E5T4ZXVA", "/b7BO6d8bvtZGJVMxWqcoSTe8KM4wvGdY7YOha7f")))
                .withRegion(Regions.US_EAST_1).build();
        S3Object o = s3.getObject("irdai", "CA List as on 31.05.2021.xls");
        S3ObjectInputStream s3is = o.getObjectContent();
        Workbook workbook = WorkbookFactory.create(s3is);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        System.out.println("\n\nIterating over Rows and Columns using Iterator\n");

        for (int rowNumber = 0; rowNumber < sheet.getLastRowNum(); rowNumber++) {
            Row row = sheet.getRow(rowNumber);

            for (int columnNumber = 0; columnNumber < 1; columnNumber++) {
                Cell cell = row.getCell(columnNumber);
                if (cell.getCellTypeEnum() == CellType.STRING) {
                    if (cell.getStringCellValue().equalsIgnoreCase(val)) {
                        List list = new ArrayList();
                        list.add(row.getCell(0).getStringCellValue());
                        list.add(row.getCell(1).getStringCellValue());
                        list.add(row.getCell(2).getStringCellValue());
                        list.add(row.getCell(3).getStringCellValue());
                        Date d1 = row.getCell(6).getDateCellValue();
                        LocalDate ld1 = convertToLocalDateViaInstant(d1);
                        list.add(ld1);
                        Date d2 = row.getCell(7).getDateCellValue();
                        LocalDate ld2 = convertToLocalDateViaInstant(d2);
                        list.add(ld2);
                        workbook.close();
                        return list;
                    }
                }
            }
        }
        List list = new ArrayList();
        list.add("Invalid");
        return list;
    }
}
