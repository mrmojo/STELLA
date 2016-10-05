package com.stellago.stellago;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static java.net.Proxy.Type.HTTP;

public class OrderChequeBookPage extends AppCompatActivity {
    private SoapTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cheque_book_page);

        Button submitButton = (Button) findViewById(R.id.chequeBookSubmitButton);
        Spinner chqBookTyp = (Spinner)findViewById(R.id.chequeBookTypeSpinner);
        String[] booktyps = {"22", "23", "24"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, booktyps);
        chqBookTyp.setAdapter(adapter);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toConfirmationPage = new Intent(getApplicationContext(), MainPage.class);
                submitSoapForOrderChequeBook();
//                toConfirmationPage.putExtra("branchDetails", (Branch)dropdown.getSelectedItem());
                startActivity(toConfirmationPage);
            }
        });

    }

    private void submitSoapForOrderChequeBook() {
        try {
            EditText AccountIDText = (EditText)findViewById(R.id.accountIDEditText);
            Spinner chqBookTyp = (Spinner)findViewById(R.id.chequeBookTypeSpinner);
            String[] booktyps = {"22", "23", "24"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, booktyps);
            chqBookTyp.setAdapter(adapter);
            EditText noOfLeavesText = (EditText)findViewById(R.id.noOfLeavesEditText);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("soapenv:Envelope");
            doc.appendChild(rootElement);

            Attr soapenv = doc.createAttribute("xmlns:soapenv");
            soapenv.setValue("http://schemas.xmlsoap.org/soap/envelope/");
            rootElement.setAttributeNode(soapenv);

            Attr web = doc.createAttribute("xmlns:web");
            web.setValue("http://webservices.bankfusion.trapedza.com");
            rootElement.setAttributeNode(web);

            Attr att = doc.createAttribute("xmlns:att");
            att.setValue("http://www.misys.com/bankfusion/attributes");
            rootElement.setAttributeNode(att);

            Attr dc = doc.createAttribute("xmlns:dc");
            dc.setValue("http://www.misys.com/cbs/types/dc");
            rootElement.setAttributeNode(dc);

            Attr head = doc.createAttribute("xmlns:head");
            head.setValue("http://www.misys.com/cbs/types/header");
            rootElement.setAttributeNode(head);

            Attr typ = doc.createAttribute("xmlns:typ");
            typ.setValue("http://www.misys.com/cbs/types");
            rootElement.setAttributeNode(typ);

            Element header = doc.createElement("soapenv:Header");
            rootElement.appendChild(header);

            Element bfgenericsoapheader = doc.createElement("web:bfgenericsoapheader");
            header.appendChild(bfgenericsoapheader);

            Element authentication = doc.createElement("web:authentication");
            bfgenericsoapheader.appendChild(authentication);

            Element userName = doc.createElement("web:userName");
            userName.appendChild(doc.createTextNode("retail"));
            authentication.appendChild(userName);

            Element password = doc.createElement("web:password");
            password.appendChild(doc.createTextNode("welcome@99"));
            authentication.appendChild(password);

            Element passCode = doc.createElement("web:passCode");
            authentication.appendChild(passCode);

            Element userLocator = doc.createElement("web:userLocator");
            authentication.appendChild(userLocator);

            Element casRestletUrl = doc.createElement("web:casRestletUrl");
            authentication.appendChild(casRestletUrl);

            Element casValidateUrl = doc.createElement("web:casValidateUrl");
            authentication.appendChild(casValidateUrl);

            Element BFHeader = doc.createElement("web:BFHeader");
            bfgenericsoapheader.appendChild(BFHeader);

            Element correlationID = doc.createElement("att:correlationID");
            BFHeader.appendChild(correlationID);

            Element zone = doc.createElement("att:zone");
            zone.appendChild(doc.createTextNode("?"));
            BFHeader.appendChild(zone);

            Element applicationContext = doc.createElement("att:applicationContext");
            BFHeader.appendChild(applicationContext);

            Element cachedBPMUsers = doc.createElement("att:cachedBPMUsers");
            BFHeader.appendChild(cachedBPMUsers);

            Element extension = doc.createElement("att:extension");
            BFHeader.appendChild(extension);

            Element Body = doc.createElement("soapenv:Body");
            rootElement.appendChild(Body);

            Element CB_IBI_ChequeBookCreate_SRV = doc.createElement("web:CB_IBI_ChequeBookCreate_SRV");
            Body.appendChild(CB_IBI_ChequeBookCreate_SRV);

            Element ChqBookRq = doc.createElement("web:ChqBookRq");
            CB_IBI_ChequeBookCreate_SRV.appendChild(ChqBookRq);

            Element rqHeader = doc.createElement("dc:rqHeader");
            ChqBookRq.appendChild(rqHeader);

            Element version = doc.createElement("head:version");
            rqHeader.appendChild(version);

            Element orig = doc.createElement("head:orig");
            rqHeader.appendChild(orig);

            Element channelId = doc.createElement("head:channelId");
            channelId.appendChild(doc.createTextNode("IBI"));
            orig.appendChild(channelId);

            Element appId = doc.createElement("head:appId");
            orig.appendChild(appId);

            Element appVer = doc.createElement("head:appVer");
            orig.appendChild(appVer);

            Element origLocale = doc.createElement("head:origLocale");
            orig.appendChild(origLocale);

            Element origId = doc.createElement("head:origId");
            orig.appendChild(origId);

            Element origBranchCode = doc.createElement("head:origBranchCode");
            orig.appendChild(origBranchCode);

            Element origCtxtId = doc.createElement("head:origCtxtId");
            orig.appendChild(origCtxtId);

            Element zoneId = doc.createElement("head:zoneId");
            orig.appendChild(zoneId);

            Element offlineMode = doc.createElement("head:offlineMode");
            offlineMode.appendChild(doc.createTextNode("false"));
            orig.appendChild(offlineMode);

            Element messageType = doc.createElement("head:messageType");
            rqHeader.appendChild(messageType);

            Element overrides = doc.createElement("head:overrides");
            rqHeader.appendChild(overrides);

            Element forcePost = doc.createElement("head:forcePost");
            overrides.appendChild(forcePost);

            Element authCodes = doc.createElement("head:authCodes");
            overrides.appendChild(authCodes);

            Element eventCode = doc.createElement("typ:eventCode");
            authCodes.appendChild(eventCode);

            Element supervisorIds = doc.createElement("typ:supervisorIds");
            authCodes.appendChild(supervisorIds);

            Element eventCodes = doc.createElement("head:eventCodes");
            overrides.appendChild(eventCodes);

            Element eventCodes_eventCode = doc.createElement("typ:eventCode");
            eventCodes.appendChild(eventCodes_eventCode);

            Element lastSupervisorId = doc.createElement("head:lastSupervisorId");
            overrides.appendChild(lastSupervisorId);

            Element authRequired = doc.createElement("head:authRequired");
            overrides.appendChild(authRequired);

            Element transReference = doc.createElement("head:transReference");
            rqHeader.appendChild(transReference);

            Element uIdTransReference = doc.createElement("head:uIdTransReference");
            transReference.appendChild(uIdTransReference);

            Element subTransReference = doc.createElement("head:subTransReference");
            transReference.appendChild(subTransReference);

            Element transRepairLoc = doc.createElement("head:transRepairLoc");
            transReference.appendChild(transRepairLoc);

            Element ChqBookReq = doc.createElement("dc:ChqBookReq");
            ChqBookRq.appendChild(ChqBookReq);

            Element CustomerId = doc.createElement("dc:CustomerId");
            CustomerId.appendChild(doc.createTextNode("CUST01"));
            ChqBookReq.appendChild(CustomerId);

            Element AccountID = doc.createElement("dc:AccountID");
            AccountID.appendChild(doc.createTextNode("01CO0CUST0106"));
            ChqBookReq.appendChild(AccountID);

            Element ChequeBookType = doc.createElement("dc:ChequeBookType");
            ChequeBookType.appendChild(doc.createTextNode("22"));
            ChqBookReq.appendChild(ChequeBookType);

            Element NumberOfLeaves = doc.createElement("dc:NumberOfLeaves");
            NumberOfLeaves.appendChild(doc.createTextNode("20"));
            ChqBookReq.appendChild(NumberOfLeaves);

            Element CollectAtBranch = doc.createElement("dc:CollectAtBranch");
            ChqBookReq.appendChild(CollectAtBranch);

            Element BranchCode = doc.createElement("dc:BranchCode");
            ChqBookReq.appendChild(BranchCode);

            String xml = getStringFromDocument(doc);
            Log.d("xml is ",xml);

            mAuthTask = new SoapTask(xml);
            mAuthTask.execute((Void) null);

        } catch (Exception e) {
            Log.d(e.toString(),"errr");
        }

    }

    public static String getStringFromDocument(Document doc) throws TransformerException {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        return writer.toString();
    }

    public class SoapTask extends AsyncTask<Void, Void, Boolean> {

        private final String xml;

        SoapTask(String xml) {
            this.xml = xml;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                URL url = new URL("http://10.240.201.137:9080/bfweb/services/ChequeBookOrder");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "keep-alive");
                conn.setRequestProperty("Content-Type", "text/xml");
                conn.setRequestProperty("SendChunked", "True");
                conn.setRequestProperty("UseCookieContainer",
                        "True");
                conn.setFollowRedirects(false);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(true);
                conn.setReadTimeout(10 * 1000);
                // httpURLConnection.setConnectTimeout(10 * 1000);
                conn.setRequestProperty("SOAPAction", "http://10.240.201.137:9080/bfweb/services/ChequeBookOrder");
                conn.setDoOutput(true);
                conn.connect();
                java.io.OutputStreamWriter wr = new java.io.OutputStreamWriter(conn.getOutputStream());
                wr.write(xml);
                wr.flush();
                // Read the response
                java.io.BufferedReader rd = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) { Log.d("liiines",line); /*jEdit: print(line); */ }
                Thread.sleep(2000);
            } catch (Exception e) {
                Log.d("may nag error", e.toString());
                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
                    mAuthTask = null;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}