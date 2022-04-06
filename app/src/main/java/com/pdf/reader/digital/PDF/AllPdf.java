package com.pdf.reader.digital.PDF;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;




import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pdf.reader.digital.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AllPdf extends AppCompatActivity {
    RecyclerView recyclerView;
    PdfAdapter adapter;
    List<File> pdflist;
    Context ctx = AllPdf.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pdf);
        runtimepermission();

    }

    public void runtimepermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                try {
                    DispplayPdf();
                } catch (Exception e) {

                }

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findPdf(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                arrayList.addAll(findPdf(singleFile));
            } else {
                if (singleFile.getName().endsWith(".pdf")) {
                    arrayList.add(singleFile);
                }
            }

        }
        return arrayList;
    }


    public ArrayList<File> Search_Dir(File dir) {
        String pdfPattern = ".pdf";
        ArrayList<File> arrayList = new ArrayList<>();
        File FileList[] = dir.listFiles();

        if (FileList != null) {
            for (int i = 0; i < FileList.length; i++) {

                if (FileList[i].isDirectory()) {
                    Search_Dir(FileList[i]);
                } else {
                    if (FileList[i].getName().endsWith(pdfPattern)) {
                        //here you have that file.
                        arrayList.add(FileList[i]);
                    }
                }
            }
        }
        return arrayList;
    }


    public void DispplayPdf() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        pdflist = new ArrayList<>();
        pdflist.addAll(findPdf(Environment.getExternalStorageDirectory()));
//        pdflist.addAll(Search_Dir(Environment.getExternalStorageDirectory()));
        adapter = new PdfAdapter(pdflist, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }


  /*  @Override
    public void OnPDFSelect(File file) {
        String path = file.getAbsolutePath();
        startActivity(new Intent(ctx, PDFVIEW.class)
                .putExtra("path", path));
    }
    */

}