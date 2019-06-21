import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class TestingGrounds {
    static void main(String[] args) {
        /**
         * phone numbers are i column 4
         */
        File myFile = new File("/home/philip/Documents/call_list.xlsx")
        goThroughData(myFile)

        /**
         * phone numbers are in column 3
         */
        File myFile1 = new File("/home/philip/Documents/data_list.xlsx")
        goThroughData(myFile1)

        /**
         * @ this point I need to take the 1st record in myFile1 and compare it
         * against every single record within myFile.
         */



        /**
         * phone numbers are in column 3
         */
        File myFile2 = new File("/home/philip/Documents/data_list_2.xlsx")
        goThroughData(myFile2)
    }

    static goThroughData(def myFile){
        FileInputStream fis = new FileInputStream(myFile)

        // Finds the workbook instance for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis)

        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(0)

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator()

        // Traversing over each row of XLSX file
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next()

            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator()
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next()

                switch (cell.getCellType()) {
                    case CellType.STRING:
                        System.out.print(cell.getStringCellValue() + "\t")
                        break
                    case CellType.NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t")
                        break
                    case CellType.BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t")
                        break
                    default :
                        println "Nothing Here..."
                }
            }
            System.out.println("")
        }
    }
}
