package com.totem.avisame.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.totem.avisame.R;
import com.totem.avisame.activities.MainActivity;
import com.totem.avisame.models.Message;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<Message> mAlerts;
    private Context mContext;

    public MessagesAdapter(Context _context, List<Message> messages) {
        this.mContext = _context;
        this.mAlerts = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cell_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Message temp = mAlerts.get(position);

        holder.mDate.setText(temp.getDate());
        holder.mMessage.setText(temp.getMessage());
        holder.mLinkToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (temp.getLatitude() != null && temp.getLongitude() != null) {
                    String location = "geo:" + temp.getLatitude() + "," + temp.getLongitude();
                    Uri gmmIntentUri = Uri.parse(location);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    ((MainActivity)mContext).onLocationShow(mapIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAlerts.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mMessage;
        private TextView mLinkToMap;
        private TextView mDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mDate = (TextView) itemView.findViewById(R.id.message_date);
            mMessage = (TextView) itemView.findViewById(R.id.message);
            mLinkToMap = (TextView) itemView.findViewById(R.id.link_map);
        }
    }

    public interface MessageActions{

        void onLocationShow(Intent mapIntent);
    }
}
