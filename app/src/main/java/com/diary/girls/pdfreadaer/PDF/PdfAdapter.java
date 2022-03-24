package com.diary.girls.pdfreadaer.PDF;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diary.girls.pdfreadaer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfHolder> {
    List<File> pdfFile;
    Context context;


    public PdfAdapter(List<File> pdfFile, Context context) {
        this.pdfFile = pdfFile;
        this.context = context;

    }

    @NonNull
    @Override
    public PdfHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pdf_item_layout, parent, false);
        return new PdfHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfHolder holder, int position) {
        holder.setdat(position);
    }

    @Override
    public int getItemCount() {
        return pdfFile.size();
    }

    public class PdfHolder extends RecyclerView.ViewHolder {
        TextView pdftitle;

        public PdfHolder(@NonNull View itemView) {
            super(itemView);
            pdftitle = itemView.findViewById(R.id.pdfTextTitle);
        }

        public void setdat(int position) {
            pdftitle.setText(pdfFile.get(position).getName());
            pdftitle.setSelected(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PDFVIEW.class);
                    intent.putExtra("path", pdfFile.get(position).getAbsolutePath());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
//                    lintener.OnPDFSelect(pdfFile.get(position));
                }
            });
        }
    }

}
