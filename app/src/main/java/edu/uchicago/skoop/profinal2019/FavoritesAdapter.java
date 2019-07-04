package edu.uchicago.skoop.profinal2019;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;


// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.RecommendationHolder>{

    class RecommendationHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final ImageView iconImageView;
        private final Button btnInfo;
        private final Button btnWiki;
        private final Button btnYoutube;
        private Recommendation rec;

        private RecommendationHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            btnInfo = itemView.findViewById(R.id.btnInfo);
            btnWiki = itemView.findViewById(R.id.btnHome);
            btnYoutube = itemView.findViewById(R.id.btnYoutube);
            this.rec = null;
        }
    }

    private final LayoutInflater layoutInflater;
    private List<Recommendation> recs;
    private Context context;

    FavoritesAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RecommendationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.row_item, parent, false);
        return new RecommendationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecommendationHolder holder, int position) {
        if (recs != null) {
            Recommendation current = recs.get(position);
            holder.rec = current;
            holder.nameText.setText(current.getName());
            holder.iconImageView.setImageResource(R.drawable.ic_star);

            holder.btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    putExtrasOnIntent(holder);
                }
            });

            holder.btnWiki.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!holder.rec.getWikipedia().equals("null")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.rec.getWikipedia()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(context,R.string.no_wiki, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            });

            holder.btnYoutube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!holder.rec.getYoutube().equals("null")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.rec.getYoutube()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(context,R.string.no_youtube, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            });
        } else {
            holder.nameText.setText(R.string.no_results);
        }
    }

    void setRecs(List<Recommendation> allRecs){
        recs = allRecs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (recs != null)
            return recs.size();
        else return 0;
    }

    public void putExtrasOnIntent(RecommendationHolder holder) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", String.valueOf(holder.rec.getId()));
        intent.putExtra("name", holder.rec.getName());
        intent.putExtra("type", holder.rec.getType());
        intent.putExtra("description", holder.rec.getDescription());
        intent.putExtra("wiki", holder.rec.getWikipedia());
        intent.putExtra("youtube", holder.rec.getYoutube());
        context.startActivity(intent);
    }


}
