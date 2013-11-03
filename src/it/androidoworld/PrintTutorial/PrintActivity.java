package it.androidoworld.PrintTutorial;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v4.print.PrintHelper;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class PrintActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    Button btnPrintImage, btnPrintHtml;
    WebView customCode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnPrintImage = (Button) findViewById(R.id.btnPrintImage);
        btnPrintImage.setOnClickListener(this);

        btnPrintHtml = (Button) findViewById(R.id.btnPrintHtml);
        btnPrintHtml.setOnClickListener(this);

        customCode = (WebView) findViewById(R.id.wvCustomCode);

        String customHTML = "DevCorner\n" +
                "\tPagina di test\n" +
                "\n" +
                "\n" +
                "\t==DevCorner - 5 novembre 2013==";
        customCode.loadDataWithBaseURL(null,customHTML,"text/HTML","UTF-8",null);

    }

    @Override
    public void onClick(View view) {
        if(PrintHelper.systemSupportsPrint()){
            switch(view.getId()){
                case R.id.btnPrintHtml:
                    printHTML();
                    break;
                case R.id.btnPrintImage:
                    printImage();
                    break;
            }
        }else
            Toast.makeText(this,"Questo dispositivo non supporta la stampa",Toast.LENGTH_SHORT).show();
    }

    private void printImage(){
        PrintHelper helper = new PrintHelper(this);
        helper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.android_robot);
        helper.printBitmap("DevCorner - stampiamo con la nostra app",image);
    }

    private void printHTML(){
        PrintManager manager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter adapter = customCode.createPrintDocumentAdapter();
        String printJobName = getString(R.string.app_name) + " - DevCorner test";
        PrintJob job = manager.print(printJobName,adapter,new PrintAttributes.Builder().build());
    }
}
