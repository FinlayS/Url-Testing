package webdrivertest;
//import com.javafortesters.xmlgenrationclasses.generate_or03.GenerateOr03XML;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestAllUrls extends WebDriverBase {


@Test
public void main() throws IOException, ElementNotVisibleException {


    /***************************************
     * Create a directory for the run output.
     ****************************************/
    boolean saveTheLogs = true;

    //if (saveTheLogs) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_M_yyyy_HH_mm");
        String date = sdf.format(new Date());
        String folder = ("Run_" + date);
        File theDir = new File(System.getProperty("user.dir")
                + "/Testing/Results/" +folder);

        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir);
            boolean result = false;

            try {
                theDir.mkdirs();
            } catch (SecurityException se) {
                //handle it
            }
        }
        /***************************************
         * Create the log file
         ****************************************/
        File aTempFile = new File(theDir + File.separator + "TestRunLog" + date + ".csv");
        aTempFile.createNewFile();
        assertThat(aTempFile.isFile(), is(true));
        FileWriter writer = new FileWriter(aTempFile);
        BufferedWriter buffer = new BufferedWriter(writer);
        PrintWriter print = new PrintWriter(buffer);

        print.println("Page ,Site  ,Url ,Message   ");
        print.close();

try {
    File inputfile = new File(System.getProperty("user.dir")
            + "/UrlList.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory
            .newInstance();
    DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
    Document doc = docbuilder.parse(inputfile);
    doc.getDocumentElement().normalize();
    NodeList nodelist = doc.getElementsByTagName("UrlTestData");

    for (int tmp = 0; tmp < nodelist.getLength(); tmp++) { //Start of node list for
        Node nNode = nodelist.item(tmp);

        readIndividualStoreLoop:
        if (nNode.getNodeType() == Node.ELEMENT_NODE) { //Start of node list if
            Element eElement = (Element) nNode;
            String inputTest = eElement.getAttribute("iTestID");

            // Enter and individual store code here otherwise run for all by TagName (NodeList above)
            String testToCompare = new String("");

            if (!testToCompare.isEmpty()) {
                if (!inputTest.equals(testToCompare)) {
                    break readIndividualStoreLoop;
                }
            }

            // Get Strings from XML input.
            String isUrlID = eElement.getElementsByTagName("iUrlID")
                    .item(0).getTextContent();
            String isBaseUrl = eElement.getElementsByTagName("ibaseurl")
                    .item(0).getTextContent();
            String isCategory = eElement.getElementsByTagName("iCategory")
                    .item(0).getTextContent();

            // Tidy and wait
            driver.manage().deleteAllCookies();
            WebDriverWait wait = new WebDriverWait(driver, 25);

            // Open the home page & add the category to build the url.
            String searchUrl = (isBaseUrl + isCategory);
            driver.get(searchUrl);

            // Print to the log for each item i
            writer = new FileWriter(aTempFile, true);
            buffer = new BufferedWriter(writer);
            print = new PrintWriter(buffer);

            print.println(isUrlID + "," + isBaseUrl + "," + isCategory + "," + "Url Found?" + "," );

            print.close();

            // Print a screenshot of the final confirmation page.
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(theDir + "/" + isUrlID + ".png"));

        } //End of node list (if)

    } //End of node list (for)
    driver.close();

} catch (Exception e) {
    System.out.print("General error has happened, test stopped unexpectedly");
        e.getMessage();
        e.printStackTrace();
    }


} // End of main IOException

} //End of class