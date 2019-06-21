import com.jameskleeh.excel.ExcelBuilder
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class  GetYetMore{
    static void main(String[] args) {
        /**
         * Get list of titles to cycle through to get all data
         */
        def urlList = "https://www.yellowpages.com/tucson-az/small-business"
        Document doc1 = Jsoup.connect(urlList).timeout(0).get()
        Elements content = doc1.getElementsByClass("info")

        ArrayList<String> listOfRecords = new ArrayList<>()
        for(Element data : content){
            Elements name = data.select("a.business-name")
            Elements phone = data.select("div.phones")
            Elements address = data.select("div.street-address")
            Elements cityState = data.select("div.locality")

            String[] records = new String[4]
            records[0] = name.text()
            records[1] = address.text()
            records[2] = cityState.text()
            records[3] = phone.text()

            listOfRecords << records
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        /**
         * insert data into excel file
         */
        XSSFWorkbook workbook = ExcelBuilder.build {
            sheet {
                for(int i=0;i<listOfRecords.size();i++){
                    row {
                        cell(listOfRecords[i][0])
                        cell(listOfRecords[i][1])
                        cell(listOfRecords[i][2])
                        cell(listOfRecords[i][3])
                    }
                }
            }
        }
        workbook.write(new FileOutputStream(new File("/home/philip/Documents/data_list_2.xlsx")))
        println("Mission Completed!")
    }
}
