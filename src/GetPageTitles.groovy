import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class GetPageTitles {
    static void main(String[] args) {
        def urlList = "https://business.tucsonchamber.org/list"
        Document doc1 = Jsoup.connect(urlList).timeout(0).get()
        Elements arrayOfNames1 = doc1.getElementsByClass("mn-subcats-col1")

        for(Element url : arrayOfNames1){
            Elements link = url.select("a[href]")
            String unCut = link.toString()
            String cut = unCut.substring(9,unCut.indexOf(">")-1)
            println(cut)
        }
    }
}
