import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import com.jameskleeh.excel.ExcelBuilder
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class GetList {
    static void main(String[] args) {

        /**
         * Get list of titles to cycle through to get all data
         */
        def urlList = "https://business.tucsonchamber.org/list"
        Document doc1 = Jsoup.connect(urlList).timeout(0).get()
        Elements arrayOfNames1 = doc1.getElementsByClass("mn-subcats-col1")
        Elements arrayOfNames2 = doc1.getElementsByClass("mn-subcats-col2")

        String cut
        ArrayList<String> listOfURIs = new ArrayList<>()
        for(Element url : arrayOfNames1){
            Elements link = url.select("a[href]")
            String unCut = link.toString()
            cut = unCut.substring(9,unCut.indexOf(">")-1)
            listOfURIs << cut
        }

        for(Element url : arrayOfNames2){
            Elements link = url.select("a[href]")
            String unCut = link.toString()
            cut = unCut.substring(9,unCut.indexOf(">")-1)
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
            Elements content = doc.getElementsByClass("mn-listingcontent")

            for(Element data : content){
                Elements name = data.select("div.mn-title")
                Elements address = data.select("div.mn-address1")
                Elements cityState = data.select("div.mn-citystatezip")
                Elements phone = data.select("li.mn-phone")


                String[] records = new String[4]
                records[0] = name.text()
                records[1] = address.text()
                records[2] = cityState.text()
                records[3] = phone.text()

                listOfRecords << records
            }
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        }//end for loop:listOfURIs
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
        workbook.write(new FileOutputStream(new File("/home/philip/Documents/call_list.xlsx")))
        println("Mission Completed!")
    }
}
