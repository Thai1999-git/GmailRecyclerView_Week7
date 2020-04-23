package vn.hust.edu.gmailrecyclerview.Adapters;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.hust.edu.gmailrecyclerview.Models.GmailItemModel;
import vn.hust.edu.gmailrecyclerview.R;

public class GmailItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<GmailItemModel> items;
    List<GmailItemModel> displayitems;
    String keyword;

    public GmailItemAdapter(List<GmailItemModel> items) {
        this.items = items;

        displayitems = new ArrayList<>();
        displayitems.addAll(items);
        keyword = "";
    }

    public void showAll(){
        displayitems.clear();
        displayitems.addAll(items);
        notifyDataSetChanged();
    }

    public void search(String keyword){
        this.keyword = keyword;
        displayitems.clear();
        for (GmailItemModel item : items ){
            if (item.getName().contains(keyword) || item.getSubject().contains(keyword) || item.getContent().contains(keyword))
                 displayitems.add(item);
        }
        notifyDataSetChanged();
    }

    public void showFavorite(){
        this.keyword = "";
        displayitems.clear();
        for (GmailItemModel item : items ){
            if (item.isFavorite())
                displayitems.add(item);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_gmail, parent, false);
        return new GmailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GmailViewHolder viewHolder = (GmailViewHolder) holder;
        GmailItemModel item = displayitems.get(position);

        viewHolder.textLetter.setText(item.getName().substring(0, 1));
        Drawable background = viewHolder.textLetter.getBackground();
        background.setColorFilter(new PorterDuffColorFilter(item.getColor(), PorterDuff.Mode.SRC_ATOP));

        if (keyword.length() > 2){
            String name  = item.getName().replace(keyword, "<i>" + keyword + "</i>");
            String subject  = item.getSubject().replace(keyword, "<i>" + keyword + "</i>");
            String content  = item.getContent().replace(keyword, "<i>" + keyword + "</i>");
            viewHolder.textName.setText(Html.fromHtml(name));
            viewHolder.textSubject.setText(Html.fromHtml(subject));
            viewHolder.textContent.setText(Html.fromHtml(content));
        }else {
            viewHolder.textName.setText(item.getName());
            viewHolder.textSubject.setText(item.getSubject());
            viewHolder.textContent.setText(item.getContent());
        }
        viewHolder.textTime.setText(item.getTime());
        if (item.isFavorite())
            viewHolder.imageFavorite.setImageResource(R.drawable.ic_star);
        else
            viewHolder.imageFavorite.setImageResource(R.drawable.ic_star_border);
    }

    @Override
    public int getItemCount() {
        return displayitems.size();
    }

    class GmailViewHolder extends RecyclerView.ViewHolder {
        TextView textLetter;
        TextView textName;
        TextView textSubject;
        TextView textContent;
        TextView textTime;
        ImageView imageFavorite;


        public GmailViewHolder(@NonNull View itemView) {
            super(itemView);

            textLetter = itemView.findViewById(R.id.text_letter);
            textName = itemView.findViewById((R.id.text_name));
            textSubject = itemView.findViewById(R.id.text_subject);
            textContent = itemView.findViewById(R.id.text_content);
            textTime = itemView.findViewById(R.id.text_time);
            imageFavorite = itemView.findViewById(R.id.image_favorite);

            imageFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isFavorite = displayitems.get(getAdapterPosition()).isFavorite();
                    displayitems.get(getAdapterPosition()).setFavorite(!isFavorite);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
