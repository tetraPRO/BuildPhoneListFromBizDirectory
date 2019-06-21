import com.jameskleeh.excel.ExcelBuilder
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class GetMoreData {
    static void main(String[] args) {
        /**
         * Get list of titles to cycle through to get all data
         */
        def urlList = "https://www.tucsonalist.com/"
        Document doc1 = Jsoup.connect(urlList).timeout(0).get()
        Elements arrayOfNames = doc1.getElementsByClass("sabai-directory-category")

        String cut
        ArrayList<String> listOfURIs = new ArrayList<>()
        for(Element url : arrayOfNames){
            Elements link = url.select("a[href]")
            String unCut = link.toString()
            cut = unCut.substring(9,unCut.indexOf("class")-2)

            listOfURIs << cut
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        /**
         * Cycle through each list
         */
        ArrayList<String[]> listOfRecords = new ArrayList<>()
        for(int j=0;j<listOfURIs.size();j++){
            def url = listOfURIs[j]
            Document doc = Jsoup.connect(url).timeout(0).get()
            Elements content = doc.getElementsByClass("sabai-directory-info")

            for(Element data : content){
                Elements address = data.select("div.sabai-directory-location")
                Elements phone = data.select("div.sabai-directory-contact-tel").select("span.sabai-hidden-xs")
                Elements email = data.select("div.sabai-directory-contact-email")
                Elements website = data.select("div.sabai-directory-contact-website")

                String[] records = new String[4]
                records[0] = address.text()
                records[1] = phone.text()
                records[2] = email.text()
                records[3] = website.text()

                listOfRecords << records
            }
        }//end for loop:listOfURIs
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        /**
         * insert data into excel file
         */
        XSSFWorkbook workbook = ExcelBuilder.build {
            sheet {
                for(int i=0;i<listOfRecords.size();i++){
                    row {
                        cell(listOfRecords[i][3])
                        cell(listOfRecords[i][0])
                        cell(listOfRecords[i][1])
                        cell(listOfRecords[i][2])
                    }
                }
            }
        }
        workbook.write(new FileOutputStream(new File("/home/philip/Documents/data_list.xlsx")))
        println("Mission Completed!")
    }
}
