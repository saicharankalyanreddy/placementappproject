package com.example.ossd_project;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class pdfview extends AppCompatActivity {

    PDFView pdfView;

    String pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        pdfView = findViewById(R.id.pdf);

        pp= getIntent().getStringExtra("pdflink");

        LOADURL loadurl = new LOADURL(pdfview.this);
        loadurl.execute(pp);




    }

    public class LOADURL extends AsyncTask<String,Void,InputStream>
    {
        private ProgressDialog progressDialog;
        public LOADURL(pdfview loadPdf) {

            progressDialog = new ProgressDialog(loadPdf);

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Fetching PDF from server...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try
            {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)
                        url.openConnection();
                if (urlConnection.getResponseCode()==200)
                {

                    inputStream = new
                            BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (IOException e) {

                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {


            pdfView.fromStream(inputStream)
                    .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    // allows to draw something on the current page, usually visible in the middle of the screen
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    // mode to fit pages in the view// fit each page to the view, else smaller pages are scaled relative to largest page.
                    // toggle night mode
                    .load();
            progressDialog.dismiss();
        }
}


    }
